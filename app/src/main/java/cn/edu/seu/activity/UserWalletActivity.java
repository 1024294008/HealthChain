package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xujiaji.happybubble.Auto;
import com.xujiaji.happybubble.BubbleDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.adapter.TransferListAdapter;
import cn.edu.seu.common.CoinTransManager;
import cn.edu.seu.fragment.HealthFragment;
import cn.edu.seu.http.RequestAction.UserBalanceRequest;
import cn.edu.seu.http.RequestAction.UserTransactionRecordRequest;

public class UserWalletActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView back;
    private ImageView detail;
    private ImageView clipAddress;
    private TextView transant;
    private TextView account;
    private TextView balance;
    private TextView address;
    private BubbleDialog detailDialog;
    private View dialogView;
    private ListView transactionRecordListView;
    private List<Map<String, String>>  transactionRecordList;
    private TransferListAdapter transferListAdapter;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);
        initView();
    }

    private void initView(){

        sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);

        //设置系统状态栏UI
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);

        back = (ImageView) findViewById(R.id.back);
        detail = findViewById(R.id.detail);
        transant = findViewById(R.id.transant);
        account = findViewById(R.id.account);
        balance = (TextView) findViewById(R.id.balance);
        transactionRecordList = new ArrayList<>();
        transactionRecordListView = findViewById(R.id.transactionRecordListView);
        transferListAdapter = new TransferListAdapter(this, transactionRecordList, R.layout.activity_transaction_record_list_item);
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_ethaddr_detail, null);
        address = dialogView.findViewById(R.id.address);
        clipAddress = dialogView.findViewById(R.id.clipAddress);

        detailDialog = new BubbleDialog(this)
                .addContentView(dialogView)
                .autoPosition(Auto.UP_AND_DOWN)
                .calBar(true)
                .softShowUp();

        back.setOnClickListener(this);
        detail.setOnClickListener(this);
        clipAddress.setOnClickListener(this);
        transant.setOnClickListener(this);
        address.setText(sharedPreferences.getString("ethAddress", ""));
        transactionRecordListView.setAdapter(transferListAdapter);
        transactionRecordListView.setOnItemClickListener(this);

        account.setText(sharedPreferences.getString("username", ""));
        getBalance();
        getTransactionRecord();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
            case R.id.transant:
                Intent intent = new Intent(UserWalletActivity.this, UserTransferActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.detail:
                detailDialog.setClickedView(detail).show();
                break;
            case R.id.clipAddress:
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("ethAddress", address.getText().toString()));
                Toast.makeText(this, "以太坊地址已复制到剪切板", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UserWalletActivity.this, TransactionRecordDetailActivity.class);
        intent.putExtra("id", transactionRecordList.get(position).get("id"));
        intent.putExtra("type", transactionRecordList.get(position).get("type"));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getBalance();
        getTransactionRecord();
    }

    // 获取余额
    private void getBalance(){
        // 获取余额
        // 转账方token：sharedPreferences.getString("token", "")
        // 设置balance.setText()

        String token = sharedPreferences.getString("token", "");
        Map<String, String> params = new HashMap<>();
        params.put("token", token);

        Handler handler = new UserBalanceHadnler(UserWalletActivity.this);
        UserBalanceRequest request = new UserBalanceRequest(UserWalletActivity.this, handler);

        request.doPost(params);
    }

    // 获取转账记录
    private void getTransactionRecord(){
        transactionRecordList.clear();

        String token = sharedPreferences.getString("token", "");
        String ethAddress = sharedPreferences.getString("ethAddress", "");

        // 测试数据(需要用循环)

        Map<String, String>params = new HashMap<>();
        params.put("token", token);
        params.put("ethAddress", ethAddress);

        Handler handler = new UserTransactionRecordHadnler(UserWalletActivity.this);
        UserTransactionRecordRequest request = new UserTransactionRecordRequest(UserWalletActivity.this, handler);

        request.doPost(params);


        // 以太坊地址ethAddress：sharedPreferences.getString("ethAddress", "")
    }

    /**
     * 用户钱包  -- 余额
     */
    public class UserBalanceHadnler extends Handler {

        private Context context;

        public UserBalanceHadnler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            String bal = response.getJSONObject("_data").getString("balance");
                            balance.setText("$ " + CoinTransManager.transToCoin(bal));
                        }
                        else{
                            Toast.makeText(context, "余额加载失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "余额加载失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }



    /**
     * 用户钱包  -- 交易记录
     */
    public class UserTransactionRecordHadnler extends Handler {

        private Context context;

        public UserTransactionRecordHadnler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容
                    String ethAddress = sharedPreferences.getString("ethAddress", ""); // 用于以太坊地址
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            JSONArray _data = response.getJSONArray("_data");

                            for(Integer i= _data.length() - 1; i >= 0; i--){
                                Map<String, String> item = new HashMap<>();
                                item.put("id", _data.getJSONObject(i).getString("id")); // 交易记录id
                                String sendAddress = _data.getJSONObject(i).getString("sendAddress"); // 发送方地址
                                String recieveAddress = _data.getJSONObject(i).getString("recieveAddress"); // 接受方地址

                                if(ethAddress.equals(sendAddress))
                                    item.put("type", "0"); // 交易类型(type为0表示支出。为1表示收入);
                                else if(ethAddress.equals(recieveAddress))
                                    item.put("type", "1"); // 交易类型(type为0表示支出。为1表示收入)

                                item.put("amount", CoinTransManager.transToCoin(_data.getJSONObject(i).getString("transactEth")));  // 交易金额
                                transactionRecordList.add(item);

                            }
                            transferListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "交易记录加载失败..", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "交易记录加载失败！！", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }
}

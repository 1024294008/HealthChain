package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.adapter.TransferListAdapter;
import cn.edu.seu.http.RequestAction.UserBalanceRequest;
import cn.edu.seu.http.RequestAction.UserTransactionRecordRequest;

public class UserWalletActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView back;
    private Button transant;
    private TextView balance;
    private TextView address;
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.standardBackground, null));
        }

        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);

        back = (ImageView) findViewById(R.id.back);
        transant = (Button) findViewById(R.id.transant);
        balance = (TextView) findViewById(R.id.balance);
        address = (TextView) findViewById(R.id.address);
        transactionRecordList = new ArrayList<>();
        transactionRecordListView = findViewById(R.id.transactionRecordListView);
        transferListAdapter = new TransferListAdapter(this, transactionRecordList, R.layout.activity_transaction_record_list_item);

        back.setOnClickListener(this);
        transant.setOnClickListener(this);
        address.setText(sharedPreferences.getString("ethAddress", ""));
        transactionRecordListView.setAdapter(transferListAdapter);
        transactionRecordListView.setOnItemClickListener(this);

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
                startActivity(intent);
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
                            balance.setText(bal);
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

                            for(Integer i=0; i<_data.length(); i++){
                                Map<String, String> item = new HashMap<>();
                                item.put("id", _data.getJSONObject(i).getString("id")); // 交易记录id
                                String sendAddress = _data.getJSONObject(i).getString("sendAddress"); // 发送方地址
                                String recieveAddress = _data.getJSONObject(i).getString("recieveAddress"); // 接受放地址

                                if(ethAddress.equals(sendAddress))
                                    item.put("type", "0"); // 交易类型(type为0表示支出。为1表示收入);
                                else if(ethAddress.equals(recieveAddress))
                                    item.put("type", "1"); // 交易类型(type为0表示支出。为1表示收入)

                                item.put("amount", _data.getJSONObject(i).getString("transactEth"));  // 交易金额
                                transactionRecordList.add(item);

                            }
                            transferListAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "交易记录加载失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "交易记录加载失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }
}

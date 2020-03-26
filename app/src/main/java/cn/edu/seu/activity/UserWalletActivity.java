package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.http.RequestAction.UserWalletRequest;

public class UserWalletActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private Button transant;
    private TextView balance;
    private TextView address;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);

        initView();

        getWalletData();
    }

    private void initView(){

        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);

        back = (ImageView) findViewById(R.id.back);
        transant = (Button) findViewById(R.id.transant);
        balance = (TextView) findViewById(R.id.balance);
        address = (TextView) findViewById(R.id.address);

        back.setOnClickListener(this);
        transant.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
            case R.id.transant:

                //保存用户的余额
                SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                editor.putString("userbalance", balance.getText().toString());    //key-value
                editor.commit();                                                  //提交修改

                Intent intent = new Intent(UserWalletActivity.this, UserTransferActivity.class);
                startActivity(intent);
                break;
        }
    }

    // 加载钱包数据
    public void getWalletData(){
        Handler handler = new UserWalletHadnler(UserWalletActivity.this);
        UserWalletRequest request = new UserWalletRequest(UserWalletActivity.this, handler);
        String id = sharedPreferences.getString("id", "");
        request.doGet(Integer.valueOf(id));
    }

    /**
     * 用户钱包
     */
    public class UserWalletHadnler extends Handler {

        private Context context;

        public UserWalletHadnler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容
                    Toast t = Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT);
                    t.show();

                    // {"ethAddress":"以太坊地址", "balance":"余额", "transactionRecordList":[{"id":"交易id", "transactTime":"交易时间", "transactEth": "交易金额", "transactAddr":"交易在区块链上的地址"}, ...]}
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String ethAddress = response.getString("ethAddress");
                        String balance = response.getString("balance");
                        JSONArray transactionRecordList = response.getJSONArray("transactionRecordList");


                        // -- 交易记录数据 ---------
                        List<Map<String, String>> dataList = new ArrayList<>();
                        for(Integer i=0; i< transactionRecordList.length(); i++){
                            Map<String, String> map = new HashMap<>();
                            map.put("id", transactionRecordList.getJSONObject(i).getString("id"));
                            map.put("transactTime", transactionRecordList.getJSONObject(i).getString("transactTime"));
                            map.put("transactEth", transactionRecordList.getJSONObject(i).getString("transactEth"));
                            map.put("transactAddr", transactionRecordList.getJSONObject(i).getString("transactAddr"));
                            dataList.add(map);
                        }
                        // ------------------------


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }
}

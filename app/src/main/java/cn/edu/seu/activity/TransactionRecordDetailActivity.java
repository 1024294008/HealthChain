package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.common.CoinTransManager;
import cn.edu.seu.http.RequestAction.UserTransactionRecordDetailRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransactionRecordDetailActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private ImageView back;
    private TextView transactType;
    private TextView account;
    private TextView ethAddress;
    private TextView transactEth;
    private TextView transactTime;
    private TextView transactRemarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_record_detail);
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

        back = findViewById(R.id.back);
        transactType = findViewById(R.id.transactType);
        account = findViewById(R.id.account);
        ethAddress = findViewById(R.id.ethAddress);
        transactEth = findViewById(R.id.transactEth);
        transactTime = findViewById(R.id.transactTime);
        transactRemarks = findViewById(R.id.transactRemarks);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionRecordDetailActivity.this.finish();
            }
        });

        setContent();
    }

    // 设置数据
    private void setContent(){
        String transactionId = getIntent().getStringExtra("id");
        if(getIntent().getStringExtra("type").equals("0")){
            transactType.setText("健康币支出");
        } else {
            transactType.setText("健康币转入");
        }

        Handler handler = new UserTransactionRecordDetailHandler(TransactionRecordDetailActivity.this);
        UserTransactionRecordDetailRequest request = new UserTransactionRecordDetailRequest(TransactionRecordDetailActivity.this, handler);

        String token = sharedPreferences.getString("token", "");

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("ethAddress", sharedPreferences.getString("ethAddress", ""));
        params.put("id", transactionId);  // 转账记录的id

        request.doPost(params);

    }


    /**
     * 购买以太币
     */
    public class UserTransactionRecordDetailHandler extends Handler {

        private Context context;

        public UserTransactionRecordDetailHandler(Context context) {
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
                            JSONObject _data = response.getJSONObject("_data");
                            // 根据transactionId和token获取该条转账信息，需要设置一下信息
                            account.setText(_data.getString("account"));  //对方账户
                            ethAddress.setText(_data.getString("oppositeAddress"));  //对方账户的以太坊地址
                            transactEth.setText("$ " + CoinTransManager.transToCoin(_data.getString("transactEth")));   //转账金额
                            transactTime.setText(_data.getString("transactTime"));   //转账时间
                            transactRemarks.setText(_data.getString("transactRemarks"));  //转账备注
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


}

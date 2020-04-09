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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.http.RequestAction.TransferRequest;
import cn.edu.seu.model.User;
import cn.edu.seu.views.TransferDialog;

public class UserTransferActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private EditText ethaddress; // 收钱方以太坊地址
    private EditText amount;   //转账金额
    private Button pay;
    private TransferDialog transferDialog;
    private RelativeLayout loadProgress;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transfer);

        initView();
    }

    private void initView(){
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

        back = (ImageView) findViewById(R.id.back);
        ethaddress = (EditText) findViewById(R.id.ethaddress);
        amount = (EditText) findViewById(R.id.amount);
        pay = (Button) findViewById(R.id.pay);
        loadProgress = findViewById(R.id.loadProgress);

        // 读取sharedpreferences中的用户余额
        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);

        back.setOnClickListener(this);
        pay.setOnClickListener(this);
        loadProgress.setVisibility(View.INVISIBLE);
    }

    //监听点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
            //点击开始转账
            case R.id.pay:
                startTransfer();
                break;
        }
    }

    //开始转账
    private void startTransfer(){
        if(ethaddress.getText().toString().trim().equals(sharedPreferences.getString("username", ""))){
            Toast toast = Toast.makeText(UserTransferActivity.this,"不能给自己转账",Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }
        //如果用户未输入账户和余额 提示
        if(ethaddress.getText().toString().isEmpty() || amount.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(UserTransferActivity.this,"账户和金额不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        transferDialog = new TransferDialog(this, ethaddress.getText().toString(), amount.getText().toString(), loadProgress);
        transferDialog.show();


//        Handler handler = new TransferHandler(UserTransferActivity.this);
//        TransferRequest request = new TransferRequest(UserTransferActivity.this, handler);
//        Map<String, String> param = new HashMap<String, String>();
//        // sendAddress(付款方以太坊账号-->本人账号), recieveAddress(收款方以太坊账号), transactEth(交易金额)
//        param.put("sendAddress", localEthAddress);
//        param.put("recieveAddress", ethaddress.getText().toString());
//        param.put("transactEth", amount.getText().toString());
//        request.doPost(param);
//
//
//        //提示转账成功
//        Toast toast = Toast.makeText(UserTransferActivity.this,"转账成功，可返回上一级查看您的余额和转账记录",Toast.LENGTH_SHORT);
//        toast.show();
//
//        //清空输入的账户和转账金额
//        ethaddress.setText("");
//        amount.setText("");
    }


    /**
     * 转账
     */
    public class TransferHandler extends Handler {

        private Context context;

        public TransferHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容

                    try {
                        JSONObject response = (JSONObject)msg.obj; // {"code": "200", "userBalance": "用户余额,后台通过web3直接查询"}
                        String code = response.getString("code");
                        if("200".equals(code)){
                            Toast t = Toast.makeText(context, "转账成功!", Toast.LENGTH_SHORT);
                            t.show();
                        }
                        double userBalance = response.getDouble("userBalance"); // 用户余额
                        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                        editor.putString("userbalance", String.valueOf(userBalance)); // 刷新用户余额
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }
}


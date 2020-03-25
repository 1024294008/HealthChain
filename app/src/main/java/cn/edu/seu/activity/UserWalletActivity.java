package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.seu.R;

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
    }

    private void initView(){
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
                sharedPreferences = getSharedPreferences("wallet", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                editor.putString("userbalance", balance.getText().toString());                           //key-value
                editor.commit();                                                  //提交修改

                Intent intent = new Intent(UserWalletActivity.this, UserTransferActivity.class);
                startActivity(intent);
                break;
        }
    }
}

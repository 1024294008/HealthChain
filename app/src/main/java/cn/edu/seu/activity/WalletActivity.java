package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.edu.seu.R;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageButton back;
    private ImageButton transfer;
    private TextView balance;
    private TextView ethaddress;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initView();
    }

    private void initView(){
        back = (ImageButton) findViewById(R.id.back);
        transfer = (ImageButton) findViewById(R.id.transefer);
        balance = (TextView) findViewById(R.id.balance);
        ethaddress = (TextView) findViewById(R.id.ethaddress);

        back.setOnClickListener(this);
        transfer.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
            case R.id.transfer:

                //保存用户的余额
                sharedPreferences = getSharedPreferences("wallet", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                editor.putString("userbalance", balance.getText().toString());                           //key-value
                editor.commit();                                                  //提交修改

                Intent intent = new Intent(WalletActivity.this, TransferActivity.class);
                startActivity(intent);
                break;
        }
    }
}

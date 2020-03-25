package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.edu.seu.R;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private EditText ethaddress;
    private EditText amount;   //转账金额
    private ImageButton pay;

    private String  dataBalance; //用户余额



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initView();
    }

    private void initView(){
        back = (ImageButton) findViewById(R.id.back);
        ethaddress = (EditText) findViewById(R.id.ethaddress);
        amount = (EditText) findViewById(R.id.amount);
        pay = (ImageButton) findViewById(R.id.pay);

        //读取sharedpreferences中的用户余额
        SharedPreferences data = getActivity().getSharedPreferences("wallet", Context.MODE_PRIVATE);
        dataBalance = data.getString("userbalance", "");

        back.setOnClickListener(this);
        pay.setOnClickListener(this);
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

    //判断账户是否存在
    private boolean isExist(EditText ethaddress){

        //查找用户输入的账户

        return false;
    }

    //判断用户余额是否足够
    private boolean isEnough(EditText amount, String dataBalance){
        String dataAmount = amount.getText().toString();     //转账金额

        if(dataBalance.compareTo(dataAmount) >= 0)
            return  true;
        else
            return false;
    }

    //开始转账
    private void startTransfer(){
        //如果用户未输入账户和余额 提示
        if(ethaddress.getText().toString().isEmpty() || amount.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(TransferActivity.this,"账户和余额不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        //如果账户不存在
        if( !isExist(ethaddress) ){
            //提示账户不存在
            Toast toast = Toast.makeText(TransferActivity.this,"您输入的账户不存在",Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        //如果余额不足
        if( !isEnough(amount, dataBalance) ){
            //提示用户余额不足
            Toast toast = Toast.makeText(TransferActivity.this,"您的余额不足",Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        //确认转账
        //更新我的钱包余额
        //更新对方钱包余额
        //为自己添加转账记录
        //为对方添加转账记录

        //提示转账成功
        Toast toast = Toast.makeText(TransferActivity.this,"转账成功，可返回上一级查看您的余额和转账记录",Toast.LENGTH_SHORT);
        toast.show();
    }
}

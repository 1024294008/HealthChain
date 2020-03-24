package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.seu.R;

public class RegisterActivity extends AppCompatActivity {

    //声明页面中的控件
    private ImageButton backBtn;
    private EditText username;
    private EditText password;
    private EditText telephone;
    private EditText address;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        initView();

        //点击返回按钮 跳转到登录页面
        back(backBtn);

        //点击注册按钮
        Register(register);
    }

    private  void initView(){
        backBtn = (ImageButton) this.findViewById(R.id.back);
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        telephone = (EditText) this.findViewById(R.id.telephone);
        address = (EditText) this.findViewById(R.id.address);
        register = (TextView) this.findViewById(R.id.register);
    }


    private void back(View backBtn){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Register(View register){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得各个编辑框的内容
                String name = username.getText().toString();
                String psd = password.getText().toString();
                String tel = telephone.getText().toString();
                String add = address.getText().toString();

                //如果有一个为空
                if(name.isEmpty() || psd.isEmpty() || tel.isEmpty() || add.isEmpty()){
                    Toast toast = Toast.makeText(RegisterActivity.this,"用户名、密码、电话、地址均不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //将数据存储到数据库中

                    //提示注册成功
                    Toast toast = Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

}

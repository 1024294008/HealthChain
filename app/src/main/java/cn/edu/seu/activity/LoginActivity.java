package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.seu.R;

public class LoginActivity extends AppCompatActivity {

    private TextView login;
    private EditText username;
    private EditText password;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取界面的控件
        initView();

        //点击登录事件处理方法
        Login(login);

        //点击注册事件,跳转到注册页面
        Register(register);
    }

    //获得登录界面的控件
    private void initView(){
        login = (TextView) this.findViewById(R.id.login);
        register = (TextView) this.findViewById(R.id.register);
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
    }

   //登录处理
   private void Login(View login){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUser = username.getText().toString();
                String testPassword = password.getText().toString();

                //如果用户名或密码为空，进行提示
                if(textUser.isEmpty() || testPassword.isEmpty()){
                    Toast toast = Toast.makeText(LoginActivity.this,"用户名和密码不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                //如果用户名和密码一致  跳转到主页面
                else {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
//                //用户名和密码不一致
//                else
//                {
//                    Toast toast = Toast.makeText(LoginActivity.this,"您输入的用户名和密码不一致",Toast.LENGTH_SHORT);
//                    toast.show();
//                }
            }
        });

   }

   //注册处理，跳转到注册页面
    private void Register(View register){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}

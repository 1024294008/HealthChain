package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取界面的控件
        initView();

        //点击登录事件处理方法
        login(login);

        //点击注册事件,跳转到注册页面
        register(register);
    }

    //获得登录界面的控件
    private void initView(){
        login = (TextView) this.findViewById(R.id.login);
        register = (TextView) this.findViewById(R.id.register);
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
    }

   //登录处理
   private void login(View login){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUser = username.getText().toString();
                String textPassword = password.getText().toString();

                //如果用户名或密码为空，进行提示
                if(textUser.isEmpty() || textPassword.isEmpty()){
                    Toast toast = Toast.makeText(LoginActivity.this,"用户名和密码不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                //如果用户名和密码一致  跳转到主页面
                else {
                    //保存用户名到sharedPerferences中
                    sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                    editor.putString("username", textUser);                           //key-value
                    editor.commit();                                                  //提交修改

                    //跳转到主页面
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
    private void register(View register){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}

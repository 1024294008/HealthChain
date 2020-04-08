package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.http.HttpHandler.LoginHandler;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.RequestAction.LoginRequest;

public class LoginActivity extends AppCompatActivity {

    private TextView login;
    private EditText username;
    private EditText password;
    private TextView register;
    private RelativeLayout loadProgress;

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
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        login = (TextView) this.findViewById(R.id.login);
        register = (TextView) this.findViewById(R.id.register);
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        loadProgress = findViewById(R.id.loadProgress);

        loadProgress.setVisibility(View.INVISIBLE);
    }

   //登录处理
   private void login(final View login){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String psd = password.getText().toString();

                //如果用户名或密码为空，进行提示
                if(name.isEmpty() || psd.isEmpty()){
                    Toast toast = Toast.makeText(LoginActivity.this,"用户名和密码不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                //如果用户名和密码一致  跳转到主页面
                else {

                    // 通过http调用后台方法，页面在handler渲染
                    Handler handler = new LoginHandler(LoginActivity.this, loadProgress);
                    LoginRequest request = new LoginRequest(LoginActivity.this, handler);

                    Map<String, String> param = new HashMap<String, String>();
                    param.put("account", name);
                    param.put("password", psd);

                    loadProgress.setVisibility(View.VISIBLE);
                    request.doPost(param);
                }

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

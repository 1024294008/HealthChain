package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.common.PortraitManager;
import cn.edu.seu.http.HttpHandler.RegistHandler;
import cn.edu.seu.http.RequestAction.RegistRequest;

public class RegisterActivity extends AppCompatActivity {

    //声明页面中的控件
    private ImageButton backBtn;
    private EditText username;
    private EditText password;
    private EditText telephone;
    private EditText address;
    private TextView register;
    private RelativeLayout loadProgress;

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
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        backBtn = (ImageButton) this.findViewById(R.id.back);
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        telephone = (EditText) this.findViewById(R.id.telephone);
        address = (EditText) this.findViewById(R.id.address);
        register = (TextView) this.findViewById(R.id.register);
        loadProgress = findViewById(R.id.loadProgress);

        loadProgress.setVisibility(View.INVISIBLE);
    }


    private void back(View backBtn){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    Handler handler = new RegistHandler(RegisterActivity.this, loadProgress);
                    RegistRequest request = new RegistRequest(RegisterActivity.this, handler);

                    Map<String, String> param = new HashMap<String, String>();
                    param.put("account", name);
                    param.put("password", psd);
                    param.put("address", add);
                    param.put("tel", tel);
                    param.put("portrait", PortraitManager.getPortrait().toString());
                    loadProgress.setVisibility(View.VISIBLE);
                    request.doPost(param);
//                    request.doGet();
                }

            }
        });
    }

}

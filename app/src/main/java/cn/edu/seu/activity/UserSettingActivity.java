package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cn.edu.seu.R;

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        initView();
    }

    private void initView(){
        back = (ImageView) this.findViewById(R.id.back);
        logout = (Button) this.findViewById(R.id.logout);

        back.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    //监听点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
            //点击退出登录 跳转到桌面
            case R.id.logout:
                loginOut();
                break;
        }
    }

    private void loginOut() {
        // 清除数据
        SharedPreferences sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
        editor.putString("id", "");
        editor.putString("username", "");
        editor.putString("nickName", "");
        editor.putString("password", "");
        editor.putString("ethAddress", "");
        editor.putString("sex", "");
        editor.putString("address", "");
        editor.putString("birth", "");
        editor.putString("tel", "");
        editor.putString("balance", "");
        editor.commit();

        Intent intent_login = new Intent();
        intent_login.setClass(UserSettingActivity.this, LoginActivity.class);
        intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_login);
        this.finish();
    }
}

package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.seu.R;
import cn.edu.seu.common.PortraitManager;

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

        back = findViewById(R.id.back);
        logout = findViewById(R.id.logout);

        ImageView portrait = findViewById(R.id.portrait);
        TextView nickName = findViewById(R.id.nickname);

        back.setOnClickListener(this);
        logout.setOnClickListener(this);

        //读取sharedpreferences中的数据
        SharedPreferences read = getSharedPreferences("test",Context.MODE_PRIVATE);
        //设置用户信息
        nickName.setText(read.getString("nickName", ""));
        portrait.setImageDrawable(getResources().getDrawable(PortraitManager.getPortraitSrc(read.getString("portrait", "0")), null));
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
        editor.putString("token", "");
        editor.commit();

        Intent intent_login = new Intent();
        intent_login.setClass(UserSettingActivity.this, LoginActivity.class);
        intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_login);
        this.finish();
    }
}

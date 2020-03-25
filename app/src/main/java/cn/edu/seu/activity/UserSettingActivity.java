package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//                Intent intent = new Intent(UserSettingActivity.this, LoginActivity.class);
                Intent intent = new Intent();// 创建Intent对象
                intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
                intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类

                startActivity(intent);
                break;
        }
    }

}

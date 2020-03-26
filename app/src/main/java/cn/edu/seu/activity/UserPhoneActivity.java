package cn.edu.seu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户信息界面
 */
public class UserPhoneActivity extends AppCompatActivity {

    private EditText editText_phone;
    private Button button_save;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_phone);
        initView();
    }

    private void initView(){

        sharedPreferences = UserPhoneActivity.this.getSharedPreferences("test", Context.MODE_PRIVATE);


        //得到组件： “修改手机号” 页面里的当前手机号内容组件，监听save按钮组件
        editText_phone = this.findViewById(R.id.phone);
        button_save = this.findViewById(R.id.save);
        //从数据库得到这个用户手机号，设置其内容


    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:

                String string_phone = editText_phone.getText().toString().trim();

                SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                editor.putString("tel", string_phone);                            //key-value
                editor.commit();

                if (string_phone == null) {
                    //首先将数据内容存储到数据库

                }
                //再跳转到用户信息页面
                Intent intent = new Intent(UserPhoneActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                //什么都不做，返回用户信息页面
                Intent intent_back = new Intent(UserPhoneActivity.this, UserActivity.class);
                startActivity(intent_back);
                break;
        }
    }
}
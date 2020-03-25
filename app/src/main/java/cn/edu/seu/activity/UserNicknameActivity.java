package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户修改昵称界面
 */
public class UserNicknameActivity extends AppCompatActivity {

    private EditText editText_nickname;
    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView(){
        //得到组件： “修改昵称” 页面里的当前昵称内容组件，监听save按钮组件
        editText_nickname = this.findViewById(R.id.nickname);
        button_save = this.findViewById(R.id.save);
        //从数据库得到这个用户昵称，设置其内容


    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:

                String string_nickname = editText_nickname.getText().toString().trim();
                if (string_nickname == null) {
                    //首先将数据内容存储到数据库

                }
                //再跳转到用户信息页面
                Intent intent = new Intent(UserNicknameActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                //什么都不做，返回用户信息页面
                Intent intent_back = new Intent(UserNicknameActivity.this, UserActivity.class);
                startActivity(intent_back);
                break;
        }
    }
}

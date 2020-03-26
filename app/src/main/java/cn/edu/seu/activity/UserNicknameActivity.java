package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户修改昵称界面
 */
public class UserNicknameActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_nickname;
    private Button button_save;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_nickname);
        initView();
    }

    private void initView(){

        back = (ImageView) this.findViewById(R.id.back);
        //得到组件： “修改昵称” 页面里的当前昵称内容组件，监听save按钮组件
        editText_nickname = this.findViewById(R.id.nickname);
        button_save = this.findViewById(R.id.save);

        //对组件进行监听
        back.setOnClickListener(this);
        editText_nickname.setOnClickListener(this);
        button_save.setOnClickListener(this);

    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                String string_nickname = editText_nickname.getText().toString().trim();
                if (string_nickname.isEmpty()) {
                    //提示用户昵称不能为空
                    Toast toast = Toast.makeText(UserNicknameActivity.this,"用户昵称不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //更新到数据库

                    //更新sharedPreferences中保存的用户昵称

                    //更新个人信息界面和“我”的界面中的用户昵称

                    //提示修改成功
                    Toast toast = Toast.makeText(UserNicknameActivity.this,"昵称修改成功",Toast.LENGTH_SHORT);
                    toast.show();

                    //再跳转到用户信息页面
                    this.finish();
                }

                break;
            case R.id.back:
                //什么都不做，返回用户信息页面
                this.finish();
                break;
        }
    }
}

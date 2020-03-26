package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户信息界面
 */
public class UserPasswordActivity extends AppCompatActivity {

    private EditText editText_currentPassword;
    private EditText editText_newPassword;
    private EditText editText_newPassword2;
    private Button button_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password);
        initView();
    }

    private void initView(){
        //初始化得到这几个组件就可以，不用预先渲染内容
        editText_currentPassword = this.findViewById(R.id.currentPassword);
        editText_newPassword = this.findViewById(R.id.newPassword);
        editText_newPassword2 = this.findViewById(R.id.newPassword2);
        button_save = this.findViewById(R.id.save);
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (editText_currentPassword.getText()!=null&&editText_newPassword.getText()!=null&&editText_newPassword2.getText()!=null){
                    String string_currentPassword = editText_currentPassword.getText().toString();
                    String string_newPassword = editText_newPassword.getText().toString();
                    String string_newPassword2 = editText_newPassword2.getText().toString();
                    //首先校验两次密码是否是否输入相同
                    if(!string_newPassword.equals(string_newPassword2)) {
                        Toast.makeText(this, "新密码两次输入不一致！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //校验输入新密码的长度
                    if(string_newPassword.length()<=8){
                        Toast.makeText(this, "密码长度至少8位！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //检验原密码是否和当前数据库的相同
                    //不同则error，break


                    //相同进行修改，同步更新到数据库


                    //再跳转到用户信息页面
                    Intent intent = new Intent(UserPasswordActivity.this, UserActivity.class);
                    startActivity(intent);
                    break;
                }else {
                    //什么都不做
                }
            case R.id.back:
                //什么都不做，返回用户信息页面
                Intent intent_back = new Intent(UserPasswordActivity.this, UserActivity.class);
                startActivity(intent_back);
                break;
        }
    }

}

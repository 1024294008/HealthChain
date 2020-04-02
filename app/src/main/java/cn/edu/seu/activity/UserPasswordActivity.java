package cn.edu.seu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户信息界面
 */
public class UserPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_currentPassword;
    private EditText editText_newPassword;
    private EditText editText_newPassword2;
    private Button button_save;

    public SharedPreferences sharedPreferences;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password);
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

        sharedPreferences = UserPasswordActivity.this.getSharedPreferences("test", Context.MODE_PRIVATE);


        //初始化得到这几个组件就可以，不用预先渲染内容
        editText_currentPassword = this.findViewById(R.id.currentPassword);
        editText_newPassword = this.findViewById(R.id.newPassword);
        editText_newPassword2 = this.findViewById(R.id.newPassword2);
        button_save = this.findViewById(R.id.save);
        back = this.findViewById(R.id.back);

        back.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                //相同进行修改
                SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                String password = sharedPreferences.getString("password", "");

                String string_currentPassword = editText_currentPassword.getText().toString();
                String string_newPassword = editText_newPassword.getText().toString();
                String string_newPassword2 = editText_newPassword2.getText().toString();
                //首先校验原密码是否正确
                if (string_currentPassword.equals("") || !string_currentPassword.equals(password)) {
                    Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!string_newPassword.equals("") && !string_newPassword2.equals("")){
                    //首先校验两次密码是否是否输入相同
                    if(!string_newPassword.equals(string_newPassword2)) {
                        Toast.makeText(this, "新密码两次输入不一致！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //校验输入新密码的长度
                    if(string_newPassword.length() <= 8){
                        Toast.makeText(this, "密码长度至少8位！", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    editor.putString("password", string_newPassword);                 //key-value
                    editor.commit();                                                  //提交修改
                    //提示
                    Toast toast = Toast.makeText(UserPasswordActivity.this,"密码修改成功",Toast.LENGTH_SHORT);
                    toast.show();

                    //再跳转到用户信息页面
                    this.finish();
                    break;
                }else {
                    Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
                }
            case R.id.back:
                //什么都不做，返回用户信息页面
                this.finish();
                break;
        }
    }

}

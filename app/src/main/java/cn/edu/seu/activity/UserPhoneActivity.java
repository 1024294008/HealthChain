package cn.edu.seu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
public class UserPhoneActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_phone;
    private Button button_save;
    private ImageView back;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_phone);
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

        sharedPreferences = UserPhoneActivity.this.getSharedPreferences("test", Context.MODE_PRIVATE);

        //得到组件： “修改手机号” 页面里的当前手机号内容组件，监听save按钮组件
        editText_phone = this.findViewById(R.id.phone);
        button_save = this.findViewById(R.id.save);
        back = (ImageView) this.findViewById(R.id.back);
        //从数据库得到这个用户手机号，设置其内容

        //对组件进行监听
        back.setOnClickListener(this);
        editText_phone.setOnClickListener(this);
        button_save.setOnClickListener(this);

        editText_phone.setText(sharedPreferences.getString("tel", ""));
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                String string_phone = editText_phone.getText().toString().trim();

                if (string_phone.equals("")) {
                    //提示手机号不能为空
                    Toast toast = Toast.makeText(UserPhoneActivity.this,"手机号不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                } else{
                    //更新sharedPreferences中保存的用户手机号
                    SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                    editor.putString("tel", string_phone);                            //key-value
                    editor.commit();

                    //提示修改成功
                    Toast toast = Toast.makeText(UserPhoneActivity.this,"手机号修改成功",Toast.LENGTH_SHORT);
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
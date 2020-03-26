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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户修改地址界面
 */
public class UserAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_address;
    private Button button_save;
    private ImageView back;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
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

        sharedPreferences = UserAddressActivity.this.getSharedPreferences("test", Context.MODE_PRIVATE);

        //得到组件： “修改地址” 页面里的当前地址内容组件，监听save按钮组件
        editText_address = this.findViewById(R.id.address);
        button_save = this.findViewById(R.id.save);
        back = (ImageView) this.findViewById(R.id.back);
        //从数据库得到这个地址，设置其内容

        //对组件进行监听
        back.setOnClickListener(this);
        editText_address.setOnClickListener(this);
        button_save.setOnClickListener(this);

        editText_address.setText(sharedPreferences.getString("address", ""));
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:

                String string_address = editText_address.getText().toString().trim();

                if (string_address.equals("")) {
                    //提示用户地址不能为空
                    Toast toast = Toast.makeText(UserAddressActivity.this,"用户地址不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //更新到数据库

                    //更新sharedPreferences中保存的用户昵称
                    SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                    editor.putString("address", string_address);                      //key-value
                    editor.commit();

                    //更新个人信息界面中的地址

                    //提示修改成功
                    Toast toast = Toast.makeText(UserAddressActivity.this,"地址修改成功",Toast.LENGTH_SHORT);
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

package cn.edu.seu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户信息界面
 */
public class UserSexActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton radioButton_man;
    private RadioButton radioButton_women;
    private Button button_save;

    public SharedPreferences sharedPreferences;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sex);
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

        sharedPreferences = UserSexActivity.this.getSharedPreferences("test",Context.MODE_PRIVATE);

        //得到组件： “修改手机号” 页面里的当前手机号内容组件，监听save按钮组件
        radioButton_man = this.findViewById(R.id.man);
        radioButton_women = this.findViewById(R.id.women);
        button_save = this.findViewById(R.id.save);
        back = (ImageView) this.findViewById(R.id.back);
        //从数据库得到用户性别，设置其选中

        back.setOnClickListener(this);
        button_save.setOnClickListener(this);

        if(sharedPreferences.getString("sex", "").equals("1")){
            radioButton_man.setChecked(true);
        }else if(sharedPreferences.getString("sex", "").equals("2")){
            radioButton_women.setChecked(true);
        }

    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (radioButton_man.isChecked()) {

                    // 修改SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                    editor.putString("sex", "1");                                     //key-value
                    editor.commit();                                                  //提交修改


                }else if(radioButton_women.isChecked()){

                    // 修改SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                    editor.putString("sex", "2");                                     //key-value
                    editor.commit();                                                  //提交修改

                }

                //更新个人信息界面的性别


                //提示
                Toast toast = Toast.makeText(UserSexActivity.this,"性别修改成功",Toast.LENGTH_SHORT);
                toast.show();
                //再跳转到用户信息页面
                this.finish();
                break;
            case R.id.back:
                //什么都不做，返回用户信息页面
                this.finish();
                break;
        }
    }

}

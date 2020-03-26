package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_phone);
        initView();
    }

    private void initView(){
        //得到组件： “修改手机号” 页面里的当前手机号内容组件，监听save按钮组件
        editText_phone = this.findViewById(R.id.phone);
        button_save = this.findViewById(R.id.save);
        back = (ImageView) this.findViewById(R.id.back);
        //从数据库得到这个用户手机号，设置其内容

        //对组件进行监听
        back.setOnClickListener(this);
        editText_phone.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                String string_phone = editText_phone.getText().toString().trim();
                if (string_phone.isEmpty()) {
                    //提示手机号不能为空
                    Toast toast = Toast.makeText(UserPhoneActivity.this,"手机号不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else  if( isMobileNO(string_phone) == false){
                    //提示手机号格式不正确
                    Toast toast = Toast.makeText(UserPhoneActivity.this,"请输入正确的手机号格式",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //更新到数据库

                    //更新sharedPreferences中保存的用户手机号

                    //更新个人信息界面中的手机号

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

    //判断电话格式是否正确   不知道这个格式该怎么判断了。。。。。
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "^((\\\\+?86)|(\\\\(\\\\+86\\\\)))?((((13[^4]{1})|(14[5-9]{1})|147|(15[^4]{1})|166|(17\\\\d{1})|(18\\\\d{1})|(19[89]{1}))\\\\d{8})|((134[^9]{1}|1410|1440)\\\\d{7}))$\n";
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }

}
package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户信息界面
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView_head;
    private TextView textView_nickname;
    private TextView textView_account;
    private TextView textView_address;
    private TextView textView_phone;
    private TextView textView_sex;

    private LinearLayout line_nickname;
    private LinearLayout line_account;
    private LinearLayout line_address;
    private LinearLayout line_phone;
    private LinearLayout line_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView(){
        //设置显示用户头像，用户名，账户，地址，手机号，性别，密码（不显示）
        imageView_head = this.findViewById(R.id.head);
        textView_nickname = this.findViewById(R.id.nickname);
        textView_account = this.findViewById(R.id.account);
        textView_address = this.findViewById(R.id.address);
        textView_phone = this.findViewById(R.id.phone);
        textView_sex = this.findViewById(R.id.sex);

        this.findViewById(R.id.line_nickname).setOnClickListener(this);
        this.findViewById(R.id.line_account).setOnClickListener(this);
        this.findViewById(R.id.line_address).setOnClickListener(this);
        this.findViewById(R.id.line_phone).setOnClickListener(this);
        this.findViewById(R.id.line_sex).setOnClickListener(this);

        //根据用户id从数据库里拿到后更新进本页面对应位置
        Toast.makeText(this, "进来了兄弟", Toast.LENGTH_SHORT).show();

    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_nickname:
                Intent intent_nickname = new Intent(UserActivity.this,UserNicknameActivity.class);
                Toast.makeText(this, "什么玩意", Toast.LENGTH_SHORT).show();
                startActivity(intent_nickname);
                break;
            case R.id.line_address:
                Intent intent_address = new Intent(UserActivity.this,UserAddressActivity.class);
                startActivity(intent_address);
                break;
            case R.id.line_phone:
                Intent intent_phone = new Intent(UserActivity.this,UserPhoneActivity.class);
                startActivity(intent_phone);
                break;
            case R.id.line_sex:
                Intent intent_sex = new Intent(UserActivity.this,UserSexActivity.class);
                startActivity(intent_sex);
                break;
            case R.id.line_password:
                Intent intent_password = new Intent(UserActivity.this,UserPasswordActivity.class);
                startActivity(intent_password);
                break;

        }
    }
}

package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户信息界面
 */
public class UserActivity extends AppCompatActivity {

    private ImageView imageView_head = this.findViewById(R.id.head);
    private TextView textView_nickname = this.findViewById(R.id.nickname);
    private TextView textView_account = this.findViewById(R.id.account);
    private TextView textView_address = this.findViewById(R.id.address);
    private TextView textView_phone = this.findViewById(R.id.phone);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView(){
        //设置用户头像，用户名，账户，地址，手机号
        imageView_head = this.findViewById(R.id.head);
        textView_nickname = this.findViewById(R.id.nickname);
        textView_account = this.findViewById(R.id.account);
        textView_address = this.findViewById(R.id.address);
        textView_phone = this.findViewById(R.id.phone);
        //根据用户id从数据库里拿到后更新进本页面对应位置


    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nickname:
                Intent intent_nickname = new Intent(UserActivity.this,UserNicknameActivity.class);
                startActivity(intent_nickname);
                break;
            case R.id.address:
                Intent intent_address = new Intent(UserActivity.this,UserAddressActivity.class);
                startActivity(intent_address);
                break;
            //下载对话框点击事件
            case R.id.head:

                break;
        }
    }
}

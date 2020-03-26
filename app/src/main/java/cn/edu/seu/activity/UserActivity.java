package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.http.RequestAction.UpdateUserInfoRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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
    private ImageView back;

    private LinearLayout line_nickname;
    private LinearLayout line_account;
    private LinearLayout line_address;
    private LinearLayout line_phone;
    private LinearLayout line_sex;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView(){

        // 用户信息
        sharedPreferences = UserActivity.this.getSharedPreferences("test", Context.MODE_PRIVATE);

        //设置显示用户头像，用户名，账户，地址，手机号，性别，密码（不显示）
        imageView_head = this.findViewById(R.id.head);
        textView_nickname = this.findViewById(R.id.nickname);
        textView_account = this.findViewById(R.id.account);
        textView_address = this.findViewById(R.id.address);
        textView_phone = this.findViewById(R.id.phone);
        textView_sex = this.findViewById(R.id.sex);
        back = this.findViewById(R.id.back);

        back.setOnClickListener(this);
        this.findViewById(R.id.line_nickname).setOnClickListener(this);
        this.findViewById(R.id.line_account).setOnClickListener(this);
        this.findViewById(R.id.line_address).setOnClickListener(this);
        this.findViewById(R.id.line_phone).setOnClickListener(this);
        this.findViewById(R.id.line_sex).setOnClickListener(this);
        this.findViewById(R.id.line_password).setOnClickListener(this);


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
                Log.e("Tag","我要修改密码");
                Intent intent_password = new Intent(UserActivity.this,UserPasswordActivity.class);
                startActivity(intent_password);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    // 修改用户数据，需要调用。
    public void updateUserInfo() {

        String id = sharedPreferences.getString("id", "");
        String password = sharedPreferences.getString("password", "");
        String sex = sharedPreferences.getString("sex", "");
        String tel = sharedPreferences.getString("tel", "");
        String nickName = sharedPreferences.getString("nickName", "");
        String address = sharedPreferences.getString("address", "");

        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        param.put("password", password);
        param.put("sex", sex);
        param.put("tel", tel);
        param.put("nickName", nickName);
        param.put("address", address);

        Handler handler = new UpdateUserInfoHandler(UserActivity.this);
        UpdateUserInfoRequest request = new UpdateUserInfoRequest(UserActivity.this, handler);
        request.doPost(param);

    }

    /**
     * 修改用户个人信息
     */
    public class UpdateUserInfoHandler extends Handler {

        private Context context;

        public UpdateUserInfoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容
                    Toast t = Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT);
                    t.show();
                    break;
            }
        }
    }
}

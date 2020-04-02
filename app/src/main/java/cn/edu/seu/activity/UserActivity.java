package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.common.PortraitManager;
import cn.edu.seu.http.RequestAction.UpdateUserInfoRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息界面
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView_head;
    private TextView textView_nickname;
    private TextView textView_account;
    private TextView textView_sex;
    private TextView textView_address;
    private TextView textView_phone;
    private ImageView back;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        displayUserInfo();
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
        imageView_head.setImageDrawable(getResources().getDrawable(PortraitManager.getPortraitSrc(sharedPreferences.getString("portrait", "0")), null));
        this.findViewById(R.id.line_nickname).setOnClickListener(this);
        this.findViewById(R.id.line_account).setOnClickListener(this);
        this.findViewById(R.id.line_address).setOnClickListener(this);
        this.findViewById(R.id.line_phone).setOnClickListener(this);
        this.findViewById(R.id.line_sex).setOnClickListener(this);
        this.findViewById(R.id.line_password).setOnClickListener(this);
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_nickname:
                Intent intent_nickname = new Intent(UserActivity.this,UserNicknameActivity.class);
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
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        displayUserInfo();
        updateUserInfo();
    }

    // 显示用户信息
    public void displayUserInfo(){
        textView_nickname.setText(sharedPreferences.getString("nickName", ""));
        textView_account.setText(sharedPreferences.getString("username", ""));
        textView_address.setText(sharedPreferences.getString("address", ""));
        textView_phone.setText(sharedPreferences.getString("tel", ""));

        switch (sharedPreferences.getString("sex", "")){
            case "1":
                textView_sex.setText("男");
                break;
            case "2":
                textView_sex.setText("女");
                break;
            default:
                textView_sex.setText("");
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
        String token = sharedPreferences.getString("token", "");

        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        param.put("password", password);
        param.put("sex", sex);
        param.put("tel", tel);
        param.put("nickName", nickName);
        param.put("address", address);
        param.put("token", token);

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
                    JSONObject response = (JSONObject)msg.obj;
                    try {
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            break;
                        }
                        else{
                            Toast t = Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT);
                            t.show();
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast t = Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT);
                        t.show();
                        break;
                    }

            }
        }
    }
}

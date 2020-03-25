package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.seu.R;

/**
 * 用户修改地址界面
 */
public class UserAddressActivity extends AppCompatActivity {

    private EditText editText_address;
    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        initView();
    }

    private void initView(){
        //得到组件： “修改地址” 页面里的当前地址内容组件，监听save按钮组件
        editText_address = this.findViewById(R.id.address);
        button_save = this.findViewById(R.id.save);
        //从数据库得到这个地址，设置其内容


    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:

                String string_address = editText_address.getText().toString().trim();
                if(string_address==null){
                    //首先将数据内容存储到数据库

                }
                //再跳转到用户信息页面
                Intent intent = new Intent(UserAddressActivity.this,UserActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                //什么都不做，返回用户信息页面
                Intent intent_back = new Intent(UserAddressActivity.this,UserActivity.class);
                startActivity(intent_back);
                break;
        }
    }
}

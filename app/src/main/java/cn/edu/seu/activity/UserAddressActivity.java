package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        back = (ImageView) this.findViewById(R.id.back);
        //从数据库得到这个地址，设置其内容

        //对组件进行监听
        back.setOnClickListener(this);
        editText_address.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:

                String string_address = editText_address.getText().toString().trim();
                if (string_address.isEmpty()) {
                    //提示用户地址不能为空
                    Toast toast = Toast.makeText(UserAddressActivity.this,"用户地址不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //更新到数据库

                    //更新sharedPreferences中保存的用户昵称

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

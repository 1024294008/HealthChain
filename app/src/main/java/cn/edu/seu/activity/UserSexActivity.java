package cn.edu.seu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
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

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sex);
        initView();
    }

    private void initView(){
        //得到组件： “修改手机号” 页面里的当前手机号内容组件，监听save按钮组件
        radioButton_man = this.findViewById(R.id.man);
        radioButton_women = this.findViewById(R.id.women);
        button_save = this.findViewById(R.id.save);
        back = (ImageView) this.findViewById(R.id.back);
        //从数据库得到用户性别，设置其选中

        back.setOnClickListener(this);
        button_save.setOnClickListener(this);

    }
    //处理点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (radioButton_man.isChecked()) {
                    //首先将数据内容存储到数据库


                }else if(radioButton_women.isChecked()){
                    //首先将数据内容存储到数据库
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

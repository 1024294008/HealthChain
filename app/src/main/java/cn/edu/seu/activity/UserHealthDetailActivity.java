package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import cn.edu.seu.R;

public class UserHealthDetailActivity extends AppCompatActivity implements View.OnClickListener{

    //声明需要的控件
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_health_detail);

        //初始化控件
        initView();
    }

    private void initView(){
        back = (ImageButton) this.findViewById(R.id.back);

        back.setOnClickListener(this);
    }

    //重写点击事件
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
        }
    }
}

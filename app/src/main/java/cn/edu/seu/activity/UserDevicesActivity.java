package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.edu.seu.R;

public class UserDevicesActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_devices);

        initView();
    }

    private void initView(){
        back = (ImageView) this.findViewById(R.id.back);

        back.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
        }
    }
}

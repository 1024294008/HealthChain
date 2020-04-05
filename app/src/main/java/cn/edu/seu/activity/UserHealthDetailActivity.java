package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.edu.seu.R;

public class UserHealthDetailActivity extends AppCompatActivity implements View.OnClickListener{

    //声明需要的控件
    private ImageButton back;

    private TextView distance;
    private TextView heat;
    private TextView sleepQuality;
    private TextView heartRate;
    private TextView evaluation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_health_detail);

        //初始化控件
        initView();
    }

    private void initView(){
        back = (ImageButton) this.findViewById(R.id.back);

        heat = this.findViewById(R.id.heat);
        distance = this.findViewById(R.id.distance);
        sleepQuality = this.findViewById(R.id.sleepQuality);
        heartRate = this.findViewById(R.id.heartRate);
        evaluation = this.findViewById(R.id.evaluation);

        back.setOnClickListener(this);

        // 设置内容
        setContent();
    }

    // 点击事件
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
        }
    }

    // 设置用户健康数据详情页面内容
    private void setContent(){

    }
}

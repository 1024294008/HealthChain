package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;

import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MedicalDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private ImageView portrait_org;
    private ImageView portrait_user;
    private TextView requestService;
    private TextView organizationName;
    private TextView serviceName;
    private TextView introduction;
    private TextView serviceDetails;
    private TextView cost;
    private TextView tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_detail);
        initView();

        // 设置服务详情页面内容
        setContent();
    }

    private void initView(){
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        back = this.findViewById(R.id.back);
        requestService = this.findViewById(R.id.requestService);
        portrait_org = this.findViewById(R.id.portrait_org);
        portrait_user = this.findViewById(R.id.portrait_user);
        organizationName = this.findViewById(R.id.organizationName);
        serviceName = this.findViewById(R.id.serviceName);
        introduction = this.findViewById(R.id.introduction);
        cost = this.findViewById(R.id.cost);
        serviceDetails = this.findViewById(R.id.serviceDetails);
        tel = this.findViewById(R.id.tel);

        back.setOnClickListener(this);
        requestService.setOnClickListener(this);
//        portrait_user.setOnClickListener(this);
//        portrait_org.setOnClickListener(this);
//        organizationName.setOnClickListener(this);
//        introduction.setOnClickListener(this);
//        cost.setOnClickListener(this);
//        serviceDetails.setOnClickListener(this);
//        tel.setOnClickListener(this);
//        serviceName.setOnClickListener(this);
    }

    // 点击事件
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.requestService:
                //弹出对话框
                break;
        }
    }

    // 设置服务页面详情内容
    private void setContent(){

    }

}

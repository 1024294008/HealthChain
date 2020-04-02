package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.common.PortraitManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MedicalDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView portrait_org;
    private ImageView portrait_user;
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
    }

    private void initView(){
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        // 获取intent信息
        Integer serviceId = Integer.valueOf(getIntent().getStringExtra("serviceId"));

        ImageButton back = this.findViewById(R.id.back);
        TextView requestService = this.findViewById(R.id.requestService);

        portrait_org = this.findViewById(R.id.portrait_org);
        portrait_user = this.findViewById(R.id.portrait_user);

        organizationName = this.findViewById(R.id.organizationName);
        introduction = this.findViewById(R.id.introduction);
        serviceName = this.findViewById(R.id.serviceName);
        serviceDetails = this.findViewById(R.id.serviceDetails);
        cost = this.findViewById(R.id.cost);
        tel = this.findViewById(R.id.tel);

        back.setOnClickListener(this);
        requestService.setOnClickListener(this);

        // 设置服务详情页面内容
        setContent(serviceId);
        Toast.makeText(this, serviceId.toString(), Toast.LENGTH_SHORT).show();
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
    private void setContent(Integer serviceId){
        // 获取医疗信息和服务信息

        //设置信息
//        portrait_org.setImageDrawable(this.getResources().getDrawable(PortraitManager.getPortraitSrc(), null));;
//        portrait_user.setImageDrawable(this.getResources().getDrawable(PortraitManager.getPortraitSrc(), null));;
//        organizationName.setText();
//        introduction.setText();
//        serviceName.setText();
//        serviceDetails.setText();
//        cost.setText();
//        tel.setText();

    }

}

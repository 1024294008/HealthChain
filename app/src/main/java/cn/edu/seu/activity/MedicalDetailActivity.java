package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.common.CoinTransManager;
import cn.edu.seu.common.PortraitManager;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.RequestAction.MedicalServiceRequest;
import cn.edu.seu.views.BuyServiceDialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicalDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView portrait_org;
    private ImageView portrait_user;
    private TextView organizationName;
    private TextView serviceName;
    private TextView introduction;
    private TextView serviceDetails;
    private TextView cost;
    private TextView tel;
    private String ethAddress;
    private BuyServiceDialog buyServiceDialog;
    private RelativeLayout loadProgress;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_detail);
        initView();
    }

    private void initView(){

        sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);

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

        loadProgress = findViewById(R.id.loadProgress);

        back.setOnClickListener(this);
        requestService.setOnClickListener(this);
        loadProgress.setVisibility(View.INVISIBLE);

        // 设置服务详情页面内容
        setContent(serviceId);
    }

    // 点击事件
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.requestService:
                buyServiceDialog = new BuyServiceDialog(this, ethAddress, cost.getText().toString(), loadProgress);
                buyServiceDialog.show();
                break;
        }
    }

    // 设置服务页面详情内容
    private void setContent(Integer serviceId){
        // 获取医疗信息和服务信息

        Handler handler = new MedicalServiceHandler(MedicalDetailActivity.this);
        MedicalServiceRequest request = new MedicalServiceRequest(MedicalDetailActivity.this, handler);
        request.doGet(serviceId);

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

    /**
     * 医疗服务详细信息
     */
    public class MedicalServiceHandler extends Handler {

        private Context context;

        public MedicalServiceHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容
                    // 设置信息
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            JSONObject _data = response.getJSONObject("_data");
                            JSONObject serviceAndOrgInfo = _data.getJSONObject("serviceAndOrgInfo");

                            portrait_org.setImageDrawable(context.getResources().getDrawable(PortraitManager.getPortraitSrc(String.valueOf(serviceAndOrgInfo.getInt("portrait_org"))), null));
                            portrait_user.setImageDrawable(context.getResources().getDrawable(PortraitManager.getPortraitSrc(String.valueOf(serviceAndOrgInfo.getInt("portrait_service"))), null));
                            organizationName.setText(serviceAndOrgInfo.getString("organizationName"));
                            introduction.setText(serviceAndOrgInfo.getString("introduction"));
                            tel.setText(serviceAndOrgInfo.getString("tel"));
                            serviceName.setText(serviceAndOrgInfo.getString("serviceName"));
                            serviceDetails.setText(serviceAndOrgInfo.getString("serviceDetails"));
                            cost.setText(CoinTransManager.transToCoin(serviceAndOrgInfo.getString("cost")));
                            ethAddress = serviceAndOrgInfo.getString("ethAddress");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }


            }
        }
    }

}

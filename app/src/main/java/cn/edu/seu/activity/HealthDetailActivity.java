package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.fragment.HealthFragment;
import cn.edu.seu.http.RequestAction.LatestDataRequest;

public class HealthDetailActivity extends AppCompatActivity implements View.OnClickListener{

    //声明需要的控件
    private ImageButton back;

    private TextView distance;
    private TextView heat;
    private TextView sleepQuality;
    private TextView heartRate;
    private TextView evaluation;
    private TextView uploadTime;
    private TextView permitVisit;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail);

        //初始化控件
        initView();
    }

    private void initView(){
        sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);

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

        back = (ImageButton) this.findViewById(R.id.back);
        heat = this.findViewById(R.id.heat);
        distance = this.findViewById(R.id.distance);
        sleepQuality = this.findViewById(R.id.sleepQuality);
        heartRate = this.findViewById(R.id.heartRate);
        evaluation = this.findViewById(R.id.evaluation);
        uploadTime = this.findViewById(R.id.uploadTime);
        permitVisit = this.findViewById(R.id.permitVisit);

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
        Handler handler = new HealthDataHandler(this);
        LatestDataRequest request = new LatestDataRequest(this, handler);
        String token = sharedPreferences.getString("token", ""); // 获取用户id
        Map<String, String> item = new HashMap<>();
        item.put("token", token);
        item.put("index", getIntent().getStringExtra("index"));
        request.doPost(item);
    }

    public class HealthDataHandler extends Handler {

        private Context context;

        public HealthDataHandler(Context context){
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        JSONObject response = (JSONObject) msg.obj;
                        JSONObject healthData = response.getJSONObject("_data");
                        heat.setText(healthData.getString("heat"));
                        distance.setText(healthData.getString("distance"));
                        sleepQuality.setText(healthData.getString("sleepQuality"));
                        heartRate.setText(healthData.getString("heartRate"));
                        evaluation.setText(healthData.getString("evaluation"));
                        uploadTime.setText(healthData.getString("uploadTime"));
                        permitVisit.setText((healthData.getString("permitVisit")).equals("0") ? "私有":"公开");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast t = Toast.makeText(context, "数据加载失败..", Toast.LENGTH_SHORT);
                        t.show();
                        setData("","","","","", "");
                    }
            }
        }
        public void setData(String _uploadTime, String _distance, String _heat, String _sleepQuality, String _heartRate, String _evaluation){
            uploadTime.setText(_uploadTime);
            distance.setText(_distance);
            heat.setText(_heat);
            sleepQuality.setText(_sleepQuality);
            heartRate.setText(_heartRate);
            evaluation.setText(_evaluation);
        }
    }
}

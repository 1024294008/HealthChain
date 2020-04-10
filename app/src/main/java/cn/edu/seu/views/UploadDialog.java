package cn.edu.seu.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.http.HttpHandler.UploadDataHandler;
import cn.edu.seu.http.RequestAction.UploadDataRequest;

public class UploadDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String deviceName;

    private TextView distance;
    private TextView heat;
    private TextView sleepQuality;
    private TextView heartRate;
    private Switch share;
    private EditText evaluation;
    private RelativeLayout loadProgress;

    public SharedPreferences sharedPreferences;

    public UploadDialog(@NonNull Context context, RelativeLayout loadProgress) {
        super(context);
        this.context = context;
        this.loadProgress = loadProgress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_upload);
        initView();
    }

    // 初始化组件
    private void initView(){

        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);

        // 设置对话框属性
        setCanceledOnTouchOutside(true);
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int)((context.getResources().getDisplayMetrics()).widthPixels * 0.8);
        p.height = (int)((context.getResources().getDisplayMetrics()).heightPixels * 0.65);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setAttributes(p);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);

        // 获取控件
        distance = findViewById(R.id.distance);
        heat = findViewById(R.id.heat);
        sleepQuality = findViewById(R.id.sleepQuality);
        heartRate = findViewById(R.id.heartRate);
        share = findViewById(R.id.share);
        evaluation = findViewById(R.id.evaluation);

        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.confirmUpload).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                UploadDialog.this.dismiss();
                break;
            case R.id.confirmUpload:
                startUpload();
                break;
        }
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public void show() {
        super.show();
        if(getDeviceName() != null){
            loadData();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setDeviceName(null);
        // 复位
        distance.setText("10000米");
        heat.setText("1000卡路里");
        sleepQuality.setText("非常良好");
        heartRate.setText("75分钟/次");
        share.setChecked(true);
        evaluation.setText("");
    }

    // 从当前设备中读取数据
    private void loadData(){
        // ------通过蓝牙读取指定设备的健康数据-----

        // --------------------------------------
        // 测试集
        distance.setText("10000米");
        heat.setText("1000卡路里");
        sleepQuality.setText("非常良好");
        heartRate.setText("75次/分钟");
    }

    // 上传数据
    private void startUpload(){
        if(evaluation.getText().toString().equals("")){
            Toast.makeText(context, "写个心情呗", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取数据并上传
        String str_distance =  distance.getText().toString();
        String str_heat = heat.getText().toString();
        String str_sleepQuality = sleepQuality.getText().toString();
        String str_heartRate =  heartRate.getText().toString();
        boolean is_share =  share.isChecked();
        String str_evaluation = evaluation.getText().toString();

        this.dismiss();
        loadProgress.setVisibility(View.VISIBLE);

        Handler handler =new UploadDataHandler(context, loadProgress);
        UploadDataRequest request = new UploadDataRequest(context, handler);

        Map<String, String> param = new HashMap<String, String>();
        param.put("token", sharedPreferences.getString("token", ""));
        param.put("distance", str_distance);
        param.put("heat", str_heat);
        param.put("sleepQuality", str_sleepQuality);
        param.put("heartRate", str_heartRate);
        param.put("permitVisit", is_share?"1":"0");
        param.put("evaluation", str_evaluation);
        param.put("uploadTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date()));
        request.doPost(param);

    }
}

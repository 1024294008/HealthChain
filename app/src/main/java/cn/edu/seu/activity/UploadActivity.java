package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.adapter.DeviceListAdapter;
import cn.edu.seu.views.UploadDialog;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传数据界面
 */
public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    // 设备数据列表
    private List<Map<String, String>> deviceList;
    private DeviceListAdapter deviceListAdapter;
    private RelativeLayout loadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initView();
        scanDevices();
    }

    private void initView(){
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
        loadProgress = findViewById(R.id.loadProgress);

        ListView deviceListView = findViewById(R.id.deviceList);
        deviceList = new ArrayList<>();
        deviceListAdapter = new DeviceListAdapter(this, deviceList, R.layout.activity_upload_list_item, new UploadDialog(this, loadProgress));

        deviceListView.setAdapter(deviceListAdapter);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.refresh).setOnClickListener(this);

        loadProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.refresh:
                scanDevices();
                break;
        }
    }

    // 扫描设备
    private void scanDevices(){
        deviceList.clear();
        // -------通过蓝牙扫描设备-------

        // ----------------------------
        // 测试集
        String[] deviceNameTest = new String[]{"小米手环", "华为手环", "苹果手表"};
        for (Integer i = 0; i < deviceNameTest.length; i++) {
            Map<String, String> showItem = new HashMap<>();
            showItem.put("deviceName", deviceNameTest[i]);
            showItem.put("deviceAddr", i.toString());

            deviceList.add(showItem);
        }
        deviceListAdapter.notifyDataSetChanged();
    }
}

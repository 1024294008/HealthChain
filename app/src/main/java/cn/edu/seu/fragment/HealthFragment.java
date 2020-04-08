package cn.edu.seu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xujiaji.happybubble.Auto;
import com.xujiaji.happybubble.BubbleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.seu.R;
import cn.edu.seu.activity.DataAnalyzeActivity;
import cn.edu.seu.activity.HealthDetailActivity;
import cn.edu.seu.activity.MainActivity;
import cn.edu.seu.activity.MedicalDetailActivity;
import cn.edu.seu.activity.UploadActivity;
import cn.edu.seu.adapter.HealthListAdapter;
import cn.edu.seu.adapter.MedicalListAdapter;
import cn.edu.seu.http.RequestAction.HistoryDataRequest;
import cn.edu.seu.http.RequestAction.LatestDataRequest;

/**
 * 健康数据显示界面
 */
public class HealthFragment extends Fragment  implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private View dialogView;

    private LinearLayout healthFront;
    private LinearLayout healthBg;
    private RelativeLayout searchHistory;
    private ImageButton back;
    private ImageButton uploadOptions;
    private EditText searchKey;

    private BubbleDialog uploadDialog;

    private ListView healthListView;
    private HealthListAdapter healthListAdapter;

    // 原始数据源
    private List<Map<String, String>> healthListOrigin;
    // 处理后的数据源
    private List<Map<String, String>> healthList;

    public SharedPreferences sharedPreferences;

    // 最近一条数据显示的组件
    private TextView uploadTime;
    private TextView distance;
    private TextView heat;
    private TextView sleepQuality;
    private TextView heartRate;
    private TextView evaluation;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_health, container, false);

        //初始化组件
        initView();

        //显示healthFront,隐藏healthBg
        showFront();

        // 获取历史数据
        getHealthListOrigin();

        // 获取最近一条数据
        getLatestData();

        return view;
    }

    private void initView(){

        // 用户信息存在sharedPreferences
        sharedPreferences = this.getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);

        healthFront = (LinearLayout) view.findViewById(R.id.healthFront);
        healthBg = (LinearLayout) view.findViewById(R.id.healthBg);
        searchHistory = (RelativeLayout) view.findViewById(R.id.searchHistory);
        back = (ImageButton) view.findViewById(R.id.back);
        searchKey = view.findViewById(R.id.searchKey);
        healthListView = view.findViewById(R.id.healthListView);
        uploadOptions = view.findViewById(R.id.uploadOptions);

        dialogView = LayoutInflater.from(HealthFragment.this.getActivity()).inflate(R.layout.dialog_health_upload_options, null);

        // 首页显示的最近一条数据的控件
        uploadTime = (TextView)view.findViewById(R.id.uploadTime);
        distance = (TextView)view.findViewById(R.id.distance);
        heat = (TextView)view.findViewById(R.id.heat);
        sleepQuality = (TextView)view.findViewById(R.id.sleepQuality);
        heartRate = (TextView)view.findViewById(R.id.heartRate);
        evaluation = (TextView)view.findViewById(R.id.evaluation);

        healthList = new ArrayList<>();
        healthListOrigin = new ArrayList<>();
        healthListAdapter = new HealthListAdapter(this.getActivity(), healthList, R.layout.fragment_health_list_item);

        uploadDialog = new BubbleDialog(HealthFragment.this.getActivity())
                .addContentView(dialogView)
                .autoPosition(Auto.UP_AND_DOWN)
                .calBar(true)
                .softShowUp();

        dialogView.findViewById(R.id.upload).setOnClickListener(this);
        dialogView.findViewById(R.id.dataAnalyze).setOnClickListener(this);
        healthFront.setOnClickListener(this);
        healthBg.setOnClickListener(this);
        searchHistory.setOnClickListener(this);
        back.setOnClickListener(this);
        uploadOptions.setOnClickListener(this);
        healthListView.setAdapter(healthListAdapter);
        healthListView.setOnItemClickListener(this);

        // 监听搜索框内容变化
        searchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getHealthList(s.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            getLatestData();
            getHealthListOrigin();
        }
        showFront();
    }

    //重写点击事件
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                showFront();
                break;
            case R.id.searchHistory:
                showBg();
                onResume();
                break;
            case R.id.uploadOptions:
                //显示上传数据对话框
                uploadDialog.setClickedView(uploadOptions).show();
                break;
            case R.id.upload:
                jumpUploadPage();
                break;
            case R.id.dataAnalyze:
                startActivity(new Intent(this.getActivity(), DataAnalyzeActivity.class));
                uploadDialog.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getActivity(), HealthDetailActivity.class);
        intent.putExtra("index", healthList.get(position).get("index"));
        startActivityForResult(intent, 1);
    }

    //显示healthFront,隐藏healthBg
    public void showFront(){
        healthBg.setVisibility(View.GONE);
        healthFront.setVisibility(View.VISIBLE);

        // 隐藏键盘
        InputMethodManager inputManager =
                (InputMethodManager) searchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(searchKey.getWindowToken(), 0);
    }

    //显示healthBg,隐藏healthFront
    private void showBg(){
        healthBg.setVisibility(View.VISIBLE);
        healthFront.setVisibility(View.GONE);

        searchKey.setText("");
        searchKey.setFocusable(true);
        searchKey.setFocusableInTouchMode(true);
        searchKey.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) searchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(searchKey, 0);
    }


    // 跳转到上传数据页面
    private void jumpUploadPage(){
        startActivityForResult(new Intent(this.getActivity(), UploadActivity.class), 0);
        uploadDialog.dismiss();
    }

    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(healthFront.getVisibility() == View.GONE &&  keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    Log.e("Tag","点击了返回键");
                    showFront();
                    return true;
                }
                return false;
            }
        });
    }

    // 获取HealthListOrigin原始数据
    private void getHealthListOrigin(){
        healthListOrigin.clear();

        // --------------从后台获取数据-------------

        Handler handler = new HistoryDataHandler(this.getActivity());
        HistoryDataRequest request = new HistoryDataRequest(this.getActivity(), handler);
        Map<String, String> params = new HashMap<>();
        params.put("token", sharedPreferences.getString("token", ""));
        request.doPost(params);
//        Map<String, String> param = new HashMap<>();
//        param.put("userid", sharedPreferences.getString("id", "")); // 获取用户id
//        request.doPost(param);


//        String[] servicesTest = new String[]{"地方", "Cindy", "y灾难", "din", "我是", "再见", "cu", "c差", "$电风扇", "fds", "wie", "sdf", "fdsa"};
//        for (Integer i = 0; i < servicesTest.length; i++) {
//            Map<String, String> showItem = new HashMap<>();
//            showItem.put("time", i.toString());
//            showItem.put("eval", servicesTest[i]);
//            showItem.put("dataAddr", servicesTest[i]);

//            healthListOrigin.add(showItem);
//        }
        // --------------------------------------------
    }

    public void getLatestData(){
        // 将数据显示到控件上
        Handler handler = new LatestDataHandler(this.getActivity());
        LatestDataRequest request = new LatestDataRequest(this.getActivity(), handler);
        String token = sharedPreferences.getString("token", ""); // 获取用户id
        Map<String, String> item = new HashMap<>();
        item.put("token", token);
        item.put("index", "-1");
        request.doPost(item);
    }

    // 根据关键字更新获取MedicalList数据
    private void getHealthList(String keywords){
        healthList.clear();
//        if(keywords.equals(""))
//            return;
        for(Map<String, String> item: healthListOrigin){
            if(item.get("eval").startsWith(keywords))
                healthList.add(item);
        }
        healthListAdapter.notifyDataSetChanged();
    }

    /**
     * 历史数据
     */
    public class HistoryDataHandler extends Handler {

        private Context context;

        public HistoryDataHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            JSONArray arry = response.getJSONArray("_data");

                            for(Integer i=0; i<arry.length(); i++){
                                Map<String, String> map = new HashMap<>();
                                // 时间格式化
                                String time = arry.getJSONObject(i).getString("uploadTime").substring(5, 10);
                                map.put("time", time);
                                map.put("eval", arry.getJSONObject(i).getString("evaluation"));
                                map.put("index", arry.getJSONObject(i).getString("index"));
                                healthListOrigin.add(map);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    /**
     * 最新一条数据  ---  直接返回具体数据
     */
    public class LatestDataHandler extends Handler {

        private Context context;

        public LatestDataHandler(Context context){
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        JSONObject response = (JSONObject) msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            JSONObject latestData = response.getJSONObject("_data");
                            setData(latestData.getString("uploadTime"), latestData.getString("distance"), latestData.getString("heat"),
                                    latestData.getString("sleepQuality"), latestData.getString("heartRate"), latestData.getString("evaluation"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

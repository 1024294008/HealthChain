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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.adapter.DescriptionListAdapter;
import cn.edu.seu.http.RequestAction.UserVisitorRequest;

public class UserVisitorsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private ListView visitorsListView;
    private List<Map<String, String>> visitorsList;
    private DescriptionListAdapter descriptionListAdapter;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_visitors);
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

        back = (ImageView) this.findViewById(R.id.back);
        visitorsListView = (ListView) this.findViewById(R.id.visitorsListView);
        visitorsList = new ArrayList<>();
        descriptionListAdapter = new DescriptionListAdapter(this, visitorsList, R.layout.activity_description_list_item);

        back.setOnClickListener(this);
        visitorsListView.setAdapter(descriptionListAdapter);
        getVisitor();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回，返回到上一级页面
            case R.id.back:
                finish();
                break;
        }
    }

    private void getVisitor(){
        // 测试数据

        // 访客记录表
        Handler handler = new UserVisitorHandler(UserVisitorsActivity.this);
        UserVisitorRequest request = new UserVisitorRequest(UserVisitorsActivity.this, handler);

        String token = sharedPreferences.getString("token", "");

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        request.doPost(params);

    }

    // 访客记录 -- xxx于xx时间访问了您的健康数据
    public class UserVisitorHandler extends Handler {

        private Context context;

        public UserVisitorHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容   ---  添加列表数据
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");
                        if("200".equals(_code)){
                            JSONArray dataList = response.getJSONArray("_data");
                            for(Integer i = dataList.length() - 1; i >= 0; i--){
                                String organizationName = dataList.getJSONObject(i).getString("organizationName");
                                String visitTime = dataList.getJSONObject(i).getString("visitTime");
                                String dis = organizationName + "于" + visitTime + "访问了您的健康数据";
                                Map<String, String> item = new HashMap<>();
                                item.put("description", dis);
                                visitorsList.add(item);
                            }
                            descriptionListAdapter.notifyDataSetChanged();
                            break;
                        }
                        else{
                            Toast.makeText(context, "访客记录查询失败", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "访客记录查询失败", Toast.LENGTH_SHORT).show();
                        break;
                    }

            }
        }
    }
}
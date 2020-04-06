package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.adapter.DescriptionListAdapter;

public class UserVisitorsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private ListView visitorsListView;
    private List<Map<String, String>> visitorsList;
    private DescriptionListAdapter descriptionListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_visitors);
        initView();
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
        Map<String, String> item = new HashMap<>();
        item.put("description", "东南大学附属医院于2020年4月6日访问了您的健康数据");
        visitorsList.add(item);;

        descriptionListAdapter.notifyDataSetChanged();
    }
}
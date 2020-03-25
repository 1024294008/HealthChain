package cn.edu.seu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xujiaji.happybubble.Auto;
import com.xujiaji.happybubble.BubbleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.edu.seu.R;
import cn.edu.seu.adapter.HealthListAdapter;
import cn.edu.seu.adapter.MedicalListAdapter;

/**
 * 健康数据显示界面
 */
public class HealthFragment extends Fragment  implements View.OnClickListener{
    private View view;

    private LinearLayout healthFront;
    private LinearLayout healthBg;
    private RelativeLayout searchHistory;
    private ImageButton back;
    private ImageButton uploadOptions;
    private EditText searchKey;

    private ListView healthListView;
    private HealthListAdapter healthListAdapter;

    // 原始数据源
    private List<Map<String, String>> healthListOrigin;
    // 处理后的数据源
    private List<Map<String, String>> healthList;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_health, container, false);

        //初始化组件
        initView();

        //显示healthFront,隐藏healthBg
        showFront();

        getHealthListOrigin();

        return view;
    }

    private void initView(){
        healthFront = (LinearLayout) view.findViewById(R.id.healthFront);
        healthBg = (LinearLayout) view.findViewById(R.id.healthBg);
        searchHistory = (RelativeLayout) view.findViewById(R.id.searchHistory);
        back = (ImageButton) view.findViewById(R.id.back);
        searchKey = view.findViewById(R.id.searchKey);
        healthListView = view.findViewById(R.id.healthListView);
        uploadOptions = view.findViewById(R.id.uploadOptions);

        healthList = new ArrayList<>();
        healthListOrigin = new ArrayList<>();
        healthListAdapter = new HealthListAdapter(this.getActivity(), healthList, R.layout.fragment_health_list_item);

        healthFront.setOnClickListener(this);
        healthBg.setOnClickListener(this);
        searchHistory.setOnClickListener(this);
        back.setOnClickListener(this);
        uploadOptions.setOnClickListener(this);
        healthListView.setAdapter(healthListAdapter);

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
                showOptionsDialog();
                break;
        }
    }

    //显示healthFront,隐藏healthBg
    private void showFront(){
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
    }

    //显示上传数据对话框
    private void showOptionsDialog(){
        new BubbleDialog(HealthFragment.this.getActivity())
                .addContentView(LayoutInflater.from(HealthFragment.this.getActivity()).inflate(R.layout.dialog_health_upload_options, null))
                .setClickedView(uploadOptions)
                .autoPosition(Auto.UP_AND_DOWN)
                .calBar(true)
                .softShowUp()
                .show();
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
        String[] servicesTest = new String[]{"地方", "Cindy", "y灾难", "din", "我是", "再见", "cu", "c差", "$电风扇", "fds", "wie", "sdf", "fdsa"};
        for (Integer i = 0; i < servicesTest.length; i++) {
            Map<String, String> showItem = new HashMap<>();
            showItem.put("time", i.toString());
            showItem.put("eval", servicesTest[i]);
            showItem.put("dataAddr", servicesTest[i]);
            healthListOrigin.add(showItem);
        }
        // --------------------------------------------
    }

    // 根据关键字更新获取MedicalList数据
    private void getHealthList(String keywords){
        healthList.clear();
        if(keywords.equals(""))
            return;
        for(Map<String, String> item: healthListOrigin){
            if(item.get("eval").startsWith(keywords))
                healthList.add(item);
        }
        healthListAdapter.notifyDataSetChanged();
    }

}

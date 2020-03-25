package cn.edu.seu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
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
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.edu.seu.R;
import cn.edu.seu.adapter.MedicalListAdapter;

/**
 * 医疗服务显示界面
 */

public class MedicalFragment extends Fragment implements View.OnClickListener {
    private View view;

    //声明组件
    private ImageButton back;
    private ImageButton search;

    private EditText searchKey;

    private RelativeLayout medicalFront;
    private RelativeLayout medicalBg;

    private ListView medicalListViewFront;
    private ListView medicalListViewBg;

    private MedicalListAdapter medicalListAdapterFront;
    private MedicalListAdapter medicalListAdapterBg;

    // 数据源
    private List<Map<String, String>> medicalListFront;
    private List<Map<String, String>> medicalListBg;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medical, container, false);

        //初始化组件
        initView();

        // 请求数据
        getMedicalListFront();

        //显示medicalSearch,隐藏medicalBg
        showFront();

        return view;
    }

    //获取组件
    private void initView(){
        back = (ImageButton) view.findViewById(R.id.back);
        search = (ImageButton) view.findViewById(R.id.search);
        medicalFront = (RelativeLayout) view.findViewById(R.id.medicalFront);
        medicalBg = (RelativeLayout) view.findViewById(R.id.medicalBg);
        searchKey = view.findViewById(R.id.searchKey);

        medicalListViewFront = view.findViewById(R.id.medicalListViewFront);
        medicalListViewBg = view.findViewById(R.id.medicalListViewBg);

        medicalListFront = new ArrayList<>();
        medicalListBg = new ArrayList<>();

        medicalListAdapterFront = new MedicalListAdapter(this.getActivity(), medicalListFront, R.layout.fragment_medical_list_item, 0);
        medicalListAdapterBg = new MedicalListAdapter(this.getActivity(), medicalListBg, R.layout.fragment_medical_list_item, 1);

        medicalListViewFront.setAdapter(medicalListAdapterFront);
        medicalListViewBg.setAdapter(medicalListAdapterBg);
        back.setOnClickListener(this);
        search.setOnClickListener(this);

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
                getMedicalListBg(s.toString());
            }
        });
    }


    //重写监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                showFront();
                break;
            case R.id.search:
                showBg();
                onResume();
                break;
        }
    }

    //显示medicalSearch,隐藏medicalBg
    private void showFront(){
        // 隐藏键盘
        InputMethodManager inputManager =
                (InputMethodManager) searchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(searchKey.getWindowToken(), 0);

        medicalListViewFront.setVisibility(View.VISIBLE);
        medicalListViewBg.setVisibility(View.INVISIBLE);
        medicalBg.setVisibility(View.GONE);
        medicalFront.setVisibility(View.VISIBLE);
    }

    //显示medicalBg,隐藏medicalSearch
    private void showBg(){
        medicalListBg.clear();
        medicalListAdapterBg.notifyDataSetChanged();
        medicalListViewFront.setVisibility(View.INVISIBLE);
        medicalListViewBg.setVisibility(View.VISIBLE);
        medicalBg.setVisibility(View.VISIBLE);
        medicalFront.setVisibility(View.GONE);

        searchKey.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusable(true);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(medicalBg.getVisibility() == View.VISIBLE &&  keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                   Log.e("Tag","点击了返回键");
                   showFront();
                   return true;
                }
                else{
                    Log.e("Tag","点击了返回键-------------");
                    return false;
                }

            }
        });
    }

    // 获取MedicalListFront
    private void getMedicalListFront(){
        medicalListFront.clear();

        // 预留1位
        Map<String, String> firstItem = new HashMap<>();
        firstItem.put("serviceName", "serviceHistory");
        medicalListFront.add(firstItem);

        // --------------从后台获取数据-------------
        String[] servicesTest = new String[]{"地方", "Cindy", "y灾难", "din", "我是", "再见", "cu", "c差", "$电风扇", "fds", "wie", "sdf", "fdsa"};
        for (Integer i = 0; i < servicesTest.length; i++) {
            Map<String, String> showItem = new HashMap<>();
            showItem.put("serviceId", i.toString());
            showItem.put("serviceName", servicesTest[i]);
            medicalListFront.add(showItem);
        }
        // --------------------------------------------

        medicalListAdapterFront.notifyDataSetChanged();
    }

    // 通过关键字更新MedicalListBg
    private void getMedicalListBg(String keywords){
        medicalListBg.clear();
        if(keywords.equals(""))
            return;
        for(Map<String, String> item: medicalListFront){
            if(item.get("serviceName").startsWith(keywords))
                medicalListBg.add(item);
        }
        medicalListAdapterBg.notifyDataSetChanged();
    }
}

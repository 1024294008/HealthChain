package cn.edu.seu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.edu.seu.activity.MedicalDetailActivity;
import cn.edu.seu.activity.ServiceHistoryActivity;
import cn.edu.seu.adapter.MedicalListAdapter;
import cn.edu.seu.common.PortraitManager;
import cn.edu.seu.http.RequestAction.AllMedicalServiceRequest;

/**
 * 医疗服务显示界面
 */

public class MedicalFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
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
        medicalListViewFront.setOnItemClickListener(this);
        medicalListViewBg.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getActivity(), MedicalDetailActivity.class);
        switch (parent.getId()){
            case R.id.medicalListViewFront:
                if(position == 0){
                    intent = new Intent(this.getActivity(), ServiceHistoryActivity.class);
                    break;
                }
                intent.putExtra("serviceId", medicalListFront.get(position).get("serviceId"));
                break;
            case R.id.medicalListViewBg:
                intent.putExtra("serviceId", medicalListBg.get(position).get("serviceId"));
                break;
        }
        this.getActivity().startActivity(intent);
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
        searchKey.setFocusable(true);
        searchKey.setFocusableInTouchMode(true);
        searchKey.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) searchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(searchKey, 0);
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
//        String[] servicesTest = new String[]{"地方", "Cindy", "y灾难", "din", "我是", "再见", "cu", "c差", "$电风扇", "fds", "wie", "sdf", "fdsa"};
//        for (Integer i = 0; i < servicesTest.length; i++) {
//            Map<String, String> showItem = new HashMap<>();
//            showItem.put("serviceId", i.toString());
//            showItem.put("serviceName", servicesTest[i]);
//            showItem.put("portrait", PortraitManager.getPortrait().toString());
//            medicalListFront.add(showItem);
//        }

        Handler handler = new AllMedicalServiceHandler(this.getActivity()); // 数据在这里获取
        AllMedicalServiceRequest request = new AllMedicalServiceRequest(this.getActivity(), handler);
        request.doGet();

        // --------------------------------------------
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

    /**
     * 查找所有医疗服务页面
     */
    public class AllMedicalServiceHandler extends Handler {

        public Context context;

        public AllMedicalServiceHandler(Context context){
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    // 具体执行内容
                    try {
                        JSONObject response = (JSONObject)msg.obj;
                        String _code = response.getString("_code");

                        if("200".equals(_code)){
                            JSONObject _data = response.getJSONObject("_data");
                            JSONObject medicalServiceList = _data.getJSONObject("medicalServiceList");
                            JSONArray data = medicalServiceList.getJSONArray("data");

//                            Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();

                            for(Integer i=0; i<data.length(); i++){
                                Map<String, String> map = new HashMap<>();
                                map.put("serviceId", String.valueOf(data.getJSONObject(i).getInt("id")));
                                map.put("serviceName", data.getJSONObject(i).getString("serviceName"));
                                map.put("portrait", data.getJSONObject(i).getString("portrait"));
                                medicalListFront.add(map);
                            }
                        }
                        else{
                            Toast.makeText(context, "医疗服务列表查找失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "医疗服务列表查找失败", Toast.LENGTH_SHORT).show();
                    }
                    medicalListAdapterFront.notifyDataSetChanged();
                    break;
            }
        }
    }
}

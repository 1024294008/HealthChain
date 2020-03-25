package cn.edu.seu.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
public class MedicalFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medical, container, false);
        initView();
        return view;
    }

    private void initView(){
        // 测试
        String[] acrs = new String[]{"545", "cindy", "地点", "Bar", "Ck", "David", "我是"};
        List<Map<String, String>> medicalList = new ArrayList<>();
        for (int i = 0; i < acrs.length; i++) {
            Map<String, String> showItem = new HashMap<>();
            showItem.put("serviceName", acrs[i]);
            medicalList.add(showItem);
        }

        ListView listView = view.findViewById(R.id.medicalListViewFront);
        MedicalListAdapter medicalListAdapter = new MedicalListAdapter(this.getActivity(), medicalList, R.layout.fragment_medical_list_item, 0);

        listView.setAdapter(medicalListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this.getActivity(), "hello", Toast.LENGTH_LONG).show();
    }
}

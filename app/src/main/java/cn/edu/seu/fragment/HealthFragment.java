package cn.edu.seu.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.edu.seu.R;

/**
 * 健康数据显示界面
 */
public class HealthFragment extends Fragment {
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_health, container, false);
        initView();
        return view;
    }

    private void initView(){

    }
}

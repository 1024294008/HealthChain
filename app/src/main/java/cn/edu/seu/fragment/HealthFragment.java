package cn.edu.seu.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.edu.seu.R;

/**
 * 健康数据显示界面
 */
public class HealthFragment extends Fragment  implements View.OnClickListener{
    private View view;

    private LinearLayout healthFront;
    private LinearLayout healthBg;
    private RelativeLayout searchHistory;
    private ImageButton back;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_health, container, false);

        //初始化组件
        initView();

        //显示healthFront,隐藏healthBg
        showFront();

        return view;
    }

    private void initView(){
        healthFront = (LinearLayout) view.findViewById(R.id.healthFront);
        healthBg = (LinearLayout) view.findViewById(R.id.healthBg);
        searchHistory = (RelativeLayout) view.findViewById(R.id.searchHistory);
        back = (ImageButton) view.findViewById(R.id.back);

        healthFront.setOnClickListener(this);
        healthBg.setOnClickListener(this);
        searchHistory.setOnClickListener(this);
        back.setOnClickListener(this);
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
        }
    }

    //显示healthFront,隐藏healthBg
    private void showFront(){
        healthBg.setVisibility(View.GONE);
        healthFront.setVisibility(View.VISIBLE);
    }

    //显示healthBg,隐藏healthFront
    private void showBg(){
        healthBg.setVisibility(View.VISIBLE);
        healthFront.setVisibility(View.GONE);
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
}

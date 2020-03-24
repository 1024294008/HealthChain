package cn.edu.seu.fragment;

import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.edu.seu.R;

/**
 * 医疗服务显示界面
 */
public class MedicalFragment extends Fragment implements View.OnClickListener{
    private View view;

    //声明组件
    private ImageButton back;
    private ImageButton search;

    private RelativeLayout medicalFront;
    private RelativeLayout medicalBg;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medical, container, false);

        //初始化组件
        initView();

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

        back.setOnClickListener(this);
        search.setOnClickListener(this);
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
            default:
                onResume();
                break;
        }
    }

    //显示medicalSearch,隐藏medicalBg
    private void showFront(){
        medicalBg.setVisibility(View.GONE);
        medicalFront.setVisibility(View.VISIBLE);
    }

    //显示medicalBg,隐藏medicalSearch
    private void showBg(){
        medicalBg.setVisibility(View.VISIBLE);
        medicalFront.setVisibility(View.GONE);
    }




    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(medicalFront.getVisibility() == View.GONE &&  keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    System.out.println("---------------");
                    System.out.println("点击了返回键");
                    System.out.println("---------------");
                    showFront();
                    return true;
                }
                System.out.println("按下了返回键");
                return false;
            }
        });
    }

}

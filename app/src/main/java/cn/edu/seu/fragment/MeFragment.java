package cn.edu.seu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.edu.seu.R;
import cn.edu.seu.activity.MainActivity;
import cn.edu.seu.activity.UserActivity;

/**
 * 医疗服务显示界面
 */
public class MeFragment extends Fragment {
    private View view;

    //声明页面中的控件
    private LinearLayout user;
    private LinearLayout wallet;
    private LinearLayout device;
    private LinearLayout set;
    private LinearLayout visitor;
    private TextView username;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);

        //初始化组件
        initView();

        //点击用户，跳转到用户信息页面
        goUser(user);

        //点击钱包，跳转到钱包页面
        goWallet(wallet);

        //点击智能设备，跳转到设备界面
        goDevice(device);

        //点击访客记录，跳转到访客记录页面
        goVisitor(visitor);

        //点击设置，跳转到设置页面
        goSet(set);

        return view;
    }

    //初始化组件
    private void initView(){
        user = (LinearLayout) view.findViewById(R.id.user);
        wallet = (LinearLayout) view.findViewById(R.id.wallet);
        device = (LinearLayout) view.findViewById(R.id.device);
        set = (LinearLayout) view.findViewById(R.id.set);
        visitor  = (LinearLayout) view.findViewById(R.id.visitor);
        username = (TextView) view.findViewById(R.id.username);

        //读取sharedpreferences中的数据
        SharedPreferences read = getActivity().getSharedPreferences("test",Context.MODE_PRIVATE);
        String name = read.getString("username", "");
        //设置账户名称idName
        username.setText(name);
    }

    //点击用户，跳转到用户信息页面
    private void goUser(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
            }
        });
    }

    //点击钱包，跳转到钱包页面
    private void goWallet(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //点击智能设备，跳转到设备界面
    private void goDevice(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //点击访客，跳转到访客记录页面
    private void goVisitor(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //点击设置，跳转到设置页面
    private void goSet(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

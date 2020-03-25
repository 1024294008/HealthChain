package cn.edu.seu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.fragment.HealthFragment;
import cn.edu.seu.fragment.MeFragment;
import cn.edu.seu.fragment.MedicalFragment;
import cn.edu.seu.http.RequestAction.LatestDataRequest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CharArrayBuffer;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private Fragment healthFragment;
    private Fragment medicalFragment;
    private Fragment meFragment;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){

        // 用户信息存在sharedPreferences
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
        //获取控件
        RadioButton editTab = findViewById(R.id.healthTab);
        RadioButton browserTab = findViewById(R.id.medicalTab);
        RadioButton setTab = findViewById(R.id.meTab);
        RadioGroup mainTab = findViewById(R.id.mainTab);

        healthFragment = new HealthFragment();
        medicalFragment = new MedicalFragment();
        meFragment = new MeFragment();

        //调整tab图片
        Drawable drawables[] = editTab.getCompoundDrawables();
        drawables[1].setBounds(new Rect(0, 0, drawables[1].getMinimumWidth()/8, drawables[1].getMinimumHeight()/8));
        editTab.setCompoundDrawables(null,drawables[1],null,null);
        drawables = browserTab.getCompoundDrawables();
        drawables[1].setBounds(new Rect(0, 0, drawables[1].getMinimumWidth()/8, drawables[1].getMinimumHeight()/8));
        browserTab.setCompoundDrawables(null,drawables[1],null,null);
        drawables = setTab.getCompoundDrawables();
        drawables[1].setBounds(new Rect(0, 0, drawables[1].getMinimumWidth()/8, drawables[1].getMinimumHeight()/8));
        setTab.setCompoundDrawables(null,drawables[1],null,null);

        //添加fragment
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.content, healthFragment);
        transaction.add(R.id.content, medicalFragment);
        transaction.add(R.id.content, meFragment);
        transaction.hide(medicalFragment);
        transaction.hide(meFragment);
        transaction.commit();

        //设置tab选项卡切换事件
        mainTab.setOnCheckedChangeListener(this);
    }

    //处理tab选项卡切换事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (checkedId){
            case R.id.healthTab:
                transaction.show(healthFragment);
                transaction.hide(medicalFragment);
                transaction.hide(meFragment);
                break;
            case R.id.medicalTab:
                transaction.hide(healthFragment);
                transaction.show(medicalFragment);
                transaction.hide(meFragment);
                break;
            case R.id.meTab:
                transaction.hide(healthFragment);
                transaction.hide(medicalFragment);
                transaction.show(meFragment);
                break;
        }
        transaction.commit();
    }

}

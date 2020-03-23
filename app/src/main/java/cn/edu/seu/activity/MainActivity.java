package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;
import cn.edu.seu.fragment.HealthFragment;
import cn.edu.seu.fragment.MeFragment;
import cn.edu.seu.fragment.MedicalFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private Fragment healthFragment;
    private Fragment medicalFragment;
    private Fragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
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

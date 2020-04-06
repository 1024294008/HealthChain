package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionRecordDetailActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private ImageView back;
    private TextView transactType;
    private TextView ethAddress;
    private TextView transactEth;
    private TextView transactTime;
    private TextView transactRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_record_detail);
        initView();
    }

    private void initView(){
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

        back = findViewById(R.id.back);
        transactType = findViewById(R.id.transactType);
        ethAddress = findViewById(R.id.ethAddress);
        transactEth = findViewById(R.id.transactEth);
        transactTime = findViewById(R.id.transactTime);
        transactRemarks = findViewById(R.id.transactRemarks);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionRecordDetailActivity.this.finish();
            }
        });

        setContent();
    }

    // 设置数据
    private void setContent(){
        String transactionId = getIntent().getStringExtra("id");
        if(getIntent().getStringExtra("type").equals("0")){
            transactType.setText("以太币支出");
        } else {
            transactType.setText("以太币收入");
        }

        // 根据transactionId和token获取该条转账信息，需要设置一下信息
        // ethAddress.setText();  对方账户的以太坊地址（要区分收入和支出的情况）
        // transactEth.setText();   转账金额
        //transactTime.setText();   转账时间
        //transactRemarks.setText();  转账备注
    }
}

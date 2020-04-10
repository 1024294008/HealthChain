package cn.edu.seu.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.common.CoinTransManager;
import cn.edu.seu.http.HttpHandler.TransferToOrgHandler;
import cn.edu.seu.http.RequestAction.TransferToOrgRequest;

public class BuyServiceDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String receiverEthAddress; // 接收方以太坊地址
    private String value; // 转账金额
    private EditText purchasePassword;
    private RelativeLayout loadProgress;

    public SharedPreferences sharedPreferences;

    public BuyServiceDialog(@NonNull Context context, String receiverEthAddress, String value, RelativeLayout loadProgress)  {
        super(context);
        this.context = context;
        this.receiverEthAddress = receiverEthAddress;
        this.value = value;
        this.loadProgress = loadProgress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_transfer);
        initView();
    }

    // 初始化组件
    private void initView(){
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);

        // 设置对话框属性
        setCanceledOnTouchOutside(true);
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int)((context.getResources().getDisplayMetrics()).widthPixels * 0.8);
        p.height = (int)((context.getResources().getDisplayMetrics()).heightPixels * 0.4);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setAttributes(p);

        // 获取控件
        purchasePassword = findViewById(R.id.purchasePassword);

        ((TextView)findViewById(R.id.transferValue)).setText("$ " + this.value);

        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.confirmTransfer).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                BuyServiceDialog.this.dismiss();
                break;
            case R.id.confirmTransfer:
                startTransfer();
                break;
        }
    }

    // 转账
    private void startTransfer(){
        // 先判断密码
        if(purchasePassword.getText().toString().equals("")){
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!purchasePassword.getText().toString().equals(sharedPreferences.getString("password", ""))){
            Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        // 用户向机构转账
        // 转账
        // 转账方token：sharedPreferences.getString("token", "")
        // 接收方以太坊地址（机构）：this.receiverEthAddress
        // 转账金额：this.value

        String token = sharedPreferences.getString("token", "");
        String receiverEthAddr = this.receiverEthAddress;
        String val = CoinTransManager.transToEth(this.value);

        this.dismiss();
        loadProgress.setVisibility(View.VISIBLE);

        Handler handler = new TransferToOrgHandler(context, loadProgress);
        TransferToOrgRequest request = new TransferToOrgRequest(context, handler);

        Map<String, String>params = new HashMap<>();
        params.put("token", token);
        params.put("receiverEthAddr", receiverEthAddr);
        params.put("value", val);
        params.put("transactRemarks", "购买服务");

        request.doPost(params);
    }
}

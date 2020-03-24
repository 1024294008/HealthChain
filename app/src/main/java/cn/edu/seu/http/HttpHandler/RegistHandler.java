package cn.edu.seu.http.HttpHandler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import cn.edu.seu.R;
import cn.edu.seu.activity.MainActivity;
import cn.edu.seu.activity.RegisterActivity;

/**
 * 注册
 */
public class RegistHandler extends Handler {

    private Context context;

    public RegistHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                // 具体执行内容
                //提示注册成功
                Toast toast = Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT);
                toast.show();
                // 关闭当前页面 --> 返回到登录页面
                RegisterActivity curr_act = (RegisterActivity)context;
                curr_act.finish();
                break;
        }
    }
}

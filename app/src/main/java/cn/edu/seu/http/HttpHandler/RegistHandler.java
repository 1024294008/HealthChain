package cn.edu.seu.http.HttpHandler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.seu.R;
import cn.edu.seu.activity.MainActivity;
import cn.edu.seu.activity.RegisterActivity;

/**
 * 注册
 */
public class RegistHandler extends Handler {

    private Context context;
    private RelativeLayout loadProgress;

    public RegistHandler(Context context, RelativeLayout loadProgress) {
        this.context = context;
        this.loadProgress = loadProgress;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                // 具体执行内容
                //提示注册成功
                JSONObject response = (JSONObject)msg.obj;
                try {
                    String _code = response.getString("_code");
                    if("200".equals(_code)){
                        Toast toast = Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT);
                        toast.show();
                        // 关闭当前页面 --> 返回到登录页面
                        RegisterActivity curr_act = (RegisterActivity)context;
                        curr_act.finish();
                    }
                    else{
                        Toast toast = Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                } catch (JSONException e) {
                    Toast toast = Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT);
                    toast.show();
                    e.printStackTrace();
                } finally {
                    loadProgress.setVisibility(View.INVISIBLE);
                }

        }
    }
}

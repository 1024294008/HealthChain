package cn.edu.seu.http.HttpHandler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.seu.activity.LoginActivity;
import cn.edu.seu.activity.MainActivity;

/**
 * 登录
 */
public class LoginHandler extends Handler {

    private Context context;
    public SharedPreferences sharedPreferences;
    private RelativeLayout loadProgress;

    public LoginHandler(Context context, RelativeLayout loadProgress) {
        this.context = context;
        this.loadProgress = loadProgress;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                // 具体执行内容
//                Toast t = Toast.makeText(context, msg.obj.toString() + "login", Toast.LENGTH_SHORT);
//                t.show();

                JSONObject response = (JSONObject)msg.obj;  // 请求到的数据

                //保存用户名到sharedPerferences中
                sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();       //获取编辑器
                try {
                    String _code = response.getString("_code"); // 状态码
                    if("200".equals(_code)) {
                        JSONObject _data = response.getJSONObject("_data"); // 数据
                        String token = _data.getString("token");    // token
                        JSONObject userInfo = _data.getJSONObject("userInfo");  // 用户信息

                        editor.putString("id", String.valueOf(userInfo.getInt("id")));
                        editor.putString("username", userInfo.getString("account"));
                        editor.putString("portrait", String.valueOf(userInfo.getInt("portrait")));
                        editor.putString("nickName", userInfo.getString("nickname"));
                        editor.putString("password", userInfo.getString("password"));
                        editor.putString("ethAddress", userInfo.getString("ethAddress"));
                        editor.putString("sex", userInfo.getString("sex"));
                        editor.putString("address", userInfo.getString("address"));
                        editor.putString("birth", userInfo.getString("birth"));
                        editor.putString("tel", userInfo.getString("tel"));
                        editor.putString("token", token);
                        editor.commit(); //提交修改
                    }
                    else {
                        Toast.makeText(context, "账户信息错误,请重新确认", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Toast t = Toast.makeText(context, "请求错误,请重试", Toast.LENGTH_SHORT);
                    t.show();
                    e.printStackTrace();
                    return;
                } finally {
                    loadProgress.setVisibility(View.INVISIBLE);
                }

                //跳转到主页面
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((LoginActivity)context).finish();
                break;
        }
    }
}

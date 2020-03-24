package cn.edu.seu.http.HttpHandler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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

    public LoginHandler(Context context) {
        this.context = context;
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
                    String textUser = response.getString("account");    // 提取账号信息
                    editor.putString("username", textUser);                           //key-value
                    editor.commit();                                                  //提交修改
                } catch (JSONException e) {
                    Toast t = Toast.makeText(context, "请求错误,请重试", Toast.LENGTH_SHORT);
                    t.show();
                    e.printStackTrace();
                }

                //跳转到主页面
                Intent intent = new Intent(context, MainActivity.class);

                context.startActivity(intent);
                break;
        }
    }
}

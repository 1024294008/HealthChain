package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查看用户个人钱包  -- 账户余额
 */
public class UserBalanceRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public UserBalanceRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet(String id)
    {
        String url = "";
        httpRequest.setUrl(url);
        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
        String url = Url.USER_BALANCE_URL;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}


package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查看用户个人钱包
 */
public class UserWalletRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public UserWalletRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet(String id)
    {
        String url = Url.USER_WALLET_URL + "?id" + id;
        httpRequest.setUrl(url);
        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
//        String url = "";
//        httpRequest.setUrl(url);
//        httpRequest.post(param);
    }
}


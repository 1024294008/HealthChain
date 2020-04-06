package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 向用户转账
 */
public class TransferToUserRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public TransferToUserRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet()
    {
        String url = "http://192.168.31.112:80/json.html";
        httpRequest.setUrl(url);
        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
        String url = Url.USER_TRANSFER_TO_USER;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}

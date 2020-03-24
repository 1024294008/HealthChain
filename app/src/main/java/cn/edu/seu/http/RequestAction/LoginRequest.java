package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;

public class LoginRequest {

    private final String url = "http://192.168.31.112:80/json.html";
    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public LoginRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet()
    {
        httpRequest.setUrl(this.url);
        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
        httpRequest.setUrl(this.url);
        httpRequest.post(param);
    }
}

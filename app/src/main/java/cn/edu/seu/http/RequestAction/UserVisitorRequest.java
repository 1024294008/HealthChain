package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 *  用户访客记录
 */
public class UserVisitorRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public UserVisitorRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }


    public void doGet(int id)
    {
        String url = "";
        httpRequest.setUrl(url);
        httpRequest.get();
    }


    public void doPost(Map<String, String> param)
    {
        String url = Url.VISIT_DATA_URL;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}


package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;

public class OrganizationDetailRequest {

    private final String url = "";
    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public OrganizationDetailRequest(Context context, Handler handler) {
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


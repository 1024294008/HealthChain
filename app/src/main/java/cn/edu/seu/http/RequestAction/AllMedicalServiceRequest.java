package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查找所有医疗服务
 */
public class AllMedicalServiceRequest {
    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public AllMedicalServiceRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet()
    {
        String url = Url.ALL_MEDICAL_SERVICE_URL;
//        String url = "http://192.168.31.112:80/json.html";
        httpRequest.setUrl(url);
        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
//        String url = Url.ALL_MEDICAL_SERVICE_URL;
//        httpRequest.setUrl(url);
//        httpRequest.post(null);
    }
}

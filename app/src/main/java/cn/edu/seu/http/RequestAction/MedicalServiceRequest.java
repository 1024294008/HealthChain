package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查看医疗服务详细信息
 */
public class MedicalServiceRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public MedicalServiceRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet(int id)
    {
        String url = Url.MEDICAL_SERVICE_URL + "?id=" + id;
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


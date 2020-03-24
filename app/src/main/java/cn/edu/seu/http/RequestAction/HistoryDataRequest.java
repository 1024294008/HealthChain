package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查找历史数据
 */
public class HistoryDataRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public HistoryDataRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet()
    {
//        String url = "";
//        httpRequest.setUrl(url);
//        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
        String url = Url.HISTORY_DATA_URL;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}


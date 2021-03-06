package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 历史数据详情
 */
public class DataDetailsRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public DataDetailsRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet(String dataAddr)
    {
        String url = Url.HISTORY_DATA_DETAILS_URL + "?dataAddr" + dataAddr;
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


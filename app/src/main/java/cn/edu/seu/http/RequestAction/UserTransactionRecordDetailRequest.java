package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 交易记录详情
 */
public class UserTransactionRecordDetailRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public UserTransactionRecordDetailRequest(Context context, Handler handler) {
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
        String url = Url.TRANSACTION_RECORD_DETAIL;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}


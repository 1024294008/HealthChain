package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查看转账记录
 */
public class TransactionRecordRequest {
    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public TransactionRecordRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    public void doGet(String ethAddress)
    {
//        String url = Url.USER_TRANSACTION_RECORD_URL + "?ethAddress" + ethAddress;
//        httpRequest.setUrl(url);
//        httpRequest.get();
    }

    public void doPost(Map<String, String> param)
    {
//        String url = Url.USER_TRANSACTION_RECORD_URL;
//        httpRequest.setUrl(url);
//        httpRequest.post(param);
    }
}

package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;

import java.util.Map;

import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查看用户个人钱包  -- 所有交易记录
 */
public class UserTransactionRecordRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public UserTransactionRecordRequest(Context context, Handler handler) {
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
        String url = Url.USER_TRANSFER_ALL_URL;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}


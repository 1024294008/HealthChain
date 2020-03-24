package cn.edu.seu.http.RequestAction;

import android.content.Context;
import android.os.Handler;
import java.util.Map;
import cn.edu.seu.http.HttpRequest.HttpRequest;
import cn.edu.seu.http.url.Url;

/**
 * 查看用户个人信息, 修改用户个人信息
 */
public class UserInfoRequest {

    private Context context;
    private Handler handler;
    private HttpRequest httpRequest;

    public UserInfoRequest(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.httpRequest = new HttpRequest(this.context, this.handler);
    }

    /**
     * 查看用户信息
     * @param id
     */
    public void doGet(int id)
    {
        String url = Url.USER_INFO_URL + "?id" + id;
        httpRequest.setUrl(url);
        httpRequest.get();
    }

    /**
     * 修改个人信息
     * @param param
     */
    public void doPost(Map<String, String> param)
    {
        String url = Url.UPDATE_USER_INFO_URL;
        httpRequest.setUrl(url);
        httpRequest.post(param);
    }
}


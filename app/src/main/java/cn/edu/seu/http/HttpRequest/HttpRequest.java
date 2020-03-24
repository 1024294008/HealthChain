package cn.edu.seu.http.HttpRequest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import cn.edu.seu.http.IHttpApi.HttpApi;

public class HttpRequest implements HttpApi {

    private RequestQueue queue;
    private Context context;
    private Handler handler;
    private String url;

    public HttpRequest(Context context, Handler handler) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.handler = handler;
    }

    @Override
    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void get() {
        String url = this.getUrl();
        // get请求方式
        JsonObjectRequest request = new JsonObjectRequest(getUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("发生了一个错误！");
                        error.printStackTrace();
                    }
                });
        queue.add(request);
    }


    @Override
    public void post(Map<String, String> param) {
        JSONObject requestParam = new JSONObject(param);
        String url = this.getUrl();
        // post请求方式
        JsonObjectRequest request = new JsonObjectRequest(url, requestParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("发生了一个错误！");
                        error.printStackTrace();
                    }
                });
        queue.add(request);
    }
}


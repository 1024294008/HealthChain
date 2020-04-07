package cn.edu.seu.http.HttpRequest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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
        url = this.getUrl();
        // get请求方式
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUrl(),
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
                        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                        Toast t = Toast.makeText(context, "请求失败...", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
        queue.add(request);
    }


    @Override
    public void post(Map<String, String> param) {
        JSONObject requestParam = new JSONObject(param);
        String url = this.getUrl();
        // post请求方式
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestParam,
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
                        Toast t = Toast.makeText(context, "请求失败...", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
        queue.add(request);
    }
}


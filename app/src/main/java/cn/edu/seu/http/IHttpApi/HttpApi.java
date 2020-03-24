package cn.edu.seu.http.IHttpApi;

import java.util.Map;

public interface HttpApi {

    public void setUrl(String url);

    public String getUrl();

    public void get();

    public void post(Map<String, String> param);

}

package cn.edu.seu.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.edu.seu.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xujiaji.happybubble.Auto;
import com.xujiaji.happybubble.BubbleDialog;

import cn.edu.seu.http.url.Url;

import java.net.URLEncoder;

public class DataAnalyzeActivity extends AppCompatActivity implements View.OnClickListener {
    private BubbleDialog chartOptions;
    private ImageView switchChart;
    private View dialogView;
    private ProgressBar loadProgress;
    private WebView loadWebView;
    private SharedPreferences sharedPreferences;
    private final String[] chartsUrl = new String[]{
            "http://" + Url.IFCONFIG + "/api/user/uploadTimeChart",
            "http://" + Url.IFCONFIG + "/api/user/distanceChart",
            "http://" + Url.IFCONFIG + "/api/user/heatChart",
            "http://" + Url.IFCONFIG + "/api/user/heartRateChart",
            "http://" + Url.IFCONFIG + "/api/user/sleepQualityChart",
            "http://" + Url.IFCONFIG + "/api/user/permitVisitChart"
    };
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analyze);
        initView();
    }

    private void initView(){
        //设置系统状态栏UI
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.standardBackground, null));
        }
        switchChart = findViewById(R.id.switchChart);
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_data_analyze_options, null);
        loadWebView = findViewById(R.id.loadWebView);
        loadProgress = findViewById(R.id.loadProgress);

        sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        chartOptions = new BubbleDialog(this)
                .addContentView(dialogView)
                .autoPosition(Auto.UP_AND_DOWN)
                .calBar(true)
                .softShowUp();

        findViewById(R.id.back).setOnClickListener(this);
        switchChart.setOnClickListener(this);
        dialogView.findViewById(R.id.chart1).setOnClickListener(this);
        dialogView.findViewById(R.id.chart2).setOnClickListener(this);
        dialogView.findViewById(R.id.chart3).setOnClickListener(this);
        dialogView.findViewById(R.id.chart4).setOnClickListener(this);
        dialogView.findViewById(R.id.chart5).setOnClickListener(this);
        dialogView.findViewById(R.id.chart6).setOnClickListener(this);

        // 设置webview
        loadProgress.setVisibility(View.INVISIBLE);
        loadWebView.setVisibility(View.VISIBLE);
        loadWebView.clearCache(true);
        WebSettings webSettings = loadWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);

        loadWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadWebView.setVisibility(View.INVISIBLE);
                loadProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadProgress.setVisibility(View.INVISIBLE);
                loadWebView.setVisibility(View.VISIBLE);
            }
        });
        setCurrentChart(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.switchChart:
                chartOptions.setClickedView(switchChart).show();
                break;
            case R.id.chart1:
                setCurrentChart(1);
                chartOptions.dismiss();
                break;
            case R.id.chart2:
                setCurrentChart(2);
                chartOptions.dismiss();
                break;
            case R.id.chart3:
                setCurrentChart(3);
                chartOptions.dismiss();
                break;
            case R.id.chart4:
                setCurrentChart(4);
                chartOptions.dismiss();
                break;
            case R.id.chart5:
                setCurrentChart(5);
                chartOptions.dismiss();
                break;
            case R.id.chart6:
                setCurrentChart(6);
                chartOptions.dismiss();
                break;
        }
    }

    // 设置当前应该显示的表
    private void setCurrentChart(Integer i){
        String postData = "";
        try{
            postData = "token=" + URLEncoder.encode(token, "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        loadWebView.postUrl(chartsUrl[i - 1], postData.getBytes());
    }
}

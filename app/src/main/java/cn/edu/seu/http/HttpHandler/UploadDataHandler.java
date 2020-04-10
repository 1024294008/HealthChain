package cn.edu.seu.http.HttpHandler;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import cn.edu.seu.views.UploadDialog;

/**
 * 上传数据
 */
public class UploadDataHandler extends Handler {

    private Context context;
    private RelativeLayout loadProgress;

    public UploadDataHandler(Context context, RelativeLayout loadProgress) {
        this.context = context;
        this.loadProgress = loadProgress;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                // 具体执行内容
                JSONObject response = (JSONObject)msg.obj;  // 请求到的数据
                try {
                    String _code = response.getString("_code"); // 状态码
                    if("200".equals(_code)) {
                        Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Toast t = Toast.makeText(context, "请求出错", Toast.LENGTH_SHORT);
                    t.show();
                    e.printStackTrace();
                    return;
                } finally {
                    loadProgress.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}



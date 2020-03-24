package cn.edu.seu.http.HttpHandler;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * 数据详情
 */
public class DataDetailsHandler extends Handler {

    private Context context;

    public DataDetailsHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                // 具体执行内容
                Toast t = Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT);
                t.show();
                break;
        }
    }
}



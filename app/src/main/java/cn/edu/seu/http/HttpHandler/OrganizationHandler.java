package cn.edu.seu.http.HttpHandler;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * 查找医疗机构
 */
public class OrganizationHandler extends Handler {

    private Context context;

    public OrganizationHandler(Context context) {
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



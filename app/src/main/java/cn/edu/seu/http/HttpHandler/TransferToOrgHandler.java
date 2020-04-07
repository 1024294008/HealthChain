package cn.edu.seu.http.HttpHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 向机构转账
 */
public class TransferToOrgHandler extends Handler {

    private Context context;

    public TransferToOrgHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                // 具体执行内容

                try {
                    JSONObject response = (JSONObject)msg.obj;
                    String _code = response.getString("_code");
                    if( "200".equals(_code)){
                        Toast t = Toast.makeText(context, "转账成功!", Toast.LENGTH_SHORT);
                        t.show();
                    }
                    else {
                        Toast t = Toast.makeText(context, "转账失败!", Toast.LENGTH_SHORT);
                        t.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(context, "转账失败!", Toast.LENGTH_SHORT);
                    t.show();
                }


                break;
        }
    }
}


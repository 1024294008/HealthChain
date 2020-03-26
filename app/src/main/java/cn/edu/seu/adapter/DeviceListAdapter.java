package cn.edu.seu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.views.UploadDialog;

public class DeviceListAdapter extends BaseAdapter {
    private List<Map<String, String>> deviceList;//绑定的数据
    private LayoutInflater inflater;//布局填充服务
    private Integer resource;//列表每一项布局资源id
    private UploadDialog dialog;//点击连接后弹出的对话框
    private Context context;

    public DeviceListAdapter(Context context, List<Map<String, String>> deviceList, Integer resource, UploadDialog dialog){
        this.context = context;
        this.deviceList = deviceList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.dialog = dialog;
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView deviceName;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            deviceName = convertView.findViewById(R.id.deviceName);

            DeviceListAdapter.ViewCache cache = new DeviceListAdapter.ViewCache();
            cache.deviceName = deviceName;
            convertView.setTag(cache);

        } else {
            DeviceListAdapter.ViewCache cache = (DeviceListAdapter.ViewCache) convertView.getTag();
            deviceName = cache.deviceName;
        }
        final String currentDeviceName = deviceList.get(position).get("deviceName");
        deviceName.setText(currentDeviceName);
        convertView.findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setDeviceName(currentDeviceName);
                dialog.show();
            }
        });

        return convertView;
    }

    private final class ViewCache {
        TextView deviceName;
    }
}

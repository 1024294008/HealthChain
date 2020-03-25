package cn.edu.seu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.edu.seu.R;

public class HealthListAdapter extends BaseAdapter {
    private List<Map<String, String>> healthDataList;//绑定的数据
    private LayoutInflater inflater;//布局填充服务
    private Integer resource;//列表每一项布局资源id

    public HealthListAdapter(Context context, List<Map<String, String>> healthDataList, Integer resource){
        this.healthDataList = healthDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return healthDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return healthDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView time;
        TextView eval;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            time = convertView.findViewById(R.id.time);
            eval = convertView.findViewById(R.id.eval);

            ViewCache cache = new ViewCache();
            cache.time = time;
            cache.eval = eval;
            convertView.setTag(cache);

        } else {
            ViewCache cache = (ViewCache) convertView.getTag();
            time = cache.time;
            eval = cache.eval;
        }
        time.setText(healthDataList.get(position).get("time"));
        eval.setText(healthDataList.get(position).get("eval"));

        return convertView;
    }

    private final class ViewCache {
        public TextView time = null;
        public TextView eval = null;
    }
}

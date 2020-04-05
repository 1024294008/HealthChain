package cn.edu.seu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.common.PortraitManager;
import cn.edu.seu.util.pinyin.PinYinUtil;

public class DescriptionListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> descriptionList;//绑定的数据
    private LayoutInflater inflater;//布局填充服务
    private Integer resource;//列表每一项布局资源id

    public DescriptionListAdapter(Context context, List<Map<String, String>> descriptionList, Integer resource){
        this.context = context;
        this.descriptionList = descriptionList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return descriptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return descriptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView description;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            description = convertView.findViewById(R.id.description);

            ViewCache cache = new ViewCache();
            cache.description = description;
            convertView.setTag(cache);

        } else {
            ViewCache cache = (ViewCache) convertView.getTag();
            description = cache.description;
        }
        description.setText(descriptionList.get(position).get("description"));

        return convertView;
    }

    private final class ViewCache {
        private TextView description;
    }
}

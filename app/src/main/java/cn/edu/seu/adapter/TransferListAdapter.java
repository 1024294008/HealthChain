package cn.edu.seu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.edu.seu.R;

public class TransferListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> transferList;//绑定的数据
    private LayoutInflater inflater;//布局填充服务
    private Integer resource;//列表每一项布局资源id

    public TransferListAdapter(Context context, List<Map<String, String>> transferList, Integer resource){
        this.context = context;
        this.transferList = transferList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return transferList.size();
    }

    @Override
    public Object getItem(int position) {
        return transferList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView transferType;
        TextView amount;
        ImageView transferIcon;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            transferType = convertView.findViewById(R.id.transferType);
            amount = convertView.findViewById(R.id.amount);
            transferIcon = convertView.findViewById(R.id.transferIcon);

            ViewCache cache = new ViewCache();
            cache.transferType = transferType;
            cache.amount = amount;
            cache.transferIcon = transferIcon;
            convertView.setTag(cache);

        } else {
            ViewCache cache = (ViewCache) convertView.getTag();
            transferType = cache.transferType;
            amount = cache.amount;
            transferIcon = cache.transferIcon;
        }
        if(transferList.get(position).get("type").equals("0")){
            transferType.setText("健康币支出");
            transferIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_transfer_out, null));

            amount.setText("- " + transferList.get(position).get("amount"));
        } else {
            transferType.setText("健康币转入");
            transferIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_transfer_in, null));
            amount.setText("+ " + transferList.get(position).get("amount"));
        }
        return convertView;
    }

    private final class ViewCache {
        private TextView transferType;
        private TextView amount;
        private ImageView transferIcon;
    }
}

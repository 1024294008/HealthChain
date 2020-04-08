package cn.edu.seu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.edu.seu.R;
import cn.edu.seu.common.PortraitManager;
import cn.edu.seu.util.pinyin.PinYinUtil;

public class MedicalListAdapter extends BaseAdapter {
    private Integer mode; //适配器模式，0表示普通模式，1表示简易模式
    private Context context;
    private List<Map<String, String>> medicalList;//绑定的数据
    private LayoutInflater inflater;//布局填充服务
    private Integer resource;//列表每一项布局资源id

    public MedicalListAdapter(Context context, List<Map<String, String>> medicalList, Integer resource, Integer mode){
        this.mode = mode;
        this.context = context;
        this.medicalList = medicalList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        if(mode == 0)
            this.sortMedicalList();
    }

    @Override
    public int getCount() {
        return medicalList.size();
    }

    @Override
    public Object getItem(int position) {
        return medicalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout serviceAcr;
        ImageView serviceIcon;
        TextView acr;
        TextView serviceName;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            serviceAcr = convertView.findViewById(R.id.serviceAcr);
            serviceIcon = convertView.findViewById(R.id.serviceIcon);
            acr = convertView.findViewById(R.id.acr);
            serviceName = convertView.findViewById(R.id.serviceName);

            ViewCache cache = new ViewCache();
            cache.serviceAcr = serviceAcr;
            cache.acr = acr;
            cache.serviceIcon = serviceIcon;
            cache.serviceName = serviceName;
            convertView.setTag(cache);

        } else {
            ViewCache cache = (ViewCache) convertView.getTag();
            serviceAcr = cache.serviceAcr;
            serviceIcon = cache.serviceIcon;
            acr = cache.acr;
            serviceName = cache.serviceName;
        }
        // 识别模式
        if(this.mode == 0) {
            // 固定设置第一项为服务历史
            if(medicalList.get(position).get("serviceName").equals("serviceHistory")) {
                serviceAcr.getLayoutParams().height = 0;
                serviceIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_main_medical_serve_history, null));
                serviceName.setText("服务历史");
                return convertView;
            }
            serviceName.setText(medicalList.get(position).get("serviceName"));
            serviceIcon.setImageDrawable(this.context.getResources().getDrawable(PortraitManager.getPortraitSrc(medicalList.get(position).get("portrait")), null));
            // 根据serviceName获取首字母acr进行分类
            String currentAcr = PinYinUtil.getFirstSpell(medicalList.get(position).get("serviceName"));
            // 如果此项与前一项属于同一类，则不显示分类标签，否则显示
            if ((position != 0) && (PinYinUtil.getFirstSpell(medicalList.get(position - 1).get("serviceName")).equals(currentAcr))) {
                serviceAcr.getLayoutParams().height = 0;
                acr.setText(currentAcr);
            } else {
                serviceAcr.getLayoutParams().height = 50;
                acr.setText(currentAcr);
            }
        }else {
            // 简易模式不显示分类标签
            serviceAcr.getLayoutParams().height = 0;
            serviceName.setText(medicalList.get(position).get("serviceName"));
            serviceIcon.setImageDrawable(this.context.getResources().getDrawable(PortraitManager.getPortraitSrc(medicalList.get(position).get("portrait")), null));
        }

        return convertView;
    }

    private final class ViewCache {
        private LinearLayout serviceAcr;
        private ImageView serviceIcon;
        private TextView acr;
        private TextView serviceName;
    }

    @Override
    public void notifyDataSetChanged() {
        if(this.mode == 0)
            this.sortMedicalList();
        super.notifyDataSetChanged();
    }

    /**
     * 对medicalList进行排序
     */
    private void sortMedicalList(){
        Collections.sort(this.medicalList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                if(o1.get("serviceName").equals("serviceHistory")) {
                    return -1;
                }
                if(o2.get("serviceName").equals("serviceHistory")) {
                    return 1;
                }
                String t1 = PinYinUtil.getFirstSpell(o1.get("serviceName"));
                String t2 = PinYinUtil.getFirstSpell(o2.get("serviceName"));
                if (t2 == null) {
                    return -1;
                } else {
                    return t1.compareTo(t2);
                }
            }
        });

    }

}

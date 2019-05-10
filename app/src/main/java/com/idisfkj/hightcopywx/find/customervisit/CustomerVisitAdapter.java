package com.idisfkj.hightcopywx.find.customervisit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import java.util.List;

public class CustomerVisitAdapter extends BaseAdapter {

    private List<BaseVisitCustomerInfo> list;

    public CustomerVisitAdapter(List<BaseVisitCustomerInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseVisitCustomerInfo bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_customervisit, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.num=(TextView) convertView.findViewById(R.id.list_customervisit_num);
            viewHolder.customerVisitDesc = (TextView) convertView.findViewById(R.id.list_customerVisitDesc);
            viewHolder.customerVisitTime = (TextView) convertView.findViewById(R.id.list_customerVisitTime);
            viewHolder.customerVisitLocation = (TextView) convertView.findViewById(R.id.list_customerVisitLocation);
            viewHolder.visitCustomerName = (TextView) convertView.findViewById(R.id.list_visitCustomerName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.num.setText(String.valueOf(position+1)+".");
        viewHolder.customerVisitDesc.setText(bean.getRemarks());
        viewHolder.customerVisitTime.setText(bean.getVisittime());
        viewHolder.customerVisitLocation.setText(bean.getAddress());
        viewHolder.visitCustomerName.setText(bean.getCustomer());
        return convertView;
    }

    static class MyViewHolder {
        private TextView num;//序号
        private TextView customerVisitDesc;//描述
        private TextView customerVisitTime;//时间
        private TextView customerVisitLocation;//地点
        private TextView visitCustomerName;//拜访客户名
    }


}
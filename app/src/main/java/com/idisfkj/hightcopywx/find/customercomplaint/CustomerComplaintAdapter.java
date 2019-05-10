package com.idisfkj.hightcopywx.find.customercomplaint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.customercomplaint.entity.BaseCustomerComplaint;

import java.util.List;

public class CustomerComplaintAdapter extends BaseAdapter {

    private List<BaseCustomerComplaint> list;

    public CustomerComplaintAdapter(List<BaseCustomerComplaint> list) {
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

        BaseCustomerComplaint bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_customercomplaint, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.num=(TextView) convertView.findViewById(R.id.list_customercomplaint_num);
            viewHolder.customerName_complaint = (TextView) convertView.findViewById(R.id.list_customerName_complaint);
            viewHolder.complaintTime = (TextView) convertView.findViewById(R.id.list_complaintTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.num.setText(String.valueOf(position+1)+".");
        viewHolder.customerName_complaint.setText(bean.getCustomername());
        viewHolder.complaintTime.setText(bean.getComplainttime());
        return convertView;
    }

    static class MyViewHolder {
        private TextView num;//序号
        private TextView customerName_complaint;//客户名称
        private TextView complaintTime;//投诉时间
    }


}
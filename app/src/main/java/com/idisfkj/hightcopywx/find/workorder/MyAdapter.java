package com.idisfkj.hightcopywx.find.workorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.workorder.entity.BaseWorkOrder;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<BaseWorkOrder> list;

    public MyAdapter(List<BaseWorkOrder> list) {
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

        BaseWorkOrder bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_workorder, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.state=(TextView) convertView.findViewById(R.id.list_state);
            viewHolder.num=(TextView) convertView.findViewById(R.id.list_num);
            viewHolder.workOrderId = (TextView) convertView.findViewById(R.id.list_name);
            viewHolder.leader = (TextView) convertView.findViewById(R.id.list_detail);
            viewHolder.orderTime = (TextView) convertView.findViewById(R.id.list_createtime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.state.setText(bean.getState().equals("0")?"未完成":"已完成");
        viewHolder.num.setText(String.valueOf(position+1)+".");
        viewHolder.workOrderId.setText(bean.getWorkorderId());
        viewHolder.leader.setText(bean.getLeader());
        viewHolder.orderTime.setText(bean.getOrdertime());
        return convertView;
    }

    static class MyViewHolder {
        private TextView num;//序号
        private TextView state;
        private TextView workOrderId;//派工单编号
        private TextView leader;//派工者
        private TextView orderTime;//派工时间
    }


}
package com.idisfkj.hightcopywx.find.customerpraise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.customerpraise.entity.BaseCustomerPraise;

import java.util.List;

public class CustomerPraiseAdapter extends BaseAdapter {

    private List<BaseCustomerPraise> list;

    public CustomerPraiseAdapter(List<BaseCustomerPraise> list) {
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

        BaseCustomerPraise bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_customerpraise, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.num=(TextView) convertView.findViewById(R.id.list_customerpraise_num);
            viewHolder.customerName_praise = (TextView) convertView.findViewById(R.id.list_customerName_praise);
            viewHolder.praiseTime = (TextView) convertView.findViewById(R.id.list_praiseTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.num.setText(String.valueOf(position+1)+".");
        viewHolder.customerName_praise.setText(bean.getCustomername());
        viewHolder.praiseTime.setText(bean.getPraisetime());
        return convertView;
    }

    static class MyViewHolder {
        private TextView num;//序号
        private TextView customerName_praise;//客户名称
        private TextView praiseTime;//表扬时间
    }


}
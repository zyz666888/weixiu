package com.idisfkj.hightcopywx.find.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import java.util.List;

public class NotificationAdapter extends BaseAdapter {

    private List<BaseNotificationInfo> list;

    public NotificationAdapter(List<BaseNotificationInfo> list) {
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

        BaseNotificationInfo bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_notification, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_notification_title);
            viewHolder.createtime = (TextView) convertView.findViewById(R.id.list_notification_createtime);
            viewHolder.content = (TextView) convertView.findViewById(R.id.list_notification_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(bean.getTitle());
        viewHolder.createtime.setText(bean.getCreateTime());
        viewHolder.content.setText(bean.getContent());
        return convertView;
    }

    static class MyViewHolder {
        private TextView title;//标题
        private TextView createtime;//时间
        private TextView content;//内容
    }


}
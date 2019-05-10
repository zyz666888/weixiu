package com.idisfkj.hightcopywx.find.askandanswer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import java.util.List;

public class AskAndAnswerAdapter extends BaseAdapter {

    private List<BaseAskAndAnswerInfo> list;

    public AskAndAnswerAdapter(List<BaseAskAndAnswerInfo> list) {
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

        BaseAskAndAnswerInfo bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_askandanswer, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.num=(TextView) convertView.findViewById(R.id.list_askandanswer_num);
            viewHolder.problem_desc = (TextView) convertView.findViewById(R.id.list_askandanswer_problem_desc);
            viewHolder.createtime = (TextView) convertView.findViewById(R.id.list_askandanswer_time);
            viewHolder.fix_desc = (TextView) convertView.findViewById(R.id.list_askandanswer_fix_desc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.num.setText(String.valueOf(position+1)+".");
        viewHolder.fix_desc.setText(bean.getFixDesc());
        viewHolder.createtime.setText(bean.getCreateTime());
        viewHolder.problem_desc.setText(bean.getProblemDesc());
        return convertView;
    }

    static class MyViewHolder {
        private TextView num;//序号
        private TextView problem_desc;//标题
        private TextView createtime;//时间
        private TextView fix_desc;//内容
    }


}
package com.idisfkj.hightcopywx.find.files;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import java.util.List;

public class FilesAdapter extends BaseAdapter {

    private List<BaseFile> list;

    public FilesAdapter(List<BaseFile> list) {
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

        BaseFile bean = list.get(position);
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_file, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.file_type=(ImageView) convertView.findViewById(R.id.list_file_type);
            viewHolder.file_name = (TextView) convertView.findViewById(R.id.list_file_name);
            viewHolder.file_createTime = (TextView) convertView.findViewById(R.id.list_file_createTime);
            viewHolder.file_size = (TextView) convertView.findViewById(R.id.list_file_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        String fileType=bean.getType().toLowerCase();
        if(fileType==null){
            fileType="";
        }
        if(fileType.equals("xls")||fileType.equals("xlsx")){
            viewHolder.file_type.setImageResource(R.drawable.excel);
        }else if (fileType.equals("doc")||fileType.equals("docx")){
            viewHolder.file_type.setImageResource(R.drawable.word);
        }else if (fileType.equals("pdf")){
            viewHolder.file_type.setImageResource(R.drawable.pdf);
        }else if (fileType.equals("ppt")||fileType.equals("pptx")){
            viewHolder.file_type.setImageResource(R.drawable.ppt);
        }else if (fileType.equals("txt")) {
            viewHolder.file_type.setImageResource(R.drawable.txt);
        }else{
            viewHolder.file_type.setImageResource(R.drawable.unknown);
        }

        viewHolder.file_name.setText(bean.getName());
        viewHolder.file_createTime.setText(bean.getCreateTime());
        viewHolder.file_size.setText(bean.getSize()+"byte");
        return convertView;
    }

    static class MyViewHolder {
        private ImageView file_type;//文件类型
        private TextView file_name;//文件名称
        private TextView file_createTime;//文件名称
        private TextView file_size;//文件名称
    }


}
package com.idisfkj.hightcopywx.find.friendscircle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import java.util.List;

public class NineGridTestFriendCircleAdapter extends BaseAdapter {

    private Context mContext;
    private List<FriendsCircleBean> mList;
    protected LayoutInflater inflater;

    public NineGridTestFriendCircleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<FriendsCircleBean> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return getListSize(mList);
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.item_bbs_nine_grid, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.layout.setIsShowAll(mList.get(position).getUrllist().isShowAll);
        holder.layout.setUrlList(mList.get(position).getUrllist().urlList);
        holder.desc.setText(mList.get(position).getDesc());
        holder.name.setText(mList.get(position).getName());
        holder.time.setText(mList.get(position).getTime());
        holder.img.setImageResource(Integer.valueOf(mList.get(position).getImg()));

        return convertView;
    }

    private class ViewHolder {
        NineGridTestLayout layout;
        TextView name,time,desc;
        ImageView img;

        public ViewHolder(View view) {
            layout = (NineGridTestLayout) view.findViewById(R.id.layout_nine_grid);
            name=(TextView) view.findViewById(R.id.friendcircle_name);
            time=(TextView) view.findViewById(R.id.friendcircle_time);
            desc=(TextView) view.findViewById(R.id.friendcircle_desc);
            img=(ImageView) view.findViewById(R.id.friendcircle_img);
        }
    }

    private int getListSize(List<FriendsCircleBean> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}

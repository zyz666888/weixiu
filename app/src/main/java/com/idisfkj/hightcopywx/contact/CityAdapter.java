package com.idisfkj.hightcopywx.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;

import java.util.List;
/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    protected Context mContext;
    protected List<CityBean> mDatas;
    protected LayoutInflater mInflater;

    public CityAdapter(Context mContext, List<CityBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<CityBean> getDatas() {
        return mDatas;
    }

    public CityAdapter setDatas(List<CityBean> datas) {
        mDatas = datas;
        return this;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(final CityAdapter.ViewHolder holder, final int position) {
        final CityBean cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.getCity());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //调用系统的拨号服务实现电话拨打功能
                String phone_number = cityBean.getCity();
                phone_number = phone_number.trim();//删除字符串首部和尾部的空格
                if (phone_number != null && !phone_number.equals("")) {
                    //调用系统的拨号服务实现电话拨打功能
                    //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
                    Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + phone_number));//跳转到拨号界面，同时传递电话号码
                    mContext.startActivity(Intent);
                   // Toast.makeText(mContext, "phone_number:" + phone_number, Toast.LENGTH_SHORT).show();
                }

            }
        });
//        if(position==0){
//            holder.avatar.setImageResource(R.drawable.metting);
//        }else if(position==1){
//            holder.avatar.setImageResource(R.drawable.dept);
//        }else if(position==2){
//            holder.avatar.setImageResource(R.drawable.tag);
//        }else if(position==3) {
//            holder.avatar.setImageResource(R.drawable.myfriend);
//        }else{
            holder.avatar.setImageResource(R.drawable.me);
   //     }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        ImageView avatar;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            content = itemView.findViewById(R.id.content);
        }
    }
}

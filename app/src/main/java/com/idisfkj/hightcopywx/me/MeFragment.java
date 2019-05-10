package com.idisfkj.hightcopywx.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.calendar.activity.CalendarActivity;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import com.idisfkj.hightcopywx.find.files.FileListActivity;
import com.idisfkj.hightcopywx.find.workorder.WorkOrderMainActivity;
import com.idisfkj.hightcopywx.me.friendcircle.FriendCircleAddAction;
import com.idisfkj.hightcopywx.util.UpdateManager;

import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class MeFragment extends Fragment {

    public TextView versionUpd;
    public TextView tv_myfile;
    public TextView tv_myworkorder;
    public TextView  tv_friendscircle;
    public TextView tv_calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.me_layout, null);
        versionUpd = (TextView) view.findViewById(R.id.update_version);
        tv_myfile=(TextView) view.findViewById(R.id.tv_myfile);
        tv_myworkorder=(TextView) view.findViewById(R.id.my_workorder);
        tv_friendscircle=(TextView) view.findViewById(R.id.tv_friendscircle);
        tv_calendar=(TextView) view.findViewById(R.id.tv_calendar);
        versionUpd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                UpdateManager upd= new UpdateManager(getActivity());
                upd.checkUpdate();
            }
        });
        tv_myfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(),FileListActivity.class);
                startActivity(intent);
            }
        });

        tv_myworkorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent(getActivity(),WorkOrderMainActivity.class);
                startActivity(intent1);
            }
        });
        tv_friendscircle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent2 = new Intent(getActivity(),FriendCircleAddAction.class);
                startActivity(intent2);
            }
        });
        tv_calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent3 = new Intent(getActivity(),CalendarActivity.class);
                startActivity(intent3);
            }
        });

        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout)view.findViewById(R.id.banner_me);
        final List<String> urls = new ArrayList<>();
        urls.add(getProperties(getContext()).getProperty("ad1"));
        urls.add(getProperties(getContext()).getProperty("ad2"));
        urls.add(getProperties(getContext()).getProperty("ad3"));
        urls.add(getProperties(getContext()).getProperty("ad4"));
        urls.add(getProperties(getContext()).getProperty("ad5"));
        bannerLayout.setImageLoader(new GlideImageLoader());
        bannerLayout.setViewUrls(urls);

        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Toast.makeText(InstallMainAction.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        //广告轮播--结束--

        return view;
    }
}

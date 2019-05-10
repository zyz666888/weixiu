package com.idisfkj.hightcopywx.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import com.idisfkj.hightcopywx.find.aftersale.AftersaleMainAction;
import com.idisfkj.hightcopywx.find.askandanswer.AskAndAnswerListActivity;
import com.idisfkj.hightcopywx.find.customercomplaint.CustomerComplaintListActivity;
import com.idisfkj.hightcopywx.find.customerpraise.CustomerPraiseListActivity;
import com.idisfkj.hightcopywx.find.customervisit.CustomerVisitListActivity;
import com.idisfkj.hightcopywx.find.files.FileListActivity;
import com.idisfkj.hightcopywx.find.notification.NotificationListActivity;
import com.idisfkj.hightcopywx.find.signin.SignInMainActivity;
import com.idisfkj.hightcopywx.find.workorder.WorkOrderMainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FindFragment extends Fragment {
    //定义控件
    public GridView gridView;
    public int width;
    public LinearLayout.LayoutParams layoutParams;
    //list的实际数量
    public int sum = 13;
    //list的虚拟数量
    public int sums;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.find_layout, null);
        gridView = (GridView) view.findViewById(R.id.main_grid);
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        switch (sum % 4) {
            case 0:
                sums = sum;
                break;
            case 1:
                sums = sum + 3;
                break;
            case 2:
                sums = sum + 2;
                break;
            case 3:
                sums = sum + 1;
                break;
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), InstallMainAction.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity(), CheckMainAction.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), BreakdownMainAction.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(getActivity(), NotificationListActivity.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(getActivity(), SignInMainActivity.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(getActivity(), FileListActivity.class);
                    startActivity(intent);
                }
            }
        });
        gridView.setAdapter(new MyAdapter());

        BannerLayout bannerLayout = (BannerLayout) view.findViewById(R.id.banner_find);

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
               // Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class Handler {
        public LinearLayout linearLayout;
        public ImageView imageView;
        public TextView textView;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sums;
        }

        @Override
        public Object getItem(int position) {
            return sums;
        }

        @Override
        public long getItemId(int position) {
            return sums;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Handler handler;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_gridview, null);
                handler = new Handler();
                handler.linearLayout = (LinearLayout) convertView.findViewById(R.id.item_linear);
                handler.imageView = (ImageView) convertView.findViewById(R.id.item_image);
                handler.textView = (TextView) convertView.findViewById(R.id.item_text);
                convertView.setTag(handler);
            }
            handler = (Handler) convertView.getTag();
            layoutParams = (LinearLayout.LayoutParams) handler.linearLayout.getLayoutParams();
            int w = (width - 3) / 4;
            layoutParams.width = w;
            layoutParams.height = w;
            handler.linearLayout.setLayoutParams(layoutParams);
            if (position == 0) {
                handler.imageView.setImageResource(R.drawable.edit);
                handler.textView.setText(R.string.m1);
            } else if (position == 1) {
                handler.imageView.setImageResource(R.drawable.yufangxiu);
                handler.textView.setText(R.string.m2);
            } else if (position == 2) {
                handler.imageView.setImageResource(R.drawable.guzhangxiu);
                handler.textView.setText(R.string.m3);
            } else if (position == 3) {
                handler.imageView.setImageResource(R.drawable.info);
                handler.textView.setText(R.string.m4);
            } else if (position == 4) {
                handler.imageView.setImageResource(R.drawable.kaoqin);
                handler.textView.setText(R.string.m5);
//
              } else if (position ==5) {
                handler.imageView.setImageResource(R.drawable.myfile);
                handler.textView.setText(R.string.m7);
            }

            return convertView;
        }
    }

}


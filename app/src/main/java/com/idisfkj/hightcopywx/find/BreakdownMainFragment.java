package com.idisfkj.hightcopywx.find;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class BreakdownMainFragment extends Fragment {
    //定义控件
    public GridView gridView;
    public int width;
    public LinearLayout.LayoutParams layoutParams;
    //list的实际数量
    public int sum = 18;
    //list的虚拟数量
    public int sums;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.maintain_layout, null);
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
                    Intent intent = new Intent(getActivity(), BreakdownMainAction.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity(), MaintainAction.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), BreakdownInfoAction.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(getActivity(), RecoveryAction.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(getActivity(), MeasureAction.class);
                    startActivity(intent);
                }
            }
        });
        gridView.setAdapter(new MyAdapter());

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
                handler.imageView.setImageResource(R.drawable.metting);
                handler.textView.setText(R.string.m0);
            } else if (position == 1) {
                handler.imageView.setImageResource(R.drawable.friends);
                handler.textView.setText(R.string.m1);
            } else if (position == 2) {
                handler.imageView.setImageResource(R.drawable.kaoqin);
                handler.textView.setText(R.string.m2);
            } else if (position == 3) {
                handler.imageView.setImageResource(R.drawable.myfile);
                handler.textView.setText(R.string.m3);
            } else if (position == 4) {
                handler.imageView.setImageResource(R.drawable.mast);
                handler.textView.setText(R.string.m4);
            } else if (position == 5) {
                handler.imageView.setImageResource(R.drawable.project);
                handler.textView.setText(R.string.m5);
            }

            return convertView;
        }
    }
}


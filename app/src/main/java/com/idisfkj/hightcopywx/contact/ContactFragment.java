package com.idisfkj.hightcopywx.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private static final String TAG = "zxt";
    private static final String INDEX_STRING_TOP = "↑";
    private RecyclerView mRv;
    private CityAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();

    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contact, null);
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));

        mAdapter = new CityAdapter(getActivity(), mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) view.findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) view.findViewById(R.id.indexBar);//IndexBar

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));
        return view;
    }
    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                // 但是它不需要被ItemDecoration设一个标题titile
//                mDatas.add((CityBean) new CityBean("微会议").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("部门").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("标签").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("我的朋友").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                for (int i = 0; i < data.length; i++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mDatas.add(cityBean);
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mDatas);
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}


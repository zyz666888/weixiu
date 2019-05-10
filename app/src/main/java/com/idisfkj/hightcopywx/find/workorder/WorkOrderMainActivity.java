package com.idisfkj.hightcopywx.find.workorder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import java.util.ArrayList;
import java.util.List;
import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class WorkOrderMainActivity extends FragmentActivity implements OnClickListener {
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //两个Tab对应的布局
    private LinearLayout mTabDone;
    private LinearLayout mTabTodo;

    //两个Tab对应的ImageButton
    private ImageButton mImgDone;
    private ImageButton mImgTodo;

    //两个Tab对应的TextView
    private TextView mTextViewDone;
    private TextView mTextViewTodo;
    private ImageView return_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.workordermain);
        return_img = (ImageView) findViewById(R.id.return_worker_detail);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_workorder);
        final List<String> urls = new ArrayList<>();
        urls.add(getProperties(getApplicationContext()).getProperty("ad1"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad2"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad3"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad4"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad5"));
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
    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将两个Fragment加入集合中
        mFragments.add(new TodoFragment());
        mFragments.add(new DoneFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置两个Tab的点击事件
        mTabTodo.setOnClickListener(this);
        mTabDone.setOnClickListener(this);
    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabDone = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabTodo = (LinearLayout) findViewById(R.id.id_tab_frd);

        mImgTodo = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mImgDone = (ImageButton) findViewById(R.id.id_tab_frd_img);

        mTextViewDone = (TextView) findViewById(R.id.bottom_done);
        mTextViewTodo = (TextView) findViewById(R.id.bottom_todo);

    }

    @Override
    public void onClick(View v) {
        //先将两个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_weixin:
                selectTab(0);
                break;
            case R.id.id_tab_frd:
                selectTab(1);
                break;
        }
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgTodo.setBackgroundResource(R.drawable.todo_pressed);
                mTextViewDone.setTextColor(Color.parseColor("#cfcfcf"));
                mTextViewTodo.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                mImgDone.setBackgroundResource(R.drawable.done_pressed);
                mTextViewTodo.setTextColor(Color.parseColor("#cfcfcf"));
                mTextViewDone.setTextColor(Color.parseColor("#000000"));
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgTodo.setBackgroundResource(R.drawable.todo);
        mImgDone.setBackgroundResource(R.drawable.done);
    }
}

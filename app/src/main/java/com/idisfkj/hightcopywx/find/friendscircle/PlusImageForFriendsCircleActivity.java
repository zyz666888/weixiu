package com.idisfkj.hightcopywx.find.friendscircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.uploadpictureutil.Constants;
import com.idisfkj.hightcopywx.find.uploadpictureutil.ViewPagerAdapter;

import java.util.ArrayList;

public class PlusImageForFriendsCircleActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ArrayList<String> mImaList;
    private int mPosition;
    private ViewPager mViewPager;
    private TextView mPositionTv;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_plus_image_friend_circle);
        init();
    }

    private void init() {
        //获取传递过来的数据
        mImaList = getIntent().getStringArrayListExtra(Constants.IMG_LIST);
        mPosition = getIntent().getIntExtra(Constants.POSITION, 0);
        //初始化控件
        initWidget();
    }

    private void initWidget() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager_friend_circle);
        mPositionTv = (TextView) findViewById(R.id.position_tv_friend_circle);
        //返回的点击事件
        findViewById(R.id.back_iv_friend_circle).setOnClickListener(this);
        //设置ViewPager切换的事件监听
        mViewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(PlusImageForFriendsCircleActivity.this, mImaList);
        mViewPager.setAdapter(mAdapter);
        mPositionTv.setText(mPosition + 1 + "/" + mImaList.size());
        mViewPager.setCurrentItem(mPosition);
    }
    /**
     * 定义方法返回上一个界面
     */
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(Constants.IMG_LIST, mImaList);
        setResult(Constants.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv_friend_circle:
                back();
                break;
            default:
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        mPositionTv.setText(position + 1 + "/" + mImaList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果返回键被按下
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

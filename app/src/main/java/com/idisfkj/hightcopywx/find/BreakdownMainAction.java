package com.idisfkj.hightcopywx.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class BreakdownMainAction extends Activity implements View.OnClickListener {


    private TextView textview4;
    private TextView textview5;
    private TextView textview6;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);
        textview4=(TextView)findViewById(R.id.textview4);
        textview5=(TextView)findViewById(R.id.textview5);
        textview6=(TextView)findViewById(R.id.textview6);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);
        img6=(ImageView)findViewById(R.id.img6);
        textview4.setOnClickListener(this);
        textview5.setOnClickListener(this);
        textview6.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);

        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_breakdown);
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
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textview4:
                Intent intent3 = new Intent(this,BreakdownInfoAction.class);
                startActivity(intent3);
                break;
            case R.id.textview5:
                Intent intent4 = new Intent(this,RecoveryAction.class);
                startActivity(intent4);
                break;
            case R.id.textview6:
                Intent intent5 = new Intent(this,MeasureAction.class);
                startActivity(intent5);
                break;

            case R.id.img4:
                Intent intent8 = new Intent(this,BreakdownInfoAction.class);
                startActivity(intent8);
                break;
            case R.id.img5:
                Intent intent9 = new Intent(this,RecoveryAction.class);
                startActivity(intent9);
                break;
            case R.id.img6:
                Intent intent10 = new Intent(this,MeasureAction.class);
                startActivity(intent10);
                break;
        }
    }
}

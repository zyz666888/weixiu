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

public class CheckMainAction extends Activity implements View.OnClickListener {

    private TextView textview2;
    private TextView textview3;
    private ImageView img2;
    private ImageView img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkreports);
        textview2=(TextView)findViewById(R.id.textview2);
        textview3=(TextView)findViewById(R.id.textview3);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        textview2.setOnClickListener(this);
        textview3.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_check);
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
            case R.id.textview2:
                Intent intent2 = new Intent(this,MaintainAction.class);
                startActivity(intent2);
                break;

            case R.id.textview3:
                Intent intent3 = new Intent(this,MeasureAction.class);
                startActivity(intent3);
                break;
            case R.id.img2:
                Intent intent7 = new Intent(this,MaintainAction.class);
                startActivity(intent7);
                break;

            case R.id.img3:
                Intent intent8 = new Intent(this,MeasureAction.class);
                startActivity(intent8);
                break;
        }
    }
}

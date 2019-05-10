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

public class InstallMainAction extends Activity implements View.OnClickListener {

    private TextView textview1;
    private ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.installreports);
        textview1=(TextView)findViewById(R.id.textview1);
        img1=(ImageView)findViewById(R.id.img1);
        textview1.setOnClickListener(this);
        img1.setOnClickListener(this);
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_install);
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
    }
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textview1:
                Intent intent1 = new Intent(this,InstallAndDebugAction.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.img1:
                Intent intent6 = new Intent(this,InstallAndDebugAction.class);
                startActivity(intent6);
                finish();
                break;

        }
    }
}

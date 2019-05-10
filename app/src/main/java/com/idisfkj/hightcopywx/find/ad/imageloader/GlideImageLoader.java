package com.idisfkj.hightcopywx.find.ad.imageloader;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;

public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}

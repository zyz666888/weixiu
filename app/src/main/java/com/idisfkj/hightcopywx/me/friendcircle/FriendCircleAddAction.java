package com.idisfkj.hightcopywx.me.friendcircle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.uploadpictureutil.Constants;
import com.idisfkj.hightcopywx.find.uploadpictureutil.GridViewAdapter;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PictureSelectorConfig;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PlusImageActivity;
import com.idisfkj.hightcopywx.uploadpictures.UploadHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

/**
 * Created by dell on 2018/5/25.
 */

public class FriendCircleAddAction extends Activity {

    private ArrayAdapter adapter;
    //处理信息
    private EditText problemdesc;//问题描述
    //保存上传图片的数据源
    private GridView mGridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter;
    private Button cancel,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_circle);
        init();
        //处理信息
        problemdesc = (EditText) findViewById(R.id.problemdesc);
        submit=(Button) findViewById(R.id.friendcircle_submit);
        cancel=(Button) findViewById(R.id.friendcircle_cancel);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //uploadPics();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //图片选择器的回显
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    //图片选择结果的回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    //例如 LocalMedia里面返回了三种path
                    //1、media.getPath() 为原图的path
                    //2、media.getCutPath() 为裁剪后的path 需要判断media.isCut() 是否为true
                    //3、media.getCompressPath() 为压缩后的path 徐判断media.isCompressed() 是否weitrue
                    //如果裁剪并压缩了 则以取到的压缩路径为准 因为是先裁剪后压缩的
                    break;

                default:
                    break;
            }
        }
        if (requestCode == Constants.REQUEST_CODE_MAIN && resultCode == Constants.RESULT_CODE_VIEW_IMG) {
            //查看了大图界面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(Constants.IMG_LIST);
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAdapter.notifyDataSetChanged();
        }

    }

    //图片初始化
    private void init() {
        mGridView = (GridView) findViewById(R.id.gridView_recovery1);
        initGridView();
    }

    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter = new GridViewAdapter(FriendCircleAddAction.this, mPicList);
        mGridView.setAdapter(mGridViewAdapter);
        //设置GridView的条目的点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    /**
     * 添加照片
     *
     * @param num
     */
    private void selectPic(int num) {
        PictureSelectorConfig.initMultiConfig(FriendCircleAddAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(FriendCircleAddAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
        finish();
    }

    /**
     * 处理选择的照片的地址
     *
     * @param picList
     */
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            File file = new File(localMedia.getCompressPath().toString());
            String name = file.getName();
            long length = file.length();
            Log.e("PATH", name + " 所占的内存是： " + length / 1024 + "KB");
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath();
                mPicList.add(compressPath);
                mGridViewAdapter.notifyDataSetChanged();
            }
        }
    }

    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList.get(i)));
                }
                UploadHelper helper = new UploadHelper(FriendCircleAddAction.this);
                helper.friendcirclepost(FriendCircleAddAction.this, getProperties(getApplicationContext()).getProperty("friendcircleUploadpicURL"), files);
            }
        }).start();
    }
}

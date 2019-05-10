package com.idisfkj.hightcopywx.find;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.camerautil.StaticClass;
import com.idisfkj.hightcopywx.camerautil.UtilTools;
import com.idisfkj.hightcopywx.entity.BaseQualityTestInfo;
import com.idisfkj.hightcopywx.find.uploadpictureutil.Constants;
import com.idisfkj.hightcopywx.find.uploadpictureutil.GridViewAdapter;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PictureSelectorConfig;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PlusImageActivity;
import com.idisfkj.hightcopywx.uploadpictures.UploadHelper;
import com.idisfkj.hightcopywx.util.OKHttpUtil;
import com.idisfkj.hightcopywx.util.UUIDUtil;
import com.karics.library.zxing.android.CaptureActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class QualityInfoAction extends Activity {
    private static final int REQUEST_CODE_SCAN = 0x0000;
    Button scanner;
    Button submit_q;
    public static final int REQUEST_CODE_WRITE = 9;
    public static final int REQUEST_CODE_CAMERA = 10;
    private GridView mGridView;
    //保存上传图片的数据源
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter;
    //    private static String uploadpicURL = "http://192.168.50.200:8080/imes/quality/multiFileUpload.do";
//    private static String scanURL = "http://192.168.50.200:8080/imes/afterSale/productScan.do?productCode=";
//    private static String submitURL = "http://192.168.50.200:8080/imes/quality/saveQuality.do";
    //产品信息扫一扫
    EditText pdName_q, pdId_q, sellId_q, proTaskId_q;
    private String AFTERSALEPROBLEMDESC = "0";
    private String AFTERSALEFIXDESC = "1";
    private String QUALITYTESTRESULT = "2";
    private String qualitytestid;
    private Button cancel;
    private EditText testName, testResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quality_test_info);
        Intent intent=getIntent();
        String toast=intent.getStringExtra("toast");
        if(toast!=null&&!toast.isEmpty()) {
            Toast.makeText(QualityInfoAction.this, toast, Toast.LENGTH_SHORT).show();
        }
        scanner = (Button) findViewById(R.id.ECoder_scaning);
        submit_q = (Button) findViewById(R.id.submit_q);
        cancel = (Button) findViewById(R.id.cancel_q);

        //初始化的方法
        init();
        //扫一扫初始化
        initProductInfo();
        //扫一扫按钮按下
        scanner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(QualityInfoAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        //取消按钮按下
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindFragment.class);
                startActivity(intent);
                finish();
            }
        });
        //确认按钮按下
        submit_q.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(pdId_q.getText().toString().isEmpty()){
                    Toast.makeText(QualityInfoAction.this,"产品编号不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(testName.getText().toString().isEmpty()){
                    Toast.makeText(QualityInfoAction.this,"检查项不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(testResult.getText().toString().isEmpty()){
                    Toast.makeText(QualityInfoAction.this,"结果不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseQualityTestInfo bs = new BaseQualityTestInfo();
                qualitytestid = UUIDUtil.getUUID();
                //质量检查ID
                bs.setQualityTestId(qualitytestid);
                //产品ID
                bs.setPdId(pdId_q.getText().toString());
                //检查结果
                bs.setTestResult(testResult.getText().toString());
                //检查项
                bs.setTestName(testName.getText().toString());
                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(QualityInfoAction.this);
                http.insertQualityTestInfo(bs, getProperties(getApplicationContext()).getProperty("qualitySubmitURL"));
            }
        });
    }


    class ScanThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(QualityInfoAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdScanURL") + pdId_q.getText());
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String val = data.getString("value");
            JSONObject dataJson = null;

            try {
                dataJson = new JSONObject(val);
                JSONObject response = dataJson.getJSONObject("data");
                String productCode = null2empty(response.get("productCode").toString());//产品编号
                String productName = null2empty(response.get("productName").toString());//产品名称
                String orderCode = null2empty(response.get("orderCode").toString());//销售订单号
                String taskNumber = null2empty(response.get("taskNumber").toString());//生产任务号
                pdName_q.setText(productName);
                pdId_q.setText(productCode);
                sellId_q.setText(orderCode);
                proTaskId_q.setText(taskNumber);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    //扫一扫数据获取--结束---

    public void permission(String permision, int code) {
        String[] permissions = {permision};
        //验证是否许可权限
        if (ContextCompat.checkSelfPermission(this, permision) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                QualityInfoAction.this.requestPermissions(permissions, code);
            }
        } else {
            if (code == REQUEST_CODE_WRITE) {
                permission(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA);
            }
            if (code == REQUEST_CODE_CAMERA) {
                //申请权限
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //判断内存卡是否可用,可以的话进行储存
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
                startActivityForResult(intent, StaticClass.CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                    permission(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA);
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    UtilTools.showShrotToast(this, "没有权限不能打开相机");
                }
                return;
            }

            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //判断内存卡是否可用,可以的话进行储存
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
                    startActivityForResult(intent, StaticClass.CAMERA_REQUEST_CODE);
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    UtilTools.showShrotToast(this, "没有权限不能打开相机");
                }
                break;
        }
    }

    //扫一扫数据获取--开始---
    private void initProductInfo() {
        pdName_q = (EditText) findViewById(R.id.pdName_q);//产品名称
        pdId_q = (EditText) findViewById(R.id.pdId_q);//产品编号
        sellId_q = (EditText) findViewById(R.id.sellId_q);//销售订单号
        proTaskId_q = (EditText) findViewById(R.id.proTaskId_q);//生产任务号
        testName = (EditText) findViewById(R.id.testName);//检验项
        testResult = (EditText) findViewById(R.id.testResult);//检验结果
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra("codedContent");
                //Bitmap bitmap = data.getParcelableExtra("codedBitmap");
                pdId_q.setText(content);
                //数据库的值反显
                new ScanThread().start();
            }
        }
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

    public String null2empty(String value) {
        if (value.equals("null")) {
            value = "";
        }
        return value;
    }

    private void init() {
        mGridView = (GridView) findViewById(R.id.gridView_quality);
        initGridView();
    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mGridViewAdapter = new GridViewAdapter(QualityInfoAction.this, mPicList);
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
        PictureSelectorConfig.initMultiConfig(QualityInfoAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(QualityInfoAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
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
                UploadHelper helper = new UploadHelper(QualityInfoAction.this);
                helper.postQuality(QualityInfoAction.this, getProperties(getApplicationContext()).getProperty("uploadpicURL"), files, QUALITYTESTRESULT, qualitytestid);
            }
        }).start();
    }
}

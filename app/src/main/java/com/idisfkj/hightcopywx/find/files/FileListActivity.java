package com.idisfkj.hightcopywx.find.files;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import com.idisfkj.hightcopywx.util.OKHttpUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class FileListActivity extends Activity {
    private static final int CHOOSE_REQUEST_CODE = 1000;

    private ListView listView;
    private WebView webView;
    private ImageView return_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);
        listView = (ListView)findViewById(R.id.file_listview);
        return_img = (ImageView) findViewById(R.id.return_filelist);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_file);
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
        new MyThread().start();
    }

    //列表的显示-----------开始-----------------
    Handler handlerlist = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String strByJson = data.getString("value");
            //先转JsonObject
            JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
            String strData="";
            if(jsonObject.get("data")!=null){
                strData=jsonObject.get("data").toString();
            }
            final ArrayList<BaseFile> userBeanList = new ArrayList<>();

            //获取产品检验记录页面的list --开始---
            if (strData != null && !strData.isEmpty()) {
                JsonObject jsonObjectdata = new JsonParser().parse(strData).getAsJsonObject();
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObjectdata.getAsJsonArray("list");
                Gson gson = new Gson();
                //循环遍历
                for (JsonElement user : jsonArray) {
                    //通过反射 得到UserBean.class
                    BaseFile userBean = gson.fromJson(user, new TypeToken<BaseFile>() {
                    }.getType());
                    userBeanList.add(userBean);
                }
                ArrayList<BaseFile> baseFileInfos = new ArrayList<>();
                for (int i = 0; i < userBeanList.size(); i++) {
                    BaseFile info = new BaseFile();
                    info.setName(userBeanList.get(i).getName());
                    info.setSize(userBeanList.get(i).getSize());
                    info.setType(userBeanList.get(i).getType());
                    info.setCreateTime(userBeanList.get(i).getCreateTime());
                    baseFileInfos.add(info);
                }
                FilesAdapter adapter = new FilesAdapter(baseFileInfos);
                listView.setAdapter(adapter);
            }
            listView.setAdapter(new FilesAdapter(userBeanList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BaseFile info= userBeanList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("fileUrl", getProperties(FileListActivity.this).getProperty("IP")+info.getUrl().replace("\\","/"));//文件超链接
                    Intent intent = new Intent(FileListActivity.this,FileDownloadOpenActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {
            final String address = getProperties(FileListActivity.this).getProperty("FILE_LIST");
            // 需要做的事:发送消息
            OKHttpUtil http=new OKHttpUtil(FileListActivity.this);
            String str = http.httpGetInfo(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            handlerlist.sendMessage(msg);
        }
    }

    //列表的显示-----------结束-----------------

    /**
     * 打开文件
     */
    private void openFile(String path) {
        TbsReaderView readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", path);
        //加载插件保存的路径
        bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "temp");
        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(getFileType(path), false);
        if (b) {
            readerView.openFile(bundle);
        }
       // frameLayout.addView(readerView);
    }
    /***
     * 获取文件类型
     *
     * @param path 文件路径
     * @return 文件的格式
     */
    private String getFileType(String path) {
        String str = "";

        if (TextUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case CHOOSE_REQUEST_CODE:
                        Uri uri = data.getData();
                        String path = getPathFromUri(this, uri);
                        openFile(path);
                        break;
                }
            }
        }
    }

    /**
     * @param context
     * @param uri     文件的uri
     * @return 文件的路径
     */
    public String getPathFromUri(final Context context, Uri uri) {
        // 选择的图片路径
        String selectPath = null;

        final String scheme = uri.getScheme();
        if (uri != null && scheme != null) {
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                // content://开头的uri
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 取出文件路径
                    selectPath = cursor.getString(columnIndex);

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!selectPath.startsWith("/storage") && !selectPath.startsWith("/mnt")) {
                        // 检查是否有"/mnt"前缀
                        selectPath = "/mnt" + selectPath;
                    }
                    //关闭游标
                    cursor.close();
                }
            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {// file:///开头的uri
                // 替换file://
                selectPath = uri.toString().replace("file://", "");
                int index = selectPath.indexOf("/sdcard");
                selectPath = index == -1 ? selectPath : selectPath.substring(index);
                if (!selectPath.startsWith("/mnt")) {
                    // 加上"/mnt"头
                    selectPath = "/mnt" + selectPath;
                }
            }
        }
        return selectPath;
    }
}





package com.idisfkj.hightcopywx.find.files;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;

import java.io.File;

public class FileDownloadOpenActivity extends Activity implements ReaderCallback {

    private TbsReaderView mTbsReaderView;
    private DownloadManager mDownloadManager;
    private long mRequestId;
    private DownloadObserver mDownloadObserver;
    private String mFileUrl;
    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileopen);
        Bundle bundle = getIntent().getExtras();
        mFileUrl = bundle.getString("fileUrl");//文件路径
        mTbsReaderView = new TbsReaderView(this, this);
        RelativeLayout rootRl = (RelativeLayout) findViewById(R.id.rl_root);
        rootRl.addView(mTbsReaderView, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mFileName = parseName(mFileUrl);
        if (isLocalExist()) {
            displayFile();
        } else {
            startDownload();
        }
    }

    private void displayFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", getLocalFile().getPath());
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(mFileName), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }

    private boolean isLocalExist() {
        return getLocalFile().exists();
    }

    private File getLocalFile() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mFileName);
    }

    private void startDownload() {
        mDownloadObserver = new DownloadObserver(new Handler());
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, mDownloadObserver);

        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(mFileUrl));
       // Request request = new Request(Uri.parse("http://114.242.94.130:5367/cxy/老化管理程序.doc"));

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mFileName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
        mRequestId = mDownloadManager.enqueue(request);
    }

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mRequestId);
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载的字节数
                int currentBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //总需下载的字节数
                int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //状态所在的列索引
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                Log.i("downloadUpdate: ", currentBytes + " " + totalBytes + " " + status);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
        if (mDownloadObserver != null) {
            getContentResolver().unregisterContentObserver(mDownloadObserver);
        }
    }

    private class DownloadObserver extends ContentObserver {

        private DownloadObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.i("downloadUpdate: ", "onChange(boolean selfChange, Uri uri)");
            queryDownloadStatus();
        }
    }

    public static final String INTERNET = "android.permission.INTERNET";
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final String DOWNLOAD_WITHOUT_NOTIFICATION = "android.permission.DOWNLOAD_WITHOUT_NOTIFICATION";
    public static final String ACCESS_DOWNLOAD_MANAGER = "android.permission.ACCESS_DOWNLOAD_MANAGER";
    public static final String WRITE_SETTINGS = "android.permission.WRITE_SETTINGS";
    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";

    public static void permissionValidate(Context context) {

        //检查代码是否拥有这个权限
        int checkResultINTERNET = context.checkCallingOrSelfPermission(INTERNET);
        //if(!=允许),抛出异常
        if (checkResultINTERNET != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请打开网络权限", Toast.LENGTH_SHORT).show();
        }
        //检查代码是否拥有这个权限
        int checkResultWRITE_EXTERNAL_STORAGE = context.checkCallingOrSelfPermission(WRITE_EXTERNAL_STORAGE);
        //if(!=允许),抛出异常
        if (checkResultWRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请打开sd卡写权限", Toast.LENGTH_SHORT).show();
        }
        //检查代码是否拥有这个权限
        int checkResultDOWNLOAD_WITHOUT_NOTIFICATION = context.checkCallingOrSelfPermission(DOWNLOAD_WITHOUT_NOTIFICATION);
        //if(!=允许),抛出异常
        if (checkResultDOWNLOAD_WITHOUT_NOTIFICATION != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请打开下载权限", Toast.LENGTH_SHORT).show();
        }
        //检查代码是否拥有这个权限
        int checkResultACCESS_DOWNLOAD_MANAGER = context.checkCallingOrSelfPermission(ACCESS_DOWNLOAD_MANAGER);
        //if(!=允许),抛出异常
        if (checkResultACCESS_DOWNLOAD_MANAGER != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请打开允许进入下载管理权限", Toast.LENGTH_SHORT).show();
        }
        //检查代码是否拥有这个权限
        int checkResultWRITE_SETTINGS = context.checkCallingOrSelfPermission(WRITE_SETTINGS);
        //if(!=允许),抛出异常
        if (checkResultWRITE_SETTINGS != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请打开写权限", Toast.LENGTH_SHORT).show();
        }
        //检查代码是否拥有这个权限
        int checkResultREAD_PHONE_STATE = context.checkCallingOrSelfPermission(READ_PHONE_STATE);
        //if(!=允许),抛出异常
        if (checkResultREAD_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请打开读取设备硬件信息权限", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.idisfkj.hightcopywx.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.idisfkj.hightcopywx.entity.BaseBreakDownInfo;
import com.idisfkj.hightcopywx.entity.BaseInstallInfo;
import com.idisfkj.hightcopywx.entity.BaseMaintainInfo;
import com.idisfkj.hightcopywx.entity.BaseMeasureInfo;
import com.idisfkj.hightcopywx.entity.BaseQualityTestInfo;
import com.idisfkj.hightcopywx.entity.BaseRecoveryInfo;
import com.idisfkj.hightcopywx.entity.BaseSign;
import com.idisfkj.hightcopywx.find.BreakdownMainAction;
import com.idisfkj.hightcopywx.find.CheckMainAction;
import com.idisfkj.hightcopywx.find.InstallMainAction;
import com.idisfkj.hightcopywx.find.QualityInfoAction;
import com.idisfkj.hightcopywx.find.RecoveryAction;
import com.idisfkj.hightcopywx.find.aftersale.AftersaleMainAction;
import com.idisfkj.hightcopywx.find.aftersale.BaseFixInfo;
import com.idisfkj.hightcopywx.find.customervisit.BaseVisitCustomerInfo;
import com.idisfkj.hightcopywx.find.signin.SignInMainActivity;
import com.idisfkj.hightcopywx.loginandregister.LoginActivity;
import com.idisfkj.hightcopywx.loginandregister.RegistActivity;
import com.idisfkj.hightcopywx.loginandregister.entity.User;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.ui.widget.RegisterActivity;
import com.idisfkj.hightcopywx.util.Islogined;
import com.idisfkj.hightcopywx.util.ResultCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestHttpUtil {
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    private Context mContext;

    public TestHttpUtil(Context context) {
        this.mContext = context;
    }

    public String httpGet(String url) {
        String str = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        Call call = okHttpClient.newCall(request);
        //同步GET请求
        try {
            Response response = call.execute();
            if (!response.isSuccessful()) {
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
            }
            str = response.body().string();
            String cookie = response.header("Set-Cookie");
            Log.d("我获得的cookie是", cookie);
            Islogined.saveCookiePreference(this.mContext, cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public String httpGetScan(String url) {
        String str = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().addHeader("cookie", Islogined.getCookiePreference(this.mContext));;
        Request request = builder.get().url(url).build();
        Call call = okHttpClient.newCall(request);
        //同步GET请求
        try {
            Response response = call.execute();
            if (!response.isSuccessful()) {
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
            }
            str = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    //安装调试
    public void testPost(String info, String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                //客户信息
                .add("installId", info) //安装
                .build();
        //创建一个请求
        Request request = new Request
                .Builder()
                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
                .post(formBody)//Post请求的参数传递
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "提交安装调试信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //此方法运行在子线程中，不能在此方法中进行UI操作。
                String result = response.body().string();
                response.body().close();
                //获取返回值
                JSONObject dataJson = null;

                try {
                    dataJson = new JSONObject(result);
                    //产品名称
                    String statusCode = dataJson.get("statusCode").toString();//名称
                    if (!statusCode.isEmpty() && statusCode.equals("200")) {
                        Intent intent = new Intent(mContext, InstallMainAction.class);
                        intent.putExtra("toast", "提交安装调试信息成功");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("toast", "超时，请重新登录！");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, RecoveryAction.class);
                        intent.putExtra("toast", "提交安装调试信息失败");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }



}
package com.idisfkj.hightcopywx.util;

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

public class OKHttpUtil {
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    private Context mContext;

    public OKHttpUtil(Context context) {
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
    public void insertInstallAndDebugInfo(BaseInstallInfo info, String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                //客户信息
                .add("installId", info.getInstallId()) //安装
                .add("customerNm", info.getCustomerNm()) //客户名称
                .add("customerAdd", info.getCustomerAdd()) //客户地点
                .add("customerTel", info.getCustomerTel()) //手机
                //产品信息
                .add("pdId_install", info.getPdId_install()) //产品信息ID
                .add("sn_install", info.getSn_install()) //产品信息序列号
                .add("project_install", info.getProject_install()) //产品信息所属项目
                .add("subwaytype_install", info.getSubwaytype_install()) //产品信息列车车型
                .add("subwayid_install", info.getSubwayid_install()) //产品信息对应车辆编号
                //备件信息
                .add("pdId_r_install", info.getPdId_install()) //备件信息ID
                .add("isHaveExtraParts_install", info.getIsHaveExtraParts_install()) //备件信息是否有额外配件
                .add("pd_r_num_install", info.getPd_r_num_install()) //备件信息数量
                //处理信息
                .add("problemdesc_install", info.getProblemdesc_install()) //描述
                .add("installStartTime", info.getInstallStartTime()) //开始时间
                .add("installEndTime", info.getInstallEndTime()) //结束时间
                .add("sumTime", info.getSumTime()) //总工时
                .add("isHaveLocalProblem", info.getIsHaveLocalProblem()) //是否有现场问题
                .add("localProblemDesc", info.getLocalProblemDesc()) //现场问题描述
                //结果
                .add("installResult", info.getInstallResult()) //安装调试结果
                .add("unfinishedDesc", info.getUnfinishedDesc()) //开口项描述
                .add("installer", info.getInstaller()) //安装调试配合人
                .add("reporter", info.getReporter()) //报告人
                .add("reportTime", info.getReportTime()) //报告时间
                .add("reportReceivePart", info.getReportReceivePart()) //报告接收部门
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

    //检修保养
    public void insertMaintainInfo(BaseMaintainInfo info, String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                //客户信息
                .add("maintainId", info.getMaintainId()) //安装
                .add("customerNm_maintain", info.getCustomerNm_maintain()) //客户名称
                .add("contact_maintain", info.getContact_maintain()) //客户联系人
                .add("customerTel_maintain", info.getCustomerTel_maintain()) //手机
                //产品信息
                .add("pdId_maintain", info.getPdId_maintain()) //产品信息ID
                .add("sn_maintain", info.getSn_maintain()) //产品信息序列号
                .add("project_maintain", info.getProject_maintain()) //产品信息所属项目
                .add("subwaytype_maintain", info.getSubwaytype_maintain()) //产品信息列车车型
                .add("subwayid_maintain", info.getSubwayid_maintain()) //产品信息对应车辆编号
                //备件信息
                .add("pdId_r_maintain", info.getPdId_r_maintain()) //备件信息ID
                .add("isHaveReplaceParts_maintain", info.getIsHaveReplaceParts_maintain()) //配件更换
                .add("pd_r_num_maintain", info.getPd_r_num_maintain()) //备件信息数量
                //检修内容
                .add("maintainType", info.getMaintainType()) //检修保养类型
                .add("maintainDesc", info.getMaintainDesc()) //描述
                .add("maintainStartTime", info.getMaintainStartTime()) //开始时间
                .add("maintainEndTime", info.getMaintainEndTime()) //结束时间
                .add("sumTime_maintain", info.getSumTime_maintain()) //总工时
                //结果
                .add("maintainResult", info.getMaintainResult()) //检修保养结果
                .add("hiddenDangerDesc_maintain", info.getHiddenDangerDesc_maintain()) //隐患描述
                .add("maintainer", info.getMaintainer()) //检修保养人
                .add("reporter_maintain", info.getReporter_maintain()) //报告人
                .add("reportTime_maintain", info.getReporter_maintain()) //报告时间
                .add("reportReceivePart_maintain", info.getReporter_maintain()) //报告接收部门
                .add("correctActionIsNeeded_maintain", info.getCorrectActionIsNeeded_maintain()) //是否需要纠正措施
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
                Log.e("TAG", "提交检修保养服务报告信息失败");
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
                        Intent intent = new Intent(mContext, CheckMainAction.class);
                        intent.putExtra("toast", "提交检修保养服务报告信息成功");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("toast", "超时，请重新登录！");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, CheckMainAction.class);
                        intent.putExtra("toast", "提交检修保养服务报告信息失败");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }

    //提交质量检验信息
    public void insertQualityTestInfo(BaseQualityTestInfo info, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                .add("qualityTestId", info.getQualityTestId()) //质量检验ID
                .add("pdId", info.getPdId()) //产品信息ID
                .add("testResult", info.getTestResult()) //检查结果
                .add("testName", info.getTestName()) //检查项
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
                Log.e("TAG", "提交质量检验信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //此方法运行在子线程中，不能在此方法中进行UI操作。
                String result = response.body().string();
                response.body().close();
                Log.i("result", result);

                //获取返回值
                JSONObject dataJson = null;

                try {
                    dataJson = new JSONObject(result);
                    //产品名称
                    String statusCode = dataJson.get("statusCode").toString();//名称
                    if (!statusCode.isEmpty() && statusCode.equals("200")) {
                        Intent intent = new Intent(mContext, QualityInfoAction.class);
                        intent.putExtra("toast", "提交质量检验信息成功!");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("toast", "超时，请重新登录!");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, QualityInfoAction.class);
                        intent.putExtra("toast", "提交质量检验信息失败!");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //安装调试
    public void insertMeasureInfo(BaseMeasureInfo info,String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                //客户信息
                .add("measureId", info.getMeasureId()) //纠正措施
                .add("customerNm_measure", info.getCustomerNm_measure()) //客户名称
                .add("infoSource_measure", info.getInfoSource_measure()) //信息来源
                //产品信息
                .add("pdId_measure", info.getPdId_measure()) //产品信息ID
                .add("sn_measure", info.getSn_measure()) //产品信息序列号
                .add("project_measure", info.getProject_measure()) //产品信息所属项目
                .add("occuTime_measure", info.getOccuTime_measure()) //发生时间
                .add("reportTime_measure", info.getReportTime_measure()) //报告时间
                .add("occuAd_measure", info.getOccuAd_measure()) //发生地点
                .add("problemdesc_measure", info.getProblemdesc_measure()) //故障描述
                .add("causeAnalysis_measure", info.getCauseAnalysis_measure()) //原因分析
                .add("measurePlan", info.getMeasurePlan()) //纠正措施计划
                .add("resDept_measure", info.getResDept_measure()) //责任部门
                .add("personLiable_measure", info.getPersonLiable_measure()) //责任人
                .add("timeRequest_measure", info.getTimeRequest_measure()) //时间要求
                .add("measure", info.getMeasure()) //纠正措施验证
                .add("measurePerson", info.getMeasurePerson()) //验证人
                .add("measureTime", info.getMeasureTime()) //验证时间
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
                Log.e("TAG", "提交纠正措施报告信息失败");
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
                        Intent intent = new Intent(mContext, BreakdownMainAction.class);
                        intent.putExtra("toast", "提交纠正措施报告信息成功");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("toast", "超时，请重新登录！");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, RecoveryAction.class);
                        intent.putExtra("toast", "提交纠正措施报告信息失败");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }

    //故障信息接收传递
    public void insertBreakdownInfo(BaseBreakDownInfo info, String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                //客户信息
                .add("breakdownId", info.getBreakdownId()) //纠正措施
                .add("customerNm_breakdown", info.getCustomerNm_breakdown()) //客户名称
                .add("receiveDept_breakdown", info.getReceiveDept_breakdown()) //信息接收部门
                .add("receiver_breakdown", info.getReceiver_breakdown()) //接收人
                .add("project_breakdown", info.getProject_breakdown()) //所属项目
                .add("occuTime_breakdown", info.getOccuTime_breakdown()) //发生时间
                .add("problemDesc_breakdown", info.getProblemDesc_breakdown()) //问题描述
                .add("reportDept_breakdown", info.getReportDept_breakdown()) //报送部门
                .add("reporter_breakdown", info.getReporter_breakdown()) //报送人
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
                Log.e("TAG", "故障信息接收传递信息失败");
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
                        Intent intent = new Intent(mContext, BreakdownMainAction.class);
                        intent.putExtra("toast", "故障信息接收传递信息成功");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("toast", "超时，请重新登录！");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, RecoveryAction.class);
                        intent.putExtra("toast", "故障信息接收传递信息失败");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }
    //故障信息接收传递
    public void insertRecoveryInfo(BaseRecoveryInfo info, String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                //客户信息
                .add("recoveryId", info.getRecoveryId()) //故障处理服务ID
                .add("customerNm_recovery", info.getCustomerNm_recovery()) //客户名称
                .add("infoSource_recovery", info.getInfoSource_recovery()) //信息来源
                .add("problemOccuTime_recovery", info.getProblemOccuTime_recovery()) //故障发生时间
                .add("problemReportTime_recovery", info.getProblemReportTime_recovery()) //故障报告时间
                .add("problemOccuAd_recovery", info.getProblemOccuAd_recovery()) //故障发生地点
                .add("problemReporter_recovery", info.getProblemReporter_recovery()) //故障报告人
                .add("pdId_recovery", info.getPdId_recovery()) //产品信息ID
                .add("sn_recovery", info.getSn_recovery()) //产品信息序列号
                .add("project_recovery", info.getProject_recovery()) //产品信息所属项目
                .add("subwaytype_recovery", info.getSubwaytype_recovery()) //产品信息列车车型
                .add("subwayid_recovery", info.getSubwayid_recovery()) //产品信息对应车辆编号
                .add("subwayrunhistory_recovery", info.getSubwayrunhistory_recovery()) //产品信息列车走行历史
                .add("problemdesc_recovery", info.getProblemdesc_recovery()) //问题描述
                .add("fixdesc_recovery", info.getFixdesc_recovery()) //处理描述
                .add("problemJudg_recovery", info.getProblemJudg_recovery()) //问题判断
                .add("recoveryStartTime", info.getRecoveryStartTime()) //修复开始时间
                .add("recoveryEndTime", info.getRecoveryEndTime()) //修复结束时间
                .add("diagnoseTime_recovery", info.getDiagnoseTime_recovery()) //诊断时长
                .add("fixTime_recovery", info.getFixTime_recovery()) //修理时长
                .add("testTime_recovery", info.getTestTime_recovery()) //试验时长
                .add("sumTime_recovery", info.getSumTime_recovery()) //总工时
                .add("sumTimeCost_recovery", info.getSumTimeCost_recovery()) //工时成本
                .add("pdId_r_recovery", info.getPdId_r_recovery()) //备件信息ID
                .add("isReplaceParts_recovery", info.getIsReplaceParts_recovery()) //备件信息是否有配件更换
                .add("pd_r_num_recovery", info.getPd_r_num_recovery()) //备件信息数量
                .add("fixResult_recovery", info.getFixResult_recovery()) //处理结果
                .add("qualityDefectCost_recovery", info.getQualityDefectCost_recovery()) //质量缺陷成本
                .add("troubleShooting_recovery", info.getTroubleShooting_recovery()) //故障处理人
                .add("reporter_recovery", info.getReporter_recovery()) //报告人
                .add("reportTime_recovery", info.getReportTime_recovery()) //报告时间
                .add("reportReceivePart_recovery", info.getReportReceivePart_recovery()) //报告接收部门
                .add("correctActionIsNeeded_recovery", info.getCorrectActionIsNeeded_recovery()) //是否需要纠正措施

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
                Log.e("TAG", "故障处理服务报告信息失败");
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
                        Intent intent = new Intent(mContext, BreakdownMainAction.class);
                        intent.putExtra("toast", "故障处理服务报告信息成功");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("toast", "超时，请重新登录！");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, RecoveryAction.class);
                        intent.putExtra("toast", "故障处理服务报告信息失败");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }

    public String httpGetInfo(String url){
        String str="";
        OkHttpClient okHttpClient=new OkHttpClient();
        Request.Builder builder=new Request.Builder().addHeader("cookie", Islogined.getCookiePreference(this.mContext));
        Request request=builder.get().url(url).build();
        Call call=okHttpClient.newCall(request);
        //同步GET请求
        try {
            Response response=call.execute();
            if(!response.isSuccessful()) {
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
            }
            // str = new String(response.body().bytes(), "GB2312"); //然后将其转为gb2312
            str= response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    //客户拜访
    public void insertVisitCustomerInfo(BaseVisitCustomerInfo info, String url) {

        OkHttpClient okHttpClient = new OkHttpClient();

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        //Form表单格式的参数传递
//        FormBody formBody = new FormBody
//                .Builder()
//                .add("address", info.getAddress()) //质量检验ID
//                .add("customer", info.getCustomer()) //产品信息ID
//                .add("visittime", info.getVisittime()) //检查结果
//                .add("remarks", info.getRemarks()) //检查项
//                .build();

        Map<String,String> formParams=new HashMap<>();//传参
        formParams.put("address", info.getAddress());
        formParams.put("customer", info.getCustomer());
        formParams.put("visittime", info.getVisittime());
        formParams.put("remarks", info.getRemarks());
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key: formParams.keySet()) {
            sb.append(key+"="+formParams.get(key)+"&");
        }
        Log.i("TAG", ""+sb.toString());

        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());

        //创建请求
        Request request = new Request.Builder()
                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
                .url(url)
                .post(body)
                .build();

//        Gson gson=new Gson();
//        String json=gson.toJson(info);
//        //MediaType  设置Content-Type 标头中包含的媒体类型值
//        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                , json);
//
//        //创建一个请求
//        //.addHeader("content-type", "application/json;charset:utf-8")
//        //.addHeader("content-type", "text/html;charset:utf-8")
//        Request request = new Request
//                .Builder()
//                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
//                .addHeader("content-type", "application/json;charset:utf-8")
//                //.addHeader("content-type", "text/html;charset:utf-8")
//                .post(formBody)//Post请求的参数传递
//                .url(url)
//                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "提交客户拜访信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //此方法运行在子线程中，不能在此方法中进行UI操作。
                String result = response.body().string();
                response.body().close();
                Log.i("result", result);

                //获取返回值
                JSONObject dataJson = null;

                try {
                    dataJson = new JSONObject(result);
                    //产品名称
                    String statusCode = dataJson.get("statusCode").toString();//名称
                    if (!statusCode.isEmpty() && statusCode.equals("200")) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("toast", "提交客户拜访信息成功!");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals("300")) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("toast", "超时，请重新登录!");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, QualityInfoAction.class);
                        intent.putExtra("toast", "提交客户拜访信息失败!");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //员工签到
    public void insertSignInfo(BaseSign info, String url) {

        OkHttpClient okHttpClient = new OkHttpClient();

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        //Form表单格式的参数传递
//        FormBody formBody = new FormBody
//                .Builder()
//                .add("address", info.getAddress()) //签到地点
//                .add("signtime", info.getSigntime()) //签到时间
//                .add("currCompany", info.getCurrCompany()) //当前企业
//                .add("remarks", info.getRemarks()) //备注
//                .build();
//        Gson gson=new Gson();
//        String json=gson.toJson(info);
//        //MediaType  设置Content-Type 标头中包含的媒体类型值
//        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                , json);

        Map<String,String> formParams=new HashMap<>();//传参
        formParams.put("address", info.getAddress());
        formParams.put("signtime", info.getSigntime());
        formParams.put("currCompany", info.getCurrCompany());
        formParams.put("remarks", info.getRemarks());
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key: formParams.keySet()) {
            sb.append(key+"="+formParams.get(key)+"&");
        }
        Log.i("TAG", ""+sb.toString());

        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());

        //创建请求
        Request request = new Request.Builder()
                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
                .url(url)
                .post(body)
                .build();

        //创建一个请求
//        Request request = new Request
//                .Builder()
//                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
//                .post(formBody)//Post请求的参数传递
//                .url(url)
//                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "提交签到信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //此方法运行在子线程中，不能在此方法中进行UI操作。
                String result = response.body().string();
                response.body().close();
                Log.i("result", result);

                //获取返回值
                JSONObject dataJson = null;

                try {
                    dataJson = new JSONObject(result);
                    //产品名称
                    String resultCode = dataJson.get("resultCode").toString();//名称

                    if (!resultCode.isEmpty() && resultCode.equals(ResultCode.RESULT_CODE_OK)) {
                        Intent intent = new Intent(mContext, SignInMainActivity.class);
                        intent.putExtra("toast", "提交成功！");
                        intent.putExtra(SignInMainActivity.NOTIFY, "已签到");
                        mContext.startActivity(intent);
                   } else if (!resultCode.isEmpty() && resultCode.equals(ResultCode.RESULT_CODE_TIMEOUT)) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("toast", "超时，请重新登录!");
                    mContext.startActivity(intent);
                   }else {
                        Intent intent = new Intent(mContext, SignInMainActivity.class);
                        intent.putExtra("toast", "提交失败！");
                        intent.putExtra(SignInMainActivity.NOTIFY, "签到失败！");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //用户注册
    public void insertUserInfo(User info, String url) {

        OkHttpClient okHttpClient = new OkHttpClient();

        Map<String,String> formParams=new HashMap<>();//传参
        formParams.put("username", info.getUsername());
        formParams.put("password", info.getPassword());
        formParams.put("companyId", info.getCompanyId());
        formParams.put("name", info.getName());
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key: formParams.keySet()) {
            sb.append(key+"="+formParams.get(key)+"&");
        }
        Log.i("TAG", ""+sb.toString());

        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());

        //创建请求
        Request request = new Request.Builder()
                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
                .url(url)
                .post(body)
                .build();






        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "提交用户信息失败");
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
                    String statusCode = dataJson.get("resultCode").toString();//名称
                    if (!statusCode.isEmpty() && statusCode.equals(ResultCode.RESULT_CODE_OK)) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("toast", "注册成功，请登录！");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals(ResultCode.RESULT_CODE_ERROR)) {
                        Intent intent = new Intent(mContext, RegistActivity.class);
                        intent.putExtra("toast", "账号已存在，请重新注册！");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals(ResultCode.RESULT_CODE_TIMEOUT)) {
                        Intent intent = new Intent(mContext, RegistActivity.class);
                        intent.putExtra("toast", "超时，请重新注册！");
                        mContext.startActivity(intent);
                    }else {
                        Intent intent = new Intent(mContext, RegistActivity.class);
                        intent.putExtra("toast", "系统异常，请重新注册！");
                        mContext.startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }

    //  提交售后信息
    public void insertAftersaleInfo(BaseFixInfo info, String url) {

        Boolean postresult = false;
        // 2 创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        Map<String,String> formParams=new HashMap<>();//传参
        formParams.put("aftersaleId", info.getAftersaleId());//售后
        formParams.put("customerNm", info.getCustomerNm());//客户名称
        formParams.put("contact", info.getContact());//客户联系人
        formParams.put("customerTel", info.getCustomerTel());//产品信息ID
        formParams.put("pdId", info.getPdId());////产品信息ID
        formParams.put("sn", info.getSn());//产品信息序列号
        formParams.put("project", info.getProject());//产品信息所属项目
        formParams.put("subwayType", info.getSubwaytype());//产品信息列车车型
        formParams.put("subwayId", info.getSubwayid());//产品信息对应车辆编号
        formParams.put("subwayRunHistory", info.getSubwayrunhistory());//产品信息列车走行历史
        formParams.put("pdIdR", info.getPdId_r());//备件信息ID
        formParams.put("isReplaceParts", info.getIsReplaceParts());//备件信息是否有配件更换
        formParams.put("pdRNum", info.getPd_r_num());//备件信息数量
        formParams.put("problemDesc", info.getProblemDesc());//问题描述
        formParams.put("fixDesc", info.getFixDesc());//处理描述
        formParams.put("problemType", info.getProblemType());//问题判断
        formParams.put("fixStartTime", info.getFixStartTime());//修复开始时间
        formParams.put("fixEndTime", info.getFixEndTime());//修复结束时间
        formParams.put("diagnoseTime", info.getDiagnoseTime());//诊断时长
        formParams.put("fixTime", info.getFixTime());//修理时长
        formParams.put("testTime", info.getTestTime());//试验时长
        formParams.put("sumTime", info.getSumTime());//总工时
        formParams.put("sumTimeCost", info.getSumTimeCost());//工时成本
        formParams.put("fixResult", info.getFixResult());//处理结果
        formParams.put("qualityDefectCost", info.getQualityDefectCost());//质量缺陷成本
        formParams.put("troubleShooting", info.getTroubleShooting());//故障处理人
        formParams.put("reporter", info.getReporter());//报告人
        formParams.put("reportTime", info.getReportTime());//报告时间
        formParams.put("reportReceivePart", info.getReportReceivePart());//报告接收部门
        formParams.put("correctActionIsNeeded", info.getCorrectActionIsNeeded());//是否需要纠正措施

        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key: formParams.keySet()) {
            sb.append(key+"="+formParams.get(key)+"&");
        }
        Log.i("TAG", ""+sb.toString());

        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());
        //创建一个请求
        Request request = new Request
                .Builder()
                .addHeader("cookie", Islogined.getCookiePreference(this.mContext))
                .post(body)//Post请求的参数传递
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "提交售后信息失败");
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
                    String statusCode = dataJson.get("resultCode").toString();//名称
                    if (!statusCode.isEmpty() && statusCode.equals(ResultCode.RESULT_CODE_OK)) {
                        Intent intent = new Intent(mContext, AftersaleMainAction.class);
                        intent.putExtra("toast", "提交售后信息成功！");
                        mContext.startActivity(intent);
                    } else if (!statusCode.isEmpty() && statusCode.equals(ResultCode.RESULT_CODE_TIMEOUT)) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("toast", "超时，请重新登录！");
                        mContext.startActivity(intent);
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("result", result);
            }
        });
    }

}
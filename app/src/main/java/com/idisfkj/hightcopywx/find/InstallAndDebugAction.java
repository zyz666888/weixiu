package com.idisfkj.hightcopywx.find;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.entity.BaseInstallInfo;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

/**
 * Created by dell on 2018/5/25.
 */

public class InstallAndDebugAction extends Activity  {

    private static final int REQUEST_CODE_SCAN = 0x0000;//产品扫一扫
    private static final int REQUEST_CODE_SCAN_R = 0x0001;//备件扫一扫
    Button scan_install_01;
    Button scan_install_02;
    private Button  dateselect01_install,dateselect02_install,dateselect03_install;
    //产品信息
    EditText pdName_install, pdtype_install;
    //备件信息
    EditText pdName_r_install, pdtype_r_install, pd_unit_price_install, pd_cost_install;

    //客户信息
    private EditText customerNm; //客户名称
    private EditText customerAdd;//客户地点
    private EditText customerTel;//手机
    //产品信息
    private TextView pdId_install;//产品信息ID
    private EditText sn_install;//产品信息序列号
    private EditText project_install;//产品信息所属项目
    private RadioGroup subwaytype_install;//产品信息列车车型
    private EditText subwayid_install;//产品信息对应车辆编号
    //备件信息
    private TextView pdId_r_install;//备件信息ID
    private RadioGroup isHaveExtraParts_install;//备件信息是否有额外配件
    private EditText pd_r_num_install;//备件信息数量
    //安装调试内容
    private EditText problemdesc_install;//描述
    private EditText installStartTime;//开始时间
    private EditText installEndTime;//结束时间
    private EditText sumTime;//总工时
    private RadioGroup isHaveLocalProblem;//是否有现场问题
    private EditText localProblemDesc;//现场问题描述
    //结果
    private RadioGroup installResult;//安装调试结果
    private EditText unfinishedDesc; //开口项描述
    private EditText installer;//安装调试配合人
    private EditText reporter;//报告人
    private EditText reportTime;//报告时间
    private EditText reportReceivePart;//报告接收部门
    private Button cancel_install;
    private Button submit_install;
    StringBuffer stringBuilderS;
    StringBuffer stringBuilderE;
    StringBuffer stringBuilderR;
    //保存上传图片的数据源
    private String installId;
    private GridView mGridView_install;
    private ArrayList<String> mPicList_install = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter_install;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.installanddebug);
        //扫一扫初始化
        initProductInfo();
        //点击确认按钮需要提交的文本初始化
        initSubmitInfo();
        //图片初始化
        init();
        //时间选择器
        dateselect01_install=(Button)findViewById(R.id.date_select01_install);
        dateselect02_install=(Button)findViewById(R.id.date_select02_install);
        dateselect03_install=(Button)findViewById(R.id.date_select03_install);
        dateselect01_install.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(InstallAndDebugAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderS=new StringBuffer("");
                        stringBuilderS.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(InstallAndDebugAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderS.append(hourOfDay+":"+minute);
                                installStartTime.setText(stringBuilderS);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }


                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });
        dateselect02_install.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(InstallAndDebugAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderE=new StringBuffer("");
                        stringBuilderE.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(InstallAndDebugAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderE.append(hourOfDay+":"+minute);
                                installEndTime.setText(stringBuilderE);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }


                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });
        dateselect03_install.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(InstallAndDebugAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderR=new StringBuffer("");
                        stringBuilderR.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(InstallAndDebugAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderR.append(hourOfDay+":"+minute);
                                reportTime.setText(stringBuilderR);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }


                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });

        //产品信息扫一扫初始化
        scan_install_01 = (Button) findViewById(R.id.scan_install_01);
        //备品信息扫一扫初始化
        scan_install_02 = (Button) findViewById(R.id.scan_install_02);
        //确认按钮初始化
        submit_install = (Button) findViewById(R.id.confirm_install);
        //取消按钮初始化
        cancel_install = (Button) findViewById(R.id.cancel_install);
        //产品信息扫一扫按下
        scan_install_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InstallAndDebugAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        //备品信息扫一扫按下
        scan_install_02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InstallAndDebugAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN_R);

            }
        });

        //取消按钮按下
        cancel_install.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InstallMainAction.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_install.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(customerNm.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(customerAdd.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"客户地点不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(customerTel.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"手机不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //产品信息
                if(pdId_install.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"产品ID不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(sn_install.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"产品信息的序列号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(project_install.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"产品信息的所属项目不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(subwayid_install.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"产品信息对应车辆编号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //安装调试内容
                if(problemdesc_install.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"安装调试内容的描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(installStartTime.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"安装调试内容的开始时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(installEndTime.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"安装调试内容的结束时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sumTime.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"安装调试内容的总工时不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(localProblemDesc.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"安装调试内容的现场问题描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //结果
                if(unfinishedDesc.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"开口项描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(installer.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"安装调试配合人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reporter.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"报告人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportTime.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"报告时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportReceivePart.getText().toString().isEmpty()){
                    Toast.makeText(InstallAndDebugAction.this,"报告接收部门不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //产品信息列车车型
                RadioButton radioButton1 = (RadioButton) findViewById(subwaytype_install.getCheckedRadioButtonId());
                if(radioButton1==null){
                    Toast.makeText(InstallAndDebugAction.this,"请选择产品信息中的列车车型", Toast.LENGTH_SHORT).show();
                    return;
                }
                //是否有现场问题
                RadioButton radioButton2 = (RadioButton) findViewById(isHaveLocalProblem.getCheckedRadioButtonId());
                if(radioButton2==null){
                    Toast.makeText(InstallAndDebugAction.this,"请选择是否有现场问题", Toast.LENGTH_SHORT).show();
                    return;
                }
                //备件信息是否有额外配件
                RadioButton radioButton3 = (RadioButton) findViewById(isHaveExtraParts_install.getCheckedRadioButtonId());
                if(radioButton3==null){
                    Toast.makeText(InstallAndDebugAction.this,"请选择备件信息的是否有额外配件", Toast.LENGTH_SHORT).show();
                    return;
                }
                //安装调试结果
                RadioButton radioButton4 = (RadioButton) findViewById(installResult.getCheckedRadioButtonId());
                if(radioButton4==null){
                    Toast.makeText(InstallAndDebugAction.this,"请选择安装调试结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseInstallInfo bs = new BaseInstallInfo();
                installId = UUIDUtil.getUUID();
                bs.setInstallId(installId);
                //客户信息
                //客户名称
                bs.setCustomerNm(customerNm.getText().toString());
                //客户地点
                bs.setCustomerAdd(customerAdd.getText().toString());
                //手机
                bs.setCustomerTel(customerTel.getText().toString());
                //产品信息ID
                bs.setPdId_install(pdId_install.getText().toString());
                //产品信息序列号
                bs.setSn_install(sn_install.getText().toString());
                //产品信息所属项目
                bs.setProject_install(project_install.getText().toString());
                //产品信息列车车型
                bs.setSubwaytype_install(radioButton1.getText().toString());
                //产品信息对应车辆编号
                bs.setSubwaytype_install(subwayid_install.getText().toString());
                //备件信息
                bs.setPdId_r_install(pdId_r_install.getText().toString());
                //备件信息是否有额外配件
                bs.setIsHaveExtraParts_install(radioButton3.getText().toString());
                //备件信息数量
                bs.setPd_r_num_install(pd_r_num_install.getText().toString());
                //安装调试内容
                //描述
                bs.setProblemdesc_install(problemdesc_install.getText().toString());
                //开始时间
                bs.setInstallStartTime(installStartTime.getText().toString());
                //结束时间
                bs.setInstallEndTime(installEndTime.getText().toString());
                //总工时
                bs.setSumTime(sumTime.getText().toString());
                //是否有现场问题
                bs.setIsHaveLocalProblem(radioButton2.getText().toString());
                //现场问题描述
                bs.setLocalProblemDesc(localProblemDesc.getText().toString());
                //安装调试结果
                bs.setInstallResult(radioButton4.getText().toString());
                //开口项描述
                bs.setUnfinishedDesc(unfinishedDesc.getText().toString());
                //安装调试配合人
                bs.setInstaller(installer.getText().toString());
                //报告人
                bs.setReporter(reporter.getText().toString());
                //报告时间
                bs.setReportTime(reportTime.getText().toString());
                //报告接收部门
                bs.setReportReceivePart(reportReceivePart.getText().toString());

                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(InstallAndDebugAction.this);
                http.insertInstallAndDebugInfo(bs, getProperties(getApplicationContext()).getProperty("installSubmitURL"));

            }
        });

    }

    //扫一扫数据获取--开始---
    private void initProductInfo() {
        pdName_install = (EditText) findViewById(R.id.pdName_install);//产品名称
        pdtype_install = (EditText) findViewById(R.id.pdtype_install);//产品规格型号
        pdName_r_install = (EditText) findViewById(R.id.pdName_r_install);//备件名称
        pdtype_r_install = (EditText) findViewById(R.id.pdtype_r_install);//备件规格型号
        pd_unit_price_install = (EditText) findViewById(R.id.pd_unit_price_install);//备件单价
        pd_cost_install = (EditText) findViewById(R.id.pd_cost_install);//备件成本
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 产品信息扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //解析的返回值
                String content = data.getStringExtra("codedContent");
                //返回的图片
                //Bitmap bitmap = data.getParcelableExtra("codedBitmap");
                pdId_install.setText(content);
                //数据库的值反显
                new ScanThread().start();
            }
        }

        // 备件信息扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN_R && resultCode == RESULT_OK) {
            if (data != null) {
                //解析的返回值
                String content = data.getStringExtra("codedContent");
                //返回的图片
                pdId_r_install.setText(content);
                //数据库的值反显
                new ScanThread_r().start();
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
            mPicList_install.clear();
            mPicList_install.addAll(toDeletePicList);
            mGridViewAdapter_install.notifyDataSetChanged();
        }

    }

    class ScanThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(InstallAndDebugAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdScanURL") + pdId_install.getText());
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
                if (response != null) {
                    String productCode = null2empty(response.get("productCode").toString());//产品编号
                    String productModel = null2empty(response.get("productModel").toString());//规格型号
                    String productName = null2empty(response.get("productName").toString());//产品名称
                    pdId_install.setText(productCode);
                    pdName_install.setText(productName);
                    pdtype_install.setText(productModel);
                }else{
                    pdId_install.setText("");
                    pdName_install.setText("");
                    pdtype_install.setText("");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    class ScanThread_r extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(InstallAndDebugAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdBackUpScanURL") + pdId_r_install.getText());
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                mHandler_r.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String null2empty(String value) {
        if (value.equals("null")) {
            value = "";
        }
        return value;
    }

    Handler mHandler_r = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String val = data.getString("value");
            JSONObject dataJson = null;

            try {
                dataJson = new JSONObject(val);
                JSONObject response = dataJson.getJSONObject("data");
                String productCode_r = null2empty(response.get("productCode").toString());//产品编号
                String productModel_r = null2empty(response.get("productModel").toString());//规格型号
                String productName_r = null2empty(response.get("productName").toString());//产品名称
                String iinvsalecost = null2empty(response.get("iinvsalecost").toString());//单价
                String iinvncost = null2empty(response.get("iinvncost").toString());//成本
                pdId_r_install.setText(productCode_r);
                pdName_r_install.setText(productName_r);
                pdtype_r_install.setText(productModel_r);
                pd_unit_price_install.setText(iinvsalecost);
                pd_cost_install.setText(iinvncost);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

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
                mPicList_install.add(compressPath);
                mGridViewAdapter_install.notifyDataSetChanged();
            }
        }
    }
    //提交按钮按下获取每个编辑框的数据--开始---
    private void initSubmitInfo() {
        //客户信息
        customerNm = (EditText) findViewById(R.id.customerNm);//客户名称
        customerAdd = (EditText) findViewById(R.id.customerAdd);//客户地点
        customerTel = (EditText) findViewById(R.id.customerTel);//手机
        //产品信息
        pdId_install = (TextView) findViewById(R.id.pdId_install);//产品信息ID
        sn_install = (EditText) findViewById(R.id.sn_install);//产品信息序列号
        project_install = (EditText) findViewById(R.id.project_install);//产品信息所属项目
        subwaytype_install = (RadioGroup) findViewById(R.id.subwaytype_install);//产品信息列车车型
        subwayid_install = (EditText) findViewById(R.id.subwayid_install);//产品信息对应车辆编号
        //备件信息
        pdId_r_install = (EditText) findViewById(R.id.pdId_r_install);//备件信息ID
        isHaveExtraParts_install = (RadioGroup) findViewById(R.id.isHaveExtraParts_install);//备件信息是否有配件更换
        pd_r_num_install = (EditText) findViewById(R.id.pd_r_num_install);//备件信息数量
        //安装调试内容
        problemdesc_install = (EditText) findViewById(R.id.problemdesc_install);//描述
        installStartTime = (EditText) findViewById(R.id.installStartTime);//开始时间
        installEndTime = (EditText) findViewById(R.id.installEndTime); //结束时间
        sumTime = (EditText) findViewById(R.id.sumTime);//总工时
        isHaveLocalProblem = (RadioGroup) findViewById(R.id.isHaveLocalProblem);//是否有现场问题
        localProblemDesc = (EditText) findViewById(R.id.localProblemDesc);//现场问题描述
        //结果
        installResult = (RadioGroup) findViewById(R.id.installResult);//安装调试结果
        unfinishedDesc = (EditText) findViewById(R.id.unfinishedDesc); //开口项描述
        installer = (EditText) findViewById(R.id.installer);//安装调试配合人
        reporter = (EditText) findViewById(R.id.reporter);//报告人
        reportTime = (EditText) findViewById(R.id.reportTime);//报告时间
        reportReceivePart = (EditText) findViewById(R.id.reportReceivePart);//报告接收部门
    }

    //图片初始化
    private void init() {
        mGridView_install = (GridView) findViewById(R.id.gridView_install);
        initGridView();
    }

    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter_install = new GridViewAdapter(InstallAndDebugAction.this, mPicList_install);
        mGridView_install.setAdapter(mGridViewAdapter_install);
        //设置GridView的条目的点击事件
        mGridView_install.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList_install.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList_install.size());
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
        PictureSelectorConfig.initMultiConfig(InstallAndDebugAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(InstallAndDebugAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList_install);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }



    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList_install.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList_install.get(i)));
                }
                UploadHelper helper = new UploadHelper(InstallAndDebugAction.this);
                helper.post(InstallAndDebugAction.this, getProperties(getApplicationContext()).getProperty("uploadpicURL"), files, "1", installId);
            }
        }).start();
    }
    }

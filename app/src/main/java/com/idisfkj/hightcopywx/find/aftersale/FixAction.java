package com.idisfkj.hightcopywx.find.aftersale;

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
import android.widget.ArrayAdapter;
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

public class FixAction extends Activity {

    private ArrayAdapter adapter;
    private static final int REQUEST_CODE_SCAN = 0x0000;//产品扫一扫
    private static final int REQUEST_CODE_SCAN_R = 0x0001;//备件扫一扫
    Button scan2, scan3, submit_2;//产品扫一扫,备件扫一扫,提交
    EditText pdName, pdtype;
    EditText pdName_r, pdtype_r, pd_unit_price, pd_cost;
//    private static String scanURLPd = "http://192.168.50.200:8080/imes/afterSale/productScan.do?productCode=";
//    private static String scanURLPd_B = "http://192.168.50.200:8080/imes/afterSale/productScan2.do?productCode=";
//    private static String uploadpicURL = "http://192.168.50.200:8080/imes/afterSale/multiFileUpload.do";
//    private static String submitURL = "http://192.168.50.200:8080/imes/afterSale/serviceSave.do";
    //客户信息
    private EditText customerNm; //客户名称
    private EditText contact;//客户联系人
    private EditText customerTel;//手机
    //产品信息
    private TextView pdId;//产品信息ID
    private EditText sn;//产品信息序列号
    private EditText project;//产品信息所属项目
    private RadioGroup subwaytype;//产品信息列车车型
    private EditText subwayid;//产品信息对应车辆编号
    private EditText subwayrunhistory;//产品信息列车走行历史
    //备件信息
    private TextView pdId_r;//备件信息ID
    private RadioGroup isReplaceParts;//备件信息是否有配件更换
    private EditText pd_r_num;//备件信息数量
    //处理信息
    private EditText problemdesc;//问题描述
    private EditText fixDesc;//处理描述
    private RadioGroup problemType; //问题判断
    private TextView fixStartTime;//修复开始时间
    private TextView fixEndTime;//修复结束时间
    private EditText diagnoseTime;//诊断时长
    private EditText fixTime;//修理时长
    private EditText testTime;//试验时长
    private EditText sumTime;//总工时
    private EditText sumTimeCost;//工时成本
    //结果
    private EditText fixResult;//处理结果
    private EditText qualityDefectCost; //质量缺陷成本
    private EditText troubleShooting;//故障处理人
    private EditText reporter;//报告人
    private TextView reportTime;//报告时间
    private EditText reportReceivePart;//报告接收部门
    private RadioGroup correctActionIsNeeded;//是否需要纠正措施
    private Button cancel;
    //保存上传图片的数据源
    private GridView mGridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter;
    private String aftersaleid;
    private String AFTERSALEPROBLEMDESC = "0";
    private Button dateselect01,dateselect02,dateselect03;
    StringBuffer stringBuilderS;
    StringBuffer stringBuilderE;
    StringBuffer stringBuilderR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aftersalerecovery);
        Intent intent=getIntent();
        String toast=intent.getStringExtra("toast");
        if(toast!=null&&!toast.isEmpty()) {
            Toast.makeText(FixAction.this, toast, Toast.LENGTH_SHORT).show();
        }
        scan2 = (Button) findViewById(R.id.scan2);
        scan3 = (Button) findViewById(R.id.scan3);
        cancel = (Button) findViewById(R.id.cancel);
        submit_2 = (Button) findViewById(R.id.submit_2);
        pdId = (TextView) findViewById(R.id.pdId);
        pdId_r = (TextView) findViewById(R.id.pdId_r);
        //时间选择器
        dateselect01=(Button)findViewById(R.id.date_select01);
        dateselect02=(Button)findViewById(R.id.date_select02);
        dateselect03=(Button)findViewById(R.id.date_select03);
        dateselect01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c= Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(FixAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderS=new StringBuffer("");
                        stringBuilderS.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time= Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(FixAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderS.append(hourOfDay+":"+minute);
                                fixStartTime.setText(stringBuilderS);
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
        dateselect02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c= Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(FixAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderE=new StringBuffer("");
                        stringBuilderE.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time= Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(FixAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderE.append(hourOfDay+":"+minute);
                                fixEndTime.setText(stringBuilderE);
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

        dateselect03.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c= Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(FixAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderR=new StringBuffer("");
                        stringBuilderR.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time= Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(FixAction.this, new TimePickerDialog.OnTimeSetListener() {

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

        //扫一扫初始化
        initProductInfo();
        //提交按钮初始化
        initSubmitInfo();

        //图片初始化
        init();
        //产品信息扫一扫按钮按下
        scan2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FixAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

        //备件信息扫一扫按钮按下
        scan3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FixAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN_R);
            }
        });

        //取消按钮按下
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AftersaleMainAction.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {



                if(customerNm.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(contact.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"客户联系人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(customerTel.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"手机不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //产品信息
                if(pdId.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"产品ID不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(sn.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"产品信息的序列号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(project.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"产品信息的所属项目不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(subwayid.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"产品信息对应车辆编号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(subwayrunhistory.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"产品信息的列车走行历史不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //处理信息
                if(problemdesc.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的问题描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixDesc.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的处理描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixStartTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的修复开始时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixEndTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的修复结束时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(diagnoseTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的诊断时长不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的修理时长不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(testTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的试验时长不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sumTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理信息的总工时不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //结果
                if(fixResult.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"处理结果不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(qualityDefectCost.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"质量缺陷成本不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(troubleShooting.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"故障处理人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reporter.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"报告人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportTime.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"报告时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportReceivePart.getText().toString().isEmpty()){
                    Toast.makeText(FixAction.this,"报告接收部门不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //产品信息列车车型
                RadioButton radioButton1 = (RadioButton) findViewById(subwaytype.getCheckedRadioButtonId());
               if(radioButton1==null){
                   Toast.makeText(FixAction.this,"请选择产品信息中的列车车型", Toast.LENGTH_SHORT).show();
                   return;
                }
                //问题判断
                RadioButton radioButton3 = (RadioButton) findViewById(problemType.getCheckedRadioButtonId());
                if(radioButton3==null){
                    Toast.makeText(FixAction.this,"请选择处理信息中问题判断", Toast.LENGTH_SHORT).show();
                    return;
                }
                //备件信息是否有配件更换
                RadioButton radioButton2 = (RadioButton) findViewById(isReplaceParts.getCheckedRadioButtonId());
                if(radioButton2==null){
                    Toast.makeText(FixAction.this,"请选择备件信息是否有配件更换", Toast.LENGTH_SHORT).show();
                    return;
                }

                //是否需要纠正措施
                RadioButton radioButton4 = (RadioButton) findViewById(correctActionIsNeeded.getCheckedRadioButtonId());
                if(radioButton4==null){
                    Toast.makeText(FixAction.this,"请选择结果中的是否需要纠正措施", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseFixInfo bs = new BaseFixInfo();
                aftersaleid = UUIDUtil.getUUID();
                bs.setAftersaleId(aftersaleid);
                //客户信息
                //客户名称
                bs.setCustomerNm(customerNm.getText().toString());
                //客户联系人
                bs.setContact(contact.getText().toString());
                //手机
                bs.setCustomerTel(customerTel.getText().toString());
                //产品信息ID
                bs.setPdId(pdId.getText().toString());
                //产品信息序列号
                bs.setSn(sn.getText().toString());
                //产品信息所属项目
                bs.setProject(project.getText().toString());
                //产品信息列车车型
                bs.setSubwaytype(radioButton1.getText().toString());
                //产品信息对应车辆编号
                bs.setSubwayid(subwayid.getText().toString());
                //产品信息列车走行历史
                bs.setSubwayrunhistory(subwayrunhistory.getText().toString());
                //备件信息
                bs.setPdId_r(pdId_r.getText().toString());
                //备件信息是否有配件更换
                bs.setIsReplaceParts(radioButton2.getText().toString());
                //备件信息数量
                bs.setPd_r_num(pd_r_num.getText().toString());
                //处理信息
                //问题描述
                bs.setProblemDesc(problemdesc.getText().toString());
                //处理描述
                bs.setFixDesc(fixDesc.getText().toString());
                //问题判断
                bs.setProblemType(radioButton3.getText().toString());
                //修复开始时间
                bs.setFixStartTime(fixStartTime.getText().toString());
                //修复结束时间
                bs.setFixEndTime(fixEndTime.getText().toString());
                //诊断时长
                bs.setDiagnoseTime(diagnoseTime.getText().toString());
                //修理时长
                bs.setFixTime(fixTime.getText().toString());
                //试验时长
                bs.setTestTime(testTime.getText().toString());
                //总工时
                bs.setSumTime(sumTime.getText().toString());
                //工时成本
                bs.setSumTimeCost(sumTimeCost.getText().toString());
                //结果
                //处理结果
                bs.setFixResult(fixResult.getText().toString());
                //质量缺陷成本
                bs.setQualityDefectCost(qualityDefectCost.getText().toString());
                //故障处理人
                bs.setTroubleShooting(troubleShooting.getText().toString());
                //报告人
                bs.setReporter(reporter.getText().toString());
                //报告时间
                bs.setReportTime(reportTime.getText().toString());
                //报告接收部门
                bs.setReportReceivePart(reportReceivePart.getText().toString());
                //是否需要纠正措施
                bs.setCorrectActionIsNeeded(radioButton4.getText().toString());

                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(FixAction.this);
                http.insertAftersaleInfo(bs, getProperties(getApplicationContext()).getProperty("afterSaleSubmitURL"));
//                Toast.makeText(FixAction.this, "已提交", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(FixAction.this, AftersaleMainAction.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    //扫一扫数据获取--开始---
    private void initProductInfo() {
        pdName = (EditText) findViewById(R.id.pdName);//产品名称
        pdtype = (EditText) findViewById(R.id.pdtype);//产品规格型号
        pdName_r = (EditText) findViewById(R.id.pdName_r);//备件名称
        pdtype_r = (EditText) findViewById(R.id.pdtype_r);//备件规格型号
        pd_unit_price = (EditText) findViewById(R.id.pd_unit_price);//备件单价
        pd_cost = (EditText) findViewById(R.id.pd_cost);//备件成本
    }

    //提交按钮按下获取每个编辑框的数据--开始---
    private void initSubmitInfo() {
        //客户信息
        customerNm = (EditText) findViewById(R.id.customerNm);//客户名称
        contact = (EditText) findViewById(R.id.contact);//客户联系人
        customerTel = (EditText) findViewById(R.id.customerTel);//手机
        //产品信息
        pdId = (TextView) findViewById(R.id.pdId);//产品信息ID
        sn = (EditText) findViewById(R.id.sn);//产品信息序列号
        project = (EditText) findViewById(R.id.project);//产品信息所属项目
        subwaytype = (RadioGroup) findViewById(R.id.subwaytype);//产品信息列车车型
        subwayid = (EditText) findViewById(R.id.subwayid);//产品信息对应车辆编号
        subwayrunhistory = (EditText) findViewById(R.id.subwayrunhistory);//产品信息列车走行历史
        //备件信息
        pdId_r = (EditText) findViewById(R.id.pdId_r);//备件信息ID
        isReplaceParts = (RadioGroup) findViewById(R.id.isReplaceParts);//备件信息是否有配件更换
        pd_r_num = (EditText) findViewById(R.id.pd_r_num);//备件信息数量
        //处理信息
        problemdesc = (EditText) findViewById(R.id.problemdesc);//问题描述
        fixDesc = (EditText) findViewById(R.id.fixDesc);//处理描述
        problemType = (RadioGroup) findViewById(R.id.problemType); //问题判断
        fixStartTime = (TextView) findViewById(R.id.fixStartTime);//修复开始时间
        fixEndTime = (TextView) findViewById(R.id.fixEndTime);//修复结束时间
        diagnoseTime = (EditText) findViewById(R.id.diagnoseTime);//诊断时长
        fixTime = (EditText) findViewById(R.id.fixTime);//修理时长
        testTime = (EditText) findViewById(R.id.testTime);//试验时长
        sumTime = (EditText) findViewById(R.id.sumTime);//总工时
        sumTimeCost = (EditText) findViewById(R.id.sumTimeCost);//工时成本
        //结果
        fixResult = (EditText) findViewById(R.id.fixResult);//处理结果
        qualityDefectCost = (EditText) findViewById(R.id.qualityDefectCost); //质量缺陷成本
        troubleShooting = (EditText) findViewById(R.id.troubleShooting);//故障处理人
        reporter = (EditText) findViewById(R.id.reporter);//报告人
        reportTime = (TextView) findViewById(R.id.reportTime);//报告时间
        reportReceivePart = (EditText) findViewById(R.id.reportReceivePart);//报告接收部门
        correctActionIsNeeded = (RadioGroup) findViewById(R.id.correctActionIsNeeded);//是否需要纠正措施
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
                pdId.setText(content);
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
                // Bitmap bitmap = data.getParcelableExtra("codedBitmap");
                pdId_r.setText(content);
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
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAdapter.notifyDataSetChanged();
        }

    }

    class ScanThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(FixAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdScanURL") + pdId.getText());
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
                    pdId.setText(productCode);
                    pdName.setText(productName);
                    pdtype.setText(productModel);
                }else{
                    pdId.setText("");
                    pdName.setText("");
                    pdtype.setText("");
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
                OKHttpUtil http = new OKHttpUtil(FixAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdBackUpScanURL") + pdId_r.getText());
                Bundle data = new Bundle();
                data.putString("value", str);
                msg.setData(data);
                mHandler_r.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
                pdId_r.setText(productCode_r);
                pdName_r.setText(productName_r);
                pdtype_r.setText(productModel_r);
                pd_unit_price.setText(iinvsalecost);
                pd_cost.setText(iinvncost);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public String null2empty(String value) {
        if (value.equals("null")) {
            value = "";
        }
        return value;
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
        mGridViewAdapter = new GridViewAdapter(FixAction.this, mPicList);
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
        PictureSelectorConfig.initMultiConfig(FixAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(FixAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
        //finish();
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
                UploadHelper helper = new UploadHelper(FixAction.this);
                helper.post(FixAction.this, getProperties(getApplicationContext()).getProperty("afterSaleUploadpicURL"), files, AFTERSALEPROBLEMDESC, aftersaleid);
            }
        }).start();
    }
}

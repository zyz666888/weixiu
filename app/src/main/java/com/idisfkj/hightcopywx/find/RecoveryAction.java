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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.entity.BaseRecoveryInfo;
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

public class RecoveryAction extends Activity  {

    private static final int REQUEST_CODE_SCAN = 0x0000;//产品扫一扫
    private static final int REQUEST_CODE_SCAN_R = 0x0001;//备件扫一扫
    Button scan_recovery_01;
    Button scan_recovery_02;
    private Button  dateselect01_recovery,dateselect02_recovery,dateselect03_recovery,dateselect04_recovery,dateselect05_recovery;
    //产品信息
    EditText pdName_recovery, pdtype_recovery;
    //备件信息
    EditText pdName_r_recovery, pdtype_r_recovery, pd_unit_price_recovery, pd_cost_recovery;

    //客户信息
    private EditText customerNm_recovery; //客户名称
    private RadioGroup infoSource_recovery;//信息来源
    private EditText problemOccuTime_recovery;//故障发生时间
    private EditText problemReportTime_recovery;//故障报告时间
    private EditText problemOccuAd_recovery;//故障发生地点
    private EditText problemReporter_recovery;//故障报告人
    //产品信息
    private EditText pdId_recovery;//产品信息ID
    private EditText sn_recovery;//产品信息序列号
    private EditText project_recovery;//产品信息所属项目
    private RadioGroup subwaytype_recovery;//产品信息列车车型
    private EditText subwayid_recovery;//产品信息对应车辆编号
    private EditText subwayrunhistory_recovery;//产品信息列车走行历史
    //处理信息
    private EditText problemdesc_recovery;//问题描述
    private EditText fixdesc_recovery;//处理描述
    private RadioGroup problemJudg_recovery;//问题判断
    private EditText recoveryStartTime;//修复开始时间
    private EditText recoveryEndTime;//修复结束时间
    private EditText diagnoseTime_recovery;//诊断时长
    private EditText fixTime_recovery;//修理时长
    private EditText testTime_recovery;//试验时长
    private EditText sumTime_recovery;//总工时
    private EditText sumTimeCost_recovery;//工时成本
    //备件信息
    private EditText pdId_r_recovery;//备件信息ID
    private RadioGroup isReplaceParts_recovery;//备件信息是否有配件更换
    private EditText pd_r_num_recovery;//备件信息数量

    //结果
    private EditText fixResult_recovery;//处理结果
    private EditText qualityDefectCost_recovery;//质量缺陷成本
    private EditText troubleShooting_recovery;//故障处理人
    private EditText reporter_recovery;//报告人
    private EditText reportTime_recovery;//报告时间
    private EditText reportReceivePart_recovery;//报告接收部门
    private RadioGroup correctActionIsNeeded_recovery;//是否需要纠正措施
    private Button cancel_recovery;
    private Button submit_recovery;
    StringBuffer stringBuilder1;
    StringBuffer stringBuilder2;
    StringBuffer stringBuilder3;
    StringBuffer stringBuilder4;
    StringBuffer stringBuilder5;
    //保存上传图片的数据源
    private String recoveryId;
    private GridView mGridView_recovery;
    private ArrayList<String> mPicList_recovery = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter_recovery;
    private Spinner spinner2;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery);
        spinner2= (Spinner) findViewById(R.id.spinner2);
        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.zhongtie, android.R.layout.simple_spinner_item);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter2 添加到spinner中
        spinner2.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner2.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //设置默认值
        spinner2.setVisibility(View.VISIBLE);
        //扫一扫初始化
        initProductInfo();
        //点击确认按钮需要提交的文本初始化
        initSubmitInfo();
        //图片初始化
        init();
        //时间选择器
        dateselect01_recovery=(Button)findViewById(R.id.date_select01_recovery);
        dateselect02_recovery=(Button)findViewById(R.id.date_select02_recovery);
        dateselect03_recovery=(Button)findViewById(R.id.date_select03_recovery);
        dateselect04_recovery=(Button)findViewById(R.id.date_select04_recovery);
        dateselect05_recovery=(Button)findViewById(R.id.date_select05_recovery);
        dateselect01_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(RecoveryAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilder1=new StringBuffer("");
                        stringBuilder1.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(RecoveryAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilder1.append(hourOfDay+":"+minute);
                                problemOccuTime_recovery.setText(stringBuilder1);
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
        dateselect02_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(RecoveryAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilder2=new StringBuffer("");
                        stringBuilder2.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(RecoveryAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilder2.append(hourOfDay+":"+minute);
                                problemReportTime_recovery.setText(stringBuilder2);
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
        dateselect03_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(RecoveryAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilder3=new StringBuffer("");
                        stringBuilder3.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(RecoveryAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilder3.append(hourOfDay+":"+minute);
                                recoveryStartTime.setText(stringBuilder3);
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

        dateselect04_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(RecoveryAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilder4=new StringBuffer("");
                        stringBuilder4.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(RecoveryAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilder4.append(hourOfDay+":"+minute);
                                recoveryEndTime.setText(stringBuilder4);
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

        dateselect05_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(RecoveryAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilder5=new StringBuffer("");
                        stringBuilder5.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(RecoveryAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilder5.append(hourOfDay+":"+minute);
                                reportTime_recovery.setText(stringBuilder5);
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
        scan_recovery_01 = (Button) findViewById(R.id.scan_recovery_01);
        //备品信息扫一扫初始化
        scan_recovery_02 = (Button) findViewById(R.id.scan_recovery_02);
        //确认按钮初始化
        submit_recovery = (Button) findViewById(R.id.confirm_recovery);
        //取消按钮初始化
        cancel_recovery = (Button) findViewById(R.id.cancel_recovery);
        //产品信息扫一扫按下
        scan_recovery_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(RecoveryAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        //备品信息扫一扫按下
        scan_recovery_02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(RecoveryAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN_R);
            }
        });

        //取消按钮按下
        cancel_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BreakdownMainAction.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(customerNm_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(problemOccuTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"故障发生时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(problemReportTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"故障报告时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(problemOccuAd_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"故障发生地点不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(problemReporter_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"故障报告人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pdId_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"产品信息的产品信息ID不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(sn_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"产品信息的序列号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(project_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"产品信息的所属项目不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(subwayid_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"产品信息的对应车辆编号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(subwayrunhistory_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"产品信息的列车走行历史不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(problemdesc_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"处理信息的问题描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixdesc_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"处理描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(recoveryStartTime.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"修复开始时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(recoveryEndTime.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"修复结束时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(diagnoseTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"诊断时长不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"修理时长不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(testTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"试验时长不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sumTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"总工时不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sumTimeCost_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"工时成本不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pdId_r_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"备件信息ID不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pd_r_num_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"备件信息数量不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fixResult_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"处理结果不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(qualityDefectCost_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"质量缺陷成本不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(troubleShooting_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"故障处理人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reporter_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"报告人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportTime_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"报告时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportReceivePart_recovery.getText().toString().isEmpty()){
                    Toast.makeText(RecoveryAction.this,"报告接收部门不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //产品信息列车车型
                RadioButton radioButton1 = (RadioButton) findViewById(infoSource_recovery.getCheckedRadioButtonId());
                if(radioButton1==null){
                    Toast.makeText(RecoveryAction.this,"请选择客户信息中的信息来源", Toast.LENGTH_SHORT).show();
                    return;
                }

                //产品信息列车车型
                RadioButton radioButton2 = (RadioButton) findViewById(subwaytype_recovery.getCheckedRadioButtonId());
                if(radioButton2==null){
                    Toast.makeText(RecoveryAction.this,"请选择产品信息中的列车车型", Toast.LENGTH_SHORT).show();
                    return;
                }
                //问题判断
                RadioButton radioButton3 = (RadioButton) findViewById(problemJudg_recovery.getCheckedRadioButtonId());
                if(radioButton3==null){
                    Toast.makeText(RecoveryAction.this,"请选择处理信息的问题判断", Toast.LENGTH_SHORT).show();
                    return;
                }
                //备件信息是否有额外配件
                RadioButton radioButton4 = (RadioButton) findViewById(isReplaceParts_recovery.getCheckedRadioButtonId());
                if(radioButton4==null){
                    Toast.makeText(RecoveryAction.this,"请选择备件信息的是否有配件更换", Toast.LENGTH_SHORT).show();
                    return;
                }
                //安装调试结果
                RadioButton radioButton5 = (RadioButton) findViewById(correctActionIsNeeded_recovery.getCheckedRadioButtonId());
                if(radioButton5==null){
                    Toast.makeText(RecoveryAction.this,"请选择结果中是否需要纠正措施", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseRecoveryInfo bs = new BaseRecoveryInfo();
                recoveryId = UUIDUtil.getUUID();
                bs.setRecoveryId(recoveryId);
                //客户信息
                //客户名称
                bs.setCustomerNm_recovery(customerNm_recovery.getText().toString());
                //信息来源
                bs.setInfoSource_recovery(radioButton1.getText().toString());
                //故障发生时间
                bs.setProblemOccuTime_recovery(problemOccuTime_recovery.getText().toString());
                //故障报告时间
                bs.setProblemReportTime_recovery(problemReportTime_recovery.getText().toString());
                //故障发生地点
                bs.setProblemOccuAd_recovery(problemOccuAd_recovery.getText().toString());
                //故障报告人
                bs.setProblemReporter_recovery(problemReporter_recovery.getText().toString());
                //产品信息ID
                bs.setPdId_recovery(pdId_recovery.getText().toString());
                //产品信息序列号
                bs.setSn_recovery(sn_recovery.getText().toString());
                //产品信息所属项目
                bs.setProject_recovery(project_recovery.getText().toString());
                //产品信息列车车型
                bs.setSubwaytype_recovery(radioButton2.getText().toString());
                //产品信息对应车辆编号
                bs.setSubwayid_recovery(subwayid_recovery.getText().toString());
                //产品信息列车走行历史
                bs.setSubwayrunhistory_recovery(subwayrunhistory_recovery.getText().toString());
                //处理信息
                //问题描述
                bs.setProblemdesc_recovery(problemdesc_recovery.getText().toString());
                //处理描述
                bs.setFixdesc_recovery(fixdesc_recovery.getText().toString());
                //问题判断
                bs.setProblemJudg_recovery(radioButton3.getText().toString());
                //修复开始时间
                bs.setRecoveryStartTime(recoveryStartTime.getText().toString());
                //修复结束时间
                bs.setRecoveryEndTime(recoveryEndTime.getText().toString());
                //诊断时长
                bs.setDiagnoseTime_recovery(diagnoseTime_recovery.getText().toString());
                //修理时长
                bs.setFixTime_recovery(fixTime_recovery.getText().toString());
                //试验时长
                bs.setTestTime_recovery(testTime_recovery.getText().toString());
                //总工时
                bs.setSumTime_recovery(sumTime_recovery.getText().toString());
                //工时成本
                bs.setSumTimeCost_recovery(sumTimeCost_recovery.getText().toString());
                //备件信息
                //备件信息ID
                bs.setPdId_recovery(pdId_r_recovery.getText().toString());
                //备件信息是否有配件更换
                bs.setIsReplaceParts_recovery(radioButton4.getText().toString());
                //备件信息数量
                bs.setPd_r_num_recovery(pd_r_num_recovery.getText().toString());
                //结果
                //处理结果
                bs.setFixResult_recovery(fixResult_recovery.getText().toString());
                //质量缺陷成本
                bs.setQualityDefectCost_recovery(qualityDefectCost_recovery.getText().toString());
                //故障处理人
                bs.setTroubleShooting_recovery(troubleShooting_recovery.getText().toString());
                //报告人
                bs.setReporter_recovery(reporter_recovery.getText().toString());
                //报告时间
                bs.setReportTime_recovery(reportTime_recovery.getText().toString());
                //报告接收部门
                bs.setReportReceivePart_recovery(reportReceivePart_recovery.getText().toString());
                //是否需要纠正措施
                bs.setCorrectActionIsNeeded_recovery(radioButton5.getText().toString());

                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(RecoveryAction.this);
                http.insertRecoveryInfo(bs, getProperties(getApplicationContext()).getProperty("recoverySubmitURL"));

            }
        });

    }

    //扫一扫数据获取--开始---
    private void initProductInfo() {
        pdName_recovery = (EditText) findViewById(R.id.pdName_recovery);//产品名称
        pdtype_recovery = (EditText) findViewById(R.id.pdtype_recovery);//产品规格型号
        pdName_r_recovery = (EditText) findViewById(R.id.pdName_r_recovery);//备件名称
        pdtype_r_recovery = (EditText) findViewById(R.id.pdtype_r_recovery);//备件规格型号
        pd_unit_price_recovery = (EditText) findViewById(R.id.pd_unit_price_recovery);//备件单价
        pd_cost_recovery = (EditText) findViewById(R.id.pd_cost_recovery);//备件成本
    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
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
                pdId_recovery.setText(content);
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
                pdId_r_recovery.setText(content);
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
            mPicList_recovery.clear();
            mPicList_recovery.addAll(toDeletePicList);
            mGridViewAdapter_recovery.notifyDataSetChanged();
        }

    }

    class ScanThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(RecoveryAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdScanURL") + pdId_recovery.getText());
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
                    pdId_recovery.setText(productCode);
                    pdName_recovery.setText(productName);
                    pdtype_recovery.setText(productModel);
                }else{
                    pdId_recovery.setText("");
                    pdName_recovery.setText("");
                    pdtype_recovery.setText("");
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
                OKHttpUtil http = new OKHttpUtil(RecoveryAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdBackUpScanURL") + pdId_r_recovery.getText());
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
                pdId_r_recovery.setText(productCode_r);
                pdName_r_recovery.setText(productName_r);
                pdtype_r_recovery.setText(productModel_r);
                pd_unit_price_recovery.setText(iinvsalecost);
                pd_cost_recovery.setText(iinvncost);

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
                mPicList_recovery.add(compressPath);
                mGridViewAdapter_recovery.notifyDataSetChanged();
            }
        }
    }
    //提交按钮按下获取每个编辑框的数据--开始---
    private void initSubmitInfo() {
        //客户信息
        customerNm_recovery = (EditText) findViewById(R.id.customerNm_recovery);//客户名称
        infoSource_recovery = (RadioGroup) findViewById(R.id.infoSource_recovery);//信息来源
        problemOccuTime_recovery = (EditText) findViewById(R.id.problemOccuTime_recovery);//故障发生时间
        //产品信息
        problemReportTime_recovery = (EditText) findViewById(R.id.problemReportTime_recovery);//故障报告时间
        problemOccuAd_recovery = (EditText) findViewById(R.id.problemOccuAd_recovery);//故障发生地点
        problemReporter_recovery = (EditText) findViewById(R.id.problemReporter_recovery);//故障报告人
        pdId_recovery = (EditText) findViewById(R.id.pdId_recovery);//产品信息ID
        sn_recovery = (EditText) findViewById(R.id.sn_recovery);//产品信息序列号
        project_recovery = (EditText) findViewById(R.id.project_recovery);//产品信息所属项目
        subwaytype_recovery = (RadioGroup) findViewById(R.id.subwaytype_recovery);//产品信息列车车型
        subwayid_recovery = (EditText) findViewById(R.id.subwayid_recovery);//产品信息对应车辆编号
        subwayrunhistory_recovery = (EditText) findViewById(R.id.subwayrunhistory_recovery);//产品信息列车走行历史
        problemdesc_recovery = (EditText) findViewById(R.id.problemdesc_recovery);//问题描述
        fixdesc_recovery = (EditText) findViewById(R.id.fixdesc_recovery); //处理描述
        problemJudg_recovery = (RadioGroup) findViewById(R.id.problemJudg_recovery);//问题判断
        recoveryStartTime = (EditText) findViewById(R.id.recoveryStartTime);//修复开始时间
        recoveryEndTime = (EditText) findViewById(R.id.recoveryEndTime);//修复结束时间
        diagnoseTime_recovery = (EditText) findViewById(R.id.diagnoseTime_recovery);//诊断时长
        fixTime_recovery = (EditText) findViewById(R.id.fixTime_recovery); //修理时长
        testTime_recovery = (EditText) findViewById(R.id.testTime_recovery);//试验时长
        sumTime_recovery = (EditText) findViewById(R.id.sumTime_recovery);//总工时
        sumTimeCost_recovery = (EditText) findViewById(R.id.sumTimeCost_recovery);//工时成本
        pdId_r_recovery = (EditText) findViewById(R.id.pdId_r_recovery);//备件信息ID

        isReplaceParts_recovery = (RadioGroup) findViewById(R.id.isReplaceParts_recovery);//备件信息是否有配件更换
        pd_r_num_recovery = (EditText) findViewById(R.id.pd_r_num_recovery);//备件信息数量
        fixResult_recovery = (EditText) findViewById(R.id.fixResult_recovery);//处理结果
        qualityDefectCost_recovery = (EditText) findViewById(R.id.qualityDefectCost_recovery);//质量缺陷成本
        troubleShooting_recovery = (EditText) findViewById(R.id.troubleShooting_recovery);//故障处理人
        reporter_recovery = (EditText) findViewById(R.id.reporter_recovery);//报告人
        reportTime_recovery = (EditText) findViewById(R.id.reportTime_recovery);//报告时间
        reportReceivePart_recovery = (EditText) findViewById(R.id.reportReceivePart_recovery);//报告接收部门
        correctActionIsNeeded_recovery = (RadioGroup) findViewById(R.id.correctActionIsNeeded_recovery);//是否需要纠正措施
    }

    //图片初始化
    private void init() {
        mGridView_recovery = (GridView) findViewById(R.id.mGridView_recovery);
        initGridView();
    }

    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter_recovery = new GridViewAdapter(RecoveryAction.this, mPicList_recovery);
        mGridView_recovery.setAdapter(mGridViewAdapter_recovery);
        //设置GridView的条目的点击事件
        mGridView_recovery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList_recovery.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList_recovery.size());
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
        PictureSelectorConfig.initMultiConfig(RecoveryAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(RecoveryAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList_recovery);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }

    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList_recovery.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList_recovery.get(i)));
                }
                UploadHelper helper = new UploadHelper(RecoveryAction.this);
                helper.post(RecoveryAction.this, getProperties(getApplicationContext()).getProperty("uploadpicURL"), files, "1", recoveryId);
            }
        }).start();
    }
}

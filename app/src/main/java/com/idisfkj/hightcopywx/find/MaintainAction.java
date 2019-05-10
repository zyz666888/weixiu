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
import com.idisfkj.hightcopywx.entity.BaseMaintainInfo;
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

public class MaintainAction extends Activity  {

    private static final int REQUEST_CODE_SCAN = 0x0000;//产品扫一扫
    private static final int REQUEST_CODE_SCAN_R = 0x0001;//备件扫一扫
    Button scan_maintain_01;
    Button scan_maintain_02;
    //产品信息
    EditText pdName_maintain, pdtype_maintain;
    //备件信息
    EditText pdName_r_maintain, pdtype_r_maintain, pd_unit_price_maintain, pd_cost_maintain;
    //客户信息
    private EditText customerNm_maintain; //客户名称
    private EditText contact_maintain;//客户联系人
    private EditText customerTel_maintain;//手机
    //产品信息
    private TextView pdId_maintain;//产品信息ID
    private EditText sn_maintain;//产品信息序列号
    private EditText project_maintain;//产品信息所属项目
    private RadioGroup subwaytype_maintain;//产品信息列车车型
    private EditText subwayid_maintain;//产品信息对应车辆编号
    private String subwayrunhistory_maintain;//产品信息列车走行历史
    //备件信息
    private TextView pdId_r_maintain;//备件信息ID
    private RadioGroup isHaveReplaceParts_maintain;//配件更换
    private EditText pd_r_num_maintain;//备件信息数量
    //检修内容
    private RadioGroup maintainType;//检修保养类型
    private EditText maintainDesc;//描述
    private EditText maintainStartTime;//开始时间
    private EditText maintainEndTime;//结束时间
    private EditText sumTime_maintain;//总工时
    //结果
    private RadioGroup maintainResult;//检修保养结果
    private EditText hiddenDangerDesc_maintain; //隐患描述
    private EditText maintainer;//检修保养人
    private EditText reporter_maintain;//报告人
    private EditText reportTime_maintain;//报告时间
    private EditText reportReceivePart_maintain;//报告接收部门
    private RadioGroup correctActionIsNeeded_maintain;//是否需要纠正措施
    private Button cancel_maintain;
    private Button submit_maintain;
    //保存上传图片的数据源
    private String maintainId;
    private GridView mGridView_maintain;
    private ArrayList<String> mPicList_maintain = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter_maintain;
    private Button  dateselect01_maintain,dateselect02_maintain,dateselect03_maintain;
    StringBuffer stringBuilderS;
    StringBuffer stringBuilderE;
    StringBuffer stringBuilderR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain);
        //扫一扫初始化
        initProductInfo();
        //点击确认按钮需要提交的文本初始化
        initSubmitInfo();
        //图片初始化
        init();
        //时间选择器
        dateselect01_maintain=(Button)findViewById(R.id.date_select01_maintain);
        dateselect02_maintain=(Button)findViewById(R.id.date_select02_maintain);
        dateselect03_maintain=(Button)findViewById(R.id.date_select03_maintain);
        dateselect01_maintain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(MaintainAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderS=new StringBuffer("");
                        stringBuilderS.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(MaintainAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderS.append(hourOfDay+":"+minute);
                                maintainStartTime.setText(stringBuilderS);
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
        dateselect02_maintain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(MaintainAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderE=new StringBuffer("");
                        stringBuilderE.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(MaintainAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderE.append(hourOfDay+":"+minute);
                                maintainEndTime.setText(stringBuilderE);
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

        dateselect03_maintain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(MaintainAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderR=new StringBuffer("");
                        stringBuilderR.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(MaintainAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderR.append(hourOfDay+":"+minute);
                                reportTime_maintain.setText(stringBuilderR);
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
        scan_maintain_01 = (Button) findViewById(R.id.scan_maintain_01);
        //备品信息扫一扫初始化
        scan_maintain_02 = (Button) findViewById(R.id.scan_maintain_02);
        //确认按钮初始化
        submit_maintain = (Button) findViewById(R.id.confirm_maintain);
        //取消按钮初始化
        cancel_maintain = (Button) findViewById(R.id.cancel_maintain);
        //产品信息扫一扫按下
        scan_maintain_01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaintainAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        //备品信息扫一扫按下
        scan_maintain_02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MaintainAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN_R);
            }
        });

        //取消按钮按下
        cancel_maintain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckMainAction.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_maintain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(customerNm_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(contact_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"客户联系人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(customerTel_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"手机不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //产品信息
                if(pdId_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"产品ID不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(sn_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"产品信息的序列号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(project_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"产品信息的所属项目不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(subwayid_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"产品信息对应车辆编号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //检修内容
                if(maintainDesc.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"检修内容的描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(maintainStartTime.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"检修内容的开始时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(maintainEndTime.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"检修内容的结束时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sumTime_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"检修内容的总工时不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //结果
                if(hiddenDangerDesc_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"隐患描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(maintainer.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"检修保养人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reporter_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"报告人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportTime_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"报告时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportReceivePart_maintain.getText().toString().isEmpty()){
                    Toast.makeText(MaintainAction.this,"报告接收部门不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //产品信息列车车型
                RadioButton radioButton1 = (RadioButton) findViewById(subwaytype_maintain.getCheckedRadioButtonId());
                if(radioButton1==null){
                    Toast.makeText(MaintainAction.this,"请选择产品信息中的列车车型", Toast.LENGTH_SHORT).show();
                    return;
                }
                //检修保养类型
                RadioButton radioButton2 = (RadioButton) findViewById(maintainType.getCheckedRadioButtonId());
                if(radioButton2==null){
                    Toast.makeText(MaintainAction.this,"请选择检修保养类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                //配件更换
                RadioButton radioButton3 = (RadioButton) findViewById(isHaveReplaceParts_maintain.getCheckedRadioButtonId());
                if(radioButton3==null){
                    Toast.makeText(MaintainAction.this,"请选择备件信息的配件更换", Toast.LENGTH_SHORT).show();
                    return;
                }
                //检修保养结果
                RadioButton radioButton4 = (RadioButton) findViewById(maintainResult.getCheckedRadioButtonId());
                if(radioButton4==null){
                    Toast.makeText(MaintainAction.this,"请选择检修保养结果", Toast.LENGTH_SHORT).show();
                    return;
                }
                //是否需要纠正措施
                RadioButton radioButton5 = (RadioButton) findViewById(correctActionIsNeeded_maintain.getCheckedRadioButtonId());
                if(radioButton5==null){
                    Toast.makeText(MaintainAction.this,"请选择是否需要纠正措施", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseMaintainInfo bs = new BaseMaintainInfo();
                maintainId = UUIDUtil.getUUID();
                bs.setMaintainId(maintainId);
                //客户信息
                //客户名称
                bs.setCustomerNm_maintain(customerNm_maintain.getText().toString());
                //客户地点
                bs.setContact_maintain(contact_maintain.getText().toString());
                //手机
                bs.setCustomerTel_maintain(customerTel_maintain.getText().toString());
                //产品信息ID
                bs.setPdId_maintain(pdId_maintain.getText().toString());
                //产品信息序列号
                bs.setSn_maintain(sn_maintain.getText().toString());
                //产品信息所属项目
                bs.setProject_maintain(project_maintain.getText().toString());
                //产品信息列车车型
                bs.setSubwaytype_maintain(radioButton1.getText().toString());
                //产品信息对应车辆编号
                bs.setSubwayid_maintain(subwayid_maintain.getText().toString());
                //备件信息
                bs.setPdId_r_maintain(pdId_r_maintain.getText().toString());
                //备件信息配件更换
                bs.setIsHaveReplaceParts_maintain(radioButton3.getText().toString());
                //备件信息数量
                bs.setPd_r_num_maintain(pd_r_num_maintain.getText().toString());
                //检修内容
                //描述
                //检修保养类型
                bs.setMaintainType(radioButton2.getText().toString());
                //描述
                bs.setMaintainDesc(maintainDesc.getText().toString());
                //开始时间
                bs.setMaintainStartTime(maintainStartTime.getText().toString());
                //结束时间
                bs.setMaintainEndTime(maintainEndTime.getText().toString());
                //总工时
                bs.setSumTime_maintain(sumTime_maintain.getText().toString());
                //结果
                //检修保养结果
                bs.setMaintainResult(radioButton4.getText().toString());
                //隐患描述
                bs.setHiddenDangerDesc_maintain(hiddenDangerDesc_maintain.getText().toString());
                //检修保养人
                bs.setMaintainer(maintainer.getText().toString());
                //报告人
                bs.setReporter_maintain(reporter_maintain.getText().toString());
                //报告时间
                bs.setReportTime_maintain(reportTime_maintain.getText().toString());
                //报告接收部门
                bs.setReportReceivePart_maintain(reportReceivePart_maintain.getText().toString());

                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(MaintainAction.this);
                http.insertMaintainInfo(bs, getProperties(getApplicationContext()).getProperty("maintainSubmitURL"));

            }
        });

    }

    //扫一扫数据获取--开始---
    private void initProductInfo() {
        pdName_maintain = (EditText) findViewById(R.id.pdName_maintain);//产品名称
        pdtype_maintain = (EditText) findViewById(R.id.pdtype_maintain);//产品规格型号
        pdName_r_maintain = (EditText) findViewById(R.id.pdName_r_maintain);//备件名称
        pdtype_r_maintain = (EditText) findViewById(R.id.pdtype_r_maintain);//备件规格型号
        pd_unit_price_maintain = (EditText) findViewById(R.id.pd_unit_price_maintain);//备件单价
        pd_cost_maintain = (EditText) findViewById(R.id.pd_cost_maintain);//备件成本
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
                pdId_maintain.setText(content);
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
                pdId_r_maintain.setText(content);
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
            mPicList_maintain.clear();
            mPicList_maintain.addAll(toDeletePicList);
            mGridViewAdapter_maintain.notifyDataSetChanged();
        }

    }

    class ScanThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(MaintainAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdScanURL") + pdId_maintain.getText());
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
                    pdId_maintain.setText(productCode);
                    pdName_maintain.setText(productName);
                    pdtype_maintain.setText(productModel);
                }else{
                    pdId_maintain.setText("");
                    pdName_maintain.setText("");
                    pdtype_maintain.setText("");
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
                OKHttpUtil http = new OKHttpUtil(MaintainAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdBackUpScanURL") + pdId_r_maintain.getText());
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
                pdId_r_maintain.setText(productCode_r);
                pdName_r_maintain.setText(productName_r);
                pdtype_r_maintain.setText(productModel_r);
                pd_unit_price_maintain.setText(iinvsalecost);
                pd_cost_maintain.setText(iinvncost);

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
                mPicList_maintain.add(compressPath);
                mGridViewAdapter_maintain.notifyDataSetChanged();
            }
        }
    }
    //提交按钮按下获取每个编辑框的数据--开始---
    private void initSubmitInfo() {
        //客户信息
        customerNm_maintain = (EditText) findViewById(R.id.customerNm_maintain);//客户名称
        contact_maintain = (EditText) findViewById(R.id.contact_maintain);//客户联系人
        customerTel_maintain = (EditText) findViewById(R.id.customerTel_maintain);//手机
        //产品信息
        pdId_maintain = (TextView) findViewById(R.id.pdId_maintain);//产品信息ID
        sn_maintain = (EditText) findViewById(R.id.sn_maintain);//产品信息序列号
        project_maintain = (EditText) findViewById(R.id.project_maintain);//产品信息所属项目
        subwaytype_maintain = (RadioGroup) findViewById(R.id.subwaytype_maintain);//产品信息列车车型
        subwayid_maintain = (EditText) findViewById(R.id.subwayid_maintain);//产品信息对应车辆编号
        //备件信息
        pdId_r_maintain = (EditText) findViewById(R.id.pdId_r_maintain);//备件信息ID
        isHaveReplaceParts_maintain = (RadioGroup) findViewById(R.id.isHaveReplaceParts_maintain);//备件信息是否有配件更换
        pd_r_num_maintain = (EditText) findViewById(R.id.pd_r_num_maintain);//备件信息数量
        //检修内容
        maintainType = (RadioGroup) findViewById(R.id.maintainType);//检修保养类型
        maintainDesc = (EditText) findViewById(R.id.maintainDesc); //描述
        maintainStartTime = (EditText) findViewById(R.id.maintainStartTime);//开始时间
        maintainEndTime = (EditText) findViewById(R.id.maintainEndTime); //结束时间
        sumTime_maintain = (EditText) findViewById(R.id.sumTime_maintain);//总工时
        //结果
        maintainResult = (RadioGroup) findViewById(R.id.maintainResult);//检修保养结果
        hiddenDangerDesc_maintain = (EditText) findViewById(R.id.hiddenDangerDesc_maintain); //隐患描述
        maintainer = (EditText) findViewById(R.id.maintainer);//检修保养人
        reporter_maintain = (EditText) findViewById(R.id.reporter_maintain);//报告人
        reportTime_maintain = (EditText) findViewById(R.id.reportTime_maintain);//报告时间
        reportReceivePart_maintain = (EditText) findViewById(R.id.reportReceivePart_maintain);//报告接收部门
        correctActionIsNeeded_maintain = (RadioGroup) findViewById(R.id.correctActionIsNeeded_maintain);//是否需要纠正措施
    }

    //图片初始化
    private void init() {
        mGridView_maintain = (GridView) findViewById(R.id.mGridView_maintain);
        initGridView();
    }

    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter_maintain = new GridViewAdapter(MaintainAction.this, mPicList_maintain);
        mGridView_maintain.setAdapter(mGridViewAdapter_maintain);
        //设置GridView的条目的点击事件
        mGridView_maintain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList_maintain.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList_maintain.size());
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
        PictureSelectorConfig.initMultiConfig(MaintainAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(MaintainAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList_maintain);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }



    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList_maintain.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList_maintain.get(i)));
                }
                UploadHelper helper = new UploadHelper(MaintainAction.this);
                //helper.post(MaintainAction.this, getProperties(getApplicationContext()).getProperty("afterSaleUploadpicURL"), files, AFTERSALEPROBLEMDESC, maintainId);
            }
        }).start();
    }
}

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.entity.BaseMeasureInfo;
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

public class MeasureAction extends Activity  {

    private static final int REQUEST_CODE_SCAN = 0x0000;//产品扫一扫
    Button scan_measure;
    private Button  dateselect01_measure,dateselect02_measure,dateselect03_measure;
    //产品信息
    EditText pdName_measure, pdtype_measure;

    //客户信息
    private EditText customerNm_measure; //客户名称
    private RadioGroup infoSource_measure;//信息来源
    //产品信息
    private TextView pdId_measure;//产品信息ID
    private EditText sn_measure;//产品信息序列号
    private EditText project_measure;//产品信息所属项目
    //信息
    private EditText occuTime_measure;//发生时间
    private EditText reportTime_measure;//报告时间
    private EditText occuAd_measure;//发生地点
    //处理信息
    private EditText problemdesc_measure;//故障描述
    private EditText causeAnalysis_measure;//原因分析
    private EditText measurePlan;//纠正措施计划
    private EditText resDept_measure;//责任部门
    private EditText personLiable_measure;//责任人
    private EditText timeRequest_measure;//时间要求
    private EditText measure;//纠正措施验证
    private EditText measurePerson; //验证人
    private EditText measureTime;//验证时间
    private Button cancel_measure;
    private Button submit_measure;
    StringBuffer stringBuilderS;
    StringBuffer stringBuilderE;
    StringBuffer stringBuilderR;
    //保存上传图片的数据源
    private String measureId;
    private GridView mGridView_measure;
    private ArrayList<String> mPicList_measure = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter_measure;
    private ArrayAdapter adapter;
    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.zhongtie, android.R.layout.simple_spinner_item);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter2 添加到spinner中
        spinner1.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner1.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //设置默认值
        spinner1.setVisibility(View.VISIBLE);

        //扫一扫初始化
        initProductInfo();
        //点击确认按钮需要提交的文本初始化
        initSubmitInfo();
        //图片初始化
        init();
        //时间选择器
        dateselect01_measure=(Button)findViewById(R.id.date_select01_measure);
        dateselect02_measure=(Button)findViewById(R.id.date_select02_measure);
        dateselect03_measure=(Button)findViewById(R.id.date_select03_measure);
        dateselect01_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(MeasureAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderS=new StringBuffer("");
                        stringBuilderS.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(MeasureAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderS.append(hourOfDay+":"+minute);
                                occuTime_measure.setText(stringBuilderS);
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
        dateselect02_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(MeasureAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderE=new StringBuffer("");
                        stringBuilderE.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(MeasureAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderE.append(hourOfDay+":"+minute);
                                reportTime_measure.setText(stringBuilderE);
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
        dateselect03_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(MeasureAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderR=new StringBuffer("");
                        stringBuilderR.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(MeasureAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderR.append(hourOfDay+":"+minute);
                                measureTime.setText(stringBuilderR);
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
        scan_measure = (Button) findViewById(R.id.scan_measure);
        //确认按钮初始化
        submit_measure = (Button) findViewById(R.id.confirm_measure);
        //取消按钮初始化
        cancel_measure = (Button) findViewById(R.id.cancel_measure);
        //产品信息扫一扫按下
        scan_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MeasureAction.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

        //取消按钮按下
        cancel_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckMainAction.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(customerNm_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                //产品信息
                if(pdId_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"产品ID不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(sn_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"产品信息的序列号不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(project_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"产品信息的所属项目不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(occuTime_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"产品信息的发生时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportTime_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"产品信息的报告时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(occuAd_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"产品信息的发生地点不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(problemdesc_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的故障描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(causeAnalysis_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的原因分析不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(measurePlan.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的纠正措施计划不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //结果
                if(resDept_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的责任部门不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(personLiable_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的责任人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(timeRequest_measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的时间要求不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(measure.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的纠正措施验证不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(measurePerson.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的验证人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(measureTime.getText().toString().isEmpty()){
                    Toast.makeText(MeasureAction.this,"处理信息的验证时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //产品信息列车车型
                RadioButton radioButton1 = (RadioButton) findViewById(infoSource_measure.getCheckedRadioButtonId());
                if(radioButton1==null){
                    Toast.makeText(MeasureAction.this,"请选择客户信息中的信息来源", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseMeasureInfo bs = new BaseMeasureInfo();
                measureId = UUIDUtil.getUUID();
                bs.setMeasureId(measureId);
                //客户信息
                //客户名称
                bs.setCustomerNm_measure(customerNm_measure.getText().toString());
                //信息来源
                bs.setInfoSource_measure(radioButton1.getText().toString());
                //产品信息ID
                bs.setPdId_measure(pdId_measure.getText().toString());
                //产品信息序列号
                bs.setSn_measure(sn_measure.getText().toString());
                //产品信息所属项目
                bs.setProject_measure(project_measure.getText().toString());
                //发生时间
                bs.setOccuTime_measure(occuTime_measure.getText().toString());
                //报告时间
                bs.setReportTime_measure(reportTime_measure.getText().toString());
                //发生地点
                bs.setOccuAd_measure(occuAd_measure.getText().toString());
                //处理信息
                //故障描述
                bs.setProblemdesc_measure(problemdesc_measure.getText().toString());
                //原因分析
                bs.setCauseAnalysis_measure(causeAnalysis_measure.getText().toString());
                //纠正措施计划
                bs.setMeasurePlan(measurePlan.getText().toString());
                //责任部门
                bs.setResDept_measure(resDept_measure.getText().toString());
                //责任人
                bs.setPersonLiable_measure(personLiable_measure.getText().toString());
                //时间要求
                bs.setTimeRequest_measure(timeRequest_measure.getText().toString());
                //纠正措施验证
                bs.setMeasure(measure.getText().toString());
                //验证人
                bs.setMeasurePerson(measurePerson.getText().toString());
                //验证时间
                bs.setMeasureTime(measureTime.getText().toString());

                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(MeasureAction.this);
                http.insertMeasureInfo(bs, getProperties(getApplicationContext()).getProperty("measureSubmitURL"));

            }
        });
    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    //扫一扫数据获取--开始---
    private void initProductInfo() {
        pdName_measure = (EditText) findViewById(R.id.pdName_measure);//产品名称
        pdtype_measure = (EditText) findViewById(R.id.pdtype_measure);//产品规格型号
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
                pdId_measure.setText(content);
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
            mPicList_measure.clear();
            mPicList_measure.addAll(toDeletePicList);
            mGridViewAdapter_measure.notifyDataSetChanged();
        }

    }

    class ScanThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = 1;  //消息(一个整型值)
                // 需要做的事:发送消息
                OKHttpUtil http = new OKHttpUtil(MeasureAction.this);
                String str = http.httpGetScan(getProperties(getApplicationContext()).getProperty("pdIdScanURL") + pdId_measure.getText());
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
                    pdId_measure.setText(productCode);
                    pdName_measure.setText(productName);
                    pdtype_measure.setText(productModel);
                }else{
                    pdId_measure.setText("");
                    pdName_measure.setText("");
                    pdtype_measure.setText("");
                }

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
                mPicList_measure.add(compressPath);
                mGridViewAdapter_measure.notifyDataSetChanged();
            }
        }
    }
    //提交按钮按下获取每个编辑框的数据--开始---
    private void initSubmitInfo() {
        //客户信息
        customerNm_measure = (EditText) findViewById(R.id.customerNm_measure);//客户名称
        infoSource_measure = (RadioGroup) findViewById(R.id.infoSource_measure);//信息来源
        //产品信息
        pdId_measure = (TextView) findViewById(R.id.pdId_measure);//产品信息ID
        sn_measure = (EditText) findViewById(R.id.sn_measure);//产品信息序列号
        project_measure = (EditText) findViewById(R.id.project_measure);//产品信息所属项目
        occuTime_measure = (EditText) findViewById(R.id.occuTime_measure);//发生时间
        reportTime_measure = (EditText) findViewById(R.id.reportTime_measure);//报告时间
        occuAd_measure = (EditText) findViewById(R.id.occuAd_measure);//发生地点
        //处理信息
        problemdesc_measure = (EditText) findViewById(R.id.problemdesc_measure);//故障描述
        causeAnalysis_measure = (EditText) findViewById(R.id.causeAnalysis_measure);//原因分析
        measurePlan = (EditText) findViewById(R.id.measurePlan);//纠正措施计划
        resDept_measure = (EditText) findViewById(R.id.resDept_measure);//责任部门
        personLiable_measure = (EditText) findViewById(R.id.personLiable_measure);//责任人
        timeRequest_measure = (EditText) findViewById(R.id.timeRequest_measure); //时间要求
        measure = (EditText) findViewById(R.id.measure);//纠正措施验证
        measurePerson = (EditText) findViewById(R.id.measurePerson);//验证人
        measureTime = (EditText) findViewById(R.id.measureTime);//验证时间
    }

    //图片初始化
    private void init() {
        mGridView_measure = (GridView) findViewById(R.id.mGridView_measure);
        initGridView();
    }

    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter_measure = new GridViewAdapter(MeasureAction.this, mPicList_measure);
        mGridView_measure.setAdapter(mGridViewAdapter_measure);
        //设置GridView的条目的点击事件
        mGridView_measure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList_measure.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList_measure.size());
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
        PictureSelectorConfig.initMultiConfig(MeasureAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(MeasureAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList_measure);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }



    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList_measure.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList_measure.get(i)));
                }
                UploadHelper helper = new UploadHelper(MeasureAction.this);
                helper.post(MeasureAction.this, getProperties(getApplicationContext()).getProperty("uploadpicURL"), files, "3", measureId);
            }
        }).start();
    }

}

package com.idisfkj.hightcopywx.find;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.entity.BaseBreakDownInfo;
import com.idisfkj.hightcopywx.find.uploadpictureutil.Constants;
import com.idisfkj.hightcopywx.find.uploadpictureutil.GridViewAdapter;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PictureSelectorConfig;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PlusImageActivity;
import com.idisfkj.hightcopywx.uploadpictures.UploadHelper;
import com.idisfkj.hightcopywx.util.OKHttpUtil;
import com.idisfkj.hightcopywx.util.UUIDUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class BreakdownInfoAction extends Activity  {

    //客户信息
    private EditText customerNm_breakdown; //客户名称
    private EditText receiveDept_breakdown; //信息接收部门
    private EditText receiver_breakdown; //接收人
    private EditText project_breakdown;//所属项目
    private EditText occuTime_breakdown;//发生时间
    private EditText problemDesc_breakdown;//问题描述
    private EditText reportDept_breakdown;//报送部门
    private EditText reporter_breakdown;//报送人
    private Button cancel_breakdown;
    private Button submit_breakdown;
    private Button  dateselect01_breakdown;
    StringBuffer stringBuilderS;
    //保存上传图片的数据源
    private String breakdownId;
    private GridView mGridView_breakdown;
    private ArrayList<String> mPicList_breakdown = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter_breakdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breakdowninfo);

        //点击确认按钮需要提交的文本初始化
        initSubmitInfo();
        //图片初始化
        init();
        //时间选择器
        dateselect01_breakdown=(Button)findViewById(R.id.date_select01_breakdown);
        dateselect01_breakdown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c=Calendar.getInstance();

                Dialog dateDialog=new DatePickerDialog(BreakdownInfoAction.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        stringBuilderS=new StringBuffer("");
                        stringBuilderS.append(arg1+"-"+(arg2+1)+"-"+arg3+" ");
                        Calendar time=Calendar.getInstance();
                        Dialog timeDialog=new TimePickerDialog(BreakdownInfoAction.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                stringBuilderS.append(hourOfDay+":"+minute);
                                occuTime_breakdown.setText(stringBuilderS);
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
        //确认按钮初始化
        submit_breakdown = (Button) findViewById(R.id.confirm_breakdown);
        //取消按钮初始化
        cancel_breakdown = (Button) findViewById(R.id.cancel_breakdown);

        //取消按钮按下
        cancel_breakdown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BreakdownMainAction.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_breakdown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(customerNm_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                //产品信息
                if(receiveDept_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"信息接收部门不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(receiver_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"接收人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(project_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"所属项目不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(occuTime_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"发生时间不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(problemDesc_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"问题描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reportDept_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"报送部门不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(reporter_breakdown.getText().toString().isEmpty()){
                    Toast.makeText(BreakdownInfoAction.this,"报送人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseBreakDownInfo bs = new BaseBreakDownInfo();
                breakdownId = UUIDUtil.getUUID();
                bs.setBreakdownId(breakdownId);
                //客户信息
                //客户名称
                bs.setCustomerNm_breakdown(customerNm_breakdown.getText().toString());
                //信息接收部门
                bs.setReceiveDept_breakdown(receiveDept_breakdown.getText().toString());
                //接收人
                bs.setReceiver_breakdown(receiver_breakdown.getText().toString());
                //所属项目
                bs.setProject_breakdown(project_breakdown.getText().toString());
                //发生时间
                bs.setOccuTime_breakdown(occuTime_breakdown.getText().toString());
                //问题描述
                bs.setProblemDesc_breakdown(problemDesc_breakdown.getText().toString());
                //报送部门
                bs.setReportDept_breakdown(reportDept_breakdown.getText().toString());
                //报送人
                bs.setReporter_breakdown(reporter_breakdown.getText().toString());

                //上传图片
                uploadPics();
                OKHttpUtil http = new OKHttpUtil(BreakdownInfoAction.this);
               // http.insertBreakdownInfo(bs, getProperties(getApplicationContext()).getProperty("breakdownSubmitURL"));

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            mPicList_breakdown.clear();
            mPicList_breakdown.addAll(toDeletePicList);
            mGridViewAdapter_breakdown.notifyDataSetChanged();
        }
    }

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
                mPicList_breakdown.add(compressPath);
                mGridViewAdapter_breakdown.notifyDataSetChanged();
            }
        }
    }
    //提交按钮按下获取每个编辑框的数据--开始---
    private void initSubmitInfo() {
        //客户信息
        customerNm_breakdown = (EditText) findViewById(R.id.customerNm_breakdown);//客户名称
        receiveDept_breakdown = (EditText) findViewById(R.id.receiveDept_breakdown);//信息接收部门
        receiver_breakdown = (EditText) findViewById(R.id.receiver_breakdown);//接收人
        project_breakdown = (EditText) findViewById(R.id.project_breakdown);//所属项目
        occuTime_breakdown = (EditText) findViewById(R.id.occuTime_breakdown);//发生时间
        problemDesc_breakdown = (EditText) findViewById(R.id.problemDesc_breakdown);//问题描述
        reportDept_breakdown = (EditText) findViewById(R.id.reportDept_breakdown);//报送部门
        reporter_breakdown = (EditText) findViewById(R.id.reporter_breakdown);//报送人
    }

    //图片初始化
    private void init() {
        mGridView_breakdown = (GridView) findViewById(R.id.mGridView_breakdown);
        initGridView();
    }

    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter_breakdown = new GridViewAdapter(BreakdownInfoAction.this, mPicList_breakdown);
        mGridView_breakdown.setAdapter(mGridViewAdapter_breakdown);
        //设置GridView的条目的点击事件
        mGridView_breakdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList_breakdown.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList_breakdown.size());
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
        PictureSelectorConfig.initMultiConfig(BreakdownInfoAction.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(BreakdownInfoAction.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList_breakdown);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }



    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList_breakdown.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList_breakdown.get(i)));
                }
                UploadHelper helper = new UploadHelper(BreakdownInfoAction.this);
                helper.post(BreakdownInfoAction.this, getProperties(getApplicationContext()).getProperty("uploadpicURL"), files, "4", breakdownId);
            }
        }).start();
    }

}

package com.idisfkj.hightcopywx.find.signin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.entity.BaseSign;
import com.idisfkj.hightcopywx.util.OKHttpUtil;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;


public class SignedActivity extends Activity {
    public static String CUR_TIME = "current_time";
    public static String CUR_LOCAT = "current_location";
    private TextView tv_sign_time;
    private TextView tv_sign_location;
    private TextView tv_commit;
    private EditText et_signed_remarks;
    private EditText et_signed_company;
    private TextView tv_back;
    private TextView tv_title;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_info);
        initView();
    }

    private void initView() {
        tv_sign_time = (TextView) findViewById(R.id.tv_sign_time);
        tv_sign_location = (TextView) findViewById(R.id.tv_sign_location);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        et_signed_remarks = (EditText) findViewById(R.id.signed_remarks);
        et_signed_company = (EditText) findViewById(R.id.signed_company);


        if (null != getIntent()) {
            String date=CalendarUtil.getCurrentDate();
            String curTime = getIntent().getStringExtra(CUR_TIME);
            String curLocat = getIntent().getStringExtra(CUR_LOCAT);
            tv_sign_location.setText(curLocat);
            tv_sign_time.setText(date+" "+curTime);
        }
        tv_title.setText("签到");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_signed_remarks.getText().toString().isEmpty()){
                    Toast.makeText(SignedActivity.this,"描述不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(et_signed_company.getText().toString().isEmpty()){
                    Toast.makeText(SignedActivity.this,"当前企业不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseSign bs = new BaseSign();
                //签到地点
                bs.setAddress(tv_sign_location.getText().toString());
                //签到时间
                bs.setSigntime(tv_sign_time.getText().toString());
                //当前企业
                bs.setCurrCompany(et_signed_company.getText().toString());
                //备注
                bs.setRemarks(et_signed_remarks.getText().toString());

                OKHttpUtil http = new OKHttpUtil(SignedActivity.this);
                http.insertSignInfo(bs, getProperties(getApplicationContext()).getProperty("SIGN_ADD"));
//                Toast.makeText(SignedActivity.this, "已提交", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SignedActivity.this, SignInMainActivity.class);
//                intent.putExtra(SignInMainActivity.NOTIFY, "已签到");
//                startActivity(intent);
                finish();
            }
        });
    }

}

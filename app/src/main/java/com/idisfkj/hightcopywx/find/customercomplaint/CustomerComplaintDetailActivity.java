package com.idisfkj.hightcopywx.find.customercomplaint;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;

public class CustomerComplaintDetailActivity extends Activity{

    private ImageView return_img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customercomplaintdetail);
        Bundle bundle=getIntent().getExtras();
        String beComplaintdMan=bundle.getString("beComplaintdMan");//被投诉人
        String complaintContent=bundle.getString("complaintContent");//投诉内容
        String customerName_complaint=bundle.getString("customerName_complaint");//客户名称
        String complaintTime=bundle.getString("complaintTime");//投诉时间
        String complaintRemarks=bundle.getString("complaintRemarks");//备注
        TextView beComplaintdMan_t=(TextView) findViewById(R.id.beComplaintdMan);
        beComplaintdMan_t.setText(beComplaintdMan);
        TextView complaintContent_t=(TextView) findViewById(R.id.complaintContent);
        complaintContent_t.setText(complaintContent);
        TextView customerName_complaint_t=(TextView) findViewById(R.id.customerName_complaint);
        customerName_complaint_t.setText(customerName_complaint);
        TextView complaintTime_t=(TextView) findViewById(R.id.complaintTime);
        complaintTime_t.setText(complaintTime);
        TextView complaintRemarks_t=(TextView) findViewById(R.id.complaintRemarks);
        complaintRemarks_t.setText(complaintRemarks);
        return_img = (ImageView) findViewById(R.id.return_customer_complaint);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

package com.idisfkj.hightcopywx.find.customerpraise;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;

public class CustomerPraiseDetailActivity extends Activity{
    private ImageView return_img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerpraisedetail);
        Bundle bundle=getIntent().getExtras();
        String bePraisedMan=bundle.getString("bePraisedMan");//被表扬人
        String praiseContent=bundle.getString("praiseContent");//表扬内容
        String customerName_praise=bundle.getString("customerName_praise");//客户名称
        String praiseTime=bundle.getString("praiseTime");//表扬时间
        String praiseRemarks=bundle.getString("praiseRemarks");//备注
        TextView bePraisedMan_t=(TextView) findViewById(R.id.bePraisedMan);
        bePraisedMan_t.setText(bePraisedMan);
        TextView praiseContent_t=(TextView) findViewById(R.id.praiseContent);
        praiseContent_t.setText(praiseContent);
        TextView customerName_praise_t=(TextView) findViewById(R.id.customerName_praise);
        customerName_praise_t.setText(customerName_praise);
        TextView praiseTime_t=(TextView) findViewById(R.id.praiseTime);
        praiseTime_t.setText(praiseTime);
        TextView praiseRemarks_t=(TextView) findViewById(R.id.praiseRemarks);
        praiseRemarks_t.setText(praiseRemarks);
        return_img = (ImageView) findViewById(R.id.return_customer_praise);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

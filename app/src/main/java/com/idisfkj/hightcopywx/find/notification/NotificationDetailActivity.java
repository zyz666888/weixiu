package com.idisfkj.hightcopywx.find.notification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;

public class NotificationDetailActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationdetail);
        Bundle bundle=getIntent().getExtras();
        String title=bundle.getString("title");//标题
        String content=bundle.getString("content");//内容
        String createtime=bundle.getString("createtime");//时间
        TextView detail_title=(TextView) findViewById(R.id.notification_detail_title);
        detail_title.setText(title);
        TextView detail_createtime=(TextView) findViewById(R.id.notification_detail_createtime);
        detail_createtime.setText(createtime);
        TextView detail_content=(TextView) findViewById(R.id.notification_detail_content);
        detail_content.setText("\u3000\u3000"+content);
    }
}

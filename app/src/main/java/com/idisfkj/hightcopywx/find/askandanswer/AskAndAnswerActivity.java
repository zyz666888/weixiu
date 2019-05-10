package com.idisfkj.hightcopywx.find.askandanswer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;

public class AskAndAnswerActivity extends Activity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.askandanswerdetail);
        Bundle bundle=getIntent().getExtras();
        String problem_desc=bundle.getString("problem_desc");//问题描述
        String fix_desc=bundle.getString("fix_desc");//处理描述
        String createtime=bundle.getString("createtime");//时间
        TextView detail_problem_desc=(TextView) findViewById(R.id.askandanswer_detail_problem_desc);
        detail_problem_desc.setText("\u3000\u3000"+problem_desc);
        TextView detail_createtime=(TextView) findViewById(R.id.askandanswer_detail_createtime);
        detail_createtime.setText(createtime);
        TextView detail_fix_desc=(TextView) findViewById(R.id.askandanswer_detail_fix_desc);
        detail_fix_desc.setText("\u3000\u3000"+fix_desc);

    }
}

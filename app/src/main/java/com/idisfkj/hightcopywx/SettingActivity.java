package com.idisfkj.hightcopywx;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idisfkj.hightcopywx.util.MyProperUtil;
import com.idisfkj.hightcopywx.util.ToastUtils;

public class SettingActivity extends Activity {

    private EditText editText;

    private Button saveByn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        editText=(EditText) findViewById(R.id.server_address);
        saveByn=(Button) findViewById(R.id.savebtn);

        saveByn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(  MyProperUtil.getProperties(SettingActivity.this,"IP","settingConfig.properties"));
                MyProperUtil.setProperties(SettingActivity.this,"IP","123456");
                //保存服务器数据到文件中

            }
        });
    }

}

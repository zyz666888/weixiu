package com.idisfkj.hightcopywx.find.aftersale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.idisfkj.hightcopywx.R;
public class AftersaleMainAction extends Activity implements View.OnClickListener {


    private TextView textview5;
    private ImageView img5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aftersalefixreport);
        Intent intent=getIntent();
        String toast=intent.getStringExtra("toast");
        if(toast!=null&&!toast.isEmpty()) {
            Toast.makeText(AftersaleMainAction.this, toast, Toast.LENGTH_SHORT).show();
        }
        textview5=(TextView)findViewById(R.id.textview5);
        img5=(ImageView)findViewById(R.id.img5);
        textview5.setOnClickListener(this);
        img5.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.textview5:
                Intent intent4 = new Intent(this,FixAction.class);
                startActivity(intent4);
                break;
            case R.id.img5:
                Intent intent9 = new Intent(this,FixAction.class);
                startActivity(intent9);
                break;
        }
    }
}

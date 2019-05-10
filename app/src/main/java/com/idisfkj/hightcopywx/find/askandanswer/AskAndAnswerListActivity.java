package com.idisfkj.hightcopywx.find.askandanswer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import com.idisfkj.hightcopywx.util.OKHttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class AskAndAnswerListActivity extends Activity {
    private ListView listView;
    private Button askandanswer_btn;
    private EditText searchTxt;
    private ImageView register_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.askandanswerlist);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        listView = (ListView) findViewById(R.id.askandanswer_listview);
        askandanswer_btn=(Button) findViewById(R.id.askandanswer_answer_btn);
        searchTxt=(EditText) findViewById(R.id.askandanswer_search);
        register_return=(ImageView)findViewById(R.id.return_askandanswer);
        register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //1.匿名内部类
        askandanswer_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MyThread().start();
            }
        });
        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_askandanswer);
        final List<String> urls = new ArrayList<>();
        urls.add(getProperties(getApplicationContext()).getProperty("ad1"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad2"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad3"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad4"));
        urls.add(getProperties(getApplicationContext()).getProperty("ad5"));
        bannerLayout.setImageLoader(new GlideImageLoader());
        bannerLayout.setViewUrls(urls);

        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Toast.makeText(InstallMainAction.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        //广告轮播--结束--
    }

    //列表的显示-----------开始-----------------
    Handler handlerlist = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String strByJson = data.getString("value");
            //先转JsonObject
            JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
            String strData="";
            if(jsonObject.get("data")!=null){
                strData=jsonObject.get("data").toString();
            }
            final ArrayList<BaseAskAndAnswerInfo> userBeanList = new ArrayList<>();
            //获取产品检验记录页面的list --开始---
            if (strData != null && !strData.isEmpty()) {
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(strData).getAsJsonArray();

                Gson gson = new Gson();


                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    BaseAskAndAnswerInfo userBean = gson.fromJson(user, BaseAskAndAnswerInfo.class);
                    userBeanList.add(userBean);
                }
            }
            listView.setAdapter(new AskAndAnswerAdapter(userBeanList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BaseAskAndAnswerInfo info = userBeanList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("fix_desc", info.getFixDesc());//标题
                    bundle.putString("problem_desc", info.getProblemDesc());//内容
                    bundle.putString("createtime", info.getCreateTime());//时间
                    Intent intent = new Intent(AskAndAnswerListActivity.this, AskAndAnswerActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {

            final String address = getProperties(AskAndAnswerListActivity.this).getProperty("ASKANDANSWER")+searchTxt.getText().toString();
            // 需要做的事:发送消息
            OKHttpUtil http=new OKHttpUtil(AskAndAnswerListActivity.this);
            String str = http.httpGetInfo(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            handlerlist.sendMessage(msg);
        }
    }

}


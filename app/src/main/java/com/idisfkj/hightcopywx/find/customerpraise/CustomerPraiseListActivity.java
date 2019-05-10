package com.idisfkj.hightcopywx.find.customerpraise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import com.idisfkj.hightcopywx.find.customerpraise.entity.BaseCustomerPraise;
import com.idisfkj.hightcopywx.util.OKHttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class CustomerPraiseListActivity extends Activity {

    private ListView listView;
    private ImageView return_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerpraiselist);
        listView = (ListView)findViewById(R.id.customerpraise_listview);
        return_img = (ImageView) findViewById(R.id.return_customer_praise);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_customerpraise);
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
        new MyThread().start();
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
            final ArrayList<BaseCustomerPraise> userBeanList = new ArrayList<>();

            //获取产品检验记录页面的list --开始---
            if (strData != null && !strData.isEmpty()) {
                JsonObject jsonObjectdata = new JsonParser().parse(strData).getAsJsonObject();
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObjectdata.getAsJsonArray("list");
                Gson gson = new Gson();
                //循环遍历
                for (JsonElement user : jsonArray) {
                    //通过反射 得到UserBean.class
                    BaseCustomerPraise userBean = gson.fromJson(user, new TypeToken<BaseCustomerPraise>() {
                    }.getType());
                    userBeanList.add(userBean);
                }
                ArrayList<BaseCustomerPraise> customerPraiseInfos = new ArrayList<>();
                for (int i = 0; i < userBeanList.size(); i++) {
                    BaseCustomerPraise baseCustomerPraise = new BaseCustomerPraise();
                    baseCustomerPraise.setCustomername(userBeanList.get(i).getCustomername());
                    baseCustomerPraise.setPraisetime(userBeanList.get(i).getPraisetime());
                    customerPraiseInfos.add(baseCustomerPraise);
                }
                CustomerPraiseAdapter adapter = new CustomerPraiseAdapter(customerPraiseInfos);
                listView.setAdapter(adapter);
            }

            listView.setAdapter(new CustomerPraiseAdapter(userBeanList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BaseCustomerPraise info = userBeanList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("bePraisedMan", info.getBepraiseman());//被表扬人
                    bundle.putString("praiseContent", info.getContent());//表扬内容
                    bundle.putString("customerName_praise", info.getCustomername());//客户名称
                    bundle.putString("praiseTime", info.getPraisetime());//表扬时间
                    bundle.putString("praiseRemarks", info.getRemarks());//备注
                    Intent intent = new Intent(CustomerPraiseListActivity.this, CustomerPraiseDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {
            final String address = getProperties(CustomerPraiseListActivity.this).getProperty("CUSTOMER_PRAISE_LIST") + "?flag=1";
            // 需要做的事:发送消息
            OKHttpUtil http=new OKHttpUtil(CustomerPraiseListActivity.this);
            String str = http.httpGetInfo(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            handlerlist.sendMessage(msg);
        }
    }

    //列表的显示-----------结束-----------------
}

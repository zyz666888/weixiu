package com.idisfkj.hightcopywx.find.customervisit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.ad.BannerLayout;
import com.idisfkj.hightcopywx.find.ad.imageloader.GlideImageLoader;
import com.idisfkj.hightcopywx.util.OKHttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class CustomerVisitListActivity extends Activity {
    private ListView listView;
    private Button add_customervisit;
    private TextView yesterday,thisweek,thismonth;
    private ImageView return_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customervisitlist);
        listView = (ListView) findViewById(R.id.customervisit_listview);
        yesterday=(TextView) findViewById(R.id.yesterday);
        thisweek=(TextView) findViewById(R.id.thisweek);
        thismonth=(TextView) findViewById(R.id.thismonth);
        add_customervisit = (Button) findViewById(R.id.add_customervisit);
        add_customervisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(CustomerVisitListActivity.this, AddCustomerVisitActivity.class);
                startActivity(intent);
            }
        });
        return_img = (ImageView) findViewById(R.id.return_customervisit);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //广告轮播--开始--
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner_customervisit);
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
        new GetCountThread().start();
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
            final ArrayList<BaseVisitCustomerInfo> userBeanList = new ArrayList<>();

            //获取产品检验记录页面的list --开始---
            if (strData != null && !strData.isEmpty()) {
                JsonObject jsonObjectdata = new JsonParser().parse(strData).getAsJsonObject();
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObjectdata.getAsJsonArray("list");
                Gson gson = new Gson();
                //循环遍历
                for (JsonElement user : jsonArray) {
                    //通过反射 得到UserBean.class
                    BaseVisitCustomerInfo userBean = gson.fromJson(user, new TypeToken<BaseVisitCustomerInfo>() {
                    }.getType());
                    userBeanList.add(userBean);
                }
                ArrayList<BaseVisitCustomerInfo> customerVisitInfos = new ArrayList<>();
                for (int i = 0; i < userBeanList.size(); i++) {
                    BaseVisitCustomerInfo baseVisitCustomerInfo = new BaseVisitCustomerInfo();
                    baseVisitCustomerInfo.setRemarks(userBeanList.get(i).getRemarks());
                    baseVisitCustomerInfo.setVisittime(userBeanList.get(i).getVisittime());
                    baseVisitCustomerInfo.setAddress(userBeanList.get(i).getAddress());
                    baseVisitCustomerInfo.setCustomer(userBeanList.get(i).getCustomer());
                    customerVisitInfos.add(baseVisitCustomerInfo);
                }
                CustomerVisitAdapter adapter = new CustomerVisitAdapter(customerVisitInfos);
                listView.setAdapter(adapter);
            }
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    BaseCustomerComplaint info = userBeanList.get(position);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("beComplaintdMan", info.getBeComplaintdMan());//被投诉人
//                    bundle.putString("complaintContent", info.getComplaintContent());//投诉内容
//                    bundle.putString("customerName_complaint", info.getCustomerName_complaint());//客户名称
//                    bundle.putString("complaintTime", info.getComplaintTime());//投诉时间
//                    bundle.putString("complaintRemarks", info.getComplaintRemarks());//备注
//                    Intent intent = new Intent(CustomerComplaintListActivity.this, CustomerComplaintDetailActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
        }
    };

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {
            final String address = getProperties(CustomerVisitListActivity.this).getProperty("CUSTOMER_VISIT_LIST") + "?flag=1";
            // 需要做的事:发送消息
            OKHttpUtil http=new OKHttpUtil(CustomerVisitListActivity.this);
            String str = http.httpGetInfo(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            handlerlist.sendMessage(msg);
        }
    }

    public class GetCountThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {
            final String address = getProperties(CustomerVisitListActivity.this).getProperty("CUSTOMER_VISIT_COUNT");
            // 需要做的事:发送消息
            OKHttpUtil http=new OKHttpUtil(CustomerVisitListActivity.this);
            String str = http.httpGetInfo(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            handlerCount.sendMessage(msg);
        }
    }

    //拜访客户统计数-----------开始-----------------
    Handler handlerCount = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String strByJson = data.getString("value");
            //先转JsonObject
            JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
            String strData="";
            if(jsonObject.get("data")!=null){
                strData=jsonObject.get("data").toString();
            }
            if (strData != null && !strData.isEmpty()) {

                JsonObject jsonObject2 = new JsonParser().parse(strData).getAsJsonObject();
                //再转JsonArray 加上数据头
                int yesterday_str = jsonObject2.get("yesterday").getAsInt();
                int thisweek_str = jsonObject2.get("week").getAsInt();
                int thismonth_str = jsonObject2.get("month").getAsInt();
                yesterday.setText(String.valueOf(yesterday_str));
                thisweek.setText(String.valueOf(thisweek_str));
                thismonth.setText(String.valueOf(thismonth_str));
            }

        }
    };
    //拜访客户统计数-----------结束-----------------
}


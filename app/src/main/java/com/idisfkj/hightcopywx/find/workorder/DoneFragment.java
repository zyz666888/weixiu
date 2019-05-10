package com.idisfkj.hightcopywx.find.workorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.workorder.entity.BaseWorkOrder;
import com.idisfkj.hightcopywx.util.OKHttpUtil;

import java.util.ArrayList;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class DoneFragment extends Fragment {

    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workorderlistview, container, false);
        listView = (ListView) view.findViewById(R.id.listView_home);
        new MyThread().start();
        return view;
    }
    //列表的显示-----------开始-----------------
    Handler handlerlist = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String strByJson = data.getString("value");
            JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
            String strData="";
            if(jsonObject.get("data")!=null){
                strData=jsonObject.get("data").toString();
            }
            final ArrayList<BaseWorkOrder> userBeanList = new ArrayList<>();
            //获取产品检验记录页面的list --开始---
            if (strData != null && !strData.isEmpty()) {
                JsonObject jsonObjectdata = new JsonParser().parse(strData).getAsJsonObject();
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObjectdata.getAsJsonArray("list");
                Gson gson = new Gson();
                //循环遍历
                for (JsonElement user : jsonArray) {
                    //通过反射 得到UserBean.class
                    BaseWorkOrder userBean = gson.fromJson(user, new TypeToken<BaseWorkOrder>() {
                    }.getType());
                    userBeanList.add(userBean);
                }
                ArrayList<BaseWorkOrder> workOrderInfos = new ArrayList<>();
                for (int i = 0; i < userBeanList.size(); i++) {
                    BaseWorkOrder workOrder = new BaseWorkOrder();
                    workOrder.setState(userBeanList.get(i).getState());
                    workOrder.setLeader(userBeanList.get(i).getLeader());
                    workOrder.setOrdertime(userBeanList.get(i).getOrdertime());
                    workOrder.setWorkorderId(userBeanList.get(i).getWorkorderId());
                    workOrderInfos.add(workOrder);
                }
                MyAdapter adapter = new MyAdapter(workOrderInfos);
                listView.setAdapter(adapter);
            }
            //获取产品检验记录页面的list --结束---
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BaseWorkOrder info= userBeanList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("workOrderId", info.getWorkorderId());//派工单编号
                    bundle.putString("state", "已结束");//状态:1是已完成
                    bundle.putString("serviceReqId", info.getServiceRequestId());//服务请求单号
                    bundle.putString("leader", info.getLeader());//派工者
                    bundle.putString("orderTime", info.getOrdertime());//派工时间
                    bundle.putString("worker", info.getWorker());//派谁处理
                    bundle.putString("fixDept", info.getFixdept());//处理部门
                    bundle.putString("reqStartTime", info.getRequestStartTime());//要求开始时间
                    bundle.putString("reqEndTime", info.getRequestEndTime());//要求完成时间
                    bundle.putString("taskType", info.getTaskType());//任务类型
                    bundle.putString("isUnderWarranty", info.getIsUnderWarranty());//是否保修期内
                    Intent intent = new Intent(getActivity(),WorkOrderDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    };

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {

            final String address = getProperties(getContext()).getProperty("WORKORDER_LIST") + "?flag=1&state=1";
            OKHttpUtil http=new OKHttpUtil(getContext());
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

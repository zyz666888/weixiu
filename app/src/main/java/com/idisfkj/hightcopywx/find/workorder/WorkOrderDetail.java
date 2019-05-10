package com.idisfkj.hightcopywx.find.workorder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.idisfkj.hightcopywx.R;

public class WorkOrderDetail extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorderdetail);
        Bundle bundle=getIntent().getExtras();
        String workOrderId=bundle.getString("workOrderId");//派工单编号
        String state=bundle.getString("state");//派工单编号
        String serviceReqId=bundle.getString("serviceReqId");//服务请求单号
        String leader=bundle.getString("leader");//派工者
        String orderTime=bundle.getString("orderTime");//派工时间
        String worker=bundle.getString("worker");//派谁处理
        String fixDept=bundle.getString("fixDept");//处理部门
        String reqStartTime=bundle.getString("reqStartTime");//要求开始时间
        String reqEndTime=bundle.getString("reqEndTime");//要求完成时间
        String taskType=bundle.getString("taskType");//任务类型
        String isUnderWarranty=bundle.getString("isUnderWarranty");//是否保修期内
        TextView workOrderId_t=(TextView) findViewById(R.id.workOrderId_detail);
        workOrderId_t.setText(workOrderId);
        TextView state_t=(TextView) findViewById(R.id.state_detail);
        state_t.setText(state);
        TextView serviceReqId_t=(TextView) findViewById(R.id.serviceReqId_detail);
        serviceReqId_t.setText(serviceReqId);
        TextView leader_t=(TextView) findViewById(R.id.leader_detail);
        leader_t.setText(leader);
        TextView orderTime_t=(TextView) findViewById(R.id.orderTime_detail);
        orderTime_t.setText(orderTime);
        TextView worker_t=(TextView) findViewById(R.id.worker_detail);
        worker_t.setText(worker);
        TextView fixDept_t=(TextView) findViewById(R.id.fixDept_detail);
        fixDept_t.setText(fixDept);
        TextView reqStartTime_t=(TextView) findViewById(R.id.reqStartTime_detail);
        reqStartTime_t.setText(reqStartTime);
        TextView reqEndTime_t=(TextView) findViewById(R.id.reqEndTime_detail);
        reqEndTime_t.setText(reqEndTime);
        TextView taskType_t=(TextView) findViewById(R.id.taskType_detail);
        taskType_t.setText(taskType);
        TextView isUnderWarranty_t=(TextView) findViewById(R.id.isUnderWarranty_detail);
        isUnderWarranty_t.setText(isUnderWarranty);
    }

}
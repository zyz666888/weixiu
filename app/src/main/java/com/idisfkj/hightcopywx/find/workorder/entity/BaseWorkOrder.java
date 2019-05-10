package com.idisfkj.hightcopywx.find.workorder.entity;
public class BaseWorkOrder {
    /**  */
    private String id;

    /** 派工单编号 */
    private String workorderId;

    /** 服务请求号 */
    private String serviceRequestId;

    /** 派工者 */
    private String leader;

    /** 派工时间 */
    private String ordertime;

    /** 派谁处理 */
    private String worker;

    /** 处理部门 */
    private String fixdept;

    /** 要求开始时间 */
    private String requestStartTime;

    /** 要求完成时间 */
    private String requestEndTime;

    /** 任务类型 */
    private String taskType;

    /** 是否保修期内 */
    private String isUnderWarranty;

    /**  */
    private String createId;

    /**  */
    private String updateId;

    /**  */
    private String createTime;

    /**  */
    private String updateTime;

    /** 完成状态 0：未完成   1：已完成 */
    private String state;
    //名称的获取 -----开始------------
    /** 创建人 */
    private String createIdName;
    public String getCreateIdName() {
        return createIdName;
    }

    public void setCreateIdName(String createIdName) {
        this.createIdName = createIdName;
    }

    public String getUpdateIdName() {
        return updateIdName;
    }

    public void setUpdateIdName(String updateIdName) {
        this.updateIdName = updateIdName;
    }

    /** 更新者 */
    private String updateIdName;

    //名称的获取 -----结束-----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(String workorderId) {
        this.workorderId = workorderId == null ? null : workorderId.trim();
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(String serviceRequestId) {
        this.serviceRequestId = serviceRequestId == null ? null : serviceRequestId.trim();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker == null ? null : worker.trim();
    }

    public String getFixdept() {
        return fixdept;
    }

    public void setFixdept(String fixdept) {
        this.fixdept = fixdept == null ? null : fixdept.trim();
    }

    public String getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(String requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public String getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(String requestEndTime) {
        this.requestEndTime = requestEndTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public String getIsUnderWarranty() {
        return isUnderWarranty;
    }

    public void setIsUnderWarranty(String isUnderWarranty) {
        this.isUnderWarranty = isUnderWarranty == null ? null : isUnderWarranty.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getupdateId() {
        return updateId;
    }

    public void setupdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getupdateTime() {
        return updateTime;
    }

    public void setupdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}
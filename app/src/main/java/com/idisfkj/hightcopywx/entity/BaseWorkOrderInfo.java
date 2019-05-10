package com.idisfkj.hightcopywx.entity;

public class BaseWorkOrderInfo {

    private String workorderId;//派工单编号
    private String customerNm_breakdown; //服务请求单号
    private String receiveDept_breakdown; //派工者
    private String receiver_breakdown; //派工时间
    private String project_breakdown;//派谁处理
    private String occuTime_breakdown;//处理部门
    private String problemDesc_breakdown;//要求开始时间
    private String reportDept_breakdown;//要求完成时间
    private String reporter_breakdown;//任务类型
    private String isUnderWarranty;//保修期内/保修期外

    public String getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(String workorderId) {
        this.workorderId = workorderId;
    }

    public String getCustomerNm_breakdown() {
        return customerNm_breakdown;
    }

    public void setCustomerNm_breakdown(String customerNm_breakdown) {
        this.customerNm_breakdown = customerNm_breakdown;
    }

    public String getReceiveDept_breakdown() {
        return receiveDept_breakdown;
    }

    public void setReceiveDept_breakdown(String receiveDept_breakdown) {
        this.receiveDept_breakdown = receiveDept_breakdown;
    }

    public String getReceiver_breakdown() {
        return receiver_breakdown;
    }

    public void setReceiver_breakdown(String receiver_breakdown) {
        this.receiver_breakdown = receiver_breakdown;
    }

    public String getProject_breakdown() {
        return project_breakdown;
    }

    public void setProject_breakdown(String project_breakdown) {
        this.project_breakdown = project_breakdown;
    }

    public String getOccuTime_breakdown() {
        return occuTime_breakdown;
    }

    public void setOccuTime_breakdown(String occuTime_breakdown) {
        this.occuTime_breakdown = occuTime_breakdown;
    }

    public String getProblemDesc_breakdown() {
        return problemDesc_breakdown;
    }

    public void setProblemDesc_breakdown(String problemDesc_breakdown) {
        this.problemDesc_breakdown = problemDesc_breakdown;
    }

    public String getReportDept_breakdown() {
        return reportDept_breakdown;
    }

    public void setReportDept_breakdown(String reportDept_breakdown) {
        this.reportDept_breakdown = reportDept_breakdown;
    }

    public String getReporter_breakdown() {
        return reporter_breakdown;
    }

    public void setReporter_breakdown(String reporter_breakdown) {
        this.reporter_breakdown = reporter_breakdown;
    }

    public String getIsUnderWarranty() {
        return isUnderWarranty;
    }

    public void setIsUnderWarranty(String isUnderWarranty) {
        this.isUnderWarranty = isUnderWarranty;
    }
}
package com.idisfkj.hightcopywx.entity;

public class BaseBreakDownInfo {

    public String getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(String breakdownId) {
        this.breakdownId = breakdownId;
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
    //故障信息ID
    private String breakdownId;
    //客户信息
    private String customerNm_breakdown; //客户名称
    private String receiveDept_breakdown; //信息接收部门
    private String receiver_breakdown; //接收人
    private String project_breakdown;//所属项目
    private String occuTime_breakdown;//发生时间
    private String problemDesc_breakdown;//问题描述
    private String reportDept_breakdown;//报送部门
    private String reporter_breakdown;//报送人


}
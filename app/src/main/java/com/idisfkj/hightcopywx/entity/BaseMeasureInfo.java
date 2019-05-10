package com.idisfkj.hightcopywx.entity;

public class BaseMeasureInfo {

    //纠正措施ID
    private String measureId;

    //客户信息
    private String customerNm_measure; //客户名称
    private String infoSource_measure; //信息来源
    //产品信息
    private String pdId_measure;//产品信息ID
    private String sn_measure;//产品信息序列号
    private String project_measure;//产品信息所属项目
    //信息
    private String occuTime_measure;//发生时间
    private String reportTime_measure;//报告时间
    private String occuAd_measure;//发生地点
    //处理信息
    private String problemdesc_measure;//故障描述
    private String causeAnalysis_measure;//原因分析
    private String measurePlan;//纠正措施计划
    private String resDept_measure;//责任部门
    private String personLiable_measure;//责任人
    private String timeRequest_measure;//时间要求
    private String measure;//纠正措施验证
    private String measurePerson; //验证人
    private String measureTime;//验证时间
    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public String getCustomerNm_measure() {
        return customerNm_measure;
    }

    public void setCustomerNm_measure(String customerNm_measure) {
        this.customerNm_measure = customerNm_measure;
    }

    public String getInfoSource_measure() {
        return infoSource_measure;
    }

    public void setInfoSource_measure(String infoSource_measure) {
        this.infoSource_measure = infoSource_measure;
    }

    public String getPdId_measure() {
        return pdId_measure;
    }

    public void setPdId_measure(String pdId_measure) {
        this.pdId_measure = pdId_measure;
    }

    public String getSn_measure() {
        return sn_measure;
    }

    public void setSn_measure(String sn_measure) {
        this.sn_measure = sn_measure;
    }

    public String getProject_measure() {
        return project_measure;
    }

    public void setProject_measure(String project_measure) {
        this.project_measure = project_measure;
    }

    public String getOccuTime_measure() {
        return occuTime_measure;
    }

    public void setOccuTime_measure(String occuTime_measure) {
        this.occuTime_measure = occuTime_measure;
    }

    public String getReportTime_measure() {
        return reportTime_measure;
    }

    public void setReportTime_measure(String reportTime_measure) {
        this.reportTime_measure = reportTime_measure;
    }

    public String getOccuAd_measure() {
        return occuAd_measure;
    }

    public void setOccuAd_measure(String occuAd_measure) {
        this.occuAd_measure = occuAd_measure;
    }

    public String getProblemdesc_measure() {
        return problemdesc_measure;
    }

    public void setProblemdesc_measure(String problemdesc_measure) {
        this.problemdesc_measure = problemdesc_measure;
    }

    public String getCauseAnalysis_measure() {
        return causeAnalysis_measure;
    }

    public void setCauseAnalysis_measure(String causeAnalysis_measure) {
        this.causeAnalysis_measure = causeAnalysis_measure;
    }

    public String getMeasurePlan() {
        return measurePlan;
    }

    public void setMeasurePlan(String measurePlan) {
        this.measurePlan = measurePlan;
    }

    public String getResDept_measure() {
        return resDept_measure;
    }

    public void setResDept_measure(String resDept_measure) {
        this.resDept_measure = resDept_measure;
    }

    public String getPersonLiable_measure() {
        return personLiable_measure;
    }

    public void setPersonLiable_measure(String personLiable_measure) {
        this.personLiable_measure = personLiable_measure;
    }

    public String getTimeRequest_measure() {
        return timeRequest_measure;
    }

    public void setTimeRequest_measure(String timeRequest_measure) {
        this.timeRequest_measure = timeRequest_measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasurePerson() {
        return measurePerson;
    }

    public void setMeasurePerson(String measurePerson) {
        this.measurePerson = measurePerson;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

}
package com.idisfkj.hightcopywx.find.aftersale;

public class BaseFixInfo {

    public String getCustomerNm() {
        return customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getFixDesc() {
        return fixDesc;
    }

    public void setFixDesc(String fixDesc) {
        this.fixDesc = fixDesc;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getFixStartTime() {
        return fixStartTime;
    }

    public void setFixStartTime(String fixStartTime) {
        this.fixStartTime = fixStartTime;
    }

    public String getFixEndTime() {
        return fixEndTime;
    }

    public void setFixEndTime(String fixEndTime) {
        this.fixEndTime = fixEndTime;
    }

    public String getDiagnoseTime() {
        return diagnoseTime;
    }

    public void setDiagnoseTime(String diagnoseTime) {
        this.diagnoseTime = diagnoseTime;
    }

    public String getFixResult() {
        return fixResult;
    }

    public void setFixResult(String fixResult) {
        this.fixResult = fixResult;
    }

    public String getQualityDefectCost() {
        return qualityDefectCost;
    }

    public void setQualityDefectCost(String qualityDefectCost) {
        this.qualityDefectCost = qualityDefectCost;
    }

    public String getTroubleShooting() {
        return troubleShooting;
    }

    public void setTroubleShooting(String troubleShooting) {
        this.troubleShooting = troubleShooting;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportReceivePart() {
        return reportReceivePart;
    }

    public void setReportReceivePart(String reportReceivePart) {
        this.reportReceivePart = reportReceivePart;
    }

    public String getCorrectActionIsNeeded() {
        return correctActionIsNeeded;
    }

    public void setCorrectActionIsNeeded(String correctActionIsNeeded) {
        this.correctActionIsNeeded = correctActionIsNeeded;
    }

    public String getAftersaleId() {
        return aftersaleId;
    }

    public void setAftersaleId(String aftersaleId) {
        this.aftersaleId = aftersaleId;
    }

    //售后ID
    private String aftersaleId;
    //客户信息
    private String customerNm;//客户名称
    private String contact;//客户联系人
    private String customerTel;//手机
    //产品信息
    private String pdId;//产品信息ID
    private String sn;//产品信息序列号
    private String project;//产品信息所属项目
    private String subwaytype;//产品信息列车车型
    private String subwayid;//产品信息对应车辆编号
    private String subwayrunhistory;//产品信息列车走行历史
    //备件信息
    private String pdId_r;//备件信息ID
    private String isReplaceParts;//备件信息是否有配件更换
    private String pd_r_num;//备件信息数量
    private String fixTime;//修理时长
    private String testTime;//试验时长
    private String sumTime;//总工时
    private String sumTimeCost;//工时成本
    //问题描述
    private String problemDesc;
    //处理描述
    private String fixDesc;
    //问题判断
    private String problemType;
    //修复开始时间
    private String fixStartTime;
    //修复结束时间
    private String fixEndTime;
    //诊断时长
    private String diagnoseTime;
    //处理结果
    private String fixResult;
    //质量缺陷成本
    private String qualityDefectCost;
    //故障处理人
    private String troubleShooting;
    //报告人
    private String reporter;
    //报告时间
    private String reportTime;
    //报告接收部门
    private String reportReceivePart;
    //是否需要纠正措施
    private String correctActionIsNeeded;
    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSubwaytype() {
        return subwaytype;
    }

    public void setSubwaytype(String subwaytype) {
        this.subwaytype = subwaytype;
    }

    public String getSubwayid() {
        return subwayid;
    }

    public void setSubwayid(String subwayid) {
        this.subwayid = subwayid;
    }

    public String getSubwayrunhistory() {
        return subwayrunhistory;
    }

    public void setSubwayrunhistory(String subwayrunhistory) {
        this.subwayrunhistory = subwayrunhistory;
    }

    public String getPdId_r() {
        return pdId_r;
    }

    public void setPdId_r(String pdId_r) {
        this.pdId_r = pdId_r;
    }

    public String getIsReplaceParts() {
        return isReplaceParts;
    }

    public void setIsReplaceParts(String isReplaceParts) {
        this.isReplaceParts = isReplaceParts;
    }

    public String getPd_r_num() {
        return pd_r_num;
    }

    public void setPd_r_num(String pd_r_num) {
        this.pd_r_num = pd_r_num;
    }

    public String getFixTime() {
        return fixTime;
    }

    public void setFixTime(String fixTime) {
        this.fixTime = fixTime;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getSumTime() {
        return sumTime;
    }

    public void setSumTime(String sumTime) {
        this.sumTime = sumTime;
    }

    public String getSumTimeCost() {
        return sumTimeCost;
    }

    public void setSumTimeCost(String sumTimeCost) {
        this.sumTimeCost = sumTimeCost;
    }




}
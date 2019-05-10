package com.idisfkj.hightcopywx.entity;

public class BaseInstallInfo {

    //售后ID
    private String installId;

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }

    public String getCustomerNm() {
        return customerNm;
    }

    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }

    public String getCustomerAdd() {
        return customerAdd;
    }

    public void setCustomerAdd(String customerAdd) {
        this.customerAdd = customerAdd;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getPdId_install() {
        return pdId_install;
    }

    public void setPdId_install(String pdId_install) {
        this.pdId_install = pdId_install;
    }

    public String getSn_install() {
        return sn_install;
    }

    public void setSn_install(String sn_install) {
        this.sn_install = sn_install;
    }

    public String getProject_install() {
        return project_install;
    }

    public void setProject_install(String project_install) {
        this.project_install = project_install;
    }

    public String getSubwaytype_install() {
        return subwaytype_install;
    }

    public void setSubwaytype_install(String subwaytype_install) {
        this.subwaytype_install = subwaytype_install;
    }

    public String getSubwayid_install() {
        return subwayid_install;
    }

    public void setSubwayid_install(String subwayid_install) {
        this.subwayid_install = subwayid_install;
    }

    public String getPdId_r_install() {
        return pdId_r_install;
    }

    public void setPdId_r_install(String pdId_r_install) {
        this.pdId_r_install = pdId_r_install;
    }

    public String getIsHaveExtraParts_install() {
        return isHaveExtraParts_install;
    }

    public void setIsHaveExtraParts_install(String isHaveExtraParts_install) {
        this.isHaveExtraParts_install = isHaveExtraParts_install;
    }

    public String getPd_r_num_install() {
        return pd_r_num_install;
    }

    public void setPd_r_num_install(String pd_r_num_install) {
        this.pd_r_num_install = pd_r_num_install;
    }

    public String getProblemdesc_install() {
        return problemdesc_install;
    }

    public void setProblemdesc_install(String problemdesc_install) {
        this.problemdesc_install = problemdesc_install;
    }

    public String getInstallStartTime() {
        return installStartTime;
    }

    public void setInstallStartTime(String installStartTime) {
        this.installStartTime = installStartTime;
    }

    public String getInstallEndTime() {
        return installEndTime;
    }

    public void setInstallEndTime(String installEndTime) {
        this.installEndTime = installEndTime;
    }

    public String getSumTime() {
        return sumTime;
    }

    public void setSumTime(String sumTime) {
        this.sumTime = sumTime;
    }

    public String getIsHaveLocalProblem() {
        return isHaveLocalProblem;
    }

    public void setIsHaveLocalProblem(String isHaveLocalProblem) {
        this.isHaveLocalProblem = isHaveLocalProblem;
    }

    public String getLocalProblemDesc() {
        return localProblemDesc;
    }

    public void setLocalProblemDesc(String localProblemDesc) {
        this.localProblemDesc = localProblemDesc;
    }

    public String getInstallResult() {
        return installResult;
    }

    public void setInstallResult(String installResult) {
        this.installResult = installResult;
    }

    public String getUnfinishedDesc() {
        return unfinishedDesc;
    }

    public void setUnfinishedDesc(String unfinishedDesc) {
        this.unfinishedDesc = unfinishedDesc;
    }

    public String getInstaller() {
        return installer;
    }

    public void setInstaller(String installer) {
        this.installer = installer;
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

    //客户信息
    private String customerNm; //客户名称
    private String customerAdd;//客户地点
    private String customerTel;//手机
    //产品信息
    private String pdId_install;//产品信息ID
    private String sn_install;//产品信息序列号
    private String project_install;//产品信息所属项目
    private String subwaytype_install;//产品信息列车车型
    private String subwayid_install;//产品信息对应车辆编号
    //备件信息
    private String pdId_r_install;//备件信息ID
    private String isHaveExtraParts_install;//备件信息是否有额外配件
    private String pd_r_num_install;//备件信息数量
    //安装调试内容
    private String problemdesc_install;//描述
    private String installStartTime;//开始时间
    private String installEndTime;//结束时间
    private String sumTime;//总工时
    private String isHaveLocalProblem;//是否有现场问题
    private String localProblemDesc;//现场问题描述
    //结果
    private String installResult;//安装调试结果
    private String unfinishedDesc; //开口项描述
    private String installer;//安装调试配合人
    private String reporter;//报告人
    private String reportTime;//报告时间
    private String reportReceivePart;//报告接收部门

}
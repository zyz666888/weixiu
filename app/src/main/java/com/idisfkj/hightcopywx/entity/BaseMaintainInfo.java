package com.idisfkj.hightcopywx.entity;

public class BaseMaintainInfo {

    private String maintainId; //维修ID

    //客户信息

    public String getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(String maintainId) {
        this.maintainId = maintainId;
    }

    public String getPdId_maintain() {
        return pdId_maintain;
    }

    public void setPdId_maintain(String pdId_maintain) {
        this.pdId_maintain = pdId_maintain;
    }

    public String getSn_maintain() {
        return sn_maintain;
    }

    public void setSn_maintain(String sn_maintain) {
        this.sn_maintain = sn_maintain;
    }

    public String getProject_maintain() {
        return project_maintain;
    }

    public void setProject_maintain(String project_maintain) {
        this.project_maintain = project_maintain;
    }

    public String getSubwaytype_maintain() {
        return subwaytype_maintain;
    }

    public void setSubwaytype_maintain(String subwaytype_maintain) {
        this.subwaytype_maintain = subwaytype_maintain;
    }

    public String getSubwayid_maintain() {
        return subwayid_maintain;
    }

    public String getCustomerNm_maintain() {
        return customerNm_maintain;
    }

    public void setCustomerNm_maintain(String customerNm_maintain) {
        this.customerNm_maintain = customerNm_maintain;
    }

    public String getContact_maintain() {
        return contact_maintain;
    }

    public void setContact_maintain(String contact_maintain) {
        this.contact_maintain = contact_maintain;
    }

    public String getCustomerTel_maintain() {
        return customerTel_maintain;
    }

    public void setCustomerTel_maintain(String customerTel_maintain) {
        this.customerTel_maintain = customerTel_maintain;
    }

    public String getSumTime_maintain() {
        return sumTime_maintain;
    }

    public void setSumTime_maintain(String sumTime_maintain) {
        this.sumTime_maintain = sumTime_maintain;
    }

    public String getHiddenDangerDesc_maintain() {
        return hiddenDangerDesc_maintain;
    }

    public void setHiddenDangerDesc_maintain(String hiddenDangerDesc_maintain) {
        this.hiddenDangerDesc_maintain = hiddenDangerDesc_maintain;
    }

    public String getReporter_maintain() {
        return reporter_maintain;
    }

    public void setReporter_maintain(String reporter_maintain) {
        this.reporter_maintain = reporter_maintain;
    }

    public String getReportTime_maintain() {
        return reportTime_maintain;
    }

    public void setReportTime_maintain(String reportTime_maintain) {
        this.reportTime_maintain = reportTime_maintain;
    }

    public String getReportReceivePart_maintain() {
        return reportReceivePart_maintain;
    }

    public void setReportReceivePart_maintain(String reportReceivePart_maintain) {
        this.reportReceivePart_maintain = reportReceivePart_maintain;
    }

    public String getCorrectActionIsNeeded_maintain() {
        return correctActionIsNeeded_maintain;
    }

    public void setCorrectActionIsNeeded_maintain(String correctActionIsNeeded_maintain) {
        this.correctActionIsNeeded_maintain = correctActionIsNeeded_maintain;
    }

    public void setSubwayid_maintain(String subwayid_maintain) {
        this.subwayid_maintain = subwayid_maintain;
    }

    public String getPdId_r_maintain() {
        return pdId_r_maintain;
    }

    public void setPdId_r_maintain(String pdId_r_maintain) {
        this.pdId_r_maintain = pdId_r_maintain;
    }

    public String getIsHaveReplaceParts_maintain() {
        return isHaveReplaceParts_maintain;
    }

    public void setIsHaveReplaceParts_maintain(String isHaveReplaceParts_maintain) {
        this.isHaveReplaceParts_maintain = isHaveReplaceParts_maintain;
    }

    public String getPd_r_num_maintain() {
        return pd_r_num_maintain;
    }

    public void setPd_r_num_maintain(String pd_r_num_maintain) {
        this.pd_r_num_maintain = pd_r_num_maintain;
    }

    public String getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(String maintainType) {
        this.maintainType = maintainType;
    }

    public String getMaintainDesc() {
        return maintainDesc;
    }

    public void setMaintainDesc(String maintainDesc) {
        this.maintainDesc = maintainDesc;
    }

    public String getMaintainStartTime() {
        return maintainStartTime;
    }

    public void setMaintainStartTime(String maintainStartTime) {
        this.maintainStartTime = maintainStartTime;
    }

    public String getMaintainEndTime() {
        return maintainEndTime;
    }

    public void setMaintainEndTime(String maintainEndTime) {
        this.maintainEndTime = maintainEndTime;
    }


    public String getMaintainResult() {
        return maintainResult;
    }

    public void setMaintainResult(String maintainResult) {
        this.maintainResult = maintainResult;
    }


    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }


    private String customerNm_maintain; //客户名称
    private String contact_maintain;//客户联系人
    private String customerTel_maintain;//手机
    //产品信息
    private String pdId_maintain;//产品信息ID
    private String sn_maintain;//产品信息序列号
    private String project_maintain;//产品信息所属项目
    private String subwaytype_maintain;//产品信息列车车型
    private String subwayid_maintain;//产品信息对应车辆编号

    //备件信息
    private String pdId_r_maintain;//备件信息ID
    private String isHaveReplaceParts_maintain;//配件更换
    private String pd_r_num_maintain;//备件信息数量
    //检修内容
    private String maintainType;//检修保养类型
    private String maintainDesc;//描述
    private String maintainStartTime;//开始时间
    private String maintainEndTime;//结束时间
    private String sumTime_maintain;//总工时
    //结果
    private String maintainResult;//检修保养结果
    private String hiddenDangerDesc_maintain; //隐患描述
    private String maintainer;//检修保养人
    private String reporter_maintain;//报告人
    private String reportTime_maintain;//报告时间
    private String reportReceivePart_maintain;//报告接收部门
    private String correctActionIsNeeded_maintain;//是否需要纠正措施

}
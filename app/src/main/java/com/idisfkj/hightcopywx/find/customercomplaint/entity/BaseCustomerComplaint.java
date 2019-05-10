package com.idisfkj.hightcopywx.find.customercomplaint.entity;

public class BaseCustomerComplaint {
    /**  */
    private String complaintid;

    /** 被投诉人 */
    private String becomplaintman;

    /** 投诉内容 */
    private String content;

    /** 客户名称 */
    private String customername;

    /** 投诉时间 */
    private String complainttime;

    /** 备注 */
    private String remarks;

    /** 创建人 */
    private Integer createId;

    /** 更新者 */
    private Integer updateId;
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

    /**  */
    private String createTime;

    /**  */
    private String updateTime;

    public String getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid == null ? null : complaintid.trim();
    }

    public String getBecomplaintman() {
        return becomplaintman;
    }

    public void setBecomplaintman(String becomplaintman) {
        this.becomplaintman = becomplaintman == null ? null : becomplaintman.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getComplainttime() {
        return complainttime;
    }

    public void setComplainttime(String complainttime) {
        this.complainttime = complainttime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
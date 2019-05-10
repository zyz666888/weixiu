package com.idisfkj.hightcopywx.find.customerpraise.entity;

public class BaseCustomerPraise {
    /** 编号 */
    private String praiseid;

    public String getPraiseid() {
        return praiseid;
    }

    public void setPraiseid(String praiseid) {
        this.praiseid = praiseid;
    }

    public String getBepraiseman() {
        return bepraiseman;
    }

    public void setBepraiseman(String bepraiseman) {
        this.bepraiseman = bepraiseman;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPraisetime() {
        return praisetime;
    }

    public void setPraisetime(String praisetime) {
        this.praisetime = praisetime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    /** 被表扬人 */
    private String bepraiseman;

    /** 表扬内容 */
    private String content;

    /** 客户名称 */
    private String customername;

    /** 表扬时间 */
    private String praisetime;

    /** 备注 */
    private String remarks;

    /** 创建人 */
    private Integer createId;

    /** 创建时间 */
    private String createTime;

    /** 更新者 */
    private Integer updateId;

    /** 更新时间 */
    private String updateTime;


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

}
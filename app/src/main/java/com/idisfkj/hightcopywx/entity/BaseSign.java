package com.idisfkj.hightcopywx.entity;

public class BaseSign {

    /** 签到时间 */
    private String signtime;

    /** 备注 */
    private String remarks;

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrCompany() {
        return currCompany;
    }

    public void setCurrCompany(String currCompany) {
        this.currCompany = currCompany;
    }

    /** 签到地点 */

    private String address;

    /** 当前企业 */
    private String currCompany;


}
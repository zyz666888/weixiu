package com.idisfkj.hightcopywx.find.customervisit;

public class BaseVisitCustomerInfo {
    /** 客户名称 */
    private String customer;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
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

    /** 拜访时间 */
    private String visittime;

    /** 备注 */
    private String remarks;

    /** 地址 */
    private String address;

}

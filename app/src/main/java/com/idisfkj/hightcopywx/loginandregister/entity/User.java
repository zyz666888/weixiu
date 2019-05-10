package com.idisfkj.hightcopywx.loginandregister.entity;

import java.util.Date;

public class User {
    /** 用户id */
    private Integer userId;

    /** 账号 */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 姓名 */
    private String name;

    /** 手机 */
    private String mobile;

    /** 证件类型 1身份证 2士兵证 3军官证 4军人身份证 */
    private String certiType;

    /** 证件号码 */
    private String certiCode;

    /** 邮箱 */
    private String email;

    /** 状态 1正常 2删除 */
    private String status;

    /** 创建人 */
    private Integer createId;

    /** 修改人 */
    private Integer updateId;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 企业ID */
    private String companyId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCertiType() {
        return certiType;
    }

    public void setCertiType(String certiType) {
        this.certiType = certiType == null ? null : certiType.trim();
    }

    public String getCertiCode() {
        return certiCode;
    }

    public void setCertiCode(String certiCode) {
        this.certiCode = certiCode == null ? null : certiCode.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }
}
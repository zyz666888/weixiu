package com.idisfkj.hightcopywx.find.askandanswer;

public class BaseAskAndAnswerInfo {
    /** 问题描述 */
    private String problemDesc;

    /** 处理描述 */
    private String fixDesc;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /** 创建时间 */
    private String createTime;


}

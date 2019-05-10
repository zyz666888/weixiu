package com.idisfkj.hightcopywx.entity;

public class BaseQualityTestInfo {

    public String getQualityTestId() {
        return qualityTestId;
    }

    public void setQualityTestId(String qualityTestId) {
        this.qualityTestId = qualityTestId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
    //质量检验ID
    private String qualityTestId;
    //产品信息
    private String pdId;//产品信息ID
    //检验项点
    private String testName;//检查项
    private String testResult;//结果

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }


}
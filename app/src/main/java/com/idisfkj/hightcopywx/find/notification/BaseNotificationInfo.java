package com.idisfkj.hightcopywx.find.notification;

public class BaseNotificationInfo {
    private String title;//描述
    private String createTime;//时间

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String content;//地点

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

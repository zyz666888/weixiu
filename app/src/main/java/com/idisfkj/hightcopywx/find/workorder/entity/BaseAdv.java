package com.idisfkj.hightcopywx.find.workorder.entity;

public class BaseAdv {
    private String id;

    private String title;

    private String imageurl;

    private String content;

    private String freea;

    private String freeb;

    private String freec;

    private String freed;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    private String createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFreea() {
        return freea;
    }

    public void setFreea(String freea) {
        this.freea = freea == null ? null : freea.trim();
    }

    public String getFreeb() {
        return freeb;
    }

    public void setFreeb(String freeb) {
        this.freeb = freeb == null ? null : freeb.trim();
    }

    public String getFreec() {
        return freec;
    }

    public void setFreec(String freec) {
        this.freec = freec == null ? null : freec.trim();
    }

    public String getFreed() {
        return freed;
    }

    public void setFreed(String freed) {
        this.freed = freed == null ? null : freed.trim();
    }
}
package com.idisfkj.hightcopywx.find.friendscircle;

/**
 * Created by dell on 2018/8/22.
 */

public class FriendsCircleBean {

    private String img;
    private String name;
    private String time;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public NineGridTestModel getUrllist() {
        return urllist;
    }

    public void setUrllist(NineGridTestModel urllist) {
        this.urllist = urllist;
    }

    private String desc;
    private NineGridTestModel urllist;
}

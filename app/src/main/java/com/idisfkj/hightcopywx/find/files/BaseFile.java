package com.idisfkj.hightcopywx.find.files;

public class BaseFile {
    /** 文件名称 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    /** 文件路径 */

    private String path;

    /** 文件访问路径 */
    private String url;

    /** 文件类型 */
    private String type;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /** 文件大小 */
    private String size;

    private String createTime;


    }



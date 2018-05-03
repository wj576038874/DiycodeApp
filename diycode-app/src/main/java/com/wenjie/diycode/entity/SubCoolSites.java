package com.wenjie.diycode.entity;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.sites
 * @FileName: com.wenjie.diycode.entity.SubCoolSites.java
 * @Author: wenjie
 * @Date: 2017-08-25 16:41
 * @Description:
 * @Version:
 */
public class SubCoolSites {
    private String name;
    private String url;
    private String avatar_url;

    public SubCoolSites(String name, String url, String avatar_url) {
        this.name = name;
        this.url = url;
        this.avatar_url = avatar_url;
    }

    public SubCoolSites(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}

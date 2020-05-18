package com.zhuandian.travel.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-29.
 */
public class TravelGuideEntity extends BmobObject {
    private String title;
    private String content;
    private String imgUrl;
    private String viewsLocal;
    private String viewsTheme;
    private int timeLevel; //穷于时间（一级、二级、三级）
    private int moneyLevel;//穷于经费（一级、二级、三级）

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

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

    public String getViewsLocal() {
        return viewsLocal;
    }

    public void setViewsLocal(String viewsLocal) {
        this.viewsLocal = viewsLocal;
    }

    public String getViewsTheme() {
        return viewsTheme;
    }

    public void setViewsTheme(String viewsTheme) {
        this.viewsTheme = viewsTheme;
    }

    public int getTimeLevel() {
        return timeLevel;
    }

    public void setTimeLevel(int timeLevel) {
        this.timeLevel = timeLevel;
    }

    public int getMoneyLevel() {
        return moneyLevel;
    }

    public void setMoneyLevel(int moneyLevel) {
        this.moneyLevel = moneyLevel;
    }
}

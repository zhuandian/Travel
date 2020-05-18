package com.zhuandian.travel.entity;

import cn.bmob.v3.BmobUser;

/**
 * desc :
 * author：xiedong
 */
public class UserEntity extends BmobUser {
    private String nikeName;
    private String userInfo;
    private int type;  //1,属地志愿者  0.普通用户
    private String local;
    private String viewsLocal;
    private String gender;
    private String matchLocal;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMatchLocal() {
        return matchLocal;
    }

    public void setMatchLocal(String matchLocal) {
        this.matchLocal = matchLocal;
    }

    public String getViewsLocal() {
        return viewsLocal;
    }

    public void setViewsLocal(String viewsLocal) {
        this.viewsLocal = viewsLocal;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}

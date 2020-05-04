package com.zhuandian.travel.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2020/05/04
 */
public class SendHelpEntity  extends BmobObject {
    private String helperLocal;
    private UserEntity helperUser;
    private int helperLevel;
    private int helpState; //1.已经有志愿者处理 2.未处理

    public int getHelpState() {
        return helpState;
    }

    public void setHelpState(int helpState) {
        this.helpState = helpState;
    }

    public String getHelperLocal() {
        return helperLocal;
    }

    public void setHelperLocal(String helperLocal) {
        this.helperLocal = helperLocal;
    }

    public UserEntity getHelperUser() {
        return helperUser;
    }

    public void setHelperUser(UserEntity helperUser) {
        this.helperUser = helperUser;
    }

    public int getHelperLevel() {
        return helperLevel;
    }

    public void setHelperLevel(int helperLevel) {
        this.helperLevel = helperLevel;
    }
}

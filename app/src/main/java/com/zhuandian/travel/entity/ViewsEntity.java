package com.zhuandian.travel.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc : 景点类型实体类
 * author：xiedong
 * date：2020/05/04
 */
public class ViewsEntity extends BmobObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

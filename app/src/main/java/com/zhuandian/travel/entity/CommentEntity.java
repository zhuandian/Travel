package com.zhuandian.travel.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2020/05/30
 */
public class CommentEntity extends BmobObject {
    private String content;
    private UserEntity userEntity;
    private TravelTeamEntity travelTeamEntity;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public TravelTeamEntity getTravelTeamEntity() {
        return travelTeamEntity;
    }

    public void setTravelTeamEntity(TravelTeamEntity travelTeamEntity) {
        this.travelTeamEntity = travelTeamEntity;
    }
}

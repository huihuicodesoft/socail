package cn.com.wh.ring.app.bean.response;

import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;

import java.util.Date;

/**
 * Created by Hui on 2017/9/22.
 */
public class UserInfoResponse {
    private Long userId;
    private Long infoId;
    private String nickname;
    private Date birthday;
    private Byte sex;
    private String avatar;
    private String signature;
    private String region;
    private Date lastModifiedTime;

    public UserInfoResponse(Long userId, UserInfoPojo userInfoPojo, String region) {
        this.userId = userId;
        if (userInfoPojo != null) {
            this.infoId = userInfoPojo.getId();
            this.nickname = userInfoPojo.getNickname();
            this.birthday = userInfoPojo.getBirthday();
            this.sex = userInfoPojo.getSex();
            this.avatar = userInfoPojo.getAvatar();
            this.signature = userInfoPojo.getSignature();
            this.region = region;
            this. lastModifiedTime = userInfoPojo.getLastModifiedTime();
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}

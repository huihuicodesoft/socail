package cn.com.wh.ring.app.bean.response;

import java.util.Date;

/**
 * Created by Hui on 2017/9/22.
 */
public class UserInfo {
    private Long userId;
    private Long infoId;
    private String nickname;
    private Date birthday;
    private Byte sex;
    private String avatar;
    private String signature;
    private String addressCode;
    private Date lastModifiedTime;

    public UserInfo(Long userId, cn.com.wh.ring.app.bean.pojo.UserInfo userInfo) {
        this.userId = userId;
        if (userInfo != null) {
            infoId = userInfo.getId();
            nickname = userInfo.getNickname();
            birthday = userInfo.getBirthday();
            sex = userInfo.getSex();
            avatar = userInfo.getAvatar();
            signature = userInfo.getSignature();
            addressCode = userInfo.getAddressCode();
            lastModifiedTime = userInfo.getLastModifiedTime();
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

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}

package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

/**
 * Created by Hui on 2017/6/23.
 */
@Alias("UserPojo")
public class UserPojo {
    private Long id;
    private Long userId;
    private String account;
    private int accountType;
    private int userInfoId;
    private Long createTime;
    private String bindAccount;
    private int bindAccountType;
    private int state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getBindAccount() {
        return bindAccount;
    }

    public void setBindAccount(String bindAccount) {
        this.bindAccount = bindAccount;
    }

    public int getBindAccountType() {
        return bindAccountType;
    }

    public void setBindAccountType(int bindAccountType) {
        this.bindAccountType = bindAccountType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

package cn.com.wh.ring.app.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by Hui on 2017/7/7.
 */
public class UserToken implements AuthenticationToken {
    private String userMark;
    private String userType;

    public UserToken(String userMark, String userType) {
        this.userMark = userMark;
        this.userType = userType;
    }

    public String getPrincipal() {
        return userMark;
    }

    public String getCredentials() {
        return userType;
    }
}

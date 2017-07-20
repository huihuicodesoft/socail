package cn.com.wh.ring.app.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by Hui on 2017/7/7.
 */
public class UserToken implements AuthenticationToken {
    private String mark;

    public UserToken(String mark) {
        this.mark = mark;
    }

    public String getPrincipal() {
        return mark;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}

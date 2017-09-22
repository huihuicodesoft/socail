package cn.com.wh.ring.app.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by Hui on 2017/7/7.
 */
public class UserToken implements AuthenticationToken {
    private Object principal;

    public UserToken(Object principal) {
        this.principal = principal;
    }

    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}

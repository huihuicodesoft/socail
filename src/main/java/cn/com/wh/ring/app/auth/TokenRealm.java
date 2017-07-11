package cn.com.wh.ring.app.auth;

import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.app.service.user.UserTouristService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hui on 2017/7/7.
 */
public class TokenRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    UserTouristService userTouristService;

    public TokenRealm() {
        setCredentialsMatcher(new CredentialsMatcher() {

            public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
                UserToken userToken = (UserToken) token;
                if (TokenHelper.isUserByType(userToken.getCredentials())) {
                    return userService.isValid(Long.valueOf(userToken.getPrincipal()));
                } else if (TokenHelper.isTerminalByType(userToken.getCredentials())) {
                    return userTouristService.isValid(userToken.getPrincipal());
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserToken userToken = (UserToken) token;
        return new SimpleAuthenticationInfo(userToken.getPrincipal(), userToken.getCredentials(), getName());
    }
}

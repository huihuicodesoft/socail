package cn.com.wh.ring.app.auth;

import cn.com.wh.ring.app.bean.pojo.PermissionPojo;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.service.permission.PermissionService;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.app.service.user.UserTouristService;
import com.google.common.base.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Hui on 2017/7/7.
 */
public class TokenRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    UserTouristService userTouristService;
    @Autowired
    PermissionService permissionService;

    public TokenRealm() {
        setCredentialsMatcher((AuthenticationToken token, AuthenticationInfo info) ->{
                String mark = (String) token.getPrincipal();
                String[] userInfo = TokenHelper.parseToken(mark);
                if (userInfo.length == 2){
                    String userMark = userInfo[0];
                    String userType = userInfo[1];
                    if (TokenHelper.isUserByType(userType)) {
                        return userService.isValid(Long.valueOf(userMark));
                    } else if (TokenHelper.isTerminalByType(userType)) {
                        return userTouristService.isValid(userMark);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            });
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String mark = principals.getPrimaryPrincipal().toString();

        if (Strings.isNullOrEmpty(mark)) {
            return null;
        }

        String[] userInfo = TokenHelper.parseToken(mark);
        if (userInfo.length == 2){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            String userMark = userInfo[0];
            String userType = userInfo[1];
            if (TokenHelper.isUserByType(userType)) {
                List<PermissionPojo> permissions = permissionService.getPermissionsOfUser(Long.valueOf(userMark));
                for (PermissionPojo p : permissions) {
                    info.addStringPermission(p.getPermission());
                }
            } else {
                return info;
            }
        }

        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserToken userToken = (UserToken) token;
        return new SimpleAuthenticationInfo(userToken.getPrincipal(), userToken.getCredentials(), getName());
    }
}

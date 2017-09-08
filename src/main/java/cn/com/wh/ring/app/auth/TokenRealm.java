package cn.com.wh.ring.app.auth;

import cn.com.wh.ring.app.bean.pojo.Permission;
import cn.com.wh.ring.app.bean.pojo.Tourist;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.service.permission.PermissionService;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.app.service.user.TouristService;
import cn.com.wh.ring.common.secret.RSA;
import com.google.common.base.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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
    TouristService touristService;
    @Autowired
    PermissionService permissionService;

    public TokenRealm() {
        setCredentialsMatcher((AuthenticationToken authenticationToken, AuthenticationInfo info) -> {
            String token = (String) authenticationToken.getPrincipal();
            String mark = TokenHelper.getMark(token);
            String type = TokenHelper.getMarkType(token);
            if (TokenHelper.isUserByType(type)) {
                return userService.isValid(Long.valueOf(mark));
            } else {
                Tourist tourist = new Tourist();
                tourist.setTerminalMark(mark);
                tourist.setOsType(Integer.parseInt(type));
                touristService.recordAccessInfo(tourist);
                return true;
            }
        });
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = principals.getPrimaryPrincipal().toString();

        if (Strings.isNullOrEmpty(token)) {
            return null;
        }

        String mark = TokenHelper.getMark(token);
        String type = TokenHelper.getMarkType(token);
        if (TokenHelper.isUserByType(type)) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                List<Permission> permissions = permissionService.getPermissionsOfUser(Long.valueOf(mark));
                for (Permission p : permissions) {
                    info.addStringPermission(p.getPermission());
                    info.addObjectPermission(new WildcardPermission(p.getPermission()));
                }
                return info;
        }
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserToken userToken = (UserToken) token;
        return new SimpleAuthenticationInfo(userToken.getPrincipal(), userToken.getCredentials(), getName());
    }
}

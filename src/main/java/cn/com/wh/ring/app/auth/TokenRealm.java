package cn.com.wh.ring.app.auth;

import cn.com.wh.ring.app.bean.pojo.Permission;
import cn.com.wh.ring.app.bean.pojo.Terminal;
import cn.com.wh.ring.app.bean.pojo.UserTerminal;
import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import cn.com.wh.ring.app.bean.principal.UserPrincipal;
import cn.com.wh.ring.app.exception.AuthException;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.service.permission.PermissionService;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.app.service.user.TerminalService;
import cn.com.wh.ring.app.service.user.UserTerminalService;
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
    TerminalService touristService;
    @Autowired
    UserTerminalService userTerminalService;
    @Autowired
    PermissionService permissionService;

    public TokenRealm() {
        setCredentialsMatcher((AuthenticationToken authenticationToken, AuthenticationInfo info) -> {
            Object principal = authenticationToken.getPrincipal();
            try {
                if (principal instanceof UserPrincipal) {
                    UserPrincipal userPrincipal = (UserPrincipal) principal;
                    if (userService.isValid(userPrincipal.getUserId())) {
                        userTerminalService.valid(userPrincipal.getUserId(), userPrincipal.getTerminalId());
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    TerminalPrincipal terminalPrincipal = (TerminalPrincipal) principal;
                    Terminal terminal = new Terminal();
                    terminal.setMark(terminalPrincipal.getMark());
                    terminal.setOsType(terminalPrincipal.getOsType());
                    touristService.recordAccessInfo(terminal);
                    return true;
                }
            } catch (ServiceException e) {
                throw new AuthException(e.getCode(), e.getMessage());
            }
        });
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserToken;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object principal = principals.getPrimaryPrincipal();

        if (principal == null)
            return null;

        if (principal instanceof UserPrincipal) {
            Long userId = ((UserPrincipal) principal).getUserId();
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Permission> permissions = permissionService.getPermissionsOfUser(userId);
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

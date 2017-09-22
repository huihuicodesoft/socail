package cn.com.wh.ring.app.auth;

import cn.com.wh.ring.app.bean.principal.UserPrincipal;
import cn.com.wh.ring.app.exception.AuthException;
import cn.com.wh.ring.app.helper.ObjectMapperHolder;
import cn.com.wh.ring.app.helper.MessageResourceHelper;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Hui on 2017/7/7.
 */
public class TokenFilter extends NoSessionCreationFilter {
    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String TOKEN_KEY_NAME = "token";
    private static final long TOKEN_INVALID_TIME = 86400000; //token失效时间1day

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String token = getToken(httpServletRequest);
        try {
            if (Strings.isNullOrEmpty(token)) {
                throw new AuthException(ReturnCode.ERROR_TOKEN_NULL, "no token provided");
            } else {
                Object principal;
                if (TokenHelper.isTerminal(token)) {
                    principal = TokenHelper.parseTerminalToken(token);
                    if (principal == null) {
                        throw new AuthException(ReturnCode.ERROR_TOKEN, "token format error");
                    }
                } else {
                    principal = TokenHelper.parseToken(token);
                    if (principal != null) {
                        if (System.currentTimeMillis() - ((UserPrincipal) principal).getTime() > TOKEN_INVALID_TIME) {
                            throw new AuthException(ReturnCode.ERROR_TOKEN_INVALID, "token invalid");
                        }
                    } else {
                        throw new AuthException(ReturnCode.ERROR_TOKEN, "token format error");
                    }
                }
                UserToken userToken = new UserToken(principal);
                Subject subject = SecurityUtils.getSubject();
                subject.login(userToken);
                return true;
            }
        } catch (AuthException e) {
            //返回信息
            responseErrorToken((HttpServletResponse) response, e);
            return false;
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY_NAME);
        if (token != null) {
            logger.info("found principal in header! ");
            return token;
        }
        return null;
    }

    protected void responseErrorToken(HttpServletResponse response, AuthException authException) throws Exception {
        int errorCode = authException.getCode();
        Response<?> rsp = ResponseHelper.createResponse(errorCode, errorCode == ReturnCode.ERROR_TOKEN_NULL ?
                MessageResourceHelper.getInstance().getMessage("error_token_null", Locale.SIMPLIFIED_CHINESE) :
                MessageResourceHelper.getInstance().getMessage("error_token_invalid", Locale.SIMPLIFIED_CHINESE));
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getOutputStream().write(ObjectMapperHolder.getInstance().getMapper().writeValueAsBytes(rsp));
        } catch (Exception e) {
            logger.error("responseNotLogin failed", e);
        }
    }
}

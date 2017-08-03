package cn.com.wh.ring.app.auth;

import cn.com.wh.ring.app.helper.ObjectMapperHolder;
import cn.com.wh.ring.app.helper.MessageResourceHelper;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String token = getToken(httpServletRequest);
        try {
            if (Strings.isNullOrEmpty(token)) {
                throw new AuthenticationException("no token provided");
            } else {
                UserToken userToken = new UserToken(token);
                Subject subject = SecurityUtils.getSubject();
                subject.login(userToken);
                return true;
            }
        } catch (AuthenticationException e) {
            //返回信息
            responseNotLogin((HttpServletResponse) response);
            return false;
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY_NAME);
        if (token != null) {
            logger.info("found token in header! ");
            return token;
        }
//        token = CookieUtils.getCookie(TOKEN_KEY_NAME, request);
//        if (token != null) {
//            logger.info("found token in cookie! ");
//            return token;
//        }
        return null;
    }

    protected void responseNotLogin(HttpServletResponse response) throws Exception {
        String message = MessageResourceHelper.getInstance().getMessage("error_token_invalid", Locale.SIMPLIFIED_CHINESE);
        Response<?> rsp = ResponseHelper.createResponse(ReturnCode.ERROR_TOKEN, message);
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getOutputStream().write(ObjectMapperHolder.getInstance().getMapper().writeValueAsBytes(rsp));
        } catch (Exception e) {
            logger.error("responseNotLogin failed", e);
        }
    }
}

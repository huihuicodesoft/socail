package cn.com.wh.ring.app.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by Hui on 2017/9/8.
 */
public class TokenException extends AuthenticationException {
    private int code;
    private String message;

    public TokenException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

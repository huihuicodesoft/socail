package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.common.secret.AES;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Hui on 2017/7/7.
 */
public class TokenHelper {
    private static final String TOKEN_TYPE_USER = "user";
    private static final String TOKEN_TYPE_TERMINAL = "terminal";

    private static final String COLON = ":";

    public static String createUserToken(String content) {
        String result = content + COLON + TOKEN_TYPE_USER;
        return AES.encrypt(result);
    }

    public static String createTerminalToken(String content) {
        String result = content + COLON + TOKEN_TYPE_TERMINAL;
        return AES.encrypt(result);
    }

    public static String[] parseToken(String token) {
        return AES.decrypt(token).split(COLON);
    }

    public static String getMark(String token) {
        String[] infos = parseToken(token);
        if (infos.length == 2) {
            return infos[0];
        }
        return null;
    }

    /**
     * 返回当前用户的标识
     * @return
     */
    public static String getCurrentMark(){
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String mark = TokenHelper.getMark(token);
        return mark;
    }

    public static boolean isUserByType(String type) {
        return TOKEN_TYPE_USER.equals(type);
    }

    public static boolean isTerminalByType(String type) {
        return TOKEN_TYPE_TERMINAL.equals(type);
    }
}

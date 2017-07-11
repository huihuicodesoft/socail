package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.common.secret.AES;

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

    public static boolean isUserByType(String type){
        return TOKEN_TYPE_USER.equals(type);
    }

    public static boolean isTerminalByType(String type){
        return TOKEN_TYPE_TERMINAL.equals(type);
    }
}

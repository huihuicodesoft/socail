package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.common.secret.AES;
import cn.com.wh.ring.common.secret.RSA;
import com.google.common.base.Strings;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Hui on 2017/7/7.
 */
public class TokenHelper {
    private static final String TOURIST = "/tourist"; //前台标记游客
    private static final String USER = "user"; //后台生成token需要的局部信息
    private static final int USER_TOKEN_ARRAY_LENGTH = 3;
    private static final int TOURIST_TOKEN_ARRAY_LENGTH = 2;
    private static final String TYPE_USER = "tokenUser";
    private static final String TYPE_TOURIST = "touristUser";

    private static final String COLON = "_";

    public static String createToken(String content) {
        String result = content + COLON + String.valueOf(System.currentTimeMillis()) + COLON + USER;
        return AES.encrypt(result);
    }

    public static String[] parseToken(String token) {
        String[] info;
        String realToken = AES.decrypt(token);
        info = realToken.split(COLON);
        if (info == null || info.length != USER_TOKEN_ARRAY_LENGTH) {
            info = null;
        }
        return info;
    }

    public static String parseTouristToken(String token) {
        String realToken;
        try {
            realToken = RSA.decrypt(token.substring(0, token.lastIndexOf(TOURIST)));
            String[] info = realToken.split(COLON);
            if (info == null || info.length != TOURIST_TOKEN_ARRAY_LENGTH) {
                realToken = null;
            }
        } catch (Exception e) {
            realToken = null;
        }
        return realToken;
    }

    public static boolean isTourist(String token) {
        return token.endsWith(TOURIST);
    }

    /**
     * 返回当前用户的标识
     *
     * @return
     */
    public static String getCurrentMark() {
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        return getMark(token);
    }

    /**
     * 返回当前用户的类型
     *
     * @return
     */
    public static String getCurrentMarkType() {
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        return getMarkType(token);
    }

    public static String getMark(String token) {
        return token.split(COLON)[0];
    }

    public static String getMarkType(String token) {
        return token.split(COLON)[1];
    }

    public static String createSubjectPrincipalUser(String token) {
        return token + COLON + TYPE_USER;
    }

    public static String createSubjectPrincipalTourist(String token) {
        return token + COLON + TYPE_TOURIST;
    }

    public static boolean isUserByType(String type) {
        return TYPE_USER.equals(type);
    }

}

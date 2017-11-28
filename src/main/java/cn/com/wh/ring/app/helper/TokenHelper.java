package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import cn.com.wh.ring.app.bean.principal.UserPrincipal;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.common.secret.AES;
import cn.com.wh.ring.common.secret.RSA;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Hui on 2017/7/7.
 */
public class TokenHelper {
    private static final String TERMINAL = "/tourist"; //前台标记游客
    private static final String USER = "user"; //后台生成token需要的局部信息
    private static final int USER_TOKEN_ARRAY_LENGTH = 4;
    private static final int TERMINAL_TOKEN_ARRAY_LENGTH = 4;
    private static final String COLON = "_";

    /**
     * 创建用户toke
     * userId_terminalId_时间戳_user
     *
     * @param userId
     * @param terminalId
     * @return
     */
    public static String createToken(long userId, long terminalId) {
        String result = String.valueOf(userId) + COLON + String.valueOf(terminalId) + COLON + String.valueOf(System.currentTimeMillis()) + COLON + USER;
        return AES.encrypt(result);
    }

    public static UserPrincipal parseToken(String requestToken) {
        UserPrincipal userPrincipal = null;
        String realToken = AES.decrypt(requestToken);
        String[] info = realToken.split(COLON);
        if (info != null && info.length == USER_TOKEN_ARRAY_LENGTH && info[3].equals(USER)) {
            userPrincipal = new UserPrincipal();
            userPrincipal.setUserId(Long.valueOf(info[0]));
            userPrincipal.setTerminalId(Long.valueOf(info[1]));
            userPrincipal.setTime(Long.valueOf(info[2]));
        }
        return userPrincipal;
    }

    public static TerminalPrincipal parseTerminalToken(String requestToken) {
        TerminalPrincipal terminalPrincipal = null;
        try {
            String terminalInfo = RSA.decrypt(requestToken.substring(0, requestToken.lastIndexOf(TERMINAL)));
            String[] info = terminalInfo.split(COLON);
            if (info != null && info.length == TERMINAL_TOKEN_ARRAY_LENGTH) {
                terminalPrincipal = new TerminalPrincipal();
                terminalPrincipal.setUuid(info[0]);
                if (!Strings.isNullOrEmpty(info[1])){
                    terminalPrincipal.setImei(info[1]);
                }
                if (!Strings.isNullOrEmpty(info[2])){
                    terminalPrincipal.setMac(info[2]);
                }
                terminalPrincipal.setOsType(Byte.parseByte(info[3]));
            }
        } catch (Exception e) {

        }
        return terminalPrincipal;
    }

    public static boolean isTerminal(String requestToken) {
        return requestToken.endsWith(TERMINAL);
    }

    public static Long getCurrentSubjectUserId() {
        return ((UserPrincipal) getCurrentPrincipal()).getUserId();
    }

    public static String getCurrentSubjectUuid() {
        return ((TerminalPrincipal) getCurrentPrincipal()).getUuid();
    }

    public static String getCurrentSubjectUuidOrUserId() {
        String mark;
        int type = getCurrentSubjectType();
        if (type == Constants.USER_TYPE_USER) {
            mark = String.valueOf(getCurrentSubjectUserId());
        } else {
            mark = getCurrentSubjectUuid();
        }
        return mark;
    }

    public static int getCurrentSubjectType() {
        int type;
        Object principal = getCurrentPrincipal();
        if (principal instanceof UserPrincipal) {
            type = Constants.USER_TYPE_USER;
        } else {
            type = Constants.USER_TYPE_TERMINAL;
        }
        return type;
    }

    private static Object getCurrentPrincipal() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getPrincipal();
    }
}

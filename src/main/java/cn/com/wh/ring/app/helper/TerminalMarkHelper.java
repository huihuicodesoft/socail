package cn.com.wh.ring.app.helper;

/**
 * Created by Hui on 2017/7/21.
 */
public class TerminalMarkHelper {
    private static final String COLON = "+";

    public static final String contact(String mark, String type) {
        return mark + COLON + type;
    }
}

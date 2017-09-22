package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.bean.pojo.User;

/**
 * Created by Hui on 2017/9/22.
 */
public class UserHelper {
    public static final boolean canUse(Byte state) {
        boolean result = false;
        if (state != null) {
            result = state.byteValue() == User.STATE_USING;
        }
        return result;
    }
}

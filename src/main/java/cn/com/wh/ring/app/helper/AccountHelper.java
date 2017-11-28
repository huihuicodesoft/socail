package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.constant.Constants;

/**
 * Created by Hui on 2017/9/22.
 */
public class AccountHelper {
    public static final boolean canUse(Byte state) {
        boolean result = false;
        if (state != null) {
            result = state.byteValue() == Constants.STATE_USING;
        }
        return result;
    }
}

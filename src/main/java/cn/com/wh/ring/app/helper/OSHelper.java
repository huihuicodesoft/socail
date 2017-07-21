package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.constant.Constants;
import com.google.common.base.Strings;

/**
 * Created by Hui on 2017/7/20.
 */
public class OSHelper {
    public static int getOSCode(String apiVersion){
        if (Strings.isNullOrEmpty(apiVersion)) {
            return Constants.OS_UNKONW;
        } else if (apiVersion.startsWith("A")) {
            return Constants.OS_ANDROID;
        } else if (apiVersion.startsWith("I")) {
            return Constants.OS_IOS;
        } else {
            return Constants.OS_UNKONW;
        }
    }
}

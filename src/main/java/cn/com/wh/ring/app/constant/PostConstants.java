package cn.com.wh.ring.app.constant;

/**
 * Created by Hui on 2017/7/18.
 */
public class PostConstants {
    public static final int MAX_PHOTO_NUMBER = 9;
    public static final int MAX_VIDEO_NUMBER = 1;

    public static final int MEDIA_TYPE_PHOTO = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int POST_TYPE_STATE_USING = 0;
    public static final int POST_TYPE_STATE_SCRAP = 1;

    public static final int POST_TYPE_SUPPORT_ALL = 0; //全被格式
    public static final int POST_TYPE_SUPPORT_W = 1; //只支持文字
    public static final int POST_TYPE_SUPPORT_P = 2; //只支持图片
    public static final int POST_TYPE_SUPPORT_V = 3; //只支持视频
    public static final int POST_TYPE_SUPPORT_G = 4; //只支持gif
    public static final int POST_TYPE_SUPPORT_WP = 5; //只支持文字、图片
    public static final int POST_TYPE_SUPPORT_WV = 6; //只支持文字、视频
    public static final int POST_TYPE_SUPPORT_WG = 7;//只支持文字、gif
    public static final int POST_TYPE_SUPPORT_PV = 8;//只支持图片、视频
    public static final int POST_TYPE_SUPPORT_PG = 9;//只支持图片、gif
    public static final int POST_TYPE_SUPPORT_VG = 10;//只支持视频、gif
    public static final int POST_TYPE_SUPPORT_WPV = 11;//只支持文字、图片、视频
    public static final int POST_TYPE_SUPPORT_PVG = 12;//只支持图片、视频、gif
}

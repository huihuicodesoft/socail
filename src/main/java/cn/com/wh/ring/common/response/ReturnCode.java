package cn.com.wh.ring.common.response;

/**
 * Created by Hui on 2017/7/18.
 */
public class ReturnCode {
    public static final int OK = 0;
    public static final int ERROR_TOKEN = -1; //token错误，请重新登录
    public static final int ERROR_TOKEN_INVALID = -2; //token失效，请重新登录
    public static final int ERROR_TOKEN_ERROR = -3; //token不正确
    public static final int ERROR_TOKEN_NULL = -4; //token不能为空
    public static final int ERROR_PROGRAM = 1; //程序异常
    public static final int ERROR_DATA_ACCESS = 2; //数据库访问异常
    public static final int ERROR_PERMISSION = 3; //权限异常
    public static final int ERROR_INFO = 4; //信息不正确

    //控制层异常
    public static final int ERROR_FILE_STORAGE = 1001; //文件上传失败
    public static final int ERROR_FILE_UPLOAD_EMPTY = 1002; //上传文件为空
    public static final int ERROR_FILE_UPLOAD_MAX_SIZE = 1003; //上传文件过大
    public static final int ERROR_USER_INFO_NULL = 1004; //上传文件过大

    /**服务层异常**/
    public static final int ERROR_POST_ILLEGAL_MEDIA_PHOTO_NUMBER = 2001; //图片数目不合法
    public static final int ERROR_POST_ILLEGAL_MEDIA_VIDEO_NUMBER = 2002; //视频数目不合法
    public static final int ERROR_PRAISED = 2003; //已经顶过
    public static final int ERROR_CRITICIZED = 2004; //已经踩过

    //登陆注册
    public static final int ERROR_PHONE = 2005; //手机号不正确
    public static final int ERROR_SMS_CODE_INVALID = 2006; //验证码失效
    public static final int ERROR_SMS_CODE = 2007; //验证码错误
    public static final int ERROR_LOGIN = 2008; //登录失败
    public static final int ERROR_MOBILE_EXIST = 2009; //该手机号已注册
    public static final int ERROR_ACCOUNT_PASSWORD = 2011; //账号密码不正确

    //用户模块
    public static final int ERROR_USER_UN_EXIST = 2010; //用户不存在
    public static final int ERROR_USER_LOCKED = 2011; //用户已被锁
    public static final int ERROR_TERMINAL_INFO = 2015; //游客信息不正确
    public static final int ERROR_TERMINAL_INVALID = 2016; //游客被封
    
    public static final int ERROR_POST_ILLEGAL_MEDIA_TYPE = 2013; //媒体类型不合法
    public static final int ERROR_POST_COMMENT_NULL = 2014; //帖子回复不能为空

    public static final int ERROR_USER_BIND_TERMINAL_INVALID = 2017; //用户设备登录无效

    public static final int ERROR_POST_PUBLISHED = 2018; //帖子已经发布
}

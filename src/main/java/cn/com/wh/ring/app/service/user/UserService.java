package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.pojo.UserInfo;
import cn.com.wh.ring.app.bean.request.LoginMobileRequest;
import cn.com.wh.ring.app.bean.request.RegisterMobileRequest;
import cn.com.wh.ring.app.bean.request.LoginThirdRequest;
import cn.com.wh.ring.app.bean.response.LoginUserResponse;
import cn.com.wh.ring.app.bean.response.UserInfoResponse;
import cn.com.wh.ring.app.exception.ServiceException;

/**
 * Created by Hui on 2017/6/14.
 */
public interface UserService {
    /**
     * 手机号注册
     * @param registerMobileRequest
     */
    void registerMobileUser(RegisterMobileRequest registerMobileRequest);

    /**
     * 手机号登录
     * @param mobileAccount
     * @return principal
     */
    LoginUserResponse loginMobileUser(LoginMobileRequest mobileAccount);

    /**
     * 三方登录
     * @param loginThirdRequest
     * @return principal
     */
    LoginUserResponse loginThirdUser(LoginThirdRequest loginThirdRequest);

    /**
     * 修改密码
     * @param registerMobileRequest
     * @return principal
     */
    void updatePassword(RegisterMobileRequest registerMobileRequest);

    /**
     * 验证手机号
     * @param mobile
     */
    UserPojo validUserMobile(String mobile);

    /**
     * 更新用户信息
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserInfoResponse queryUser(Long userId);

    boolean isValid(Long userId) throws ServiceException;
}

package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.User;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.request.RegisterMobile;
import cn.com.wh.ring.app.bean.request.LoginThird;

/**
 * Created by Hui on 2017/6/14.
 */
public interface UserService {
    /**
     * 手机号注册
     * @param registerMobile
     */
    void registerMobileUser(RegisterMobile registerMobile);

    /**
     * 手机号登录
     * @param mobileAccount
     * @return principal
     */
    String loginMobileUser(LoginMobile mobileAccount);

    /**
     * 三方登录
     * @param loginThird
     * @return principal
     */
    String loginThirdUser(LoginThird loginThird);

    /**
     * 修改密码
     * @param registerMobile
     * @return principal
     */
    void updatePassword(RegisterMobile registerMobile);

    /**
     * 验证手机号
     * @param mobile
     */
    User validUserMobile(String mobile);

    cn.com.wh.ring.app.bean.response.User queryUser(Long userId);

    boolean isValid(Long userId);
}

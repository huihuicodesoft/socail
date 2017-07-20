package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.response.User;

/**
 * Created by Hui on 2017/6/14.
 */
public interface UserService {
    /**
     * 根据手机验证创建用户
     * @param loginMobile
     * @return token
     */
    String createUser(LoginMobile loginMobile);

    void updateUserInfo(Long userId, UserInfoPojo userPojo);

    User queryUser(Long userId);

    boolean isValid(Long userId);
}

package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.vo.UserVo;

/**
 * Created by Hui on 2017/6/14.
 */
public interface UserService {
    Long createUser(UserPojo userPojo);

    void updateUserInfo(Long userId, UserInfoPojo userPojo);

    UserVo queryUser(Long userId);
}

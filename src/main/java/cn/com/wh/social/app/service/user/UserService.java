package cn.com.wh.social.app.service.user;

import cn.com.wh.social.app.pojo.User;

/**
 * Created by Hui on 2017/6/14.
 */
public interface UserService {
    User query(Long id);
    Long insert(User user);
}

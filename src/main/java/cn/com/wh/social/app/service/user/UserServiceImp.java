package cn.com.wh.social.app.service.user;

import cn.com.wh.social.app.dao.user.UserDao;
import cn.com.wh.social.app.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/6/14.
 */
@Service
@Transactional
public class UserServiceImp implements UserService{
    @Autowired
    UserDao userDao;

    public User query(Long id) {
        User user = userDao.query(id);
        return user;
    }

    public Long insert(User user) {
        userDao.insert(user);
        return user.getId();
    }
}

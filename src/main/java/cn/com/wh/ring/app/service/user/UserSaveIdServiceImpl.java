package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserSaveId;
import cn.com.wh.ring.app.dao.user.UserSaveIdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hui on 2017/9/21.
 */
@Service
@Transactional
public class UserSaveIdServiceImpl implements UserSaveIdService{
    @Autowired
    UserSaveIdDao userSaveIdDao;

    @Override
    public List<UserSaveId> getAll() {
        return userSaveIdDao.getAll();
    }
}

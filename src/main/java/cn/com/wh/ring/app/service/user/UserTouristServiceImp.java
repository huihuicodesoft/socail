package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.dao.user.UserTouristDao;
import cn.com.wh.ring.app.bean.pojo.UserTouristPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/6/23.
 */
@Service
@Transactional
public class UserTouristServiceImp implements UserTouristService{

    @Autowired
    UserTouristDao userTouristDao;

    public void recordAccessInfo(String terminalMark) {
        UserTouristPojo userTouristPojo = new UserTouristPojo();
        userTouristPojo.setTerminalMark(terminalMark);
        userTouristDao.insertOrUpdate(userTouristPojo);
    }
}

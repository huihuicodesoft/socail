package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.constant.UserConstants;
import cn.com.wh.ring.app.dao.user.UserTouristDao;
import cn.com.wh.ring.app.bean.pojo.UserTouristPojo;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.common.secret.AES;
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

    public String recordAccessInfo(String terminalMark) {
        UserTouristPojo userTouristPojo = new UserTouristPojo();
        userTouristPojo.setTerminalMark(terminalMark);
        userTouristDao.insertOrUpdate(userTouristPojo);
        return TokenHelper.createTerminalToken(terminalMark);
    }

    public boolean isValid(String terminalMark) {
        UserTouristPojo userTouristPojo = userTouristDao.queryByTerminalMark(terminalMark);
        if (userTouristPojo == null){
            return false;
        } else {
            return userTouristPojo.getState() == UserConstants.ACCOUNT_STATE_USEING;
        }
    }
}

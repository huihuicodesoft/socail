package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminal;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.dao.user.UserTerminalDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.common.response.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/9/21.
 */
@Service
@Transactional
public class UserTerminalServiceImpl implements UserTerminalService{
    @Autowired
    UserTerminalDao userTerminalDao;

    @Override
    public void bindUserTerminal(UserTerminal userTerminal) {
        userTerminalDao.updateNoUsingByUserId(userTerminal.getUserId());
        userTerminalDao.insertOrUpdate(userTerminal);
    }

    @Override
    public void valid(long userId, long terminalId) {
        Byte using = userTerminalDao.getUsing(userId, terminalId);
        if (using == null || using == Constants.BOOLEAN_FALSE) {
            throw new ServiceException(ReturnCode.ERROR_USER_BIND_TERMINAL_INVALID, "error_user_bind_terminal_invalid");
        }
    }
}

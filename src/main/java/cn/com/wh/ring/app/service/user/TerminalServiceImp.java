package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.Terminal;
import cn.com.wh.ring.app.bean.pojo.User;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfo;
import cn.com.wh.ring.app.dao.user.TerminalDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.helper.UserHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

/**
 * Created by Hui on 2017/6/23.
 */
@Service
@Transactional
public class TerminalServiceImp implements TerminalService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class.getName());

    @Autowired
    TerminalDao terminalDao;

    public void recordAccessInfo(Terminal terminal) {
        if (terminal == null || Strings.isNullOrEmpty(terminal.getMark())) {
            throw new ServiceException(ReturnCode.ERROR_TERMINAL_INFO, "error_terminal_info");
        } else {
            Terminal dbTerminal = terminalDao.queryByMark(terminal.getMark());
            if (dbTerminal == null || UserHelper.canUse(dbTerminal.getState())) {
                terminalDao.insertOrUpdate(terminal);
            } else {
                logger.error("terminal is not used");
                throw new ServiceException(ReturnCode.ERROR_TERMINAL_INVALID, "error_terminal_invalid");
            }
        }
    }

    @Override
    public void recordDetailInfo(TerminalDetailInfo terminalDetailInfo) {
        if (terminalDetailInfo != null) {
            Terminal terminal = new Terminal();
            terminal.setMark(TokenHelper.getCurrentSubjectMark());
            terminal.setModel(terminalDetailInfo.getModel());
            terminal.setSdk(terminalDetailInfo.getSdk());
            terminal.setSystem(terminalDetailInfo.getSystem());
            terminalDao.insertOrUpdate(terminal);
        }
    }

    @Override
    public Terminal queryByMark(String mark) {
        return terminalDao.queryByMark(mark);
    }

}

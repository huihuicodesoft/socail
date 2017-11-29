package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.Terminal;
import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfo;
import cn.com.wh.ring.app.dao.user.TerminalDao;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.helper.AccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/6/23.
 */
@Service
@Transactional
public class TerminalServiceImp implements TerminalService {
    @Autowired
    TerminalDao terminalDao;

    @Override
    public void recordTerminalDetailInfo(TerminalDetailInfo terminalDetailInfo) {
        terminalDao.updateByUuid(TokenHelper.getCurrentSubjectUuid(), terminalDetailInfo);
    }

    @Override
    public Terminal queryByUuid(String uuid) {
        return terminalDao.queryByUuid(uuid);
    }

    @Override
    public boolean isValid(TerminalPrincipal terminalPrincipal) {
        boolean result = true;
        Terminal dbTerminal = terminalDao.queryByUuid(terminalPrincipal.getUuid());
        if (dbTerminal == null) {
            terminalDao.insert(new Terminal(terminalPrincipal));
        } else {
            result = AccountHelper.canUse(dbTerminal.getState());
        }
        return result;
    }
}

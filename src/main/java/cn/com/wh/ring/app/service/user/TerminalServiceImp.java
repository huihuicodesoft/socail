package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.TerminalPojo;
import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfoRequest;
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
    public void recordTerminalDetailInfo(TerminalDetailInfoRequest terminalDetailInfoRequest) {
        terminalDao.updateByUuid(TokenHelper.getCurrentSubjectUuid(), terminalDetailInfoRequest);
    }

    @Override
    public TerminalPojo queryByUuid(String uuid) {
        return terminalDao.queryByUuid(uuid);
    }

    @Override
    public boolean isValid(TerminalPrincipal terminalPrincipal) {
        boolean result = true;
        TerminalPojo dbTerminalPojo = terminalDao.queryByUuid(terminalPrincipal.getUuid());
        if (dbTerminalPojo == null) {
            terminalDao.insert(new TerminalPojo(terminalPrincipal));
        } else {
            result = AccountHelper.canUse(dbTerminalPojo.getState());
        }
        return result;
    }
}

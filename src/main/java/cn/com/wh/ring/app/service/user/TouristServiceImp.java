package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.Tourist;
import cn.com.wh.ring.app.bean.pojo.User;
import cn.com.wh.ring.app.bean.request.TouristVo;
import cn.com.wh.ring.app.dao.user.TouristDao;
import cn.com.wh.ring.app.helper.TerminalMarkHelper;
import cn.com.wh.ring.app.helper.TokenHelper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/6/23.
 */
@Service
@Transactional
public class TouristServiceImp implements TouristService {

    @Autowired
    TouristDao touristDao;

    public String recordAccessInfo(TouristVo touristVo, int osType) {
        if (touristVo == null || Strings.isNullOrEmpty(touristVo.getTerminalMark())) {
            throw new RuntimeException("tpurist info is not null");
        } else {
            Tourist tourist = new Tourist();
            String terminalMark = TerminalMarkHelper.contact(touristVo.getTerminalMark(), touristVo.getType());
            tourist.setTerminalMark(terminalMark);
            tourist.setOsType(osType);
            touristDao.insertOrUpdate(tourist);
            return TokenHelper.createTerminalToken(terminalMark);
        }
    }

    public boolean isValid(String terminalMark) {
        Tourist tourist = touristDao.queryByTerminalMark(terminalMark);
        if (tourist == null) {
            return false;
        } else {
            return tourist.getState() == User.STATE_USING;
        }
    }
}

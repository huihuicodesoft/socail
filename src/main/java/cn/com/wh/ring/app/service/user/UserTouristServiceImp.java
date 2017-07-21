package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.TouristPojo;
import cn.com.wh.ring.app.bean.request.TouristVo;
import cn.com.wh.ring.app.constant.UserConstants;
import cn.com.wh.ring.app.dao.user.TouristDao;
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
public class UserTouristServiceImp implements UserTouristService {

    @Autowired
    TouristDao userTouristDao;

    public String recordAccessInfo(TouristVo touristVo, int osType) {
        if (touristVo == null || Strings.isNullOrEmpty(touristVo.getTerminalMark())) {
            throw new RuntimeException("tpurist info is not null");
        } else {
            TouristPojo touristPojo = new TouristPojo();
            String terminalMark = touristVo.getTerminalMark() + "+" + touristVo.getType();
            touristPojo.setTerminalMark(terminalMark);
            touristPojo.setOsType(osType);
            userTouristDao.insertOrUpdate(touristPojo);
            return TokenHelper.createTerminalToken(terminalMark);
        }
    }

    public boolean isValid(String terminalMark) {
        TouristPojo userTouristPojo = userTouristDao.queryByTerminalMark(terminalMark);
        if (userTouristPojo == null) {
            return false;
        } else {
            return userTouristPojo.getState() == UserConstants.ACCOUNT_STATE_USEING;
        }
    }
}

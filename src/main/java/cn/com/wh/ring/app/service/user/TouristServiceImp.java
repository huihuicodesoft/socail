package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.Tourist;
import cn.com.wh.ring.app.bean.pojo.User;
import cn.com.wh.ring.app.dao.user.TouristDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.TerminalMarkHelper;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.common.response.ReturnCode;
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

    public void recordAccessInfo(Tourist tourist) {
        if (tourist == null || Strings.isNullOrEmpty(tourist.getTerminalMark())) {
            throw new ServiceException(ReturnCode.ERROR_TOURIST_INFO, "error_tourist_info");
        } else {
            Tourist dbTourist = touristDao.queryByTerminalMark(tourist.getTerminalMark());
            if (dbTourist == null || tourist.getState() == User.STATE_USING) {
                touristDao.insertOrUpdate(tourist);
            } else {
                throw new ServiceException(ReturnCode.ERROR_TOURIST_INVALID, "error_tourist_invalid");
            }
        }
    }
}

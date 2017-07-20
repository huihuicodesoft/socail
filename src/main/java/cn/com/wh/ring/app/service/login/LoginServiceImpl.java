package cn.com.wh.ring.app.service.login;

import cn.com.wh.ring.app.bean.pojo.SmsCodePojo;
import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.constant.UserConstants;
import cn.com.wh.ring.app.dao.sms.SmsCodeDao;
import cn.com.wh.ring.app.dao.user.UserDao;
import cn.com.wh.ring.app.dao.user.UserInfoDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.app.utils.PhoneUtils;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hui on 2017/7/19.
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class.getName());
    private static final int SMS_CODE_TIME = 60 * 1000;
    @Autowired
    SmsCodeDao smsCodeDao;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public String loginMobile(LoginMobile loginMobile) {
        if (loginMobile != null) {
            String mobile = loginMobile.getMobile();
            if (!Strings.isNullOrEmpty(mobile) && PhoneUtils.checkCellphone(mobile)) {
                SmsCodePojo smsCodePojo = smsCodeDao.query(mobile);
                if (smsCodePojo != null
                        && smsCodePojo.getCode().equals(loginMobile.getCode())
                        && System.currentTimeMillis() - smsCodePojo.getUpdateTime().getTime() <= SMS_CODE_TIME) {
                    Long userId = userDao.queryUserId(mobile, UserConstants.ACCOUNT_TYPE_MOBILE);
                    if (userId < 0) {
                        return userService.createUser(loginMobile);
                    } else {
                        return TokenHelper.createUserToken(String.valueOf(userId));
                    }
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_SMS_CODE, "error_sms_code");
                }
            } else {
                throw ServiceException.create(ReturnCode.ERROR_PHONE, "error_phone");
            }
        } else{
            logger.info("loginMobile is null");
            throw ServiceException.create(ReturnCode.ERROR_LOGIN, "error_phone");
        }
    }
}

package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.bean.pojo.SmsCodePojo;
import cn.com.wh.ring.app.bean.request.RegisterMobile;
import cn.com.wh.ring.app.dao.sms.SmsCodeDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.common.response.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Hui on 2017/7/23.
 */
@Component
public class SmsCodeHelper {
    private static final int SMS_CODE_TIME = 60 * 1000;

    @Autowired
    SmsCodeDao smsCodeDao;

    public boolean verification(RegisterMobile mobileAccount) {
        boolean result;
        SmsCodePojo smsCodePojo = smsCodeDao.query(mobileAccount.getMobile());
        if (smsCodePojo == null) {
            throw ServiceException.create(ReturnCode.ERROR_SMS_CODE, "error_sms_code");
        } else {
            if (smsCodePojo.getCode().equals(mobileAccount.getCode())
                    && System.currentTimeMillis() - smsCodePojo.getUpdateTime().getTime() <= SMS_CODE_TIME) {
                 result = true;
            } else {
                throw ServiceException.create(ReturnCode.ERROR_SMS_CODE_INVALID, "error_sms_code_invalid");
            }
        }
        return result;
    }
}

package cn.com.wh.ring.app.service.sms;

import cn.com.wh.ring.app.bean.pojo.SmsCode;
import cn.com.wh.ring.app.dao.sms.SmsCodeDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.utils.PhoneUtils;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/7/19.
 */
@Service
@Transactional
public class SmsServiceImpl implements SmsService {

    @Autowired
    SmsCodeDao smsCodeDao;

    @Override
    public void record(cn.com.wh.ring.app.bean.request.SmsCode smsCode) {
        if (smsCode != null){
            String mobile = smsCode.getMobile();
            if (!Strings.isNullOrEmpty(mobile) && PhoneUtils.checkCellphone(mobile)){
                //发送获取获取验证码
                String code = "111111";
                //存入数据库
                SmsCode smsCodeTemp = new SmsCode();
                smsCodeTemp.setMobile(mobile);
                smsCodeTemp.setCode(code);
                smsCodeDao.insertOrUpdate(smsCodeTemp);
            } else {
                throw ServiceException.create(ReturnCode.ERROR_PHONE, "error_phone");
            }
        }
    }

    @Override
    public SmsCode get(String mobile) {
        return smsCodeDao.query(mobile);
    }
}

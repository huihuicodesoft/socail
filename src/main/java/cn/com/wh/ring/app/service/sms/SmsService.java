package cn.com.wh.ring.app.service.sms;

import cn.com.wh.ring.app.bean.pojo.SmsCode;

/**
 * Created by Hui on 2017/7/19.
 */
public interface SmsService {
    void record(cn.com.wh.ring.app.bean.request.SmsCode smsCode);

    SmsCode get(String mobile);
}

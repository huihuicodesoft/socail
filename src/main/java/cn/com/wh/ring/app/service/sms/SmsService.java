package cn.com.wh.ring.app.service.sms;

import cn.com.wh.ring.app.bean.pojo.SmsCodePojo;
import cn.com.wh.ring.app.bean.request.SmsCodeRequest;

/**
 * Created by Hui on 2017/7/19.
 */
public interface SmsService {
    void record(SmsCodeRequest smsCodeRequest);

    SmsCodePojo get(String mobile);
}

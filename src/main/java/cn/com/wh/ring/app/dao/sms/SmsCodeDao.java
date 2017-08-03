package cn.com.wh.ring.app.dao.sms;

import cn.com.wh.ring.app.bean.pojo.SmsCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/19.
 */
@Repository
public interface SmsCodeDao {
    void insertOrUpdate(SmsCode smsCode);

    SmsCode query(@Param("mobile") String mobile);
}

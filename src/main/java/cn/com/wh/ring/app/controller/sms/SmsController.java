package cn.com.wh.ring.app.controller.sms;

import cn.com.wh.ring.app.bean.request.SmsCode;
import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.service.sms.SmsService;
import cn.com.wh.ring.app.utils.PhoneUtils;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hui on 2017/7/19.
 */
@RestController
@RequestMapping("rest/sms")
public class SmsController {
    @Autowired
    SmsService smsService;

    @PostMapping("v1/code")
    @ApiOperation(value = "获取短信验证码")
    public Response<?> code(@RequestBody SmsCode smsCode){
        smsService.record(smsCode);
        return ResponseHelper.createSuccessResponse();
    }
}

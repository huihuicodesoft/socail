package cn.com.wh.ring.app.controller.sms;

import cn.com.wh.ring.app.bean.request.SmsCodeRequest;
import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.service.sms.SmsService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Hui on 2017/7/19.
 */
@RestController
@RequestMapping("rest/sms")
public class SmsController {
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class.getName());

    @Autowired
    SmsService smsService;

    @PostMapping("v1/code")
    @ApiOperation(value = "获取短信验证码")
    public Response<?> code(@Valid @RequestBody SmsCodeRequest smsCodeRequest, BindingResult result){
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            smsService.record(smsCodeRequest);
            return ResponseHelper.createSuccessResponse();
        }
    }
}

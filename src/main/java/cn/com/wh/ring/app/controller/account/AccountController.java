package cn.com.wh.ring.app.controller.account;

import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.request.RegisterMobile;
import cn.com.wh.ring.app.bean.request.ThirdAccount;
import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
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
 * Created by Hui on 2017/7/23.
 */
@RestController
@RequestMapping("rest/account")
public class AccountController {
    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    UserService userService;

    @PostMapping("v1/register/mobile")
    @ApiOperation(value = "手机注册")
    public Response<?> registerMobile(@Valid @RequestBody RegisterMobile registerMobile, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            String token = userService.registerMobileUser(registerMobile);
            return ResponseHelper.createSuccessResponse(token);
        }
    }

    @PostMapping("v1/login/mobile")
    @ApiOperation(value = "手机登录")
    public Response<?> loginMobile(@Valid @RequestBody LoginMobile loginMobile, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            String token = userService.loginMobileUser(loginMobile);
            return ResponseHelper.createSuccessResponse(token);
        }
    }

    @PostMapping("v1/login/third")
    @ApiOperation(value = "三方登录")
    public Response<?> loginMobile(@Valid @RequestBody ThirdAccount thirdAccount, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            String token = userService.loginThirdUser(thirdAccount);
            return ResponseHelper.createSuccessResponse(token);
        }
    }

    @PostMapping("v1/valid/mobile")
    @ApiOperation(value = "验证手机号")
    public Response<?> loginThird(@RequestBody String mobile, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            userService.validUserMobile(mobile);
            return ResponseHelper.createSuccessResponse();
        }
    }

}
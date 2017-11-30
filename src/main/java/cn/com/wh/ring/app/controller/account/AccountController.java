package cn.com.wh.ring.app.controller.account;

import cn.com.wh.ring.app.bean.request.LoginMobileRequest;
import cn.com.wh.ring.app.bean.request.RegisterMobileRequest;
import cn.com.wh.ring.app.bean.request.LoginThirdRequest;
import cn.com.wh.ring.app.bean.response.LoginUserResponse;
import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.app.utils.PhoneUtils;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

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
    public Response<?> registerMobile(@Valid @RequestBody RegisterMobileRequest registerMobileRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            userService.registerMobileUser(registerMobileRequest);
            return ResponseHelper.createSuccessResponse();
        }
    }

    @PostMapping("v1/login/mobile")
    @ApiOperation(value = "手机登录")
    public Response<?> loginMobile(@Valid @RequestBody LoginMobileRequest loginMobileRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            LoginUserResponse loginUserResponse = userService.loginMobileUser(loginMobileRequest);
            return ResponseHelper.createSuccessResponse(loginUserResponse);
        }
    }

    @PostMapping("v1/login/third")
    @ApiOperation(value = "三方登录")
    public Response<?> loginThird(@Valid @RequestBody LoginThirdRequest loginThirdRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            LoginUserResponse loginUserResponse = userService.loginThirdUser(loginThirdRequest);
            return ResponseHelper.createSuccessResponse(loginUserResponse);
        }
    }

    @PostMapping("v1/valid/mobile")
    @ApiOperation(value = "验证手机号")
    public Response<?> validMobile(@RequestParam("mobile") String mobile) {
        if (!Strings.isNullOrEmpty(mobile) && PhoneUtils.checkCellphone(mobile)) {
            userService.validUserMobile(mobile);
            return ResponseHelper.createSuccessResponse();
        } else {
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        }
    }

    @PostMapping("v1/reset/mobile/password")
    @ApiOperation(value = "重设密码")
    public Response<?> resetMobilePassword(@Valid @RequestBody RegisterMobileRequest registerMobileRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                logger.error(error.getDefaultMessage());
            }
            throw new ResponseException(ReturnCode.ERROR_INFO, "error_info");
        } else {
            userService.updatePassword(registerMobileRequest);
            return ResponseHelper.createSuccessResponse();
        }
    }
}

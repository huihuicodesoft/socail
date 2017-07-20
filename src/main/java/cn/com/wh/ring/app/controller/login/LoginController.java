package cn.com.wh.ring.app.controller.login;

import cn.com.wh.ring.app.bean.request.LoginMobile;
import cn.com.wh.ring.app.bean.request.SmsCode;
import cn.com.wh.ring.app.service.login.LoginService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
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
@RequestMapping("rest/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("v1/mobile")
    @ApiOperation(value = "手机登录")
    public Response<?> code(@RequestBody LoginMobile loginMobile){
        String token = loginService.loginMobile(loginMobile);
        return ResponseHelper.createSuccessResponse(token);
    }
}

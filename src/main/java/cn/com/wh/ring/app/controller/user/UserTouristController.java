package cn.com.wh.ring.app.controller.user;

import cn.com.wh.ring.app.bean.pojo.UserTouristPojo;
import cn.com.wh.ring.app.service.user.UserTouristService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hui on 2017/7/10.
 */
@RestController
@RequestMapping("rest/userTourist")
@Api("游客用户控制器")
public class UserTouristController {
    @Autowired
    UserTouristService userTouristService;

    @PostMapping("v1/record")
    @ApiOperation(value = "记录设备登录")
    public Response<?> addUser(@RequestBody UserTouristPojo userTouristPojo) {
        String token = userTouristService.recordAccessInfo(userTouristPojo.getTerminalMark());
        System.out.println("token =" + token);
        return ResponseHelper.createSuccessResponse(token);
    }
}

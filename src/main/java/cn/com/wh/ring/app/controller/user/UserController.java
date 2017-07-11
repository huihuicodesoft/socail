package cn.com.wh.ring.app.controller.user;

import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.vo.UserVo;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hui on 2017/6/14.
 */
@RestController
@RequestMapping("rest/users")
@Api("用户控制器")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("v1/{userId}")
    @ApiOperation(value = "获取用户信息")
    public Response<?> getUser(@PathVariable("userId") Long userId){
        return ResponseHelper.createSuccessResponse(userService.queryUser(userId));
    }

    @GetMapping("v1")
    @ApiOperation(value = "添加用户")
    public Response<?> addUser(@RequestBody UserPojo userPojo){
        String token = userService.createUser(userPojo);
        return ResponseHelper.createSuccessResponse(token);
    }
}

//package cn.com.wh.ring.app.controller.user;
//
//import cn.com.wh.ring.app.bean.pojo.UserInfoPojo;
//import cn.com.wh.ring.app.service.user.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Created by Hui on 2017/6/14.
// */
//@RestController
//@RequestMapping("rest/users")
//@Api("用户控制器")
//public class UserController {
//    @Autowired
//    UserService userService;
//
//    @GetMapping("v1/{id}")
//    @ApiOperation(value = "获取用户信息")
//    public UserInfoPojo getUser(@PathVariable("id") Long id){
//        return userService.query(id);
//    }
////
////    @PostMapping("v1")
////    @ApiOperation(value = "添加用户")
////    public Long insertUser(@RequestBody UserInfoPojo user){
////        return userService.insert(user);
////    }
//}

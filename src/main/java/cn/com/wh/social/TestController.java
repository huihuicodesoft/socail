package cn.com.wh.social;

import org.springframework.web.bind.annotation.*;

/**
 * Created by Hui on 2017/6/9.
 */
@RestController
@RequestMapping("rest/test")
public class TestController {
    @GetMapping
    public String test(){
        return "index";
    }

    @GetMapping("user")
    public User user(){
        User user = new User();
        user.setName("wanghui");
        user.setAge(24);
        return user;
    }

    @PostMapping("user")
    public String user(@RequestBody User user){
        return user.getName();
    }

}

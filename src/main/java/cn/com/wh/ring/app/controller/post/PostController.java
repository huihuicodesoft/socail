package cn.com.wh.ring.app.controller.post;

import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.service.post.PostService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hui on 2017/7/17.
 */
@RestController
@RequestMapping("rest/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("v1/publish")
    public Response<?> publish(@RequestBody PostPublish postPublish) {
        Long id = postService.publish(postPublish);
        return ResponseHelper.createSuccessResponse(id);
    }
}

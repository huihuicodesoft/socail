package cn.com.wh.ring.app.controller.post;

import cn.com.wh.ring.app.bean.request.PageRequest;
import cn.com.wh.ring.app.bean.request.PostPublishRequest;
import cn.com.wh.ring.app.bean.response.PageResponse;
import cn.com.wh.ring.app.bean.response.PostResponse;
import cn.com.wh.ring.app.constant.PermissionConstants;
import cn.com.wh.ring.app.service.post.PostService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hui on 2017/7/17.
 */
@RestController
@RequestMapping("rest/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("v1/publish")
    @RequiresPermissions(PermissionConstants.PERMISSION_POST_PUBLISH)
    public Response<?> publish(@RequestBody PostPublishRequest postPublishRequest) {
        Long id = postService.publish(postPublishRequest);
        return ResponseHelper.createSuccessResponse(id);
    }

    @GetMapping("v1/user/page")
    @RequiresPermissions(PermissionConstants.PERMISSION_USER_INFO)
    public Response<?> queryByUserId(@RequestParam("userId") Long userId, @ModelAttribute PageRequest pageRequest) {
        PageResponse<PostResponse> response = postService.queryByUserId(userId, pageRequest);
        return ResponseHelper.createSuccessResponse(response);
    }

    @GetMapping("v1/page")
    public Response<?> query(@ModelAttribute PageRequest pageRequest) {
        PageResponse<PostResponse> response = postService.query(pageRequest);
        return ResponseHelper.createSuccessResponse(response);
    }
}

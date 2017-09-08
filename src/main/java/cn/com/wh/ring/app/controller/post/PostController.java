package cn.com.wh.ring.app.controller.post;

import cn.com.wh.ring.app.bean.request.Page;
import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.constant.PermissionConstants;
import cn.com.wh.ring.app.service.post.PostService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    public Response<?> publish(@RequestBody PostPublish postPublish) {
        Long id = postService.publish(postPublish);
        return ResponseHelper.createSuccessResponse(id);
    }

    @GetMapping("v1/user/page")
    @RequiresPermissions(PermissionConstants.PERMISSION_USER_INFO)
    public Response<?> publish(@RequestParam(value = "userId", required = false) Long userId, @ModelAttribute Page page) {
        cn.com.wh.ring.app.bean.response.Page<cn.com.wh.ring.app.bean.response.Post> response = postService.queryByUserId(userId, page);
        return ResponseHelper.createSuccessResponse(response);
    }
}

package cn.com.wh.ring.app.controller.post;

import cn.com.wh.ring.app.bean.pojo.PostType;
import cn.com.wh.ring.app.bean.response.Page;
import cn.com.wh.ring.app.constant.PermissionConstants;
import cn.com.wh.ring.app.service.post.PostTypeService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hui on 2017/8/1.
 */
@RestController
@RequestMapping("rest/postType")
public class PostTypeController {
    @Autowired
    PostTypeService postTypeService;

    @GetMapping("v1/page")
    @RequiresPermissions(PermissionConstants.PERMISSION_POST_TYPE_GET)
    public Response<?> query(@RequestParam("maxId") Long maxId,
                             @RequestParam("pageNum") int pageNum,
                             @RequestParam("pageSize") int pageSize) {
        Page<PostType> page = postTypeService.query(maxId, pageNum, pageSize);
        return ResponseHelper.createSuccessResponse(page);
    }
}

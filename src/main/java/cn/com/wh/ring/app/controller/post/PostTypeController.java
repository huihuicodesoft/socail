package cn.com.wh.ring.app.controller.post;

import cn.com.wh.ring.app.bean.pojo.PostTypePojo;
import cn.com.wh.ring.app.bean.request.PageRequest;
import cn.com.wh.ring.app.bean.response.PageResponse;
import cn.com.wh.ring.app.service.post.PostTypeService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
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
    public Response<?> query(@ModelAttribute PageRequest pageRequest) {
        PageResponse<PostTypePojo> response = postTypeService.query(pageRequest);
        return ResponseHelper.createSuccessResponse(response);
    }
}

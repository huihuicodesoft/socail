package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostTypePojo;
import cn.com.wh.ring.app.bean.request.PageRequest;
import cn.com.wh.ring.app.bean.response.PageResponse;

/**
 * Created by Hui on 2017/8/1.
 */
public interface PostTypeService {
    PageResponse<PostTypePojo> query(PageRequest pageRequest);
}

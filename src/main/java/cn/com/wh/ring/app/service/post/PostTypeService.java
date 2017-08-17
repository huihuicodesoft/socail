package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostType;
import cn.com.wh.ring.app.bean.response.Page;

/**
 * Created by Hui on 2017/8/1.
 */
public interface PostTypeService {
    Page<PostType> query(cn.com.wh.ring.app.bean.request.Page page);
}

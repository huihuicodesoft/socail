package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.response.Page;
import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.bean.request.Report;
import cn.com.wh.ring.app.bean.response.Post;

/**
 * Created by Hui on 2017/7/17.
 */
public interface PostService {
    Long publish(PostPublish postPublish);

    Page<Post> queryByUserId(Long userId, cn.com.wh.ring.app.bean.request.Page page);

    void praise(Long id);

    void criticize(Long id);

    void report(Long id, Report report);
}

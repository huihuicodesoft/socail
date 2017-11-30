package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.request.PageRequest;
import cn.com.wh.ring.app.bean.response.PageResponse;
import cn.com.wh.ring.app.bean.request.PostPublishRequest;
import cn.com.wh.ring.app.bean.request.ReportRequest;
import cn.com.wh.ring.app.bean.response.PostResponse;

/**
 * Created by Hui on 2017/7/17.
 */
public interface PostService {
    Long publish(PostPublishRequest postPublishRequest);

    PageResponse<PostResponse> queryByUserId(Long userId, PageRequest pageRequest);

    PageResponse<PostResponse> query(PageRequest pageRequest);

    void praise(Long id);

    void criticize(Long id);

    void report(Long id, ReportRequest reportRequest);
}

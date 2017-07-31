package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.bean.request.Report;

/**
 * Created by Hui on 2017/7/17.
 */
public interface PostService {
    Long publish(PostPublish postPublish);

    void praise(Long id);

    void criticize(Long id);

    void report(Long id, Report report);
}

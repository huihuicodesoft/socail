package cn.com.wh.ring.app.service.comment;

/**
 * Created by Hui on 2017/8/3.
 */
public interface CommentService {
    Long comment(Long postId, String comment);

    void praise(Long id);

    void criticize(Long id);
}

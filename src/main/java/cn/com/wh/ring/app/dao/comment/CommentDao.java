package cn.com.wh.ring.app.dao.comment;

import cn.com.wh.ring.app.bean.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/8/3.
 */
@Repository
public interface CommentDao {
    void insert(Comment postComment);

    void increasePraiseNumber(@Param("id") Long id);

    void increaseCriticizeNumber(@Param("id") Long id);

    void increaseReportNumber(@Param("id") Long id);

    List<Comment> queryOrderByTime(@Param("id") Long id);

    List<Comment> queryOrderByPraise(@Param("id") Long id);
}

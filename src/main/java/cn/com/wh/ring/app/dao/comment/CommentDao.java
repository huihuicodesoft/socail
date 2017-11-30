package cn.com.wh.ring.app.dao.comment;

import cn.com.wh.ring.app.bean.pojo.CommentPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/8/3.
 */
@Repository
public interface CommentDao {
    void insert(CommentPojo postCommentPojo);

    void increasePraiseNumber(@Param("id") Long id);

    void increaseCriticizeNumber(@Param("id") Long id);

    void increaseReportNumber(@Param("id") Long id);

    List<CommentPojo> queryOrderByTime(@Param("id") Long id);

    List<CommentPojo> queryOrderByPraise(@Param("id") Long id);
}

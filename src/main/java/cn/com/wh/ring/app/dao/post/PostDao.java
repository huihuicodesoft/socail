package cn.com.wh.ring.app.dao.post;

import cn.com.wh.ring.app.bean.pojo.PostPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/7/17.
 */
@Repository
public interface PostDao {
    void insert(PostPojo postPojo);

    void increasePraiseNumber(@Param("id") Long id);

    void increaseCriticizeNumber(@Param("id") Long id);

    void increaseCommentNumber(@Param("id") Long id);

    void increaseReportNumber(@Param("id") Long id);

    PostPojo queryById(@Param("id") Long id);

    List<PostPojo> queryByUserId(@Param("userId") Long userId, @Param("maxId") Long maxId);

    List<PostPojo> queryByState(@Param("state") Byte state, @Param("maxId") Long maxId);
}

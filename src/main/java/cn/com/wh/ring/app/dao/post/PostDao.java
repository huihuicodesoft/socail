package cn.com.wh.ring.app.dao.post;

import cn.com.wh.ring.app.bean.pojo.PostPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/17.
 */
@Repository
public interface PostDao {
    void insert(PostPojo postPojo);

    void increasePraiseNumber(@Param("id") Long id);

    void increaseCriticizeNumber(@Param("id") Long id);

    void increaseReportNumber(@Param("id") Long id);

    PostPojo queryById(@Param("id") Long id);
}

package cn.com.wh.ring.app.dao.praise;

import cn.com.wh.ring.app.bean.pojo.PraisePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/18.
 */
@Repository
public interface PraiseDao {
    void insert(PraisePojo praisePojo);

    PraisePojo query(@Param("postId") Long postId, @Param("mark") String mark);
}

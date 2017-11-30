package cn.com.wh.ring.app.dao.evaluate;

import cn.com.wh.ring.app.bean.pojo.EvaluatePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/18.
 */
@Repository
public interface EvaluateDao {
    void insert(EvaluatePojo evaluatePojo);

    EvaluatePojo query(@Param("hostId") Long postId, @Param("hostType") int hostType,
                       @Param("mark") String mark, @Param("markType") int markType);
}

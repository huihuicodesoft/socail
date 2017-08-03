package cn.com.wh.ring.app.dao.post;

import cn.com.wh.ring.app.bean.pojo.PostType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/7/31.
 */
@Repository
public interface PostTypeDao {
    List<PostType> query(@Param("maxId") Long maxId, @Param("state") int state);
}

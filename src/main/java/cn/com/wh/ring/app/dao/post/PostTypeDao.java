package cn.com.wh.ring.app.dao.post;

import cn.com.wh.ring.app.bean.pojo.PostTypePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/7/31.
 */
@Repository
public interface PostTypeDao {
    List<PostTypePojo> query(@Param("maxId") Long maxId, @Param("state") int state);
}

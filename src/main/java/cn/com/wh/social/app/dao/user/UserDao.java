package cn.com.wh.social.app.dao.user;

import cn.com.wh.social.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/14.
 */
@Repository
public interface UserDao {
    void insert(User user);
    User query(@Param("id") Long id);
}

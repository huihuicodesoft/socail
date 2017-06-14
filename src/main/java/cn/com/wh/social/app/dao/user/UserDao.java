package cn.com.wh.social.app.dao.user;

import cn.com.wh.social.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Hui on 2017/6/14.
 */
@Mapper
public interface UserDao {
    void insert(User user);
    User query(@Param("id") Long id);
}

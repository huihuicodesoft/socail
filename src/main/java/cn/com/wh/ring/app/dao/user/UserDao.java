package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/23.
 */
@Repository
public interface UserDao {
    void insert(UserPojo userPojo);

    void updateBindInfo(@Param("userId") Long userId, UserPojo userPojo);

    void updateState(@Param("userId") Long userId, int state);

    Long queryMaxUserId();

    Long queryUserId(@Param("account") String account, @Param("accountType")int accountType);
}

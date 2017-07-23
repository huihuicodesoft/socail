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

    void updatePassword(@Param("account") String account, @Param("accountType") int accountType, @Param("password") String password);

    void updateToken(UserPojo userPojo);

    void updateBindAccount(@Param("userId") Long userId, @Param("account") String account);

    void updateState(@Param("userId") Long userId, @Param("state") int state);

    Long queryMaxUserId();

    UserPojo queryByAccount(@Param("account") String account, @Param("accountType") int accountType);

    UserPojo queryByBinAccount(@Param("account") String account);

    UserPojo queryByUserId(@Param("userId") Long userId);
}

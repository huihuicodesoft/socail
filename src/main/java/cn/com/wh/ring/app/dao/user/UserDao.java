package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/23.
 */
@Repository
public interface UserDao {
    void insert(User user);

    void updatePassword(@Param("account") String account, @Param("accountType") byte accountType, @Param("password") String password);

    void updateToken(User user);

    void updateBindAccount(@Param("userId") Long userId, @Param("account") String account);

    void updateState(@Param("userId") Long userId, @Param("state") int state);

    Long queryMaxUserId();

    User queryByAccount(@Param("account") String account, @Param("accountType") byte accountType);

    User queryByBindAccount(@Param("account") String account);

    User queryByUserId(@Param("userId") Long userId);
}

package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminalPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/19.
 */
@Repository
public interface UserTerminalDao {
    void insertOrUpdate(UserTerminalPojo userTerminalPojo);

    void updateNoUsingByUserId(@Param("userId") long userId);

    Byte getUsing(@Param("userId") long userId, @Param("terminalId") long terminalId);
}

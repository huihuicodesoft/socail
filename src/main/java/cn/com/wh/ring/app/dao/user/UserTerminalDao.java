package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminal;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/19.
 */
@Repository
public interface UserTerminalDao {
    void insertOrUpdate(UserTerminal userTerminal);

    void updateNoUsingByUserId(long userId);

    Byte getUsing(long userId, long terminalId);
}

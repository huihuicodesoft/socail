package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminal;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/19.
 */
@Repository
public interface UserTerminalDao {
    void insert(UserTerminal userTerminal);

    Long queryId(UserTerminal userTerminal);
}

package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminalPojo;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/19.
 */
@Repository
public interface UserTerminalDao {
    void insert(UserTerminalPojo userTerminalPojo);

    Long queryId(UserTerminalPojo userTerminalPojo);
}

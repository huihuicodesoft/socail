package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.Terminal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/22.
 */
@Repository
public interface TerminalDao {
    void insertOrUpdate(Terminal terminal);

    Terminal queryByMark(@Param("mark") String mark);
}

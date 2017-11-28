package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.Terminal;
import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/22.
 */
@Repository
public interface TerminalDao {
    void insert(Terminal terminal);

    void updateByUuid(@Param("uuid")String uuid, @Param("terminalDetailInfo") TerminalDetailInfo terminalDetailInfo);

    Terminal queryByUuid(@Param("uuid") String uuid);
}

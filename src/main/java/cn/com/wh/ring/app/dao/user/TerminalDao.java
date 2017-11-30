package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.TerminalPojo;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfoRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/22.
 */
@Repository
public interface TerminalDao {
    void insert(TerminalPojo terminalPojo);

    void updateByUuid(@Param("uuid")String uuid, @Param("terminalDetailInfoRequest") TerminalDetailInfoRequest terminalDetailInfoRequest);

    TerminalPojo queryByUuid(@Param("uuid") String uuid);
}

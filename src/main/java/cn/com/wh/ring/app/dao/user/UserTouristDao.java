package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserPojo;
import cn.com.wh.ring.app.bean.pojo.UserTouristPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/22.
 */
@Repository
public interface UserTouristDao {
    /**
     * 根据terminalMark，如果存在就更新，如果不存在就插入
     * @param userTouristPojo
     */
    void insertOrUpdate(UserTouristPojo userTouristPojo);

    /**
     * 更新账号状态
     * @param terminalMark
     * @param state
     * 账号状态
     * 0 : 正在使用（默认）
     * 1 : 废弃
     * 2 ：锁定
     */
    void updateState(@Param("terminalMark") String terminalMark, @Param("state") int state);

    UserTouristPojo queryByTerminalMark(@Param("terminalMark") String terminalMark);
}

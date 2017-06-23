package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserTouristPojo;
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
}

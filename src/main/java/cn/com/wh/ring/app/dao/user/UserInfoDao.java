package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/6/22.
 */
@Repository
public interface UserInfoDao {
    void insert(UserInfo userInfo);

    void update(@Param("id") Long id, UserInfo userInfo);

    UserInfo queryById(@Param("id") Long id);
}

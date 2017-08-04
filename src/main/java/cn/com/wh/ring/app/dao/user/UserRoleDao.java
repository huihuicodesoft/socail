package cn.com.wh.ring.app.dao.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/8/4.
 */
@Repository
public interface UserRoleDao {
    void insert(@Param("userId") Long userId, @Param("roleId") int roleId);
}

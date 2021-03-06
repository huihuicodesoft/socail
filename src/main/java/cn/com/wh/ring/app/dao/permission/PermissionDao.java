package cn.com.wh.ring.app.dao.permission;

import cn.com.wh.ring.app.bean.pojo.PermissionPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/7/18.
 */
@Repository
public interface PermissionDao {
    List<PermissionPojo> queryByUserId(@Param("userId") Long userId);
}

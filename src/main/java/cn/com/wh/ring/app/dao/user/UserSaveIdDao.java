package cn.com.wh.ring.app.dao.user;

import cn.com.wh.ring.app.bean.pojo.UserSaveIdPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hui on 2017/6/20.
 */
@Repository
public interface UserSaveIdDao {
    List<UserSaveIdPojo> getAll();
}

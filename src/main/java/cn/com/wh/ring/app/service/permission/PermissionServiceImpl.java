package cn.com.wh.ring.app.service.permission;

import cn.com.wh.ring.app.bean.pojo.Permission;
import cn.com.wh.ring.app.dao.permission.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hui on 2017/7/18.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    PermissionDao permissionDao;

    @Override
    public List<Permission> getPermissionsOfUser(Long userId) {
        return permissionDao.queryByUserId(userId);
    }
}

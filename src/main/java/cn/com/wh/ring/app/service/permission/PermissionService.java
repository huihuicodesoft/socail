package cn.com.wh.ring.app.service.permission;

import cn.com.wh.ring.app.bean.pojo.Permission;

import java.util.List;

/**
 * Created by Hui on 2017/7/18.
 */
public interface PermissionService {
    List<Permission> getPermissionsOfUser(Long userId);
}

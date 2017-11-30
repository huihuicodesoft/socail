package cn.com.wh.ring.app.service.permission;

import cn.com.wh.ring.app.bean.pojo.PermissionPojo;

import java.util.List;

/**
 * Created by Hui on 2017/7/18.
 */
public interface PermissionService {
    List<PermissionPojo> getPermissionsOfUser(Long userId);
}

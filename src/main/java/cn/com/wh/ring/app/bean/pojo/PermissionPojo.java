package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

/**
 * Created by Hui on 2017/7/18.
 */
@Alias("PermissionPojo")
public class PermissionPojo {
    private Long id;
    private String description;
    private String permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}

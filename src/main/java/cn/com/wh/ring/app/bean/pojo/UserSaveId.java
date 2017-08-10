package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/6/20.
 */
@Alias("UserSaveId")
public class UserSaveId {
    private Long id;
    private Long saveUserId;
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaveUserId() {
        return saveUserId;
    }

    public void setSaveUserId(Long saveUserId) {
        this.saveUserId = saveUserId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

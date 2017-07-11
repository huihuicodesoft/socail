package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/6/20.
 */
@Alias("UserSaveIdPojo")
public class UserSaveIdPojo {
    private Long id;
    private Long saveUserId;
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

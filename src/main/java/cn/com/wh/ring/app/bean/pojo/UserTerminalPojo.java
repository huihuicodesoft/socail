package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

/**
 * Created by Hui on 2017/7/19.
 */
@Alias("UserTerminalPojo")
public class UserTerminalPojo {
    private Long id;
    private Long userId;
    private String ternimalMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTernimalMark() {
        return ternimalMark;
    }

    public void setTernimalMark(String ternimalMark) {
        this.ternimalMark = ternimalMark;
    }
}

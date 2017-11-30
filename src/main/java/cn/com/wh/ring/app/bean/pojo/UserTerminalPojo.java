package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

/**
 * Created by Hui on 2017/7/19.
 */
@Alias("UserTerminalPojo")
public class UserTerminalPojo {
    private Long id;
    private Long userId;
    private Long terminalId;
    private Byte using;

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

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Byte getUsing() {
        return using;
    }

    public void setUsing(Byte using) {
        this.using = using;
    }
}

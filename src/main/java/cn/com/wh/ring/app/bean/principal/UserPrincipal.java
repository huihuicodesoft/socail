package cn.com.wh.ring.app.bean.principal;

import java.io.Serializable;

/**
 * Created by Hui on 2017/9/22.
 */
public class UserPrincipal implements Serializable{
    private Long userId;
    private Long time;
    private Long terminalId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }
}

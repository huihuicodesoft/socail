package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

/**
 * Created by Hui on 2017/7/19.
 */
@Alias("UserTerminal")
public class UserTerminal {
    private Long id;
    private Long userId;
    private String terminalMark;

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

    public String getTerminalMark() {
        return terminalMark;
    }

    public void setTerminalMark(String terminalMark) {
        this.terminalMark = terminalMark;
    }
}

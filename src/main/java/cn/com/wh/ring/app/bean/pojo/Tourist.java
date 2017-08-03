package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/6/22.
 */
@Alias("Tourist")
public class Tourist {
    private Long id;
    private String terminalMark;
    private int osType; //{OS_ANDROID, OS_IOS}
    private Date lastTime;
    private int count;
    private Date createTime;
    private int state;//账号状态 0 : 正在使用（默认）1 : 废弃 2：锁定

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalMark() {
        return terminalMark;
    }

    public void setTerminalMark(String terminalMark) {
        this.terminalMark = terminalMark;
    }

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

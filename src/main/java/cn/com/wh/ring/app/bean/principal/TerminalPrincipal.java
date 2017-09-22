package cn.com.wh.ring.app.bean.principal;

import java.io.Serializable;

/**
 * Created by Hui on 2017/9/22.
 */
public class TerminalPrincipal implements Serializable{
    private String mark;
    private Byte osType;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Byte getOsType() {
        return osType;
    }

    public void setOsType(Byte osType) {
        this.osType = osType;
    }
}

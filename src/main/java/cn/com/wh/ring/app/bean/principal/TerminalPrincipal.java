package cn.com.wh.ring.app.bean.principal;

import java.io.Serializable;

/**
 * Created by Hui on 2017/9/22.
 */
public class TerminalPrincipal implements Serializable{
    private String imei;
    private String mac;
    private String uuid;
    private Byte osType;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Byte getOsType() {
        return osType;
    }

    public void setOsType(Byte osType) {
        this.osType = osType;
    }
}

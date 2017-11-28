package cn.com.wh.ring.app.bean.pojo;

import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/6/22.
 */
@Alias("Terminal")
public class Terminal {
    private Long id;
    private String uuid;
    private String imei;
    private String mac;
    private Byte osType; //{1：ANDROID; 2：IOS；9：未知}
    private String model;
    private String sdk;
    private String system;
    private Date creationTime;
    private Byte state;//账号状态 1 : 正在使用（默认）2 : 废弃 3：锁定

    public Terminal() {
    }

    public Terminal(TerminalPrincipal terminalPrincipal) {
        this.uuid = terminalPrincipal.getUuid();
        this.imei = terminalPrincipal.getImei();
        this.mac = terminalPrincipal.getMac();
        this.osType = terminalPrincipal.getOsType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public Byte getOsType() {
        return osType;
    }

    public void setOsType(Byte osType) {
        this.osType = osType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}

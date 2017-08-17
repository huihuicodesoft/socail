package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/7/31.
 */
@Alias("PostType")
public class PostType {
    public static final byte SUPPORT_ALL = 1; //全被格式
    public static final byte SUPPORT_W = 2; //只支持文字
    public static final byte SUPPORT_P = 3; //[文字] + 图片
    public static final byte SUPPORT_V = 4; //[文字] + 视频
    public static final byte SUPPORT_G = 5; //[文字] + gif
    public static final byte SUPPORT_WP = 6; // 2/3
    public static final byte SUPPORT_WV = 7; // 2/4
    public static final byte SUPPORT_WG = 8;// 2/5
    public static final byte SUPPORT_PV = 9;// 3/4
    public static final byte SUPPORT_PG = 10;// 3/5
    public static final byte SUPPORT_VG = 11;// 4/5
    public static final byte SUPPORT_WPV = 12;// 2/3/4
    public static final byte SUPPORT_PVG = 13;// 3/4/5

    private Long id;
    private String name;
    private String description;
    private String symbol;
    private byte support;
    private byte isDeleted;
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public byte getSupport() {
        return support;
    }

    public void setSupport(byte support) {
        this.support = support;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/7/18.
 */
@Alias("EvaluatePojo")
public class EvaluatePojo {
    public static final byte HOST_TYPE_POST = 1;//帖子点赞
    public static final byte HOST_TYPE_COMMENT = 2;//回复点赞

    public static final byte TYPE_PRAISE = 1;//点赞
    public static final byte TYPE_CRITICIZED = 2;//踩

    private Long id;
    private Long hostId;
    private Byte hostType;
    private String mark;
    private Integer markType;
    private Byte type;
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Byte getHostType() {
        return hostType;
    }

    public void setHostType(Byte hostType) {
        this.hostType = hostType;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getMarkType() {
        return markType;
    }

    public void setMarkType(Integer markType) {
        this.markType = markType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

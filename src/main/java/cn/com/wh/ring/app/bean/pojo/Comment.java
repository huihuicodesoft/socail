package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/8/3.
 */
@Alias("Comment")
public class Comment {
    public static final byte HOST_POST = 1;//帖子回复

    private Long id;
    private Long hostId;
    private Byte hostType;
    private Long userId;
    private String content;
    private Integer praiseNumber;
    private Integer criticizeNumber;
    private Integer reportNumber;
    private Byte isDeleted;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(Integer praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public Integer getCriticizeNumber() {
        return criticizeNumber;
    }

    public void setCriticizeNumber(Integer criticizeNumber) {
        this.criticizeNumber = criticizeNumber;
    }

    public Integer getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(Integer reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

package cn.com.wh.ring.app.bean.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by Hui on 2017/7/17.
 */
@Alias("PostPojo")
public class PostPojo {
    public static final byte MEDIA_TYPE_PHOTO = 1; //图片
    public static final byte MEDIA_TYPE_VIDEO = 2; //视频

    /**
     * 帖子状态值
     * 1：审核中（默认值）
     * 2：审核失败
     * 3：审核成功
     * 4：用户删除
     */
    public static final byte STATE_CHECKING = 1;
    public static final byte STATE_CHECK_FAIL = 2;
    public static final byte STATE_CHECK_SUCCESS = 3;
    public static final byte STATE_DELETE = 4;

    private Long id;
    private Long userId;
    private String description;
    private String mediaContent;
    private Integer postTypeId;
    private Integer praiseNumber;
    private Integer criticizeNumber;
    private Integer commentNumber;
    private Integer reportNumber;
    private Byte anonymous; //{0,1}
    private Long addressId;
    private Double lng;
    private Double lat;
    private Byte state;
    private Date creationTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(String mediaContent) {
        this.mediaContent = mediaContent;
    }

    public Integer getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Integer postTypeId) {
        this.postTypeId = postTypeId;
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

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Integer getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(Integer reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Byte getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Byte anonymous) {
        this.anonymous = anonymous;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

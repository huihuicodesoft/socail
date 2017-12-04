package cn.com.wh.ring.app.bean.response;

import java.util.Date;
import java.util.List;

/**
 * Created by Hui on 2017/8/17.
 */
public class PostResponse {
     class PostType {
        public PostType(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        private Long id;
        private String name;

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
    }

    private Long id;
    private Long userId;
    private String description;
    private List<String> mediaList;
    private PostType postType;
    private String region;
    private int praiseNumber;
    private int criticizeNumber;
    private int commentNumber;
    private int reportNumber;
    private int state;
    private boolean anonymous;
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

    public List<String> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<String> mediaList) {
        this.mediaList = mediaList;
    }

    public void setPostType(long id, String name) {
        this.postType = new PostType(id, name);
    }

    public PostType getPostType() {
        return postType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public int getCriticizeNumber() {
        return criticizeNumber;
    }

    public void setCriticizeNumber(int criticizeNumber) {
        this.criticizeNumber = criticizeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

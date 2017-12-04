package cn.com.wh.ring.app.bean.request;

import java.util.List;

/**
 * Created by Hui on 2017/7/17.
 */
public class PostPublishRequest {
    private String uuid;
    private String description;
    private List<String> mediaContent;
    private AddressRequest address;
    private int postType;
    private boolean anonymous;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(List<String> mediaContent) {
        this.mediaContent = mediaContent;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}

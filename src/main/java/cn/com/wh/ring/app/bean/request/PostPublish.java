package cn.com.wh.ring.app.bean.request;

import java.util.List;

/**
 * Created by Hui on 2017/7/17.
 */
public class PostPublish {
    private String description;
    private List<String> mediaContent;
    private int mediaType;
    private String addressCode;
    private int type;
    private boolean anonymous;

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

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}

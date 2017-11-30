package cn.com.wh.ring.app.bean.request;

/**
 * Created by Hui on 2017/8/17.
 */
public class PageRequest {
    private long maxId;
    private int pageNumber;
    private int pageSize;

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

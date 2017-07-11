package cn.com.wh.ring.app.service.user;

/**
 * Created by Hui on 2017/6/23.
 */
public interface UserTouristService {
    /**
     * 记录访问记录，如果没有就插入，有的话就更新时间
     * @param terminalMark
     * @return token
     */
    String recordAccessInfo(String terminalMark);

    boolean isValid(String terminalMark);
}

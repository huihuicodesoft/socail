package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.request.TouristVo;

/**
 * Created by Hui on 2017/6/23.
 */
public interface TouristService {
    /**
     * 记录访问记录，如果没有就插入，有的话就更新时间
     * @param touristVo
     * @return token
     */
    String recordAccessInfo(TouristVo touristVo, int osType);

    boolean isValid(String terminalMark);
}

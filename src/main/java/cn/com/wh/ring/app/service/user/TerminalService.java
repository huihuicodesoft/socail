package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.Terminal;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfo;

/**
 * Created by Hui on 2017/6/23.
 */
public interface TerminalService {
    /**
     * 记录访问记录，如果没有就插入，有的话就更新时间
     * @param terminal
     */
    void recordAccessInfo(Terminal terminal);

    /**
     * 记录设备的详细信息
     * @param terminalDetailInfo
     */
    void recordDetailInfo(TerminalDetailInfo terminalDetailInfo);

    /**
     *  根据设备标识获取设备信息
     * @param mark
     */
    Terminal queryByMark(String mark);
}

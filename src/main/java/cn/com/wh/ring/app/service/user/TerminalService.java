package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.TerminalPojo;
import cn.com.wh.ring.app.bean.principal.TerminalPrincipal;
import cn.com.wh.ring.app.bean.request.TerminalDetailInfoRequest;

/**
 * Created by Hui on 2017/6/23.
 */
public interface TerminalService {
    /**
     * 记录设备的详细信息
     * @param terminalDetailInfoRequest
     */
    void recordTerminalDetailInfo(TerminalDetailInfoRequest terminalDetailInfoRequest);

    /**
     * 根据设备标识获取设备信息
     * @param uuid
     */
    TerminalPojo queryByUuid(String uuid);

    /**
     * 记录或者验证有效性
     * @param terminalPrincipal
     */
    boolean isValid(TerminalPrincipal terminalPrincipal);
}

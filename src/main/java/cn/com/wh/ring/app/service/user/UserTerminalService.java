package cn.com.wh.ring.app.service.user;

import cn.com.wh.ring.app.bean.pojo.UserTerminal;

/**
 * Created by Hui on 2017/9/21.
 */
public interface UserTerminalService {
    /**
     * 用户单点登录，绑定设备
     *
     * @param userTerminal
     */
    void bindUserTerminal(UserTerminal userTerminal);


    /**
     * 校验用户和设备信息
     *
     * @param userId
     * @param terminalId
     */
    void valid(long userId, long terminalId);
}

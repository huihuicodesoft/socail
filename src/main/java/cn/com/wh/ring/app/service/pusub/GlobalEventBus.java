package cn.com.wh.ring.app.service.pusub;

import com.google.common.eventbus.EventBus;

import javax.annotation.PostConstruct;

/**
 * Created by Hui on 2017/11/14.
 */
public class GlobalEventBus extends EventBus {
    public GlobalEventBus() {
        super(GlobalEventBus.class.getSimpleName());
    }

    @PostConstruct
    private void init() {
        register(new DeadEventHandler(this));
        register(new EventLogger(this));
    }
}

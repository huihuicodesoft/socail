package cn.com.wh.ring.app.service.pusub;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

/**
 * Created by Hui on 2017/11/14.
 */
@Service
public class GlobalAsyEventBus extends AsyncEventBus {
    public GlobalAsyEventBus() {
        super(GlobalAsyEventBus.class.getSimpleName(), Executors.newCachedThreadPool());
    }

    @PostConstruct
    private void init() {
        register(new DeadEventHandler(this));
        register(new EventLogger(this));
    }
}

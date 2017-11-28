package cn.com.wh.ring.app.service.pusub;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jason on 16/11/30.
 */
public class DeadEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeadEventHandler.class);
    private EventBus source;
    public DeadEventHandler(EventBus source) {
        this.source = source;
    }
    @Subscribe
    @AllowConcurrentEvents
    public void handleDeadEvent(DeadEvent deadEvent) {
        logger.warn("DeadEvent found in {}. {}", source.identifier(), deadEvent.getEvent());
    }
}

package cn.com.wh.ring.app.service.pusub;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jason on 16/11/30.
 */
public class EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLogger.class);
    private EventBus source;

    public EventLogger(EventBus source) {
        this.source = source;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void logEvent(Object event) {
        logger.debug("logEvent from {}, {}", source.identifier(), event);
    }
}

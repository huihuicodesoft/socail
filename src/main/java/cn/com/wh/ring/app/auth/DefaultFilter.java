package cn.com.wh.ring.app.auth;

import org.apache.shiro.util.ClassUtils;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Hui on 2017/7/7.
 */
public enum DefaultFilter {
    getToken(TokenFilter.class);

    private final Class<? extends Filter> filterClass;

    private DefaultFilter(Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }

    public Filter newInstance() {
        return (Filter) ClassUtils.newInstance(this.filterClass);
    }

    public Class<? extends Filter> getFilterClass() {
        return this.filterClass;
    }

    public static Map<String, Filter> createInstanceMap(FilterConfig config) {
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>(values().length);
        for (DefaultFilter defaultFilter : values()) {
            Filter filter = defaultFilter.newInstance();
            if (config != null) {
                try {
                    filter.init(config);
                } catch (ServletException e) {
                    String msg = "Unable to correctly init default filter instance of type " +
                            filter.getClass().getName();
                    throw new IllegalStateException(msg, e);
                }
            }
            filters.put(defaultFilter.name(), filter);
        }
        return filters;
    }
}

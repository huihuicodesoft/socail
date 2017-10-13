package cn.com.wh.ring.app.auth;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Hui on 2017/7/6.
 */
public class ShiroFilterManager {
    public static Map<String, Filter> getFilters() {
        return DefaultFilter.createInstanceMap(null);
    }

    //先后顺序很重要，会拦截
    public static Map<String, String> getFilterChainDefinition() {
        LinkedHashMap<String, String> definitionsMap = new LinkedHashMap<String, String>();
        definitionsMap.put("/rest/**", "getToken");
        return definitionsMap;
    }
}

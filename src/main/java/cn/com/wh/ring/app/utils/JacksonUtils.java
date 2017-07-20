package cn.com.wh.ring.app.utils;

import cn.com.wh.ring.app.helper.ObjectMapperHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json字符与对像转换
 * Created by Hui on 2017/7/17.
 */
public class JacksonUtils {
    public static ObjectMapper objectMapper;

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) throws Exception {
        if (objectMapper == null) {
            objectMapper = ObjectMapperHolder.getInstance().getMapper();
        }

        return objectMapper.readValue(jsonStr, valueType);
    }

    /**
     * json数组转List
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) throws Exception {
        if (objectMapper == null) {
            objectMapper = ObjectMapperHolder.getInstance().getMapper();
        }

        return objectMapper.readValue(jsonStr, valueTypeRef);
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSon(Object object) throws Exception {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper.writeValueAsString(object);
    }

}

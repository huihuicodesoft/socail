package cn.com.wh.ring.app.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Hui on 2017/7/10.
 */
public class ObjectMapperHolder {
    private static ObjectMapperHolder instance = new ObjectMapperHolder();
    private ObjectMapper mapper;
    private ObjectMapperHolder(){
        mapper = createMapper();
    }

    public static ObjectMapperHolder getInstance() {
        return instance;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public ObjectMapper getNewMapper() {
        //对于Spring, 单例无法工作
        return createMapper();
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
}

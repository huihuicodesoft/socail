package cn.com.wh.ring.common.response;

/**
 * Created by Hui on 2017/7/10.
 */
public class ResponseHelper {
    public static <T> Response<T> createSuccessResponse(){
        return createSuccessResponse(null);
    }

    public static <T> Response<T> createSuccessResponse(T payload){
        Response<T>  response = new Response<T>();
        response.setCode(ResponseCode.OK);
        response.setDescription(null);
        response.setPayload(payload);
        return response;
    }

    public static <T> Response<T> createResponse(int code, String description, T payload){
        Response<T>  response = new Response<T>();
        response.setCode(code);
        response.setDescription(description);
        response.setPayload(payload);
        return response;
    }
}

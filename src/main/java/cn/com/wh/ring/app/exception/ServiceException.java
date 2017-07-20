package cn.com.wh.ring.app.exception;

/**
 * Created by Hui on 2017/7/18.
 * 服务层异常
 */
public class ServiceException extends RuntimeException{
    private String message;
    private Integer code;

    /**
     * Construction Method
     * @param code
     * @param message
     */
    public ServiceException(Integer code, String message){
        this.message = message;
        this.code = code;
    }

    public static ServiceException create(Integer code, String message){
        return new ServiceException(code, message);
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}

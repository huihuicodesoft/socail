package cn.com.wh.ring.app.exception;

/**
 * Created by Hui on 2017/7/12.
 * 控制层异常
 */
public class ResponseException extends RuntimeException{
    private String message;
    private Integer code;

    /**
     * Construction Method
     * @param code
     * @param message
     */
    public ResponseException(Integer code, String message){
        this.message = message;
        this.code = code;
    }

    public static ResponseException create(Integer code, String message){
        return new ResponseException(code, message);
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}

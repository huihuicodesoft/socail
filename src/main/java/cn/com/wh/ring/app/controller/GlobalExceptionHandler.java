package cn.com.wh.ring.app.controller;

import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.common.response.ReturnCode;
import cn.com.wh.ring.app.helper.MessageResourceHelper;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.NoPermissionException;
import java.util.Locale;

/**
 * Created by Hui on 2017/7/12.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response<?> handleException(ResponseException exception) {
        String message = MessageResourceHelper.getInstance().getMessage(exception.getMessage(), Locale.SIMPLIFIED_CHINESE);
        return ResponseHelper.createResponse(exception.getCode(), message);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response<?> handleException(ServiceException exception) {
        String message = MessageResourceHelper.getInstance().getMessage(exception.getMessage(), Locale.SIMPLIFIED_CHINESE);
        return ResponseHelper.createResponse(exception.getCode(), message);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response<?> handleException(DataAccessException exception) {
        String message = MessageResourceHelper.getInstance().getMessage("error_data_access", Locale.SIMPLIFIED_CHINESE);
        return ResponseHelper.createResponse(ReturnCode.ERROR_DATA_ACCESS, message + exception.getClass().getSimpleName());
    }

    @ExceptionHandler({AuthorizationException.class, NoPermissionException.class})
    @ResponseStatus(HttpStatus.OK)
    public Response<?> handleException(Exception exception) {
        return ResponseHelper.createResponse(ReturnCode.ERROR_PERMISSION, exception.getMessage());
    }

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Response<?> exception(Exception exception) {
        String desc = exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage();
        return ResponseHelper.createResponse(ReturnCode.ERROR_PROGRAM, desc);
    }

}

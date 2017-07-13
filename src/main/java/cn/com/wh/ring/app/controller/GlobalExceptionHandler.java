package cn.com.wh.ring.app.controller;

import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.helper.MessageResourceHelper;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * Created by Hui on 2017/7/12.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResponseException.class})
    public Response<?> handleException(ResponseException responseException) {
        String message = MessageResourceHelper.getInstance().getMessage(responseException.getMessage(), Locale.SIMPLIFIED_CHINESE);
        return ResponseHelper.createResponse(responseException.getCode(), message);
    }
}

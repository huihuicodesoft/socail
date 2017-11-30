package cn.com.wh.ring.app.controller;

import cn.com.wh.ring.app.bean.request.PageRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Hui on 2017/8/17.
 */
@RestControllerAdvice
public class ModelHandler {
    @ModelAttribute("page")
    public PageRequest initPage(@RequestParam(value = "maxId", required = false) Long maxId,
                                @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (maxId == null || pageNumber == null || pageSize == null) {
            return null;
        }
        PageRequest pageRequest = new PageRequest();
        pageRequest.setMaxId(maxId);
        pageRequest.setPageNumber(pageNumber);
        pageRequest.setPageSize(pageSize);
        return pageRequest;
    }
}

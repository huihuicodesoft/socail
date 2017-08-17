package cn.com.wh.ring.app.controller;

import cn.com.wh.ring.app.bean.request.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Hui on 2017/8/17.
 */
@RestControllerAdvice
public class ModelHandler {
    @ModelAttribute("page")
    public Page initPage(@RequestParam("maxId") long maxId,
                         @RequestParam("pageNumber") int pageNumber,
                         @RequestParam("pageSize") int pageSize) {
        Page page = new Page();
        page.setMaxId(maxId);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }
}

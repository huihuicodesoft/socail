package cn.com.wh.ring.app.controller.upload;

import cn.com.wh.ring.app.exception.StorageException;
import cn.com.wh.ring.app.helper.MessageResourceHelper;
import cn.com.wh.ring.app.service.storage.StorageService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseCode;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

/**
 * Created by Hui on 2017/7/11.
 */
@RestController
@RequestMapping("rest/files")
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("v1/upload")
    public Response<?> fileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return ResponseHelper.createSuccessResponse();
    }

    @ExceptionHandler(StorageException.class)
    public Response<?> storageFileNotFound(StorageException exc) {
        String message = MessageResourceHelper.getInstance().getMessage("error_file_storage", Locale.SIMPLIFIED_CHINESE);
        return ResponseHelper.createResponse(ResponseCode.ERROR_FILE_STORAGE, message);
    }

}

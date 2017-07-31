package cn.com.wh.ring.app.controller.upload;

import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.common.response.ReturnCode;
import cn.com.wh.ring.app.service.storage.StorageService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hui on 2017/7/11.
 */
@RestController
@RequestMapping("rest/files")
public class FileUploadController {
    @Value("${upload.perMaxSize}")
    int PER_MAX_SIZE;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("v1/image/upload")
    public Response<?> fileUpload(@RequestParam("file") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw ResponseException.create(ReturnCode.ERROR_FILE_UPLOAD_EMPTY, "error_file_upload_empty");
        } else {
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() > PER_MAX_SIZE) {
                    throw ResponseException.create(ReturnCode.ERROR_FILE_UPLOAD_MAX_SIZE, "error_file_upload_max_size");
                } else {
                    try {
                        fileNames.add(storageService.store(file));
                    } catch (Exception e) {
                        throw ResponseException.create(ReturnCode.ERROR_FILE_STORAGE, "error_file_storage");
                    }
                }
            }
            return ResponseHelper.createSuccessResponse(fileNames);
        }
    }
}

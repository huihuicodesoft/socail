package cn.com.wh.ring.app.controller.user;

import cn.com.wh.ring.app.bean.pojo.UserInfo;
import cn.com.wh.ring.app.constant.PermissionConstants;
import cn.com.wh.ring.app.exception.ResponseException;
import cn.com.wh.ring.app.helper.FileHelper;
import cn.com.wh.ring.app.service.storage.STORAGE_TYPE;
import cn.com.wh.ring.app.service.storage.StorageService;
import cn.com.wh.ring.app.service.user.UserService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Hui on 2017/6/14.
 */
@RestController
@RequestMapping("rest/users")
@Api("用户控制器")
public class UserController {
    @Value("${upload.perMaxSize}")
    int PER_MAX_SIZE;

    @Autowired
    UserService userService;

    @Autowired
    FileHelper fileHelper;

    @Autowired
    @Qualifier("storageServiceImpl")
    StorageService storageService;

    @GetMapping("v1/userInfo/{userId}")
    @ApiOperation(value = "获取用户信息")
    @RequiresPermissions(PermissionConstants.PERMISSION_USER_INFO)
    public Response<?> getUserInfo(@PathVariable("userId") Long userId) {
        return ResponseHelper.createSuccessResponse(userService.queryUser(userId));
    }

    @PostMapping("v1/avatar/upload")
    @ApiOperation(value = "上传头像")
    @RequiresPermissions(PermissionConstants.PERMISSION_UPLOAD_AVATAR)
    public Response<?> fileUploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            throw ResponseException.create(ReturnCode.ERROR_FILE_UPLOAD_EMPTY, "error_file_upload_empty");
        } else {
            if (file.getSize() > PER_MAX_SIZE) {
                throw ResponseException.create(ReturnCode.ERROR_FILE_UPLOAD_MAX_SIZE, "error_file_upload_max_size");
            } else {
                try {
                    String fileName = storageService.store(file, STORAGE_TYPE.AVATAR);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setAvatar(fileName);
                    userService.updateUserInfo(userInfo);
                    String url = fileHelper.getFileAvatarUrl(fileName);
                    return ResponseHelper.createSuccessResponse(url);
                } catch (Exception e) {
                    throw ResponseException.create(ReturnCode.ERROR_FILE_STORAGE, "error_file_storage");
                }
            }
        }
    }

    @PostMapping("v1/userInfo/update")
    @ApiOperation(value = "更新用户信息")
    @RequiresPermissions(PermissionConstants.PERMISSION_USER_INFO)
    public Response<?> updateUserInfo(@RequestBody UserInfo userInfo) {
        if (userInfo == null) {
            throw ResponseException.create(ReturnCode.ERROR_USER_INFO_NULL, "error_user_info_null");
        } else {
            userService.updateUserInfo(userInfo);
        }
        return ResponseHelper.createSuccessResponse();
    }
}

package cn.com.wh.ring.app.service.storage;

import cn.com.wh.ring.app.exception.StorageException;
import cn.com.wh.ring.app.helper.FileHelper;
import cn.com.wh.ring.app.helper.StorageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${upload.image.location}")
    String UPLOAD_IMAGE_LOCATION;

    @Value("${upload.avatar.location}")
    String UPLOAD_AVATAR_LOCATION;

    @Autowired
    FileHelper fileHelper;

    private Path rootFilePath;
    private String rootFileName;

    @Override
    public String store(MultipartFile file, STORAGE_TYPE storageType) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
        }
        String path = getPathByType(storageType);
        rootFileName = fileHelper.generateFileName(file.getOriginalFilename());
        rootFilePath = StorageHelper.getPath(path, rootFileName);
        try {
            Files.copy(file.getInputStream(), rootFilePath);
            return rootFileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename());
        }
    }

    private String getPathByType(STORAGE_TYPE storageType) {
        if (STORAGE_TYPE.IMAGE.equals(storageType)) {
            return UPLOAD_IMAGE_LOCATION;
        } else if (STORAGE_TYPE.AVATAR.equals(storageType)) {
            return UPLOAD_AVATAR_LOCATION;
        } else {
            throw new StorageException("storageType is not support");
        }
    }
}

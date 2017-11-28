package cn.com.wh.ring.app.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file, STORAGE_TYPE storageType);
}

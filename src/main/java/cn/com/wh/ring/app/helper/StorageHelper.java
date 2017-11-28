package cn.com.wh.ring.app.helper;

import cn.com.wh.ring.app.exception.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Hui on 2017/11/14.
 */
public class StorageHelper {
    public static Path getPath(String pathStr, String fileName) {
        Path path = Paths.get(pathStr);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            return path.resolve(fileName);
        } catch (IOException e) {
            throw new StorageException("Could not create storage path", e);
        }
    }
}

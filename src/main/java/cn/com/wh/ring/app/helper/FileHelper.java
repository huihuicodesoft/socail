package cn.com.wh.ring.app.helper;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by Hui on 2017/7/12.
 */
@Component
public final class FileHelper {
    @Value("${file.image.url.prefix}")
    String FILE_IMAGE_URL_PREFIX;
    @Value("${file.avatar.url.prefix}")
    String FILE_AVATAR_URL_PREFIX;

    /**
     * 根据文件名拼接Image的url
     *
     * @param fileName
     * @return
     */
    public String getFileImageUrl(String fileName) {
        return getFileUrl(FILE_IMAGE_URL_PREFIX, fileName);
    }

    /**
     * 根据文件名拼接avatar的url
     *
     * @param fileName
     * @return
     */
    public String getFileAvatarUrl(String fileName) {
        return getFileUrl(FILE_AVATAR_URL_PREFIX, fileName);
    }

    private String getFileUrl(String dir, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dir).append(fileName);
        return stringBuilder.toString();
    }

    /**
     * 根据原始文件名，生成新的名字
     *
     * @param originFileName
     * @return
     */
    public String generateFileName(String originFileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = originFileName.substring(originFileName.indexOf("."), originFileName.length());
        return uuid + suffix;
    }

    /**
     * a.png或path/a.png 获得 png
     *
     * @param path
     * @return
     */
    public String getExtensionName(String path) {
        String extension = null;
        if ((path != null) && (path.length() > 0)) {
            int dot = path.lastIndexOf('.');
            if ((dot > -1) && (dot < (path.length() - 1))) {
                extension = path.substring(dot + 1);
            }
        }
        return extension;
    }

    public boolean isPhoto(String path) {
        boolean result = false;
        String extension = getExtensionName(path);
        if (!Strings.isNullOrEmpty(extension)) {
            result = "png".equals(extension) || "jpg".equals(extension) || "jpeg".equals(extension);
        }
        return result;
    }

    public boolean isGif(String path) {
        boolean result = false;
        String extension = getExtensionName(path);
        if (!Strings.isNullOrEmpty(extension)) {
            result = "gif".equals(extension);
        }
        return result;
    }

    public boolean isVideo(String path) {
        boolean result = false;
        String extension = getExtensionName(path);
        if (!Strings.isNullOrEmpty(extension)) {
            result = "avi".equals(extension) || "wmv".equals(extension) || "mp4".equals(extension) || "flv".equals(extension);
        }
        return result;
    }
}

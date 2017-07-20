package cn.com.wh.ring.app.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Hui on 2017/7/12.
 */
@Component
public final class FileHelper {
    @Value("${file.url.prefix}")
    String FILE_URL_PREFIX;

    /**
     * 根据文件名拼接url
     * @param fileName
     * @return
     */
    public String getFileUrl(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FILE_URL_PREFIX).append(fileName);
        return stringBuilder.toString();
    }

    /**
     * 根据原始文件名，生成新的名字
     * @param originFileName
     * @return
     */
    public String generateFileName(String originFileName){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = originFileName.substring(originFileName.indexOf("."), originFileName.length());
        return uuid + suffix;
    }
}

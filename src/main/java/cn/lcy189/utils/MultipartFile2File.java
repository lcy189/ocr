package cn.lcy189.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * TODO MultipartFile 转 File 工具类。
 *
 */

@Component
public class MultipartFile2File {

    public File multipartFile2File(MultipartFile multipartFile) {

        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", ".xlsx");
            tempFile.deleteOnExit(); // 当JVM退出时删除临时文件

            try (OutputStream os = new FileOutputStream(tempFile)) {
                os.write(multipartFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert MultipartFile to File", e);
        }
        return tempFile;
    }

}

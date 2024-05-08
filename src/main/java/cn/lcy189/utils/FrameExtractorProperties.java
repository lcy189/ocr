package cn.lcy189.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO 视频处理配置项，读取application.yml内的video.frame-extractor。
 * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
 *
 */

@Data
@Component
@ConfigurationProperties(prefix = "video.frame-extractor")

public class FrameExtractorProperties {

    //视频路径
    private String videoFramesPath;
    //抽帧间隔
    private Integer interval;
    //降重阈值
    private Double threshold;

}

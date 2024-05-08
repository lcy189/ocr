package cn.lcy189.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.librealsense.frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * TODO：（将视频提取成帧图片）
 *
 */
@Slf4j
@Component
public class FrameExtractor {

    @Autowired
    private FrameExtractorProperties frameExtractorProperties;


    /**
     * TODO 将视频文件帧处理并以“jpg”格式进行存储。
     * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
     *
     */

    public void grabberVideoFramer(File videoPath) {

        String videoFramesPath = frameExtractorProperties.getVideoFramesPath();
        Integer interval = frameExtractorProperties.getInterval();

        File directory = new File(videoFramesPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                log.info("============无法创建视频存放文件路径============");
            }
        }

        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        /*
            获取视频文件
         */

        try {
            try (FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath)) {
                fFmpegFrameGrabber.start();
            /*
            .getFrameRate()方法：获取视频文件信息,总帧数
             */
                int ftp = fFmpegFrameGrabber.getLengthInFrames();

                log.info("============视频时长 " + ftp / fFmpegFrameGrabber.getFrameRate() + "s============");

                BufferedImage bImage = null;
                log.info("============开始运行视频提取帧，耗时较长============");

                while (flag <= ftp) {
                    //文件绝对路径+名字
                    String fileName = videoFramesPath + "/img_" + String.valueOf(flag) + ".jpg";
                    //文件储存对象
                    File outPut = new File(fileName);
                    //获取帧
                    frame = fFmpegFrameGrabber.grabImage();
                    //                System.out.println(frame);
                    if (frame != null) {
                        ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
                    }

                    //每interval帧捕获一次
                    flag += interval;
                }
                log.info("============视频抽帧运行结束============");
                fFmpegFrameGrabber.stop();
            }
        } catch (IOException E) {
            log.info("============视频抽帧失败============");
        }
    }


    private static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        return converter.getBufferedImage(frame);
    }


}
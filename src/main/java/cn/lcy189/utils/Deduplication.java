package cn.lcy189.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Deduplication {

    @Autowired
    private FrameExtractorProperties frameExtractorProperties;

    public void Deduplicate() {

        String folder = frameExtractorProperties.getVideoFramesPath();
        double threshold = frameExtractorProperties.getThreshold();

        Delete_sim.delete_sim(folder, threshold);
        log.info("============视频降重完毕，路径：" + folder + "============");
    }


}
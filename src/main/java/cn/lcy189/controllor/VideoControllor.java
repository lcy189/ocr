package cn.lcy189.controllor;

import cn.lcy189.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VideoControllor {

    @Autowired
    private VideoService videoService;

    @PostMapping("/video")
    public void videoprocess(MultipartFile video){
        System.out.println(video);
        videoService.frameExtractor(video);

    }


}

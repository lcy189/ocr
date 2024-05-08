package cn.lcy189.service.impl;

import cn.lcy189.service.VideoService;
import cn.lcy189.utils.Deduplication;
import cn.lcy189.utils.FrameExtractor;
import cn.lcy189.utils.MultipartFile2File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private FrameExtractor frameExtractor;

    @Autowired
    private MultipartFile2File multipartFile2File;

    @Autowired
    private Deduplication deduplication;

    @Override
    public void frameExtractor(MultipartFile multipartFile) {

        File file = multipartFile2File.multipartFile2File(multipartFile);

        //图片抽帧
        frameExtractor.grabberVideoFramer(file);
        //图片去重
        deduplication.Deduplicate();


    }
}

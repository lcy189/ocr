package cn.lcy189.service;


import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    void frameExtractor(MultipartFile multipartFile);

}

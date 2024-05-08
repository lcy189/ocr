package cn.lcy189.utils;

import static org.bytedeco.opencv.global.opencv_core.not;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * TODO：去重工具类
 *
 */
public class Delete_sim {
    // ori_path：图片源文件夹，threshold：相似度阈值
    public static String delete_sim(String ori_path, double threshold) {

        // 图片源文件夹
        Path basePath = Paths.get(ori_path);

        // 获取文件名
        File folder = new File(ori_path); // 替换为你的文件夹路径
        String[] listOfFiles = folder.list();

        String[] imageExtensions = {"jpeg", "jpg", "png", "gif"};

        // 文件夹为空，直接返回
        if (listOfFiles == null) {
            System.out.println("The directory is empty or it does not exist.");
            return ori_path;
        }

        // 文件名排序
        Arrays.sort(listOfFiles);


        // 挨个对比相邻两张图片，如果相似度大于阈值，删掉前一张，然后继续对比
        for (int i = 0; i < listOfFiles.length-1; i++) {
            String path1 = basePath.resolve(listOfFiles[i]).toString();
            String path2 = basePath.resolve(listOfFiles[i+1]).toString();

            // 获取文件的后缀名
            String fileExtension1 = path1.substring(path1.lastIndexOf(".") + 1);
            String fileExtension2 = path2.substring(path2.lastIndexOf(".") + 1);

            // 判断后缀名是否为图片格式
            boolean isImage1 = Arrays.asList(imageExtensions).contains(fileExtension1.toLowerCase());
            boolean isImage2 = Arrays.asList(imageExtensions).contains(fileExtension2.toLowerCase());

            if (!isImage1 || !isImage2){
                continue;
            }
            // 获取相似度
            // double similarity = Img_similarity.Img_similarity(path1, path2);
            double ssimsimilarity = Ssim_similarity.ssim(path1, path2);
            if (ssimsimilarity > threshold){
                try {
                    Files.delete(Paths.get(path1));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                continue;
            }
            // System.out.println(similarity);
            // System.out.println(ssimsimilarity);
        }
        // 原地删图片，返回相同源文件夹
        return ori_path;
    }
}

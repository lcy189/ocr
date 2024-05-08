package cn.lcy189.utils;

import org.bytedeco.opencv.opencv_core.*;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * TODO：相似度计算
 *
 */

public class Ssim_similarity {

    public static double ssim(String img_path1, String img_path2){

        Mat i1 = imread(img_path1, IMREAD_COLOR);

        Mat i2 = imread(img_path2, IMREAD_COLOR);
        Mat i1_new = new Mat();
        Mat i2_new = new Mat();

        Size imgsize = new Size(512, 512);
        resize(i1, i1_new, imgsize);
        resize(i2, i2_new, imgsize);

        double C1 = 6.5025, C2 = 58.5225;
        int d = CV_32F;
        Mat I1 = new Mat();
        Mat I2 = new Mat();
        i1_new.convertTo(I1, d);
        i2_new.convertTo(I2, d);
        Mat I1_2 = I1.mul(I1).asMat();
        Mat I2_2 = I2.mul(I2).asMat();
        Mat I1_I2 = I1.mul(I2).asMat();
        Mat mu1 = new Mat();
        Mat mu2 = new Mat();
        Size size = new Size(11,11);
        GaussianBlur(I1, mu1, size, 1.5);
        GaussianBlur(I2, mu2, size, 1.5);
        Mat mu1_2 = mu1.mul(mu1).asMat();
        Mat mu2_2 = mu2.mul(mu2).asMat();
        Mat mu1_mu2 = mu1.mul(mu2).asMat();

        Mat sigma1_2 = new Mat();
        Mat sigam2_2 = new Mat();
        Mat sigam12 = new Mat();
        GaussianBlur(I1_2, sigma1_2, size, 1.5);
        sigma1_2 = subtract(sigma1_2, mu1_2).asMat();
        // sigma1_2 = sigma1_2 - mu1_2;

        GaussianBlur(I2_2, sigam2_2, size, 1.5);
        sigam2_2 = subtract(sigam2_2, mu2_2).asMat();
        // sigam2_2 -= mu2_2;

        GaussianBlur(I1_I2, sigam12, size, 1.5);
        sigam12 = subtract(sigam12, mu1_mu2).asMat();
        // sigam12 -= mu1_mu2;
        Mat t1, t2, t3;
        t1 = add(multiply(mu1_mu2, 2), new Scalar(C1)).asMat();
        // t1 = 2 * mu1_mu2 + C1;
        t2 = add(multiply(sigam12, 2), new Scalar(C2)).asMat();
        // t2 = 2 * sigam12 + C2;
        t3 = t1.mul(t2).asMat();

        t1 = add(add(mu1_2, mu2_2), new Scalar(C1)).asMat();
        // t1 = mu1_2 + mu2_2 + C1;
        t2 = add(add(sigma1_2, sigam2_2), new Scalar(C2)).asMat();
        // t2 = sigma1_2 + sigam2_2 + C2;
        t1 = t1.mul(t2).asMat();

        Mat ssim_map = new Mat();
        divide(t3, t1, ssim_map);
        Scalar mssim = mean(ssim_map);

        double ssim = (mssim.get(0) + mssim.get(1) + mssim.get(2)) /3;

        i1 = null;
        i2 = null;
        i1_new = null;
        i2_new = null;
        I1_2 = null;
        I2_2 = null;
        imgsize = null;
        I1 = null;
        I2 = null;
        I1_I2 = null;
        mu1 = null;
        mu2 = null;
        mu1_2 = null;
        mu2_2 = null;
        mu1_mu2 = null;
        sigma1_2 = null;
        sigam2_2 = null;
        sigam12 = null;
        t1 = null;
        t2 = null;
        t3 = null;
        ssim_map = null;
        System.gc();
        return ssim;
    }


}

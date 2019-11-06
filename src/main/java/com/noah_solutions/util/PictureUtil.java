package com.noah_solutions.util;


import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PictureUtil {
    public static void reduceImg(File srcfile, File imgdist, int widthdist,
                                 int heightdist) {
        try {
            Thumbnails.of(srcfile)
                    .size(widthdist, heightdist)
                    .toFile(imgdist);
//            if (!srcfile.exists()) {
//                return;
//            }
//
//            if (!imgdist.exists()) {
//                imgdist.createNewFile();
//            }
//            Image src = javax.imageio.ImageIO.read(srcfile);
//
//            BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist,
//                    BufferedImage.TYPE_INT_RGB);
//
//            tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_SMOOTH), 0, 0,  null);
////        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_AREA_AVERAGING), 0, 0,  null);
//
//            FileOutputStream out = new FileOutputStream(imgdist);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(tag);
//            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void reduceImg(String srcfile, String imgdist, int widthdist,
                                 int heightdist) {
        try {
            Thumbnails.of(srcfile).size(widthdist, heightdist).toFile(imgdist);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 截取视频第六帧的图片
     * @param filePath 视频路径
     * @return 图片的相对路径 例：pic/1.png
     * @throws FrameGrabber.Exception
     */
    public static String videoImage(String filePath,String dirPath) throws FrameGrabber.Exception {
        String pngPath = "";
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);

        ff.start();
        int ffLength = ff.getLengthInFrames();
        org.bytedeco.javacv.Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            //截取第6帧
            if((i>5) &&  (f.image != null)){
                //执行截图并放入指定位置
                doExecuteFrame(f, dirPath);
                break;
            }
            i++;
        }
        ff.stop();

        return pngPath;
    }



    /**
     * 截取缩略图
     * @param f Frame
     * @param targerFilePath:封面图片存放路径
     */
    private static void doExecuteFrame(org.bytedeco.javacv.Frame f, String targerFilePath) {
        String imagemat = "png";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targerFilePath);
        try {
            ImageIO.write(bi, imagemat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

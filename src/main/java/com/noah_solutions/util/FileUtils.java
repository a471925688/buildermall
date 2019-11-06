package com.noah_solutions.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {
    public static String  copyFile(String oldPath, String newPath) throws Exception{
        File oldfile = new File(oldPath);
        File newFile = new File(newPath);
        try {
            int bytesum = 0;
            int byteread = 0;

            if (oldfile.exists()) { //文件存在时
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                InputStream inStream = new FileInputStream(oldfile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024*10];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                oldfile.delete();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return newFile.getPath();
    }
    public static void  copyFile(InputStream inStream,  String filePath) throws Exception{
        File file = new File(filePath);
        try {
            int bytesum = 0;
            int byteread = 0;

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fs = new FileOutputStream(file);
                byte[] buffer = new byte[1024*10];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

}

package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.ImageView;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.TbImage;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.util.PictureUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.noah_solutions.common.ProjectConfig.HOST_NAME;
import static com.noah_solutions.common.ProjectConfig.UPLOAD_PATH_LAYEDIT;

/**
 *
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {
    /*
     * 图片命名格式
     */
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmss";


    /*
     * 上传图片文件夹
     */
//    /var/lib/tomcat8/webapps/order_Image

    /*
     * 上传单张图片
     */
    @RequestMapping(value = "/uploadImages")
    public JSONObject uplodaImg(@RequestParam(value = "uploadFile",required = false) MultipartFile uploadFile,
                          HttpServletRequest request)
    {
        try {
            if (uploadFile == null) {
                uploadFile = requestMultipartFile(request).get(0);
            }
            String fileName = uploadFile.getOriginalFilename();
            if(fileName.contains("/")){
                fileName = fileName.substring(fileName.lastIndexOf("/"));
            }

            DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
//            File file = new File(ResourceUtils.getURL("classpath:").getPath()+UPLOAD_PATH + fileName);
            File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY , fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            uploadFile.transferTo(file);
            return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                    .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                    .fluentPut("fileName",fileName)
                    .fluentPut("url", "../file/fileDown?fileName=" +fileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject().fluentPut("code", CodeAndMsg.ERRORUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.ERRORUPLOAD.getMsg());
    }

    /*
     * 上传图片
     */
    @RequestMapping(value = "/uploadAllImages")
    public JSONObject uploadAllImages(@RequestParam(value = "files",required = false) MultipartFile[] files, HttpServletRequest request)throws Exception {
            if (files == null) {
                List<MultipartFile> list = requestMultipartFile(request);
                files = new MultipartFile[list.size()];
                list.toArray(files);
            }
            List<String> oldFileNames=new ArrayList<>();
            List<String> path=new ArrayList<>();
            List<String> fileNames=new ArrayList<>();
            for (MultipartFile file1:files){
                String fileName = file1.getOriginalFilename();
                oldFileNames.add(fileName);
                DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
                fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
                File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY , fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                file1.transferTo(file);
//                PictureUtil.reduceImg(ProjectConfig.FILE_TEMPORARY_DIRECTORY +fileName,ProjectConfig.FILE_TEMPORARY_DIRECTORY +"thumbnail-"+fileName,179,160);
                path.add(fileName);
                fileNames.add(fileName);
            }
        return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                .fluentPut("oldFileNames", oldFileNames)
                .fluentPut("fileNames", fileNames)
                .fluentPut("path", path);
    }


    /*
     * 上传图片
     */
    @RequestMapping(value = "/uploadAllVideos")
    public JSONObject uploadAllVideos(@RequestParam(value = "files",required = false) MultipartFile[] files,@RequestParam(value = "type") Integer type, HttpServletRequest request)throws Exception {
        if (files == null) {
            List<MultipartFile> list = requestMultipartFile(request);
            files = new MultipartFile[list.size()];
            list.toArray(files);
        }
        List<String> oldFileNames=new ArrayList<>();
        List<String> path=new ArrayList<>();
        List<String> fileNames=new ArrayList<>();
        for (MultipartFile file1:files){
            String fileName = file1.getOriginalFilename();
            oldFileNames.add(fileName);
            DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
            File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY , fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            file1.transferTo(file);
            PictureUtil.videoImage(file.getPath(),ProjectConfig.FILE_TEMPORARY_DIRECTORY +"thumbnail-"+fileName+".png");
            path.add(fileName);
            fileNames.add(fileName);

        }
        return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                .fluentPut("oldFileNames", oldFileNames)
                .fluentPut("fileNames", fileNames)
                .fluentPut("path", path);
    }


    /**
     * 图片剪切(正式路径)
     * @param newFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cutPictures.do")
    public JSONObject cutPictures(@RequestParam(value = "newFile") String newFile, TbImage image)throws Exception{
        Base64.Decoder decoder = Base64.getDecoder();
        // 去掉base64编码的头部 如："data:image/jpeg;base64," 如果不去，转换的图片不可以查看
        newFile = newFile.substring(23);
        //解码
        byte[] imgByte = decoder.decode(newFile);
        try {
            FileOutputStream out = new FileOutputStream(ProjectConfig.FILE_IMAGE_DIRECTORY+image.getImagePath()); // 输出文件路径
            out.write(imgByte);
            out.close();
            FileOutputStream thumbnailout = new FileOutputStream(ProjectConfig.FILE_IMAGE_DIRECTORY+image.getImageThumbnailsPath()); // 输出文件路径
            thumbnailout.write(imgByte);
            thumbnailout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CodeAndMsg.SUCESS.getJSON();
    }

    /**
     * 图片剪切(测试路径)
     * @param newFile
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cutPicturesText.do")
    public JSONObject cutPicturesText(@RequestParam(value = "newFile") String newFile, @RequestParam(value = "fileName") String fileName)throws Exception{
        Base64.Decoder decoder = Base64.getDecoder();
        // 去掉base64编码的头部 如："data:image/jpeg;base64," 如果不去，转换的图片不可以查看
        newFile = newFile.substring(23);
        //解码
        byte[] imgByte = decoder.decode(newFile);
        try {
            FileOutputStream out = new FileOutputStream(ProjectConfig.FILE_TEMPORARY_DIRECTORY+fileName); // 输出文件路径
            out.write(imgByte);
            out.close();
            String type = fileName.substring(fileName.lastIndexOf("."));
            String thumbnailFileName = fileName.replace(type,"-thumbnail"+type);
            FileOutputStream thumbnailout = new FileOutputStream(ProjectConfig.FILE_TEMPORARY_DIRECTORY+thumbnailFileName); // 输出文件路径
            thumbnailout.write(imgByte);
            thumbnailout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CodeAndMsg.SUCESS.getJSON();
    }
    /*
     * 上传文件
     */
    @RequestMapping(value = "/uploadAllFiles")
    public JSONObject uploadAllFiles(@RequestParam(value = "files",required = false) MultipartFile[] attch, HttpServletRequest request)throws Exception {
        if (attch == null) {
            List<MultipartFile> list = requestMultipartFile(request);
            attch = new MultipartFile[list.size()];
            list.toArray(attch);
        }
        List<String> fileNames=new ArrayList<>();
        List<String> oldFileNames=new ArrayList<>();
        List<String> path = new ArrayList<>();
//        List<String> url=new ArrayList<>();
//        List<String> textUrl=new ArrayList<>();
        for (MultipartFile file1:attch){
            String fileName = file1.getOriginalFilename();
            oldFileNames.add(fileName);
            DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
            File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY , fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            file1.transferTo(file);

            fileNames.add(fileName);
            path.add(fileName);
//            url.add("/file/downLoad?type=" +type+"&fileName="+fileName);
//            textUrl.add("/file/textFileDown?type=" +type+"&fileName="+fileName);
        }

        return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                .fluentPut("oldFileNames", oldFileNames)
                .fluentPut("fileNames", fileNames)
                .fluentPut("path", path);
    }

    /*
     * 上传文件
     */
    @RequestMapping(value = "/uploadLayedit")
    public JSONObject uploadLayedit(@RequestParam(value = "file",required = false) MultipartFile attch, HttpServletRequest request)throws Exception {
        if (attch == null) {
            attch = requestMultipartFile(request).get(0);
        }
        String fileName = attch.getOriginalFilename();
        String oldFileName = fileName;
        if(fileName.contains("/")){
            fileName = fileName.substring(fileName.lastIndexOf("/"));
        }

        DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
        fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
        File file = new File(UPLOAD_PATH_LAYEDIT , fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        attch.transferTo(file);
        return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                .fluentPut("data", new ImageView(HOST_NAME+"layedit/" +fileName,oldFileName));
    }
    /*
     * 刪除文件
     */
    @RequestMapping(value = "/DeleteLayeditFile")
    public JSONObject DeleteLayeditFile(@RequestParam(value = "imgpath",required = false) String imgpath, @RequestParam(value = "file",required = false) String filepath, HttpServletRequest request)throws Exception {
        if(StringUtils.isEmpty(imgpath)){
            new File(UPLOAD_PATH_LAYEDIT,imgpath.substring(imgpath.lastIndexOf("/")+1)).delete();
        }
        if(StringUtils.isEmpty(filepath)){
            new File(UPLOAD_PATH_LAYEDIT,filepath.substring(filepath.lastIndexOf("/")+1)).delete();
        }
        return new JSONObject().fluentPut("code",CodeAndMsg.SUCESS.getCode()).fluentPut("msg",CodeAndMsg.SUCESS.getMsg());
    }



    /**
     *
     * @param fileName 文件名稱
     * @param type  文件類型1.圖片,2.影片
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/fileDown")
    public void fileDownLoad(@RequestParam("fileName")  String fileName,@RequestParam(value = "type",defaultValue = "0") Integer type,HttpServletRequest request, HttpServletResponse response)throws Exception {
        String userAgent = request.getHeader("UserInfo-Agent");
        String oraFileName = fileName;
        String formFileName=oraFileName;
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent!=null&&(userAgent.contains("MSIE") || userAgent.contains("Trident"))) {
            formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-disposition",
                String.format("attachment; filename=\"%s\"", (formFileName)));
//        response.addHeader("Accept","video/mp4");
        response.setContentType("application/force-download");// 设置强制下载不打开
//        response.setContentLength((int)ri.content_Length);
//        response.setHeader("Content-Range", ri.content_Range);
//        response.setHeader("Accept-Ranges", "bytes");

        response.setCharacterEncoding("UTF-8");
        File file = null;
        switch (type){
            case 1:{
                file = new File(ProjectConfig.FILE_IMAGE_DIRECTORY,fileName);
                break;
            }
            case 2:{
                file = new File(ProjectConfig.FILE_VIDEO_DIRECTORY,fileName);
                break;
            }
            default:{
                file = new File(ProjectConfig.FILE_IMAGE_DIRECTORY,fileName);
                break;
            }
        }
        if(!file.isFile()){
            file = ResourceUtils.getFile("classpath:static/images/notfind.jpg");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        IOUtils.copy(fileInputStream, response.getOutputStream());
    }


    @RequestMapping("/textFileDown")
    public void textFileDown(@RequestParam("fileName")  String fileName,HttpServletRequest request, HttpServletResponse response)throws Exception {
        String userAgent = request.getHeader("UserInfo-Agent");
        String oraFileName = fileName;
        String formFileName=oraFileName;
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent!=null&&(userAgent.contains("MSIE") || userAgent.contains("Trident"))) {
            formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY,fileName);
        if(!file.isFile()){
            file = ResourceUtils.getFile("classpath:static/images/notfind.jpg");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        IOUtils.copy(fileInputStream, response.getOutputStream());

    }
    /**
     * 圖片下载，直接打开
     * @param fileName
     * @param response
     * @throws Exception
     */
    @RequestMapping("/downLoad")
    public void downLoad(@RequestParam("fileName") String fileName, HttpServletResponse response) throws Exception {
        downLoad(fileName,ProjectConfig.FILE_IMAGE_DIRECTORY,response);
    }

    /**
     * pdf下載，直接打开
     * @param fileName
     * @param response
     * @throws Exception
     */
    @RequestMapping("/downLoadPdf")
    public void downLoadPdf(@RequestParam("fileName") String fileName, HttpServletResponse response) throws Exception {
        downLoad(fileName,ProjectConfig.FILE_CONTENTS_DIRECTORY,response);
    }

    private void downLoad(String fileName,String filePath, HttpServletResponse response)throws Exception{
        File file = new File(filePath,fileName);
        if (!file.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        URL u = new URL("file:///" + file.getPath());
        response.setContentType(u.openConnection().getContentType());
        response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
        // 文件名应该编码成UTF-8
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }

    private List<MultipartFile> requestMultipartFile(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        request.setCharacterEncoding("UTF-8");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //** 页面控件的文件流* *//*
        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
        Map map =multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFiles.add((MultipartFile) map.get(obj));
        }
        return multipartFiles;
    }


}

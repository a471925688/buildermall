package com.noah_solutions.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;


/**
 * Created by xiaomc on 6/30/15.
 */
public class HttpUtil {

    private static int BUFFER_SIZE = 8096; // 缓冲区大小
    public final static boolean DEBUG = true; // 调试用
    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static TrustManager myX509TrustManager = new X509TrustManager() {

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {

        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    };

    /**
     * 发送post请求
     *
     * @param url  目标地址
     * @param data 参数
     * @return 请求结果
     */
    public static String post(String url, String data, boolean isHttps) {
        logger.info("request url : {}", url);
        try {

            // 打开连接
            // 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
            URL requestUrl = new URL(url);
            int code =0;
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String responseResult = "";
            if(isHttps){
                // 设置SSLContext
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[]{myX509TrustManager},
                        null);


                HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl
                        .openConnection();
                // 设置套接工厂
                httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());
                // 加入数据
                httpsConn.setRequestMethod("POST");
                httpsConn.setDoOutput(true);
                OutputStream out = httpsConn.getOutputStream();
                if (data != null)
                    out.write(data.getBytes("UTF-8"));
                out.flush();
                out.close();
                // 获取输入流
                inputStream = httpsConn.getInputStream();
                code = httpsConn.getResponseCode();
            }else {

                HttpURLConnection httpConn = (HttpURLConnection) requestUrl
                        .openConnection();
                // 加入数据
                httpConn.setRequestMethod("POST");
                httpConn.setDoOutput(true);
                OutputStream out = httpConn.getOutputStream();
                if (data != null)
                    out.write(data.getBytes("UTF-8"));
                out.flush();
                out.close();
                // 获取输入流
                inputStream = httpConn.getInputStream();
                code = httpConn.getResponseCode();
            }


            if (HttpsURLConnection.HTTP_OK == code) {
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                responseResult = buffer.toString();
                //logger.info("response result : {}" , responseResult);
                return responseResult;
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String get(String url, Map map)throws Exception
    {
       String getstr= formatFormParameters(map);
        url+="?"+getstr;
        return get(url,false);
    }

    /**
     * 发送get请求
     *
     * @param url 请求地址
     * @return 请求结果
     */
    public static String get(String url, boolean isHttps)throws Exception {
        logger.info("request url : {}", url);

            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String responseResult = "";
            int code;
            if(isHttps){
                // 设置SSLContext
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[]{myX509TrustManager},
                        null);
                // 打开连接
                // 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
                URL requestUrl = new URL(url);
                    HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl
                            .openConnection();
                    // 设置套接工厂
                    httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

                // 加入数据
                httpsConn.setRequestMethod("GET");
                // httpsConn.setDoOutput(true);
                // 获取输入流
                inputStream = httpsConn.getInputStream();

                code = httpsConn.getResponseCode();
            }else {

                // 打开连接
                // 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
                URL requestUrl = new URL(url);
                HttpURLConnection httpConn=(HttpURLConnection) requestUrl.openConnection();

                // 加入数据
                httpConn.setRequestMethod("GET");
                // httpsConn.setDoOutput(true);
                // 获取输入流
                inputStream = httpConn.getInputStream();
                code = httpConn.getResponseCode();
            }

            if (HttpsURLConnection.HTTP_OK == code) {
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                responseResult = buffer.toString();
                //logger.info("response result : {}" , responseResult);
                return responseResult;
            }

        return null;
    }


    public static String uploadFiles(String url, File file) {
      /*  org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        String uploadurl = url;
        PostMethod post = new PostMethod(uploadurl);
        post.setRequestHeader("UserInfo-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Host", "file.api.weixin.qq.com");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        String result = null;
        try {
            if (file != null && file.exists()) {
                FilePart filepart = new FilePart("media", file, "image/jpeg", "UTF-8");
                Part[] parts = new Part[]{filepart};
                MultipartRequestEntity entity = new MultipartRequestEntity(
                        parts,
                        post.getParams());
                post.setRequestEntity(entity);
                int status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK) {
                    String responseContent = post.getResponseBodyAsString();
                    return responseContent;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return "";
    }

    /**
     * 保存http资源到服务器文件系统
     *
     * @param destUrl  图片网络路径
     * @param filePath 图片服务器路径含文件名
     * @throws IOException
     */
    public static String downloadFile(String destUrl, String filePath) throws IOException {
        //如果不存在这个目录，就新建一个
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        // 建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpUrl.connect();
        String filemime = httpUrl.getHeaderField("Content-Type");
        String fileName = UUID.randomUUID().toString();
        String fileExt = GetExtByMime(filemime);
        String filePathWithName = Paths.get(filePath, fileName + fileExt).toString();

        // 获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());
        // 建立文件
        fos = new FileOutputStream(filePathWithName);
        if (DEBUG)
            System.out.println("正在获取链接[" + destUrl + "]的内容.../n将其保存为文件["
                    + filePathWithName + "]");
        // 保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);
        fos.close();
        bis.close();
        httpUrl.disconnect();
        return fileName + fileExt;
    }

    /**
     * 根据url地址的content-type返回文件后缀名. 目前只有 jpg 后续扩展其他类型.
     *
     * @param mimename
     * @return
     */

    private static String GetExtByMime(String mimename) {

        return ".jpg";

    }
    private static String formatFormParameters(Map map) {
        if(map==null){
            throw new IllegalArgumentException("参数为空!");
        }
        StringBuffer sb = new StringBuffer();
        for(Iterator iter = map.entrySet().iterator(); iter.hasNext();){
            Entry entry = (Entry) iter.next();
            sb.append(entry.getKey()).append("=").append(entry.getValue()!=null?
                    entry.getValue().toString():"");
            if(iter.hasNext()){
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

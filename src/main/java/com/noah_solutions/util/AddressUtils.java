package com.noah_solutions.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 2018 12 12 lijun
 * 根据IP地址获取详细的地域信息
 */
public class AddressUtils {
    /**
     *
     * @param ip
     *            请求的参数 格式为：name=xxx&pwd=xxx
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getAddresses(String ip)
            throws UnsupportedEncodingException {
//        // 这里调用pconline的接口
//        String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
//        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
//        String returnStr = null;
//        try {
//            returnStr = HttpUtil.get(urlStr,false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (returnStr != null) {
//            JSONObject jsonResult = JSONObject.parseObject(returnStr);
//            // 处理返回的省市区信息
//            System.out.println(returnStr);
////            String[] temp = returnStr.split(",");
//            if(jsonResult.getInteger("code")!=0){
//                return "0";//无效IP，局域网测试
//            }
//            JSONObject data = jsonResult.getJSONObject("data");
//            return data.getString("country")+data.getString("area")+data.getString("region")+data.getString("city");
//        }
//        return null;


//        String urlStr = "http://ip.ws.126.net/ipquery?ip="+ip;
//        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
//        String returnStr = null;
//        try {
//            returnStr = HttpUtil.get(urlStr,false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (returnStr != null) {
//            JSONObject data = JSONObject.parseObject(returnStr.split(";")[1].split("=")[1]);
//            // 处理返回的省市区信息
//            System.out.println(returnStr);
//
//            return data.getString("province")+data.getString("city")+data.getString("region")+data.getString("city");
//        }
//        return null;


        String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = null;
        try {
            returnStr = HttpUtil.get(urlStr,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnStr != null) {
            JSONObject jsonResult = JSONObject.parseObject(returnStr);
            // 处理返回的省市区信息
            System.out.println(returnStr);
//            String[] temp = returnStr.split(",");
            if(jsonResult.getInteger("code")!=0){
                return "0";//无效IP，局域网测试
            }
            JSONObject data = jsonResult.getJSONObject("data");
            return data.getString("country")+data.getString("area")+data.getString("region")+data.getString("city");
        }
        return null;


    }

    public static  String getString(String tex){
        return Base64.encodeBase64String(DigestUtils.md5Hex(tex).getBytes());
    }
    // 测试
    public static void main(String[] args) {
        Long str = System.currentTimeMillis();
        System.out.println(str);
        System.out.println(getString("{\"originalsStreet\":\"香港-香港市-九龍区\",\"originalsaddress\":\"廣東-珠海市-香洲区\",\"sendDateTime\":\"2018-08-07 11:00:03\",\"totalVolume\":0.001,\"totalWeight\":500,\"logisticCompanyID\":\"DEPPON\"}"+"ebde0284bf1abdcb59b52d37d52acd4d"+str));
        /*AddressUtils addressUtils = new AddressUtils();
        // 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信
        String ip = "219.136.134.157";
        String address = "";
        try {
            address = addressUtils.getAddresses(ip);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(address);*/
        // 输出结果为：广东省,广州市,越秀区
    }
}

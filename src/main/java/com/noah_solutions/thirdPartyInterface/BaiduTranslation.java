package com.noah_solutions.thirdPartyInterface;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.util.HttpUtil;
import com.noah_solutions.util.MD5Util;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class BaiduTranslation {
    private static String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private static final String APP_ID = "20190318000278222";
    private static final String SECURITY_KEY = "DV287J2HzBiipJrBi3rx";

    public static void main(String[] args)throws Exception {

        String query = "北京市";
        System.out.println(query(query));
    }

    public static String query(String text) {
        String salt = String.valueOf(System.currentTimeMillis());
        String digest = null;
        try {
            digest = MD5Util.getStringMD5(APP_ID + text + salt + SECURITY_KEY);
        JSONObject params = new JSONObject().fluentPut("q", URLEncoder.encode(text, "UTF-8"))
                .fluentPut("from", "auto")
                .fluentPut("to", "auto")
                .fluentPut("appid", APP_ID)
                .fluentPut("salt", salt)
                .fluentPut("sign", digest);
//            return HttpUtil.get(url, params);
            JSONObject res = JSONObject.parseObject(HttpUtil.get(url, params));
            System.out.println(res.toJSONString());
            if(!StringUtils.isEmpty(res.getString("error_code"))&&res.getString("error_code").equals("54003")){
                return "";
            }
            return res.getJSONArray("trans_result").getJSONObject(0).getString("dst");
        } catch (Exception e) {
            e.printStackTrace();
        }
      return null;
    }


}
package com.noah_solutions.thirdPartyInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Place;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 德邦快遞信息庫
 */
public class DeBangExpress {
    //获取德邦新运费时效url
    private static final String shippingUrl ="http://dpapi.deppon.com/dop-interface-sync/standard-query/queryPriceTime.action";
    private static final String newTraceQueryUrl ="http://dpsanbox.deppon.com/sandbox-web/standard-order/newTraceQuery.action";

    private static final String companyCode = "EWBHQNYFAKJYXGS";//公司編碼
    private static final String Appkey = "ebde0284bf1abdcb59b52d37d52acd4d";
    private static final String logisticCompanyID = "DEPPON";


    ////////////////////////////////請求參數名  start///////////////////////////////////////////////////
    ////////////////////////////////請求參數名  start///////////////////////////////////////////////////
    public static  final String PARAMS = "params";//请求参数
    public static  final String DIGEST = "digest";//密文摘要
    public static  final String TIMESTAMP = "timestamp";//时间戳
    public static  final String COMPANYCODE = "companyCode";//公司编码
    ////////////////////////////////請求參數名  end///////////////////////////////////////////////////
    ////////////////////////////////請求參數名  end///////////////////////////////////////////////////




    ////////////////////////////////請求報文字段(params中的參數) start///////////////////////////////////////////////////
    ////////////////////////////////請求報文字段(params中的參數) start///////////////////////////////////////////////////
    public static  final String LOGISTICCOMPANYID = "logisticCompanyID";//物流公司ID
    public static  final String ORIGINALSSTREET = "originalsStreet";//出发城市
    public static  final String ORIGINALSADDRESS = "originalsaddress";//目标城市
    public static  final String SENDDATETIME = "sendDateTime";//发出时间
    public static  final String TOTALVOLUME = "totalVolume";//体积
    public static  final String TOTALWEIGHT = "totalWeight";//重量


    public static  final String MAILNO = "mailNo";//订单号
    ////////////////////////////////請求報文字段(param中的參數) end///////////////////////////////////////////////////
    ////////////////////////////////請求報文字段(param中的參數) end///////////////////////////////////////////////////








    ////////////////////////////////返回参数字段 start///////////////////////////////////////////////////
    ////////////////////////////////返回参数字段 start///////////////////////////////////////////////////
    public static  final String REASON = "reason";//错误原因
    public static  final String RESULT = "result";//请求成功标识
    public static  final String RESULTCODE = "resultCode";//结果代码
    public static  final String UNIQUERREQUESTNUMBER = "uniquerRequestNumber";//唯一请求编码
    public static  final String RESPONSEPARAM = "responseParam";//唯一请求编码

    public static  final String PRODUCTCODE = "productCode";//运输性质编码
    public static  final String PRODUCTNAME = "productName";//运输性质
    public static  final String HEAVYRATE = "heavyRate";//重货
    public static  final String LIGHTRATE = "lightRate";//轻货
    public static  final String GROUNDPRICE = "groundPrice";//首重价格
    public static  final String UPPERGROUND = "upperGround";//首重
    public static  final String RATEOFSTAGE1 = "rateOfStage1";//续重价格1
    public static  final String LOWEROFSTAGE1 = "lowerOfStage1";//续重1
    public static  final String LOWEROFSTAGE2 = "lowerOfStage2";//续重价格2
    public static  final String LABEL = "label";//标签
    public static  final String TOTALFEE = "totalfee";//总价
    public static  final String DAYS = "days";//运输时效
    public static  final String ARRIVEDATE = "arriveDate";//到达时间
    public static  final String OMSPRODUCTCODE = "omsProductCode";//oms code

    public static  final String TRACE_LIST = "trace_list";//轨迹列表

    ////////////////////////////////返回参数字段  end///////////////////////////////////////////////////
    ////////////////////////////////返回参数字段  end///////////////////////////////////////////////////

    public  enum  TraceQueryStatus{
        GOT,DEPARTURE,ARRIVAL,SENT_SCAN,FAILED,OTHER,SIGNED;//已揽收,运势中,已到底指定地点,派送中,已拒绝签收,其他情况,已签收
    }
    /**
     * 德邦快递轨迹信息
     */
    public static class TraceQuery{
        private String site;//地点
        private String city;//城市
        private String description;//描述
        private String time;//时间
        private String status;//状态

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }




    /**
     *  	获取德邦新运费时效
     * @param originalsStreet 出发地
     * @param originalsaddress	目的地
     * @param totalVolume	体积
     * @param totalWeight 重量
     * @return
     * @throws Exception
     */
    public static JSONObject getAllCountryPlace(String originalsStreet,String originalsaddress,Double totalVolume,Double totalWeight)throws Exception{
        Long timestamp = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<String, Object>();

        String params =  new JSONObject().fluentPut(ORIGINALSSTREET,originalsStreet)
                .fluentPut(ORIGINALSADDRESS,originalsaddress)
                .fluentPut(TOTALVOLUME,totalVolume)
                .fluentPut(TOTALWEIGHT,totalWeight)
                .fluentPut(SENDDATETIME,getSenddatetime())
                .fluentPut(LOGISTICCOMPANYID,logisticCompanyID).toJSONString();
        String digest = Base64.encodeBase64String(DigestUtils.md5Hex(params+Appkey+timestamp).getBytes());
        data.put(PARAMS,params);
        data.put(DIGEST,digest);
        data.put(COMPANYCODE,companyCode);
        data.put(TIMESTAMP,timestamp);
        return JSONObject.parseObject(HttpClientUtil.doPost(shippingUrl,data));
    }


    /**
     * 客户依据德邦运单号查询货物轨迹信息
     * @param mailNo 快递单号
     * @return
     * @throws Exception
     */
    public static JSONObject getNewTraceQuery(String mailNo)throws Exception{
        Long timestamp = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<String, Object>();
        String params =  new JSONObject().fluentPut(MAILNO,mailNo)
                .fluentPut(SENDDATETIME,getSenddatetime())
                .fluentPut(LOGISTICCOMPANYID,logisticCompanyID).toJSONString();
        String digest = Base64.encodeBase64String(DigestUtils.md5Hex(params+Appkey+timestamp).getBytes());
        data.put(PARAMS,params);
        data.put(DIGEST,digest);
        data.put(COMPANYCODE,companyCode);
        data.put(TIMESTAMP,timestamp);
        return JSONObject.parseObject(HttpClientUtil.doPost(newTraceQueryUrl,data));
    }



    //获取时间
    private static String getSenddatetime(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 11);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.DATE, 1);
        Date date = c.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }


//    //测试
//    public static void main(String[] args)throws Exception{
//        JSONObject jsonObject = getAllCountryPlace("四川省-内江市-资中县","广东省-珠海市-香洲区",0.001,50);
//        System.out.println(jsonObject.toString());
//    }



    /**
     *	獲取德邦快遞運費
     * @param sPlaceEntity 出發地
     * @param oPlace 目的地
     * @param totalWeight 重量
     * @param totalVolume 體積
     * @return
     */
    public static String getDeBangFreight(Integer type, Place sPlaceEntity, Place oPlace, Double totalVolume, String totalWeight) throws Exception{
        Long startTime = System.currentTimeMillis();

        String originalsStreet = sPlaceEntity.getDetails();//獲取本地地址信息
        String originalsaddress = oPlace.getDetails();//獲取本地地址信息
        JSONObject resultObject = null;
        String freight = "";
        if(StringUtils.countMatches(originalsStreet,"-")>2) {
            originalsStreet = originalsStreet.substring(originalsStreet.indexOf("-")+1, originalsStreet.length());
        }
        if(StringUtils.countMatches(originalsaddress,"-")>2) {
            originalsaddress = originalsaddress.substring(originalsaddress.indexOf("-")+1, originalsaddress.length());
        }
        resultObject =  DeBangExpress.getAllCountryPlace(originalsStreet, originalsaddress, totalVolume, Double.parseDouble(totalWeight));
        if(resultObject!=null&&resultObject.getBoolean(DeBangExpress.RESULT)) {
            JSONArray jsonArray = resultObject.getJSONArray(DeBangExpress.RESPONSEPARAM);
            int totalfee =0;
            switch (type) {
                case 1:
                    totalfee = jsonArray.getJSONObject(0).getIntValue(DeBangExpress.TOTALFEE);
                    break;
                case 2:
                    totalfee = jsonArray.getJSONObject(1).getIntValue(DeBangExpress.TOTALFEE);
                default:
                    break;
            }
            freight = Math.round((totalfee+totalfee*0.1)/ ProjectConfig.TodayRate)+"";
        }
        return freight;

    }

}

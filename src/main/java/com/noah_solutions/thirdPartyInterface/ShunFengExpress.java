package com.noah_solutions.thirdPartyInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.util.HttpClientUtil;
import com.noah_solutions.util.HttpUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 順豐快遞信息庫
 */
public class ShunFengExpress {
    //獲取所有國家地址（支持的發貨地址）
    private static final String findCountryPlace1 ="http://www.sf-express.com/sf-service-owf-web/service/region/countryCode/subRegions/origins";

    //獲取所有國家地址（支持的收貨地址）
    private static final String findCountryPlace2 ="http://www.sf-express.com/sf-service-owf-web/service/region/countryCode/subRegions/dests";



    //順豐1級地址code值
    public static  final String FIRST_COUNTRY_CODE = "A000000000";

    public static  final String ID = "distId";
    public static  final String CODE = "code";
    public static  final String NAME = "name";
    public static  final String LEVEL = "level";
    public static  final String PARENT_ID = "parentId";
    public static  final String PARENT_CODE = "parentCode";
    public static  final String COUNTRY_CODE = "countryCode";
    public static  final String LANG = "lang";
    public static  final String ORIGIN_CODE = "originCode";

    /**
     * 獲取所有的順豐收發貨地址信息
     * @return
     * @throws Exception
     */
    public static JSONArray getAllCountryPlace(String countryCode)throws Exception{
        return  JSONObject.parseArray(HttpUtil.get(findCountryPlace1.replace(COUNTRY_CODE,countryCode), new JSONObject().fluentPut(LEVEL,"-1").fluentPut(LANG,"sc").fluentPut("region","cn").fluentPut("translate","")))
                .fluentAddAll(JSONObject.parseArray(HttpUtil.get(findCountryPlace2.replace(COUNTRY_CODE,countryCode), new JSONObject().fluentPut(ORIGIN_CODE,"B440300000").fluentPut(LEVEL,"-1").fluentPut(LANG,"sc").fluentPut("region","cn").fluentPut("translate",""))));
    }


    public static void main(String[] args)throws Exception{
        JSONArray jsonArray = getAllCountryPlace(FIRST_COUNTRY_CODE);
        System.out.println(jsonArray.toJSONString());
    }

    public static String getShunFengFreight(Integer type,String sfCode, String sfCode1, String queryType, String productConfigWeight) throws Exception {
        //获取运费
        String queryFreight ="http://www.sf-express.com/sf-service-owf-web/service/rate/newRates?origin=ORIGIN&dest=DEST&weight=WEUGHT&time=2019-01-15T11%3A30%3A00%2B08%3A00&volume=0&queryType=QUERYTYPE&lang=sc&region=cn&translate=";

        queryFreight = queryFreight.replace("ORIGIN", sfCode);//起始地點
        queryFreight = queryFreight.replace("DEST", sfCode1);//收貨地點
        queryFreight = queryFreight.replace("QUERYTYPE", queryType);
        queryFreight = queryFreight.replace("WEUGHT", productConfigWeight);//重量

        String strResult = HttpClientUtil.doGet(queryFreight);
        Long freight=null;
        if(!StringUtils.isEmpty(strResult)){
            JSONArray result = JSONObject.parseArray(strResult);
            switch (type){
                case 3:{
                    JSONObject curResult = resHandle("顺丰标快",result);
                    freight = curResult.getLong("totalFreight");
                    if(curResult.getString("currencyName").equals("人民币")){
                        freight = Math.round(freight* ProjectConfig.TodayRate);
                    }
                    break;
                }
                case 4:{
                    JSONObject curResult = resHandle("顺丰特惠",result);
                   freight = curResult.getLong("totalFreight");
                    if(curResult.getString("currencyName").equals("人民币")){
                        freight = Math.round(freight* ProjectConfig.TodayRate);
                    }
                    break;
                }
                default:{

                }
            }

        }
        if(StringUtils.isEmpty(freight))
            throw new CustormException("運費計算失敗,下單后請等待商家報價!<br>Freight calculation failed. Please wait for quotation after placing order.",1);
        freight=Math.round(freight+freight*0.1);
        return freight+"";
    }

    private static JSONObject resHandle(String name,JSONArray result){
        JSONObject data = new JSONObject();
        for(int i=0;i<result.size();i++){
            if(result.getJSONObject(i).getString("limitTypeName").equals(name)){
                data = result.getJSONObject(i);
                break;
            }
        }
        return data;
    }
}

package com.noah_solutions.thirdPartyInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.util.HttpClientUtil;
import net.sf.json.xml.XMLSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.*;

@Component
public class Paydollar {
    //支付相關操作api
    private static String orderApiUrl;


    private static String  merchantIdHKD;
    private static String  loginIdHKD;
    private static String  passwordHKD;


    private static String  merchantIdCNY;
    private static String  loginIdCNY;
    private static String  passwordCNY;

    @Value("${Paydollar.orderApiUrl}")
    public  void setOrderApiUrl(String orderApiUrl) {
        Paydollar.orderApiUrl = orderApiUrl;
    }

    @Value("${Paydollar.hkd.merchantId}")
    public  void setMerchantIdHKD(String merchantIdHKD) {
        Paydollar.merchantIdHKD = merchantIdHKD;
    }
    @Value("${Paydollar.hkd.loginId}")
    public  void setLoginIdHKD(String loginIdHKD) {
        Paydollar.loginIdHKD = loginIdHKD;
    }
    @Value("${Paydollar.hkd.password}")
    public  void setPasswordHKD(String passwordHKD) {
        Paydollar.passwordHKD = passwordHKD;
    }
    @Value("${Paydollar.cny.merchantId}")
    public  void setMerchantIdCNY(String merchantIdCNY) {
        Paydollar.merchantIdCNY = merchantIdCNY;
    }
    @Value("${Paydollar.cny.loginId}")
    public  void setLoginIdCNY(String loginIdCNY) {
        Paydollar.loginIdCNY = loginIdCNY;
    }
    @Value("${Paydollar.cny.password}")
    public  void setPasswordCNY(String passwordCNY) {
        Paydollar.passwordCNY = passwordCNY;
    }

    /**
     * Capture(預授權),Void(撤銷已接受的訂單),RequestRefund(退款)，Query(查詢)
     */
    private  enum  ActionType{
        Capture,Void,RequestRefund,Query;
    }

    /**
     * 撤銷已接受的訂單
     * @param payRef 付款参考编号
     * @return 成或失敗
     */
    public static JSONObject cancellationOrder(Integer curr,String payRef){
       return queryResult(request(curr,payRef,ActionType.Void.toString(),null,null));
    }

    /**
     * 訂單退款
     * @param payRef
     * @param amount
     * @return
     */
    public static JSONObject refundOrder(Integer curr,String payRef,Double amount){
        return queryResult(request(curr,payRef,ActionType.RequestRefund.toString(),amount,null));
    }

    /**
     *  查詢訂單狀態
     * @param orderRef
     * @return
     */
    public static JSONObject queryOrder(Integer curr,String orderRef){
        String result = request(curr,null,ActionType.Query.toString(),null,orderRef);
        return  queryXmlResult(result);
    }



    private static String request(Integer curr,String payRef,String actionType,Double amount,String orderRef){
        String merchantId = "";
        String loginId = "";
        String password = "";
        if(curr.intValue() == HKD.getValue().intValue()||curr.intValue() == USD.getValue().intValue()){
            merchantId = Paydollar.merchantIdHKD;
            loginId = Paydollar.loginIdHKD;
            password = Paydollar.passwordHKD;
        }else if(curr.intValue() == CNY.getValue().intValue()){
            merchantId = Paydollar.merchantIdCNY;
            loginId = Paydollar.loginIdCNY;
            password = Paydollar.passwordCNY;
        }
        JSONObject params = new JSONObject().fluentPut("merchantId",merchantId)
                .fluentPut("loginId",loginId).fluentPut("password",password).fluentPut("actionType",actionType);
        if(!StringUtils.isEmpty(payRef))
            params.fluentPut("payRef",payRef);
        if(amount!=null)
            params.fluentPut("amount",amount);
        if(!StringUtils.isEmpty(orderRef))
            params.fluentPut("orderRef",orderRef);
        System.out.println("payParam="+params.toJSONString());
        return HttpClientUtil.doPost(orderApiUrl,params);


    }

    private static JSONObject queryResult(String data){
        String[] datas = data.split("&");
        JSONObject jsonObject = new JSONObject();
        for (String s:datas) {
            String[] str = s.split("=");
            if(str.length>1){
                jsonObject.fluentPut(str[0],str[1]);
            }else {
                jsonObject.fluentPut(str[0],null);
            }
        }

        return jsonObject;
    }


    private static JSONObject queryXmlResult(String data){
        JSONObject jsonObject = new JSONObject();
        System.out.println(data);
        if(StringUtils.isEmpty(data)){
            jsonObject = null;
        }else if(data.equals("error")){
            jsonObject.fluentPut("error",1);
        }else {
            XMLSerializer xmlSerializer = new XMLSerializer();
            String resutStr = xmlSerializer.read(data).toString();
            JSONArray result = JSONObject.parseArray(resutStr);
            if(result.size()>0){
                jsonObject = result.getJSONObject(0);
            }else {
                jsonObject = null;
            }
        }
       return jsonObject;
    }
}

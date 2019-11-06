package com.noah_solutions.thirdPartyInterface.wxpay.sdk;


import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 签名验证工具地址:
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=20_1
 *
 * 统一下单API说明:
 * https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
 * @author 123
 *
 */
public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        unifiedOrder();
//        orderQuery();
    }

    /**
     * 统一下单接口<br>
     * 场景：公共号支付、扫码支付、APP支付
     */
    public static  void unifiedOrder (){
        try {
            WXPay wxPay = new WXPay(WxPayConfigImpl.getInstance());

            //订单号 （调整为自己的生产逻辑）

            //随机数
            String nonce_str = WXPayUtil.generateNonceStr();
            //获取发起电脑 ip,可以用百度之类的代替来测试
            String spbill_create_ip = "123.123.123.123";
            //回调接口(反正本机测试时不用,随意搞一个能访问的)
            String notify_url = "http://www.weixin.qq.com/wxpay/pay.php";
            //模式
            String trade_type = "NATIVE";//"JSAPI";

            SortedMap<String,String> packageParams = new TreeMap<String,String>();
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", "test-coke");  //（调整为自己的名称）格式: 腾讯充值中心-QQ会员充值
            packageParams.put("out_trade_no", "1124555455");
            packageParams.put("total_fee", "10"); //价格的单位为分
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notify_url);
            packageParams.put("trade_type", trade_type);
            wxPay.fillRequestData(packageParams);
            Map runMap = wxPay.unifiedOrder(packageParams);
            System.out.println("=============returnXml==============");
            System.out.println(runMap.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //发送:
//		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
//		<xml>
//		<appid>公众号APPID</appid>
//		<body>test-coke</body>
//		<mch_id>自己的商户号</mch_id>
//		<nonce_str>rE8iTtCENf4VSOtOCZ0PIbkuuNklyFpw</nonce_str>
//		<notify_url>http://www.weixin.qq.com/wxpay/pay.php</notify_url>
//		<out_trade_no>自己的商户号-1561942616146</out_trade_no>
//		<sign>907192B0875A02F199B45B7F30356644751DF6D4A2AFA77C0F3C226E4A60D276</sign>
//		<sign_type>HMAC-SHA256</sign_type>
//		<spbill_create_ip>123.123.123.123</spbill_create_ip>
//		<total_fee>10</total_fee>
//		<trade_type>NATIVE</trade_type>
//		</xml>

        //结果:
//		<xml><return_code><![CDATA[SUCCESS]]></return_code>
//		<return_msg><![CDATA[OK]]></return_msg>
//		<appid><![CDATA[公众号APPID]]></appid>
//		<mch_id><![CDATA[自己的商户号]]></mch_id>
//		<nonce_str><![CDATA[ayhkOwhZysjBQNR6]]></nonce_str>
//		<sign><![CDATA[021F45467BA5119C389D5F08D768CC5A635687057BBD6445E6B7A1BBB34F4AA9]]></sign>
//		<result_code><![CDATA[SUCCESS]]></result_code>
//		<prepay_id><![CDATA[wx010855154971807287de77f31587946700]]></prepay_id>
//		<trade_type><![CDATA[NATIVE]]></trade_type>
//		<code_url><![CDATA[weixin://wxpay/bizpayurl?pr=kMwACCs]]></code_url>
//		</xml>

        //解释后内容
        //{nonce_str=ayhkOwhZysjBQNR6, code_url=weixin://wxpay/bizpayurl?pr=kMwACCs, appid=公众号APPID, sign=021F45467BA5119C389D5F08D768CC5A635687057BBD6445E6B7A1BBB34F4AA9, trade_type=NATIVE, return_msg=OK, result_code=SUCCESS, mch_id=自己的商户号, return_code=SUCCESS, prepay_id=wx010855154971807287de77f31587946700}

    }

    /**
     * 查询订单接口<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     */
//    public static void orderQuery(){
//        try {
//            //sdk类引用
//            WxPayConfigImpl config = WxPayConfigImpl.getInstance();
//
//            WXPay pyPay = new WXPay(config);
//
//            //订单号
//            String out_trade_no = "自己的商户号-1561942616146";
//            //appid
//            String appid = config.getAppID();
//            //商业号
//            String mch_id = config.getMchID();
//            //随机数
//            String nonce_str = WXPayUtil.generateNonceStr();
//            //模式
//            String trade_type = "NATIVE";//"JSAPI";
//
//            SortedMap<String,String> packageParams = new TreeMap<String,String>();
//            packageParams.put("appid", appid);
//            packageParams.put("mch_id", mch_id);
//            packageParams.put("nonce_str", nonce_str);
//            packageParams.put("out_trade_no", out_trade_no);
//
//            Map runMap = pyPay.orderQuery(packageParams);
//
//            System.out.println("=============returnXml==============");
//            System.out.println(runMap.toString());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //发送
////		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
////		<xml>
////		<appid>公众号APPID</appid>
////		<mch_id>自己的商户号</mch_id>
////		<nonce_str>epQ1ZECqQryQJv0ZOybGc8XIjWTJupNX</nonce_str>
////		<out_trade_no>自己的商户号-1561942616146</out_trade_no>
////		<sign>366964520A9209243A714EAB55D46E542B56152E705814D4DB20FF1567511DA9</sign>
////		<sign_type>HMAC-SHA256</sign_type>
////		</xml>
//
//        //结果:
////		<xml><return_code><![CDATA[SUCCESS]]></return_code>
////		<return_msg><![CDATA[OK]]></return_msg>
////		<appid><![CDATA[公众号APPID]]></appid>
////		<mch_id><![CDATA[自己的商户号]]></mch_id>
////		<device_info><![CDATA[]]></device_info>
////		<nonce_str><![CDATA[dewGq9USU04gFwUE]]></nonce_str>
////		<sign><![CDATA[94D3B1BFB852E19CB3F421DBC23D5A3AE5A7CBDC9448C8B146959036141EFE2F]]></sign>
////		<result_code><![CDATA[SUCCESS]]></result_code>
////		<total_fee>10</total_fee>
////		<out_trade_no><![CDATA[自己的商户号-1561942616146]]></out_trade_no>
////		<trade_state><![CDATA[NOTPAY]]></trade_state>
////		<trade_state_desc><![CDATA[订单未支付]]></trade_state_desc>
////		</xml>
//    }

}

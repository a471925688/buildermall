package com.noah_solutions.thirdPartyInterface.Alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayInterface {
    AlipayClient alipayClient;
    {
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json",AlipayConfig.charset,AlipayConfig.alipay_public_key);
    }

}

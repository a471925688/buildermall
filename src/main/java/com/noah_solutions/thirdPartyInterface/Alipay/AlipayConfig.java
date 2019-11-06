package com.noah_solutions.thirdPartyInterface.Alipay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2019101468380351";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCuX+DcU4UV9N0EeYC5wdNGeGZIA1KAh9t+OyzaTyWxeWkVbDo17sRWJQXMMLQ9KLoo85EZor0aD8n01C4BJIBLmUHbRcocbv+fO79q7zdpgOqW9mXOCfLqNJyugrKRW8pEOeLqDQjdD5pXriXByfN+6aigTThU+udW62RAXm1MtFvU8dpu5M16NfswMItm8Bj+e+MDv6GEUllGXgO3QjsoxUTj1Di0DVm9wb6pJGVVljXYfdkdcIKaef/KviGmmixK0HuqM43RGWVbkMM9k5NnlvTnYJ9mqi1vIYLE1WswZO5wO+zpnVKfg9Wwamd3rBQrOffyYoiSWOB65YMvdWMpAgMBAAECggEANBlneMznfZjslFKtOFMGCg8dGDUN6GMvHHcXvp5ZN757vC/a8JRHSynCorCGmaNag7PKTauG7ot7HtBqcmLyhiPjPl2KtpYff/b9CTkyCC7BaJtz7WyhjIJtseJQ8gdH1ryo5J9CX7IyvjHFDDKHswqjHEoWI6XXiuvHx0evRnQ6CXzKFtExn/ds4phqwiPkbBeMusX4IsoGd6xXs8q7h3zbbQwkgfOML/GuLhJ6OR0agcRvfHaZ4rDERcCvlny+opx3StZYl3FO9SPJ4GJpgNuDKBE4cKqvsA8PK7A6fRjDikK2cd8x6zwhaE9GhXXB3QfqDQxDaba5fc2mwLO7BQKBgQDolUGTAtq3eX0JAqLHgUM/yVkmCFFCk1mWEKUwKfu2INVdmvBICrJcxMiX52nNMnF3nC4yObRBioIn8AJKvFTtuoBS0f+YEMHmjYOYTMJQXmbD6wkC4wLZSGZJvf7ahBCE3/SkJzOYGfeby0WSEZnuv6Uho/JPyigGYMhb24lqVwKBgQC/7lGkgBCd+3qp9Rwb9KY9Ia6eQRfB6fLtoCIG8Y9FjenTQbtF45lSnPyuAmxKdSAsFYw7qKc9ZdrpP75ETVpsKSv8PtnSqevdoxgLhZg4zv8B1WqTMa269GZvfvFdQyj1n7p+yUS4GO3d4A/xlulMkPZrbYLLNb+QXxOq84IufwKBgFCfnLGXZbYAOSWscjzyRBuScoLKk1Cev4lTj8+YXhcOXqgqE20FuDqMRHTT1Us6Vzb9Ps5OTnwBxYiOnFzH2DDoW59aCF3wQSbom1tqEFnHv5vel6sWgmBLxpl9cSDSct98utNEe5AYQEsTNbSiwVePu3Bj4pViIGnFlu6wtXvVAoGADLIH5niuILUUvQ9Ah7zcDikfMsqddq8x6XPxLEJoNrKXZ+juKUZPyv4lXKE+ZoJu2Vunhf626UHGhC06OaxgMc1bYnkJ0Xx55JQ4L/LrSb6vcYCbZxcfJcGq0O81qfSaTfz9ygsoJ4FGoqmChHl1/yGLmzmIMEbi3/raAzHOR6MCgYAI9+A9U2ABcGPT6cKAW/v05hfsBTJIY0O6GobcFsyPQnl8GTaT28iqTz0SngZQkw1xbL5CSWnzy+48i54Qc/SN0/MyfcTFvNTjiwm3+PcHjCPEraDqJ2Qa33IvrWAWCkn6Md5UL9OFTo2lEBQjMzhdEEyGrT5rZm+t7LurrCwY9g==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuXAbtqN/TD3nFY5I2aZLDjnf6sUu6j2tH9xFInquoRhOoe+ubQ/E6FYgn1c2unSNh930bBKsgKLjJafKBgAMfNucVfbKPAKjkmYVFzqyI26npwY9nlGhWoIWYTlo7ltmRj2jVQKV1avXrpu4RkqB+wgFhnfA5tw1G2XQVG6+XBxzuprq4g1mdaEQczSvYFYfUFVCOU9wGCb7iWT2M5SpG5MQU4EOBtoIvUhT1PGGqn87EP0+WLyonxVPSpFcrPufAVMQasU4fSckqG7Qi8c21G9B7hyvKFWahmQGutpyWYYlRNz/ixD/fatljLRwz7bTwgkjTCqv2O9p8gfA40h1PwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.buildermall.com/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://www.buildermall.com/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


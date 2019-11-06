package com.noah_solutions.thirdPartyInterface.wxpay.sdk;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WxPayConfigImpl extends WXPayConfig {

    private byte[] certData;
    private static WxPayConfigImpl INSTANCE;

    //公众号AppID
    private String appId = "wx8258e21050f92a3d";
    //商户号
    private String mchId = "1535410021";
    //key
    private String key = "1b4c09247ec02edce69f6a2d92006250";

    private WxPayConfigImpl(){
//        String certPath = "D://CERT/common/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }

    public static WxPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WxPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WxPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return this.appId;
    }

    public String getMchID() {
        return this.mchId;
    }

    public String getKey() {
        return this.key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}

package com.noah_solutions.thirdPartyInterface.wxpay.sdk;

import java.io.InputStream;

public class WXPayInfo extends WXPayConfig {
    private String appID = "wx8258e21050f92a3d";
    private String mchID = "1535410021";
    private String key = "1b4c09247ec02edce69f6a2d92006250";
    private InputStream certStream;
    private IWXPayDomain iwxPayDomain;

    @Override
    String getAppID() {
        return appID;
    }

    @Override
    String getMchID() {
        return mchID;
    }

    @Override
    String getKey() {
        return key;
    }

    @Override
    InputStream getCertStream() {
        return certStream;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return iwxPayDomain;
    }
}

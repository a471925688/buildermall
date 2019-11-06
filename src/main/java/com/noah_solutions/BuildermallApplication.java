package com.noah_solutions;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.PlaceCache;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.service.IAdminService;
import com.noah_solutions.service.ICartService;
import com.noah_solutions.service.IUserService;
import com.noah_solutions.util.HttpClientUtil;
import com.noah_solutions.websockt.WebSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@SpringBootApplication
@EnableSwagger2
@EnableAsync // 启动异步调用
@ServletComponentScan
public class BuildermallApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Resource
    private PlaceCache placeCache;

    @Resource
    private ProductTypeCache productTypeCache;

    @Resource
    private ICartService cartService;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BuildermallApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(BuildermallApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpClientUtil.doGet("http://api.k780.com/?app=finance.rate&scur=HKD&tcur=CNY&appkey=41110&sign=eb0522725557dff66de88bc31a1bde35"));
            if(jsonObject.getInteger("success")==1){
                ProjectConfig.TodayRate = jsonObject.getJSONObject("result").getDoubleValue("rate");//初始化當日利率
            }else {
                System.out.println("獲取匯率失敗");
            }
            jsonObject = JSONObject.parseObject(HttpClientUtil.doGet("http://api.k780.com/?app=finance.rate&scur=CNY&tcur=USD&appkey=41110&sign=eb0522725557dff66de88bc31a1bde35"));
            if(jsonObject.getInteger("success")==1){
                ProjectConfig.TodayRateUSD = jsonObject.getJSONObject("result").getDoubleValue("rate");//初始化當日利率（美元）
            }else {
                System.out.println("獲取匯率失敗");
            }

        }catch (Exception e){
            System.out.println("匯率獲取失敗,socke鏈接錯誤");
        }
        placeCache.init();
        productTypeCache.init();
        cartService.setOfflineAll();
        System.out.println("啟動成功.............................................................................................................................");
    }

}

package com.noah_solutions.timingtask;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.HtmlDataCache;
import com.noah_solutions.common.PlaceCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.repository.UserRepository;
import com.noah_solutions.service.IDealingSlipService;
import com.noah_solutions.service.IOrderService;
import com.noah_solutions.thirdPartyInterface.Paydollar;
import com.noah_solutions.util.DateUtil;
import com.noah_solutions.util.HttpClientUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;


@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultithreadScheduleTask {

    @Resource
    private IOrderService orderService;

    @Resource
    private IDealingSlipService dealingSlipService;

    @Async
    @Scheduled(cron = "0 0 12 * * ?")  //每天12點更新汇率
    public void updateTodayRate(){
        JSONObject jsonObject = JSONObject.parseObject(HttpClientUtil.doGet("http://api.k780.com/?app=finance.rate&scur=HKD&tcur=CNY&appkey=41110&sign=eb0522725557dff66de88bc31a1bde35"));
        if(jsonObject.getInteger("success")==1){
            ProjectConfig.TodayRate = jsonObject.getJSONObject("result").getDoubleValue("rate");//初始化當日利率
            System.out.println("獲取匯率成功" + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        }else {
            System.out.println("獲取匯率失敗");
        }
    }

    @Async
    @Scheduled(initialDelay=1000, fixedRate=1000*60)  //定时删除超时页面缓存数据
    public void delTimeOutHtmlData() {
       HtmlDataCache.delTimeOutData();
        System.out.println("删除页面超时缓存 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }


    @Async
    @Scheduled(cron = "0 0 3 * * ?")  //每天凌晨3點检测订单签收状态
    public void checkOrderSignedStatus(){
        orderService.checkOrderSignedStatus();
        System.out.println("每天凌晨3點检测订单签收状态 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }


    @Async
    @Scheduled(cron = "0 0 4 * * ?")  //每天凌晨4點更新订单收货状态
    public void updateOrderStateByDateTime(){
        String dataTime = DateUtil.getNextDay(new Date(),-14+"");
        orderService.updateOrderStateByDateTime(dataTime);
        System.out.println("每天凌晨4點更新订单收货状态 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }


    @Async
    @Scheduled(cron = "0 0 9 * * ?")  //每天早上9點提醒超过3天的未评价产品
    public void checkComment(){
        String dataTime = DateUtil.getNextDay(new Date(),-14+"");
        orderService.updateOrderStateByDateTime(dataTime);
        System.out.println("每天早上9點提醒超过3天的未评价产品: " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }

    @Async
    @Scheduled(fixedRate=1000*60*5)  //更新超过5分钟的交易状态
    public void updateDeslAState(){
        String dataTime = DateUtil.getNextTime(new Date(),-5+"");
        dealingSlipService.updateDeslAState(dataTime);
        System.out.println("更新超过5分钟的交易状态 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }

    @Async
    @Scheduled(fixedRate=1000*60*60)  //更新超过24小时未付款订单为取消订单
    public void checkOvertimeOrders(){
        String dataTime = DateUtil.getNextDay(new Date(),-1+"");
        orderService.checkOvertimeOrders(dataTime);
        System.out.println("更新超过24小时的未付款订单 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }

}

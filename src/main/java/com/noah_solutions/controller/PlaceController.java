package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.PlaceCache;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IPlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 2019 1 9 地點處理器
 */
@RestController
@RequestMapping("place")
public class PlaceController {
    @Resource
    private IPlaceService placeService;

    @Resource
    private PlaceCache placeCache;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////


    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //獲取所有省地址
    @RequestMapping("/getAllProvincePlace.do")
    public JSONObject getAllProvincePlace(){
       return CodeAndMsg.SUCESS.getJSON(placeCache.getProvincePlaces());
    }


    @RequestMapping("/getPlaceByPlaceId.do")
    public JSONObject getPlaceByPlaceId(@RequestParam("placeId")String placeId){
        return CodeAndMsg.SUCESS.getJSON(placeCache.getPlaceById(placeId));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    @GetMapping("/initShunFengPlace.do")
    private JSONObject initShunFengPlace()throws Exception{
        placeService.initShunFengPlace();
        return CodeAndMsg.SUCESS.getJSON();
    }
    @GetMapping("/initPlaceByShunFengPlace.do")
    private JSONObject initPlaceByShunFengPlace()throws Exception{
        placeService.initPlaceByShunFengPlace();
        return CodeAndMsg.SUCESS.getJSON();
    }
    @GetMapping("/initShunFenPlaceType.do")
    private JSONObject initShunFenPlaceType()throws Exception{
        placeService.setShunFenPlaceType();
        return CodeAndMsg.SUCESS.getJSON();
    }
    @GetMapping("/initOverseas.do")
    private JSONObject initOverseas()throws Exception{
        placeService.initOverseas();
        return CodeAndMsg.SUCESS.getJSON();
    }


    @GetMapping("/initPlaceDetails.do")
    private  JSONObject initPlaceDetails(){
        placeService.initPlaceDetails();
        return CodeAndMsg.SUCESS.getJSON();
    }


    @GetMapping("/initLocalPlace.do")
    private  JSONObject initLocalPlace(){
        placeService.initPlaceDetails();
        return CodeAndMsg.SUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}

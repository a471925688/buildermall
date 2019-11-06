package com.noah_solutions.service;

import com.noah_solutions.entity.Place;
import com.noah_solutions.entity.ShunFengPlace;

import java.util.List;

public interface IPlaceService {
    /**
     * 對接順豐地址信息
     * @throws Exception
     */
    void initShunFengPlace()throws Exception;

    /**
     * 初始化本地地址數據庫詳情描述
     */
    void initPlaceDetails();
    /**
     * 初始化本地地址庫(通過順豐)
     */
    void initPlaceByShunFengPlace()throws  Exception;

    /**
     * 初始化順豐區域類型
     * @throws Exception
     */
    void setShunFenPlaceType()throws Exception;

    void initOverseas()throws Exception;

    /**
     * 獲取所有省級地址
     * @return
     */
    List<Place> getAllProvincePlace();


    void translationPlace()throws Exception;

    void initPlaceDetailsEng();



    ShunFengPlace getShunFengPlaceByPlaceId(String placeId);
}

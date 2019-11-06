package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.TbImage;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IAdvertisingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2018 11 30 lijun
 * 管理員相關控制器
 */
@RestController
@RequestMapping("/advertising")
public class AdvertisingController {
    @Resource
    private IAdvertisingService advertisingService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加用户页面
    @GetMapping("/addAdvertisingView")
    public ModelAndView addImageView(){
        return new ModelAndView("picture/addOrUpdateAdvertising");
    }

    //编辑用户页面
    @GetMapping("/editAdvertisingView")
    public ModelAndView editImageView(@RequestParam("advertisingId")String imageId){
        return new ModelAndView("picture/addOrUpdateAdvertising","imageId",imageId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //根据条件分页获取廣告圖信息
    @RequestMapping("/getAdvertisingPageByCont.do")
    public JSONObject getAdvertisingPageByCont(TbImage condition, @RequestParam("page") Integer page,
                                         @RequestParam("limit") Integer limit){
        Page<TbImage> images = advertisingService.selectImagePageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "imageId")));
        return CodeAndMsg.SUCESS.getJSON(images.getContent()).fluentPut("count",images.getTotalElements());
    }
    //查询单个廣告圖
    @RequestMapping("/getAdvertisingById.do")
    public JSONObject getImageById(@RequestParam("advertisingId") String imageId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(advertisingService.selectImageByImageId(imageId));
    }
    //分组查询廣告圖数量
    @RequestMapping("/getAllCountGroupByImageGroupId.do")
    public  JSONObject getAllCountGroupByImageGroupId(){
        return CodeAndMsg.SUCESS.getJSON(advertisingService.selecrAllCountGroupByImageGroupId());
    }
    //查询廣告圖数量
    @RequestMapping("/getCountAdvertising.do")
    public JSONObject getCountAdvertising(){
        return CodeAndMsg.SUCESS.getJSON(advertisingService.selectCountImageByCont(new TbImage()));
    }
    //查询廣告圖数量
    @RequestMapping("/getCountAdvertisingByCont.do")
    public JSONObject getCountAdvertisingByCont(TbImage image){
        return CodeAndMsg.SUCESS.getJSON(advertisingService.selectCountImageByCont(image));
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加用户
    @RequestMapping("/addAdvertising.do")
    public JSONObject addImage(TbImage image)throws Exception {
         advertisingService.addImage(image);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑用户
    @RequestMapping("/editAdvertising.do")
    public JSONObject editImage(TbImage image)throws Exception {
        advertisingService.updateImage(image);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //删除单个用户
    @PostMapping("/delAdvertisingById.do")
    public JSONObject delImageById(@RequestParam("advertisingId") String imageId)throws Exception {
        advertisingService.delImageByImageId(imageId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个用户
    @PostMapping("/delAllAdvertisingById.do")
    public JSONObject delAllImageById(@RequestParam("advertisingIds[]") List<String> imageIds)throws Exception {
        advertisingService.delAllImageByImageId(imageIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}

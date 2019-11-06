package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.AdvertisingType;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IAdvertisingTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2018 11 30 lijun
 * 權限相關控制器
 */
@RestController
@RequestMapping("advertisingType")
public class AdvertisingTypeController {
    @Resource
    private IAdvertisingTypeService advertisingTypeService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加類型页面
    @GetMapping("/addAdvertisingTypeView")
    public ModelAndView addAdvertisingTypeView(){
        return new ModelAndView("picture/addOrUpdateAdvertisingType");
    }

    //编辑類型页面
    @GetMapping("/editAdvertisingTypeView")
    public ModelAndView editAdvertisingTypeView(@RequestParam("advertisingTypeId")String advertisingTypeId){
        return new ModelAndView("picture/addOrUpdateAdvertisingType","advertisingTypeId",advertisingTypeId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有類型
    @RequestMapping("/getAllAdvertisingType.do")
    public JSONObject getAllAdvertisingType(){
        return CodeAndMsg.SUCESS.getJSON(advertisingTypeService.selectAllAdvertisingType());
    }

    //根据条件查询所有類型
    @RequestMapping("/getAllAdvertisingTypeByCont.do")
    public JSONObject getAllAdvertisingTypeByCont(AdvertisingType advertisingType){
        return CodeAndMsg.SUCESS.getJSON(advertisingTypeService.selectAllAdvertisingTypeByCont(advertisingType));
    }

    //查询類型数量
    @RequestMapping("/getCountAdvertisingType.do")
    public JSONObject getCountAdvertisingType(){
        return CodeAndMsg.SUCESS.getJSON(advertisingTypeService.selectCountAdvertisingType());
    }

    //根据条件分页获取類型信息
    @RequestMapping("/getAdvertisingTypePageByCont.do")
    public JSONObject getAdvertisingTypePageByCont(AdvertisingType condition,@RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer limit){
        Page<AdvertisingType> advertisingTypes = advertisingTypeService.selectAdvertisingTypePageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "advertisingTypeId")));
        return CodeAndMsg.SUCESS.getJSON(advertisingTypes.getContent()).fluentPut("count",advertisingTypes.getTotalElements());
    }
    //查询单个類型
    @RequestMapping("/getAdvertisingTypeById.do")
    public JSONObject getAdvertisingTypeById(@RequestParam("advertisingTypeId") String advertisingTypeId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(advertisingTypeService.selectAdvertisingTypeByAdvertisingTypeId(advertisingTypeId));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加類型
    @RequestMapping("/addAdvertisingType.do")
    public JSONObject addAdvertisingType(AdvertisingType advertisingType,@RequestParam(value = "perIds[]",required = false) List<String> perIds)throws Exception {
        advertisingTypeService.saveAdvertisingType(advertisingType,perIds);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑類型
    @RequestMapping("/editAdvertisingType.do")
    public JSONObject editAdvertisingType(AdvertisingType advertisingType,@RequestParam(value = "perIds[]",required = false) List<String> perIds)throws Exception {
        advertisingTypeService.saveAdvertisingType(advertisingType,perIds);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }


    //删除单个類型
    @PostMapping("/delAdvertisingTypeById.do")
    public JSONObject delAdvertisingTypeById(@RequestParam("advertisingTypeId") String advertisingTypeId)throws Exception {
        advertisingTypeService.delAdvertisingTypeByAdvertisingTypeId(advertisingTypeId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个類型
    @PostMapping("/delAllAdvertisingTypeById.do")
    public JSONObject delAllAdvertisingTypeById(@RequestParam("advertisingTypeIds[]") List<String> advertisingTypeIds)throws Exception {
        advertisingTypeService.delAllAdvertisingTypeByAdvertisingTypeId(advertisingTypeIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}

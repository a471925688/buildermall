//package com.noah_solutions.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.noah_solutions.entity.Color;
//import com.noah_solutions.ex.CodeAndMsg;
//import com.noah_solutions.service.IColorService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * 2018 12 20 lijun
// * 顏色相關控制器
// */
//@RestController
//@RequestMapping("color")
//public class ColorController {
//    @Resource
//    private IColorService colorService;
//
//
//
//
//    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
//    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
//    //添加顏色页面
////    @GetMapping("/addProductColorView")
////    public ModelAndView addProductColorView(){
////        return new ModelAndView("product/productColor/addOrUpdateProductColor");
////    }
//
//    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
//    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
//
//
//
//
//
//    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
//    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
//
//
//    //查询所有顏色
//    @RequestMapping("/getAllColor.do")
//    public JSONObject getAllColor(){
//        return CodeAndMsg.SUCESS.getJSON(colorService.selectAllColor());
//    }
//
////    @RequestMapping("/getAllProductColor.do")
////    public String getAllProductColor(){
//////        return CodeAndMsg.SUCESS.getJSON(colorService.selectAllProductColor());
////        String data = "{\"code\":\"0\",\"msg\":\"成功\",\"data\":[\n" +
////                "{ \"name\":\"測試1\" , \"value\":\"1\" },\n" +
////                "{ \"name\":\"測試2\" , \"value\":\"2\" },\n" +
////                "{ \"name\":\"測試3\" , \"value\":\"3\" }\n" +
////                "]}";
////        return data;
////    }
//    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
//    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
//
//
//
//
//
//
//
//
//
//
//
//    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
//    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
//    //添加顏色
//    @RequestMapping("/addColor.do")
//    public JSONObject addColor(Color color)throws Exception {
//        colorService.addColor(color);
//        return CodeAndMsg.ADDSUCESS.getJSON();
//    }
//    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
//    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
//
//
//
//}

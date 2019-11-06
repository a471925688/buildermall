package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.entity.ProductType;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IProductTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2018 11 30 lijun
 * 權限相關控制器
 */
@RestController
@RequestMapping("productType")
public class ProductTypeController {
    @Resource
    private IProductTypeService productTypeService;

    @Resource
    private ProductTypeCache productTypeCache;



    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////

    @GetMapping("/productTypeInfoView")
    //加载产品类型列表
    public ModelAndView productTypeInfoView(@RequestParam("productTypeId") String productTypeId){
        return new ModelAndView("product/productType/productTypeInfo","productTypeId",productTypeId);
    }
    //添加产品类型页面
    @GetMapping("/addProductTypeView")
    public ModelAndView addProductTypeView(@RequestParam("productTypeParentId")String productTypeParentId){
        return new ModelAndView("product/productType/addProductType","productTypeParentId",productTypeParentId);
    }

    //编辑产品类型页面
    @GetMapping("/editProductTypeView")
    public ModelAndView editProductTypeView(@RequestParam("productTypeId")String productTypeId){
        return new ModelAndView("product/productType/addProductType","productTypeId",productTypeId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //根据条件获取所有产品类型信息(树形页面处理)
    @RequestMapping("/getProductTypeByCont.do")
    public JSONObject getProductTypeByCont(ProductType condition){
            if(StringUtils.isEmpty(condition.getProductTypeParentId())){
                condition.setProductTypeParentId("0");
            }
//            return CodeAndMsg.SUCESS.getJSON(productTypeService.selectProductTypeByCont(condition));
        return CodeAndMsg.SUCESS.getJSON(productTypeCache.getProductTypesChildById(condition.getProductTypeParentId()));
    }

    //根据条件获取所有产品类型信息(树形页面处理)
    @RequestMapping("/getAllProductType.do")
    public JSONObject getAllProductType(){
        return CodeAndMsg.SUCESS.getJSON(productTypeCache.getAllProductTypes());
    }


    //根據等級(ProductTypeLevel,ProductTypeOrder)排序獲取所有产品类型
    @RequestMapping("/getAllProductTypeOrderByLv.do")
    public JSONObject getAllProductTypeOrderByLv(@RequestParam("level") Integer level){
        return CodeAndMsg.SUCESS.getJSON(productTypeCache.getProductTypesByLevel(level));
    }


    //根据条件查询产品类型数量
    @RequestMapping("/getCountProductTypeByCont.do")
    public JSONObject getCountProductTypeByCont(ProductType condition){
        if(StringUtils.isEmpty(condition.getProductTypeParentId())){
            condition.setProductTypeParentId("0");
        }
        return CodeAndMsg.SUCESS.getJSON(productTypeService.selectCountProductTypeByCont(condition));
    }

    //根据条件分页获取产品类型信息
    @RequestMapping("/getProductTypePageByCont.do")
    public JSONObject getProductTypePageByCont(ProductType condition,@RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer limit){
        Page<ProductType> productTypes = productTypeService.selectProductTypePageByCont(condition, PageRequest.of(page-1,limit));
        return CodeAndMsg.SUCESS.getJSON(productTypes.getContent()).fluentPut("count",productTypes.getTotalElements());
    }
    //查询单个产品类型
    @RequestMapping("/getProductTypeById.do")
    public JSONObject getProductTypeById(@RequestParam("productTypeId") String productTypeId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(productTypeCache.getProductTypesById(productTypeId));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加产品类型
    @RequestMapping("/addProductType.do")
    public JSONObject addProductType(ProductType productType)throws Exception {
        productTypeService.addProductType(productType);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑产品类型
    @RequestMapping("/editProductType.do")
    public JSONObject editProductType(ProductType productType)throws Exception {
        productTypeService.editProductType(productType);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //删除单个产品类型
    @PostMapping("/delProductTypeById.do")
    public JSONObject delProductTypeById(@RequestParam("productTypeId") String productTypeId)throws Exception {
        productTypeService.delProductTypeByProductTypeId(productTypeId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个产品类型
    @PostMapping("/delAllProductTypeById.do")
    public JSONObject delAllProductTypeById(@RequestParam("productTypeIds[]") List<String> productTypeIds)throws Exception {
        productTypeService.delAllProductTypeByProductTypeId(productTypeIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////




    //////////////////////////////特殊需求  start//////////////////////////////////////////////////////////
    //////////////////////////////特殊需求  start//////////////////////////////////////////////////////////
    //数据对接(把旧版本的商品类型数据对接过来)
//    @GetMapping("/copyOldType.do")
//    public JSONObject copyOldType(){
//
//
//
//        productTypeService.copyOldType();
//        return CodeAndMsg.SUCESS.getJSON();
//    }

    //////////////////////////////特殊需求  end//////////////////////////////////////////////////////////
    //////////////////////////////特殊需求  end//////////////////////////////////////////////////////////
}

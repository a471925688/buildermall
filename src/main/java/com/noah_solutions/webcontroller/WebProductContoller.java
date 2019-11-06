package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.ProductTable;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.TablePage;
import com.noah_solutions.controller.BaseController;
import com.noah_solutions.entity.ProductEvaluation;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IAdminService;
import com.noah_solutions.service.IBrandService;
import com.noah_solutions.service.IProductService;
import io.swagger.annotations.*;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.noah_solutions.ex.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/webProduct")
@Api(value = "Web端產品相關控制器")
public class WebProductContoller  extends BaseController{
    @Resource
    private IProductService productService;
    @Resource
    private IBrandService brandService;
    @Resource
    private IAdminService adminService;

//    @ApiOperation(value="獲取所有產品", notes="根據條件返回所有產品")
//    @ApiImplicitParams({
//            @ApiImplicitParam( paramType = "query", name = "productTypeId",dataType = "String",value = "產品類型ID", required = false),
//            @ApiImplicitParam( paramType = "query", name = "brandId",dataType = "String",value = "品牌ID", required = false),
//            @ApiImplicitParam( paramType = "query", name = "searchVal", dataType = "String",value = "搜索條件", required = false),
//            @ApiImplicitParam( paramType = "query", name = "page",dataType = "Integer",value = "頁數", required = true),
//            @ApiImplicitParam( paramType = "query", name = "limit", dataType = "Integer",value = "條數", required = true)
//    })
//    @ApiResponses({
//            @ApiResponse(code = 1, message = "ok"),
//            @ApiResponse(code = 0, message = "平台異常"),
//    })
//    @PostMapping("/getProductPageByProduct.do")
//    public JSONObject getProductPageByProduct( @RequestParam(value = "productTypeId",required = false) String productTypeId,
//                                               @RequestParam(value = "brandId",required = false) String brandId,
//                                               @RequestParam(value = "searchVal",required = false) String searchVal,
//                                               @RequestParam(value = "admin",required = false) String admin,
//                                               @RequestParam("page") Integer page, @RequestParam("limit") Integer limit,HttpServletResponse response){
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        TablePage<ProductTable> products = productService.selectProductPageByContent(admin,productTypeId,brandId,searchVal, PageRequest.of(page-1,limit),null);
//        return CodeAndMsg.SUCESS.getJSON(products.getContent()).fluentPut("count",products.getTotalElements());
//    }

    @ApiOperation(value="獲取所有產品", notes="根據條件返回所有產品")
    @ApiImplicitParams({
            @ApiImplicitParam( paramType = "query", name = "productTypeId",dataType = "String",value = "產品類型ID", required = false),
            @ApiImplicitParam( paramType = "query", name = "brandId",dataType = "String",value = "品牌ID", required = false),
            @ApiImplicitParam( paramType = "query", name = "searchVal", dataType = "String",value = "搜索條件", required = false),
            @ApiImplicitParam( paramType = "query", name = "page",dataType = "Integer",value = "頁數", required = true),
            @ApiImplicitParam( paramType = "query", name = "limit", dataType = "Integer",value = "條數", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getProductPageByProduct.do")
    public JSONObject getProductPageByProduct( @RequestParam(value = "productTypeId",required = false) String productTypeId,
                                               @RequestParam(value = "brandId",required = false) String brandId,
                                               @RequestParam(value = "searchVal",required = false) String searchVal,
                                               @RequestParam(value = "adminId",required = false) String adminId,
                                               @RequestParam(value = "order",required = false) Integer order,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("limit") Integer limit){
        TablePage<ProductTable> products = productService.selectProductPageByContent(adminId,productTypeId,brandId,searchVal, PageRequest.of(page-1,limit),order);
        return SUCESS.getJSON(products.getContent()).fluentPut("count",products.getTotalElements());
    }


    @ApiOperation(value="獲取所有產品", notes="根據條件返回所有產品")
    @ApiImplicitParams({
            @ApiImplicitParam( paramType = "query", name = "productTypeId",dataType = "String",value = "產品類型ID", required = false),
            @ApiImplicitParam( paramType = "query", name = "brandId",dataType = "String",value = "品牌ID", required = false),
            @ApiImplicitParam( paramType = "query", name = "searchVal", dataType = "String",value = "搜索條件", required = false),
            @ApiImplicitParam( paramType = "query", name = "page",dataType = "Integer",value = "頁數", required = true),
            @ApiImplicitParam( paramType = "query", name = "limit", dataType = "Integer",value = "條數", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getAllSupplierName.do")
    public JSONObject getAllSupplierName(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(adminService.getAllSupplierName());
    }


    @ApiOperation(value="分页获取产品评价", notes="分页获取产品评价")
    @ApiImplicitParams({
            @ApiImplicitParam( paramType = "query", name = "productId",dataType = "String",value = "產品ID", required = false),
            @ApiImplicitParam( paramType = "query", name = "page",dataType = "Integer",value = "頁數", required = true),
            @ApiImplicitParam( paramType = "query", name = "limit", dataType = "Integer",value = "條數", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getProductEvaluationPageByProduct.do")
    public JSONObject getProductEvaluationPageByProductId( @RequestParam(value = "productId") String productId,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("limit") Integer limit){
        Page<ProductEvaluation> productEvaluations = productService.selectProductEvaluationPageByProductId(productId, PageRequest.of(page-1,limit));
        return CodeAndMsg.SUCESS.getJSON(productEvaluations.getContent()).fluentPut("count",productEvaluations.getTotalElements());
    }

    @ApiOperation(value="查看产品页面", notes="根据产品id获取产品详情")
    @ApiImplicitParam(paramType = "query", name = "productId",dataType = "String",value = "產品ID")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getProductByProductId.do")
    public JSONObject getProductByProductId(@RequestParam("productId")String productId,HttpSession session,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(productService.selectProductById(productId,getLoginUserId(session)));
    }


    @ApiOperation(value="獲取產品類型", notes="獲取所有產品類型")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getAllProductType.do")
    public JSONObject getAllProductType(){
        return CodeAndMsg.SUCESS.getJSON(ProductTypeCache.getAllProductTypes());
    }



    @ApiOperation(value="獲取熱門產品類型", notes="獲取熱門產品類型")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getFirst5OrderByProductSelloutNumDesc.do")
    public JSONObject getFirst5OrderByProductSelloutNumDesc(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(ProductTypeCache.getFirst5OrderByProductSelloutNumDesc());
    }



    @ApiOperation(value="獲取品牌", notes="獲取所有品牌")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getAllBrand.do")
    public JSONObject getAllBrand(){
        return CodeAndMsg.SUCESS.getJSON(brandService.selectAllBrand());
    }



    @ApiOperation(value="获取所有1,2级产品类型", notes="首页左侧的类型初始化")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getLv1AndLv2ProductTypes.do")
    public JSONObject getLv1AndLv2ProductTypes(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(ProductTypeCache.getLv1AndLv2ProductTypes());
    }

    @ApiOperation(value="获取所有產品類型樹", notes="獲取樹形結構產品類型")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getAllProductTypeTree.do")
    public JSONObject getAllProductTypeTree(){
        return CodeAndMsg.SUCESS.getJSON(ProductTypeCache.getProductTypesZtree());
    }


    @ApiOperation(value="獲取最近瀏覽的產品")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/productBrowse.do")
    public JSONObject getProductBrowse(@RequestParam("page") Integer page,
                                       @RequestParam("limit") Integer limit,
                                       HttpSession session,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(productService.getProductBrowse(getLoginUserId(session),page,limit));
    }
}

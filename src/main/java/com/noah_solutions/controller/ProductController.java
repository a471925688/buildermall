package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.ProductTable;
import com.noah_solutions.common.IdManage;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.common.TablePage;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Product;
import com.noah_solutions.entity.TemProduct;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IProductService;
import com.noah_solutions.service.ITemProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

import static com.noah_solutions.common.ProjectConfig.LOGIN_ADMIN;
import static com.noah_solutions.ex.CodeAndMsg.DELSUCESS;
import static com.noah_solutions.ex.CodeAndMsg.HANDLESUCESS;

/**
 * 2018 11 30 lijun
 * 權限相關控制器
 */
@RestController
@RequestMapping("product")
public class ProductController {
    @Resource
    private IProductService productService;

    @Resource
    private ITemProductService temProductService;

    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////

    //添加产品页面
    @GetMapping("/addProductView")
    public ModelAndView addProductView(@RequestParam(value = "copyProductId",required = false)String copyProductId){

        return new ModelAndView("product/product/addProduct","copyProductId",copyProductId);
    }

    //编辑产品页面
    @GetMapping("/editProductView")
    public ModelAndView editProductView(@RequestParam("productId")String productId){
        return new ModelAndView("product/product/addProduct","productId",productId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //根据条件分页获取产品类型信息
    @RequestMapping("/getProductPageByCont.do")
    public JSONObject getProductPageByCont(Product condition, @RequestParam("page") Integer page,
                                           @RequestParam("limit") Integer limit){
        Page<Product> products = productService.selectProductPageByCont(condition, PageRequest.of(page-1,limit));
        return CodeAndMsg.SUCESS.getJSON(products.getContent()).fluentPut("count",products.getTotalElements());
    }

    //根据条件分页获取产品类型信息
    @RequestMapping("/getProductPageByProduct.do")
    public JSONObject getProductPageByProduct(Product product, @RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer limit,HttpSession session){
        product.setAdminId(chekckAdminType(session));
        TablePage<ProductTable> products = productService.selectProductPageByProductCont(product, PageRequest.of(page-1,limit));
        return CodeAndMsg.SUCESS.getJSON(products.getContent()).fluentPut("count",products.getTotalElements());
    }

    //根据条件分页获取产品类型信息
    @RequestMapping("/text.do")
    public JSONObject getProductPageByText(){

        return CodeAndMsg.SUCESS.getJSON();
    }

    //查询单个產品
    @RequestMapping("/getProductById.do")
    public JSONObject getProductById(@RequestParam("productId") String productId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(productService.selectProductById(productId,null));
    }

    //查询所有產品單位
    @RequestMapping("/getAllProductUnit.do")
    public JSONObject getAllProductUnit()throws Exception {
        return CodeAndMsg.SUCESS.getJSON(productService.selectAllProductUnit());
    }

    //查询訂單数量
    @RequestMapping("/getCountProductByCont.do")
    public JSONObject getCountProductByCont(Product product){
        return CodeAndMsg.SUCESS.getJSON(productService.selectCountProductByCont(product));
    }
    //售出总数
    @RequestMapping("/getTotalSold.do")
    public JSONObject getTotalSold(){
        return CodeAndMsg.SUCESS.getJSON(productService.selectTotalSold());
    }
    //獲取複製產品數據
    @RequestMapping("/getCopyProduct.do")
    public JSONObject getCopyProduct(@RequestParam("productId")String productId,
                                     HttpSession session)throws Exception {
        return CodeAndMsg.ADDSUCESS.getJSON(      productService.getCopyProduct(productId));
    }




    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////



    @RequestMapping("/addProduct.do")
    @Transactional
    public JSONObject addProduct(@RequestParam("productInfo")String productInfo,
                                 HttpSession session)throws Exception {
        Product product =  JSONObject.parseObject(productInfo, Product.class);
        Admin admin = (Admin)session.getAttribute(LOGIN_ADMIN);
        if(!admin.getAdminType().equals(ProjectConfig.AdminType.ADMIN.getValue())){
            temProductService.saveTemProduct(new TemProduct(productInfo,product.getProductId(),admin.getAdminId()));
        }else {
            product.setAdminId(admin.getAdminId());
            productService.addProduct(product);
        }
        return CodeAndMsg.ADDSUCESS.getJSON();
    }

    @RequestMapping("/editProduct.do")
    public JSONObject editProduct(@RequestParam("productInfo")String productInfo,
                                 HttpSession session)throws Exception {
        Product product =  JSONObject.parseObject(productInfo, Product.class);
        Admin admin = (Admin)session.getAttribute(LOGIN_ADMIN);
        if(!admin.getAdminType().equals(ProjectConfig.AdminType.ADMIN.getValue())){
            temProductService.saveTemProduct(new TemProduct(productInfo,product.getProductId(),admin.getAdminId()));
        }else {
            productService.updateProduct(product);
        }
        return CodeAndMsg.ADDSUCESS.getJSON();
    }

//    //删除单个产品类型
    @PostMapping("/delProductById.do")
    public JSONObject delProductById(@RequestParam("productId") String productId)throws Exception {
        productService.delProductByProductId(productId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }
//
//    删除多个产品类型
    @PostMapping("/delAllProductById.do")
    public JSONObject delAllProductById(@RequestParam("productIds[]") List<String> productIds)throws Exception {
        productService.delAllProductByProductId(productIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //改变上下架状态
    @RequestMapping("/editProductState.do")
    public JSONObject editProductState(@RequestParam("productState") Boolean productState,@RequestParam("productId") String productId)throws Exception {
        productService.changeProductState(productState,productId);
        return HANDLESUCESS.getJSON();
    }
    /**
     * 刪除size
     * @param productSizeId
     * @return
     */
    @PostMapping("/delProductSizeByProductSizeId.do")
    public JSONObject delProductSizeByProductSizeId(@RequestParam("productSizeId")String productSizeId){
        productService.delProductSizeByProductSizeId(productSizeId);
        return DELSUCESS.getJSON();
    }
    /**
     * 刪除Specifications
     * @param productSpecificationsId
     * @return
     */
    @PostMapping("/delProductSpecificationsByProductSpecificationsId.do")
    public JSONObject delProductSpecificationsByProductSpecificationsId(@RequestParam("productSpecificationsId")String productSpecificationsId){
        productService.delProductSpecificationsByProductSpecificationsId(productSpecificationsId);
        return DELSUCESS.getJSON();
    }
    /**
     * 刪除Config
     * @param productConfigId
     * @return
     */
    @PostMapping("/delProductConfigByProductConfigId.do")
    public JSONObject delProductConfigByProductConfigId(@RequestParam(value = "productConfigId",required = false)String productConfigId){
        productService.delProductConfigByProductConfigId(productConfigId);
        return DELSUCESS.getJSON();
    }

    /**
     * 批量刪除Config
     * @param productConfigIds
     * @return
     */
    @PostMapping("/delAllProductConfigByProductConfigId.do")
    public JSONObject delAllProductConfigByProductConfigId(@RequestParam("productConfigIds[]")List productConfigIds){
        productService.delAllProductConfigByProductConfigId(productConfigIds);
        return DELSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////




    //////////////////////////////業務部分  start//////////////////////////////////////////////////////////
    //////////////////////////////業務部分  start//////////////////////////////////////////////////////////
    //檢測管理員類型,并返回供應商id
    private String chekckAdminType(HttpSession session){
        String adminId = null;
        Admin admin = (Admin)session.getAttribute(LOGIN_ADMIN);
        if(admin.getAdminType()== ProjectConfig.AdminType.SUPPLIER.getValue())
            adminId = admin.getAdminId();
        return adminId;
    }

    //////////////////////////////業務部分  end//////////////////////////////////////////////////////////
    //////////////////////////////業務部分  end//////////////////////////////////////////////////////////






    //////////////////////////////特殊需求  start//////////////////////////////////////////////////////////
    //////////////////////////////特殊需求  start//////////////////////////////////////////////////////////



    //////////////////////////////特殊需求  end//////////////////////////////////////////////////////////
    //////////////////////////////特殊需求  end//////////////////////////////////////////////////////////
}

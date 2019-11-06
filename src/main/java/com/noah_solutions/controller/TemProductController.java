package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.TemProduct;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IProductService;
import com.noah_solutions.service.ITemProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.noah_solutions.common.ProjectConfig.LOGIN_ADMIN;
import static com.noah_solutions.ex.CodeAndMsg.SUCESS;

/**
 * 2018 11 30 lijun
 * 管理員相關控制器
 */
@RestController
@RequestMapping("/temProduct")
public class TemProductController {
    @Resource
    private ITemProductService temProductService;

    @Resource
    private IProductService productService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //查看待审核产品詳情
    @GetMapping("/temProductDetailView")
    public ModelAndView temProductDetailView(@RequestParam("temProductId")String temProductId){
        return new ModelAndView("transaction/temProduct/temProductDetailView","temProductId",temProductId);
    }
   
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有待审核产品
    @RequestMapping("/getAllTemProduct.do")
    public JSONObject getAllTemProduct(){
        return SUCESS.getJSON(temProductService.selectAllTemProduct());
    }
    
    //根据条件分页获取待审核产品信息
    @RequestMapping("/getTemProductPageByCont.do")
    public JSONObject getTemProductPageByCont(TemProduct condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                         HttpSession session){
        condition.setAdminId(chekckAdminType(session));
        Page<TemProduct> temProducts = temProductService.selectTemProductPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.ASC, "temProductState")));
        return SUCESS.getJSON(temProducts.getContent()).fluentPut("count",temProducts.getTotalElements());
    }

    //查询待审核产品数量
    @RequestMapping("/getCountTemProduct.do")
    public JSONObject getCountTemProduct(){
        return SUCESS.getJSON(temProductService.selectCountTemProductByCont(new TemProduct()));
    }
    //查询待审核产品数量
    @RequestMapping("/getCountTemProductByCont.do")
    public JSONObject getCountTemProductByCont(TemProduct temProduct){
        return SUCESS.getJSON(temProductService.selectCountTemProductByCont(temProduct));
    }
    //查询單個待审核产品
    @RequestMapping("/getTemProductById.do")
    public JSONObject getTemProductById(@RequestParam("temProductId") String temProductId){
        return SUCESS.getJSON(temProductService.getTemProductById(temProductId));
    }

    //分組獲取待审核产品數量（审核狀態）
    @RequestMapping("/getCountTemProductPaymentStatus.do")
    public JSONObject getCountTemProductPaymentStatus(){
        return SUCESS.getJSON(temProductService.selectCountTemProductPaymentStatus());
    }

    //分組獲取待审核产品數量（待审核产品狀態）
    @RequestMapping("/getCountTemProductState.do")
    public JSONObject getCountTemProductState(HttpSession session){
        return SUCESS.getJSON(temProductService.selectCountTemProductState(chekckAdminType(session)));
    }


    //分組獲取待审核产品數量（待审核产品狀態）
    @RequestMapping("/getCountTemProductRefundState.do")
    public JSONObject getCountTemProductRefundState(HttpSession session){
        return CodeAndMsg.SUCESS.getJSON(temProductService.selectCountTemProductByState(chekckAdminType(session)));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////









    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //通過審核
    @PostMapping("/auditPass.do")
    public JSONObject auditPass(@RequestParam("temProductIds[]") List<String> temProductIds)throws Exception {
        productService.auditPass(temProductIds);
        return CodeAndMsg.HANDLESUCESS.getJSON();
    }
    //審核失敗
    @PostMapping("/auditReject.do")
    public JSONObject auditReject(@RequestParam("temProductIds[]") List<String> temProductIds)throws Exception {
        productService.auditReject(temProductIds);
        return CodeAndMsg.HANDLESUCESS.getJSON();
    }


//    //删除单个待审核产品
//    @PostMapping("/delTemProductById.do")
//    public JSONObject delTemProductById(@RequestParam("temProductId") String temProductId)throws Exception {
//        temProductService.delTemProductByTemProductId(temProductId);
//        return CodeAndMsg.DELSUCESS.getJSON();
//    }
//
//    //删除多个待审核产品
//    @PostMapping("/delAllTemProductById.do")
//    public JSONObject delAllTemProductById(@RequestParam("temProductIds[]") List<String> temProductIds)throws Exception {
//        temProductService.delAllTemProductByTemProductId(temProductIds);
//        return CodeAndMsg.DELSUCESS.getJSON();
//    }

    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////

    //檢測管理員類型,并返回供應商id
    private String chekckAdminType(HttpSession session){
        String adminId = null;
        Admin admin = (Admin)session.getAttribute(LOGIN_ADMIN);
        if(admin.getAdminType()== ProjectConfig.AdminType.SUPPLIER.getValue())
            adminId = admin.getAdminId();
        return adminId;
    }



}

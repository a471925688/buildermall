package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.SupplierApply;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IProductService;
import com.noah_solutions.service.ISupplierApplyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
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
@RequestMapping("/supplier")
public class SupplierApplyController {
    @Resource
    private ISupplierApplyService supplierApplyService;

    @Resource
    private IProductService productService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////


    //添加产品类型页面
    @GetMapping("/registerSuppiesDetail")
    public ModelAndView registerSuppiesDetail(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return new ModelAndView("supplier/registerSuppiesDetail");
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有待审核供应申请
    @RequestMapping("/getAllSupplierApply.do")
    public JSONObject getAllSupplierApply(){
        return SUCESS.getJSON(supplierApplyService.selectAllSupplierApply());
    }
    
    //根据条件分页获取待审核供应申请信息
    @RequestMapping("/getSupplierApplyPageByCont.do")
    public JSONObject getSupplierApplyPageByCont(SupplierApply condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                         HttpSession session){
        Page<SupplierApply> supplierApplys = supplierApplyService.selectSupplierApplyPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.ASC, "state")));
        return SUCESS.getJSON(supplierApplys.getContent()).fluentPut("count",supplierApplys.getTotalElements());
    }

    //查询待审核供应申请数量
    @RequestMapping("/getCountSupplierApply.do")
    public JSONObject getCountSupplierApply(){
        return SUCESS.getJSON(supplierApplyService.selectCountSupplierApplyByCont(new SupplierApply()));
    }
    //查询待审核供应申请数量
    @RequestMapping("/getCountSupplierApplyByCont.do")
    public JSONObject getCountSupplierApplyByCont(SupplierApply supplierApply){
        return SUCESS.getJSON(supplierApplyService.selectCountSupplierApplyByCont(supplierApply));
    }
    //查询單個待审核供应申请
    @RequestMapping("/getSupplierApplyById.do")
    public JSONObject getSupplierApplyById(@RequestParam("supplierApplyId") String supplierApplyId){
        return SUCESS.getJSON(supplierApplyService.getSupplierApplyById(supplierApplyId));
    }

    //分組獲取待审核供应申请數量（审核狀態）
    @RequestMapping("/getCountSupplierApplyPaymentStatus.do")
    public JSONObject getCountSupplierApplyPaymentStatus(){
        return SUCESS.getJSON(supplierApplyService.selectCountSupplierApplyPaymentStatus());
    }

    //分組獲取待审核供应申请數量（待审核供应申请狀態）
    @RequestMapping("/getCountSupplierApplyState.do")
    public JSONObject getCountSupplierApplyState(HttpSession session){
        return SUCESS.getJSON(supplierApplyService.selectCountSupplierApplyState(chekckAdminType(session)));
    }


    //分組獲取待审核供应申请數量（待审核供应申请狀態）
    @RequestMapping("/getCountSupplierApplyRefundState.do")
    public JSONObject getCountSupplierApplyRefundState(HttpSession session){
        return CodeAndMsg.SUCESS.getJSON(supplierApplyService.selectCountSupplierApplyByState(chekckAdminType(session)));
    }



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////









    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    //通過審核
    @PostMapping("/auditPass.do")
    public JSONObject auditPass(@RequestParam("supplierApplyIds[]") List<String> supplierApplyIds)throws Exception {
        supplierApplyService.auditPass(supplierApplyIds);
        return CodeAndMsg.HANDLESUCESS.getJSON();
    }
    //審核失敗
    @PostMapping("/auditReject.do")
    public JSONObject auditReject(@RequestParam("supplierApplyIds[]") List<String> supplierApplyIds)throws Exception {
        supplierApplyService.auditReject(supplierApplyIds);
        return CodeAndMsg.HANDLESUCESS.getJSON();
    }


//    //删除单个待审核供应申请
//    @PostMapping("/delSupplierApplyById.do")
//    public JSONObject delSupplierApplyById(@RequestParam("supplierApplyId") String supplierApplyId)throws Exception {
//        supplierApplyService.delSupplierApplyBySupplierApplyId(supplierApplyId);
//        return CodeAndMsg.DELSUCESS.getJSON();
//    }
//
//    //删除多个待审核供应申请
//    @PostMapping("/delAllSupplierApplyById.do")
//    public JSONObject delAllSupplierApplyById(@RequestParam("supplierApplyIds[]") List<String> supplierApplyIds)throws Exception {
//        supplierApplyService.delAllSupplierApplyBySupplierApplyId(supplierApplyIds);
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

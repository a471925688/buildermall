package com.noah_solutions.controller;


import com.noah_solutions.common.ProjectConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @GetMapping("/index")
    public String index(HttpSession session){
        //清楚session
        session.removeAttribute(ProjectConfig.LOGIN_ADMIN);
        return "index";
    }
    @GetMapping("/main")
    public String main(Model model){
       model.addAttribute("ctx",ProjectConfig.HOST_NAME);
       return "main";
   }
    //显示主页
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    //////////////////////////管理员管理   start////////////////////////////
    //////////////////////////管理员管理   start////////////////////////////
//    //权限管理
//    @GetMapping("/adminCompetence")
//    public String adminCompetence(){
//        return "admin/adminCompetence.html";
//    }
    //用户列表
    @GetMapping("/adminView")
    public String adminView(){
        return "admin/admin/adminView";
    }

    //权限列表
    @GetMapping("/permissionView")
    public String permissionView(){
        return "admin/permission/permissionView";
    }
    //角色列表
    @GetMapping("/roleView")
    public String roleView(){
        return "admin/role/roleView";
    }
    //个人信息
    @GetMapping("/adminInfoView")
    public String adminInfoView(){
        return "admin/info/adminInfoView";
    }


    //////////////////////////管理员管理   end////////////////////////////
    //////////////////////////管理员管理   end////////////////////////////

    //////////////////////////会员管理   start///////////////////////////
    //////////////////////////会员管理   start////////////////////////////

    @GetMapping("/userListView")
    public String userListView(){
        return "user/userInfo/userListView";
    }
    //////////////////////////会员管理   end////////////////////////////
    //////////////////////////会员管理   end////////////////////////////


    //////////////////////////優惠碼管理   start///////////////////////////
    //////////////////////////優惠碼管理   start////////////////////////////

    @GetMapping("/discountCodeListView")
    public String discountCodeListView(){
        return "discountCode/discountCodeListView";
    }

    //////////////////////////優惠碼管理   end////////////////////////////
    //////////////////////////優惠碼管理   end////////////////////////////



    //////////////////////////产品管理  start///////////////////////////
    //////////////////////////产品管理   start////////////////////////////
    //产品审核列表
    @GetMapping("/temProductView")
    public String temProductView(){
        return "/product/product/temProductView";
    }

    //产品列表
    @GetMapping("/productView")
    public String productView(){
        return "product/product/productView";
    }
    //產品類型
    @GetMapping("/productTypeView")
    public String productTypeView(){
        return "/product/productType/productTypeView";
    }
    //品牌列表
    @GetMapping("/brandView")
    public String brandView(){
        return "/product/brand/brandView";
    }

    //////////////////////////产品管理  end///////////////////////////
    //////////////////////////产品管理   end////////////////////////////


    //////////////////////////交易管理  start///////////////////////////
    //////////////////////////交易管理   start////////////////////////////
    //订单管理
    @GetMapping("/orderManagementView")
    public String orderManagementView(){
        return "transaction/order/orderManagementView";
    }
//    public String orderManagementView(){
//        return "transaction/order/text";
//    }
    @GetMapping("/orderRefundView")
    public String orderRefundView(){
    return "transaction/order/orderRefundView";
    }

    //////////////////////////交易管理  end///////////////////////////
    //////////////////////////交易管理   end////////////////////////////

    //////////////////////////供应商申请管理  start///////////////////////////
    //////////////////////////供应商申请管理   start////////////////////////////
    //供应商管理
    @GetMapping("/supplierApplyView")
    public String supplierApplyView(){
        return "supplier/supplierApplyView";
    }
    //////////////////////////供应商申请管理  end///////////////////////////
    //////////////////////////供应商申请管理   end////////////////////////////


    //////////////////////////圖片管理  start///////////////////////////
    //////////////////////////圖片管理   start////////////////////////////
    //图片管理
    @GetMapping("/systemView")
    public String systemView(){
        return "system/systemView";
    }

    //////////////////////////圖片管理  end///////////////////////////
    //////////////////////////圖片管理   end////////////////////////////


    //////////////////////////系統管理  start///////////////////////////
    //////////////////////////系統管理   start////////////////////////////
    //圖片管理
    @GetMapping("/advertisingView")
    public String advertisingView(){
        return "picture/advertisingView";
    }
    @GetMapping("/advertisingTypeView")
    public String advertisingTypeView(){
        return "picture/advertisingTypeView";
    }

    //////////////////////////系統管理  end///////////////////////////
    //////////////////////////系統管理   end////////////////////////////

    //测试
    @GetMapping("/ces")
    public String text(){
        return "ces/test";
    }

}

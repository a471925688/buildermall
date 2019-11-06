package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.DiscountCode;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IDiscountCodeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 2018 11 30 lijun
 * 權限相關控制器
 */
@RestController
@RequestMapping("discountCode")
public class DiscountCodeController {
    @Resource
    private IDiscountCodeService discountCodeService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加用户页面
    @GetMapping("/addDiscountCodeView")
    public ModelAndView addDiscountCodeView(){
        return new ModelAndView("discountCode/addOrUpdateDiscountCode");
    }

    //编辑用户页面
    @GetMapping("/editDiscountCodeView")
    public ModelAndView editDiscountCodeView(@RequestParam("discountCodeId")String discountCodeId){
        return new ModelAndView("discountCode/addOrUpdateDiscountCode","discountCodeId",discountCodeId);
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有用户
    @RequestMapping("/getAllDiscountCode.do")
    public JSONObject getAllDiscountCode(){
        return CodeAndMsg.SUCESS.getJSON(discountCodeService.selectAllDiscountCode());
    }
    //根据条件分页获取用户信息
    @RequestMapping("/getDiscountCodePageByCont.do")
    public JSONObject getDiscountCodePageByCont(DiscountCode condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit){
        Page<DiscountCode> discountCodes = discountCodeService.selectDiscountCodePageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "discountCodeId")));
        return CodeAndMsg.SUCESS.getJSON(discountCodes.getContent()).fluentPut("count",discountCodes.getTotalElements());
    }
    //查询单个用户
    @RequestMapping("/getDiscountCodeById.do")
    public JSONObject getDiscountCodeById(@RequestParam("discountCodeId") String discountCodeId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(discountCodeService.selectDiscountCodeByDiscountCodeId(discountCodeId));
    }
    //查询用户数量
    @RequestMapping("/getCountDiscountCode.do")
    public JSONObject getCountDiscountCode(){
        return CodeAndMsg.SUCESS.getJSON(discountCodeService.selectCountDiscountCodeByCont(new DiscountCode()));
    }
    //查询用户数量
    @RequestMapping("/getCountDiscountCodeByCont.do")
    public JSONObject getCountDiscountCodeByCont(DiscountCode discountCode){
        return CodeAndMsg.SUCESS.getJSON(discountCodeService.selectCountDiscountCodeByCont(discountCode));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加用户
    @RequestMapping("/addDiscountCode.do")
    public JSONObject addDiscountCode(DiscountCode discountCode, HttpSession session)throws Exception {
        discountCode.setAdminId(((Admin)session.getAttribute(ProjectConfig.LOGIN_ADMIN)).getAdminId());
        discountCodeService.addDiscountCode(discountCode);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑用户
    @RequestMapping("/editDiscountCode.do")
    public JSONObject editDiscountCode(DiscountCode discountCode)throws Exception {
        discountCodeService.updateDiscountCode(discountCode);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    //改变用户状态
    @RequestMapping("/editDiscountCodeIsProhibit.do")
    public JSONObject editDiscountCodeIsProhibit(@RequestParam("discountCodeIsProhibit") Boolean discountCodeIsProhibit,@RequestParam("discountCodeId") String discountCodeId)throws Exception {
        discountCodeService.changeDiscountCodeIsProhibit(discountCodeIsProhibit,discountCodeId);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //删除单个用户
    @PostMapping("/delDiscountCodeById.do")
    public JSONObject delDiscountCodeById(@RequestParam("discountCodeId") String discountCodeId)throws Exception {
        discountCodeService.delDiscountCodeByDiscountCodeId(discountCodeId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个用户
    @PostMapping("/delAllDiscountCodeById.do")
    public JSONObject delAllDiscountCodeById(@RequestParam("discountCodeIds[]") List<String> discountCodeIds)throws Exception {
        discountCodeService.delAllDiscountCodeByDiscountCodeId(discountCodeIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IAdminService;
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
 * 管理員相關控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private IAdminService adminService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加用户页面
    @GetMapping("/addAdminView")
    public ModelAndView addAdminView(){
        return new ModelAndView("admin/admin/addOrUpdateAdmin");
    }

    //编辑用户页面
    @GetMapping("/editAdminView")
    public ModelAndView editAdminView(@RequestParam("adminId")String adminId){
        return new ModelAndView("admin/admin/addOrUpdateAdmin","adminId",adminId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有管理员
    @RequestMapping("/getAllAdmin.do")
    public JSONObject getAllAdmin(){
        return CodeAndMsg.SUCESS.getJSON(adminService.selectAllAdmin());
    }

    //查询所有管理员
    @RequestMapping("/getAllAdminByCont.do")
    public JSONObject getAllAdminByCont(Admin admin){
        return CodeAndMsg.SUCESS.getJSON(adminService.getAllAdminByCont(admin));
    }
    //根据条件分页获取管理员信息
    @RequestMapping("/getAdminPageByCont.do")
    public JSONObject getAdminPageByCont(Admin condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit){
        Page<Admin> admins = adminService.selectAdminPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "adminId")));
        return CodeAndMsg.SUCESS.getJSON(admins.getContent()).fluentPut("count",admins.getTotalElements());
    }
    //查询单个管理员
    @RequestMapping("/getAdminById.do")
    public JSONObject getAdminById(@RequestParam("adminId") String adminId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(adminService.selectAdminByAdminId(adminId));
    }
    //分组查询管理员数量
    @RequestMapping("/getAllCountGroupByRoleId.do")
    public  JSONObject getAllCountGroupByRoleId(){
        return CodeAndMsg.SUCESS.getJSON(adminService.selecrAllCountGroupByRoleId());
    }
    //查询管理员数量
    @RequestMapping("/getCountAdmin.do")
    public JSONObject getCountAdmin(){
        return CodeAndMsg.SUCESS.getJSON(adminService.selectCountAdminByCont(new Admin()));
    }
    //查询管理员数量
    @RequestMapping("/getCountAdminByCont.do")
    public JSONObject getCountAdminByCont(Admin admin){
        return CodeAndMsg.SUCESS.getJSON(adminService.selectCountAdminByCont(admin));
    }
    //獲取管理員登錄次數
    @RequestMapping("/getCountLoginByAdminType.do")
    public JSONObject getCountLoginByAdminType(@RequestParam("adminType") Integer adminType){
        return CodeAndMsg.SUCESS.getJSON(adminService.getCountLoginByAdminType(adminType));
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加用户
    @RequestMapping("/addAdmin.do")
    public JSONObject addAdmin(Admin admin, Login login)throws Exception {
        adminService.addAdmin(admin,login);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑用户
    @RequestMapping("/editAdmin.do")
    public JSONObject editAdmin(Admin admin,Login login)throws Exception {
        adminService.updateAdmin(admin,login);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    //改变用户状态
    @RequestMapping("/editAdminIsProhibit.do")
    public JSONObject editAdminIsProhibit(@RequestParam("adminIsProhibit") Boolean adminIsProhibit,@RequestParam("adminId") String adminId)throws Exception {
        adminService.changeAdminIsProhibit(adminIsProhibit,adminId);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    //修改当前管理员信息
    @RequestMapping("/editCurrentAdminInfo.do")
    public JSONObject editCurrentAdminInfo(Admin admin,Login login,HttpSession session)throws Exception {
        adminService.editCurrentAdminInfo(admin,login,(Admin) session.getAttribute(ProjectConfig.LOGIN_ADMIN));
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    //修改密码
    @PostMapping("/editLoginPassWord.do")
    public JSONObject editLoginPassWord(@RequestParam("oldPassWord") String oldPassWord, @RequestParam("newPassWord") String newPassWord, HttpSession session)throws Exception {
        adminService.updateLoginPassWord(oldPassWord,newPassWord, (Admin) session.getAttribute(ProjectConfig.LOGIN_ADMIN));
        return CodeAndMsg.HANDLESUCESS.getJSON();
    }
    //删除单个用户
    @PostMapping("/delAdminById.do")
    public JSONObject delAdminById(@RequestParam("adminId") String adminId)throws Exception {
        adminService.delAdminByAdminId(adminId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个用户
    @PostMapping("/delAllAdminById.do")
    public JSONObject delAllAdminById(@RequestParam("adminIds[]") List<String> adminIds)throws Exception {
        adminService.delAllAdminByAdminId(adminIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}

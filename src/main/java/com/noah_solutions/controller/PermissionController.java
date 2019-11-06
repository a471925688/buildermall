package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.Permission;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IPermissionService;
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
@RequestMapping("permission")
public class PermissionController {
    @Resource
    private IPermissionService permissionService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////

    @GetMapping("/permissionInfoView")
    //加载权限列表
    public ModelAndView permissionInfoView(@RequestParam("perId") String perId){
        return new ModelAndView("admin/permission/permissionInfo","perId",perId);
    }
    //添加权限页面
    @GetMapping("/addPermissionView")
    public ModelAndView addPermissionView(@RequestParam("perParentId")String perParentId){
        return new ModelAndView("admin/permission/addPermission","perParentId",perParentId);
    }

    //编辑权限页面
    @GetMapping("/editPermissionView")
    public ModelAndView editPermissionView(@RequestParam("perId")String perId){
        return new ModelAndView("admin/permission/addPermission","perId",perId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //根据条件获取所有权限信息(树形页面处理)
    @RequestMapping("/getPermissionByCont.do")
    public JSONObject getPermissionByCont(Permission condition){
            if(StringUtils.isEmpty(condition.getPerParentId())){
                condition.setPerParentId("0");
            }
            return CodeAndMsg.SUCESS.getJSON(permissionService.selectPermissionByCont(condition));
    }

    //根據等級(perLevel,perOrder)排序獲取所有权限
    @RequestMapping("/getAllPermissonOrderByLv.do")
    public JSONObject getAllPermissonOrderByLv(){
        return CodeAndMsg.SUCESS.getJSON(permissionService.selectAllPermissonOrderByLv());
    }

    //根據等級(perLevel,perOrder)排序獲取所有权限
    @RequestMapping("/getAllPermissonByRoleId.do")
    public JSONObject getAllPermissonByRoleId(@RequestParam("roleId") String roleId){
        return CodeAndMsg.SUCESS.getJSON(permissionService.selectAllPermissonByRoleId(roleId));
    }

    //根据条件查询权限数量
    @RequestMapping("/getCountPermissionByCont.do")
    public JSONObject getCountPermissionByCont(Permission condition){
        if(StringUtils.isEmpty(condition.getPerParentId())){
            condition.setPerParentId("0");
        }
        return CodeAndMsg.SUCESS.getJSON(permissionService.selectCountPermissionByCont(condition));
    }

    //根据条件分页获取权限信息
    @RequestMapping("/getPermissionPageByCont.do")
    public JSONObject getPermissionPageByCont(Permission condition,@RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer limit){
        Page<Permission> permissions = permissionService.selectPermissionPageByCont(condition, PageRequest.of(page-1,limit));
        return CodeAndMsg.SUCESS.getJSON(permissions.getContent()).fluentPut("count",permissions.getTotalElements());
    }
    //查询单个权限
    @RequestMapping("/getPermissionById.do")
    public JSONObject getPermissionById(@RequestParam("perId") String perId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(permissionService.selectPermissionByPerId(perId));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加权限
    @RequestMapping("/addPermission.do")
    public JSONObject addPermission(Permission permission)throws Exception {
        permissionService.savePermission(permission);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑权限
    @RequestMapping("/editPermission.do")
    public JSONObject editPermission(Permission permission)throws Exception {
        permissionService.savePermission(permission);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //删除单个权限
    @PostMapping("/delPermissionById.do")
    public JSONObject delPermissionById(@RequestParam("perId") String perId)throws Exception {
        permissionService.delPermissionByPerId(perId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个权限
    @PostMapping("/delAllPermissionById.do")
    public JSONObject delAllPermissionById(@RequestParam("perIds[]") List<String> perIds)throws Exception {
        permissionService.delAllPermissionByPerId(perIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}

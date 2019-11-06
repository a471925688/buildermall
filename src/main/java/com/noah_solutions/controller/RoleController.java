package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.Role;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2018 11 30 lijun
 * 權限相關控制器
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Resource
    private IRoleService roleService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加角色页面
    @GetMapping("/addRoleView")
    public ModelAndView addRoleView(){
        return new ModelAndView("admin/role/addOrUpdateAdmin");
    }

    //编辑角色页面
    @GetMapping("/editRoleView")
    public ModelAndView editRoleView(@RequestParam("roleId")String roleId){
        return new ModelAndView("admin/role/addOrUpdateAdmin","roleId",roleId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有角色
    @RequestMapping("/getAllRole.do")
    public JSONObject getAllRole(){
        return CodeAndMsg.SUCESS.getJSON(roleService.selectAllRole());
    }

    //根据条件查询所有角色
    @RequestMapping("/getAllRoleByCont.do")
    public JSONObject getAllRoleByCont(Role role){
        return CodeAndMsg.SUCESS.getJSON(roleService.selectAllRoleByCont(role));
    }

    //查询角色数量
    @RequestMapping("/getCountRole.do")
    public JSONObject getCountRole(){
        return CodeAndMsg.SUCESS.getJSON(roleService.selectCountRole());
    }

    //根据条件分页获取角色信息
    @RequestMapping("/getRolePageByCont.do")
    public JSONObject getRolePageByCont(Role condition,@RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer limit){
        Page<Role> roles = roleService.selectRolePageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "roleId")));
        return CodeAndMsg.SUCESS.getJSON(roles.getContent()).fluentPut("count",roles.getTotalElements());
    }
    //查询单个角色
    @RequestMapping("/getRoleById.do")
    public JSONObject getRoleById(@RequestParam("roleId") String roleId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(roleService.selectRoleByRoleId(roleId));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加角色
    @RequestMapping("/addRole.do")
    public JSONObject addRole(Role role,@RequestParam(value = "perIds[]",required = false) List<String> perIds)throws Exception {
        roleService.saveRole(role,perIds);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑角色
    @RequestMapping("/editRole.do")
    public JSONObject editRole(Role role,@RequestParam(value = "perIds[]",required = false) List<String> perIds)throws Exception {
        roleService.saveRole(role,perIds);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }


    //删除单个角色
    @PostMapping("/delRoleById.do")
    public JSONObject delRoleById(@RequestParam("roleId") String roleId)throws Exception {
        roleService.delRoleByRoleId(roleId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个角色
    @PostMapping("/delAllRoleById.do")
    public JSONObject delAllRoleById(@RequestParam("roleIds[]") List<String> roleIds)throws Exception {
        roleService.delAllRoleByRoleId(roleIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}

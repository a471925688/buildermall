package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.ReceivingInfo;
import com.noah_solutions.entity.User;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IUserService;
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
@RequestMapping("user")
public class UserController {
    @Resource
    private IUserService userService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加用户页面
    @GetMapping("/addUserView")
    public ModelAndView addUserView(){
        return new ModelAndView("user/userInfo/addOrUpdateUser");
    }

    //编辑用户页面
    @GetMapping("/editUserView")
    public ModelAndView editUserView(@RequestParam("userId")String userId){
        return new ModelAndView("user/userInfo/addOrUpdateUser","userId",userId);
    }
    //显示用户详情
    @GetMapping("/detailsUserView")
    public ModelAndView detailsUserView(@RequestParam("userId")String userId){
        return new ModelAndView("user/userInfo/detailsUserView","user",userService.selectUserByUserId(userId));
    }

    //显示添加地址页面
    @GetMapping("/addReceivingInfoView")
    public ModelAndView addReceivingInfoView(@RequestParam("userId")String userId){
        return new ModelAndView("user/userInfo/detailsUserView","user",userService.selectUserByUserId(userId));
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有用户
    @RequestMapping("/getAllUser.do")
    public JSONObject getAllUser(){
        return CodeAndMsg.SUCESS.getJSON(userService.selectAllUser());
    }
    //根据条件分页获取用户信息
    @RequestMapping("/getUserPageByCont.do")
    public JSONObject getUserPageByCont(User condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit){
        Page<User> users = userService.selectUserPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "userId")));
        return CodeAndMsg.SUCESS.getJSON(users.getContent()).fluentPut("count",users.getTotalElements());
    }
    //查询单个用户
    @RequestMapping("/getUserById.do")
    public JSONObject getUserById(@RequestParam("userId") String userId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(userService.selectUserByUserId(userId));
    }
    //查询用户数量
    @RequestMapping("/getCountUser.do")
    public JSONObject getCountUser(){
        return CodeAndMsg.SUCESS.getJSON(userService.selectCountUserByCont(new User()));
    }
    //查询用户数量
    @RequestMapping("/getCountUserByCont.do")
    public JSONObject getCountUserByCont(User user){
        return CodeAndMsg.SUCESS.getJSON(userService.selectCountUserByCont(user));
    }

    //查询登錄次數
    @RequestMapping("/getCountLogin.do")
    public JSONObject getCountLogin(){
        return CodeAndMsg.SUCESS.getJSON(userService.getCountLogin());
    }



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加用户
    @RequestMapping("/addUser.do")
    public JSONObject addUser(User user, Login login,
                              @RequestParam("jsonReceivingInfos") String jsonReceivingInfos)throws Exception {
        userService.addUser(user,login,JSONObject.parseArray(jsonReceivingInfos,ReceivingInfo.class));
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑用户
    @RequestMapping("/editUser.do")
    public JSONObject editUser(User user,Login login,
                               @RequestParam("jsonReceivingInfos") String jsonReceivingInfos)throws Exception {
        userService.updateUser(user,login,JSONObject.parseArray(jsonReceivingInfos,ReceivingInfo.class));
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    //改变用户状态
    @RequestMapping("/editUserIsProhibit.do")
    public JSONObject editUserIsProhibit(@RequestParam("userIsProhibit") Boolean userIsProhibit,@RequestParam("userId") String userId)throws Exception {
        userService.changeUserIsProhibit(userIsProhibit,userId);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //删除单个用户
    @PostMapping("/delUserById.do")
    public JSONObject delUserById(@RequestParam("userId") String userId)throws Exception {
        userService.delUserByUserId(userId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个用户
    @PostMapping("/delAllUserById.do")
    public JSONObject delAllUserById(@RequestParam("userIds[]") List<String> userIds)throws Exception {
        userService.delAllUserByUserId(userIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

package com.noah_solutions.service;

import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAdminService {

    //查询所有管理员
    List<Admin> selectAllAdmin();
    List<Admin> getAllAdminByCont(Admin admin);

    Page<Admin> selectAdminPageByCont(Admin condition, Pageable pageable);
    //查询指定角色的所有管理员
    List<Admin> selectAllAdminByRoleId(String roleId);

    //通过id查询单个管理员
    Admin selectAdminByAdminId(String adminId);
    //分组查询所有管理员数量（通过权限id）
    List selecrAllCountGroupByRoleId();
    //根据条件获取总数
    Long selectCountAdminByCont(Admin condition);
    //獲取登錄次數
    Long getCountLoginByAdminType(Integer adminType);
    //添加管理员
    void addAdmin(Admin admin, Login login)throws Exception;
    //更新管理员
    void updateAdmin(Admin admin, Login login)throws Exception;
    //改变用户状态
    void changeAdminIsProhibit(Boolean adminIsProhibit,String adminId)throws Exception;
    //更新用户密码
    void updateLoginPassWord(String oldPassWord, String newPassWord, Admin admin)throws Exception;
    //更新当前管理员信息
    void editCurrentAdminInfo(Admin adminInfo, Login login, Admin admin)throws Exception;

    //删除单个管理员
    void delAdminByAdminId(String adminId);
    //批量删除管理员
    void delAllAdminByAdminId(List<String> adminIds);

//    Long countAllByAdminTypeNotMember();
    //用户登录
    Admin adminLogin(String loginUserName,String loginPassWord)throws Exception;

    List getAllSupplierName();
}

package com.noah_solutions.service;

import com.noah_solutions.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPermissionService {
    //根据添加获取所以信息
    Page<Permission> selectPermissionPageByCont(Permission condition, Pageable pageable);
    //根据条件获取总数
    Long selectCountPermissionByCont(Permission condition);
    //通過條件查詢所有條件
    List<Permission> selectPermissionByCont(Permission condition);
    //通过roleId获取所有权限
    List<Permission> selectAllPermissonByRoleId(String roleId);
    //通過權限等級排序查詢所有條件
    List<Permission> selectAllPermissonOrderByLv();

    //通过id查询单个权限
    Permission selectPermissionByPerId(String perId);

    //保存权限
    void savePermission(Permission permission)throws Exception;

    //删除单个权限
    void delPermissionByPerId(String perId);
    //批量删除权限
    void delAllPermissionByPerId(List<String> perIds);
}

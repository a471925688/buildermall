package com.noah_solutions.service;

import com.noah_solutions.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoleService {
    //根据條件分頁获取所有信息
    Page<Role> selectRolePageByCont(Role condition, Pageable pageable);
    //根據條件查詢所有角色
    List<Role> selectAllRoleByCont(Role condition);

    //根据条件获取总数
    Long selectCountRoleByCont(Role condition);
    //获取总数
    Long selectCountRole();
    //通过id查询单个角色
    Role selectRoleByRoleId(String roleId);
    //保存角色
    void saveRole(Role role,List<String> perIds)throws Exception;

    //删除单个角色
    void delRoleByRoleId(String roleId);
    //批量删除角色
    void delAllRoleByRoleId(List<String> roleIds);

    List<Role> selectAllRole();
}

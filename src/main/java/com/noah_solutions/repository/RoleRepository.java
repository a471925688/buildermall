package com.noah_solutions.repository;

import com.noah_solutions.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    //根据id查询权限
    Role findByRoleId(String roleId);
    //通過多個id查詢
    List<Role> findAllByRoleIdIn(List<String> roleId);
    //通過權限名稱查詢權限信息
    Role findByRoleName(String roleName);
}

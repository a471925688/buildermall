package com.noah_solutions.repository;

import com.noah_solutions.entity.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {
    //根据id查询权限
    Permission findByPerId(String perId);
    List<Permission> findAllByPerParentId(String peParentId);
    Set<Permission> findAllByPerIdIn(List<String> perId);
    //通过id排序获取所有权限
    @Query("select p from Permission  p " +
            "inner join com.noah_solutions.entity.RolePermission rp on p.perId=rp.perId " +
            "inner join com.noah_solutions.entity.Role r on rp.roleId=r.roleId " +
            "where r.roleId=?1")
    List<Permission> findAllByRoleId(String roleId, Sort sort);
}

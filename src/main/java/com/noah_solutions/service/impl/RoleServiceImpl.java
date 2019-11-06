package com.noah_solutions.service.impl;

import com.noah_solutions.entity.Role;
import com.noah_solutions.repository.PermissionRepository;
import com.noah_solutions.repository.RoleRepository;
import com.noah_solutions.service.IRoleService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PermissionRepository permissionRepository;





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //根据条件分页获取所有角色
    @Override
    public Page<Role> selectRolePageByCont(Role condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        roleRepository.count(Example.of(condition,matcher));
        return roleRepository.findAll(Example.of(condition,matcher),pageable);
    }
    //根据条件查询所有角色
    @Override
    public List<Role> selectAllRoleByCont(Role condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return roleRepository.findAll(Example.of(condition,matcher));
    }

    //查询单个角色
    @Override
    public Role selectRoleByRoleId(String roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    //根據條件查询总数
    @Override
    public Long selectCountRoleByCont(Role condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  roleRepository.count(Example.of(condition,matcher));
    }
    //查询总数
    @Override
    public Long selectCountRole() {
        return  roleRepository.count();
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //保存角色
    @Override
    public void saveRole(Role role,List<String> perIds)throws Exception {
        role.setRoleUpdateTime(DateUtil.getStringDate());
        role.setPermissions(permissionRepository.findAllByPerIdIn(perIds));
        roleRepository.save(role);
    }

    //删除角色
    @Override
    public void delRoleByRoleId(String roleId) {
        roleRepository.deleteById(roleId);
    }
    //批量删除角色
    @Override
    public void delAllRoleByRoleId(List<String> roleIds) {
        List<Role> roles = roleRepository.findAllByRoleIdIn(roleIds);
        //删除所有
        roleRepository.deleteAll(roles);
    }

    @Override
    public List<Role> selectAllRole() {
        return roleRepository.findAll();
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //处理更新单个角色
    private void updateRole(Role updateRole){
        Role role = roleRepository.getOne(updateRole.getRoleId());
        BeanUtils.copyNotNullProperties(updateRole,role);
        roleRepository.save(role);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

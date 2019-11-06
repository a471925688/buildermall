package com.noah_solutions.service.impl;

import com.noah_solutions.entity.Permission;
import com.noah_solutions.repository.PermissionRepository;
import com.noah_solutions.service.IPermissionService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {
    @Resource
    private PermissionRepository permissionRepository;






    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //根据条件分页获取所有权限
    @Override
    public Page<Permission> selectPermissionPageByCont(Permission condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        permissionRepository.count(Example.of(condition,matcher));
        return permissionRepository.findAll(Example.of(condition,matcher),pageable);
    }
    //根据条件查询所有权限
    @Override
    public List<Permission> selectPermissionByCont(Permission condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return permissionRepository.findAll(Example.of(condition,matcher),new Sort(Sort.Direction.ASC, "perLevel").and(new Sort(Sort.Direction.ASC,"perOrder")));
    }

    @Override
    public  List<Permission> selectAllPermissonByRoleId(String roleId){
        return permissionRepository.findAllByRoleId(roleId,new Sort(Sort.Direction.ASC, "perLevel").and(new Sort(Sort.Direction.ASC,"perOrder")));
    }
    //根據等級排序獲取查询所有权限
    @Override
    public List<Permission> selectAllPermissonOrderByLv() {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return permissionRepository.findAll(new Sort(Sort.Direction.ASC, "perLevel").and(new Sort(Sort.Direction.ASC,"perOrder")));
    }
    //查询单个权限
    @Override
    public Permission selectPermissionByPerId(String perId) {
        return permissionRepository.findByPerId(perId);
    }

    //查询总数
    @Override
    public Long selectCountPermissionByCont(Permission condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  permissionRepository.count(Example.of(condition,matcher));
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //保存权限
    @Override
    public void savePermission(Permission permission)throws Exception {
        permission.setPerUpdateTime(DateUtil.getStringDate());
        if(!permission.getPerParentId().equals(0)&&!permission.getPerParentId().equals("0")){
            //查出上一个节点
            Permission parentPermission = permissionRepository.getOne(permission.getPerParentId());
            //把上一个节点设为父节点
            parentPermission.setPerIsParent(1);
            //设置节点等级等于父节点等级+1
            permission.setPerLevel(parentPermission.getPerLevel()+1);
        }
//        如果图标不为空则将临时文件拷贝到目标文件并删除临时文件返回文件路径
//        if(!StringUtils.isEmpty(permission.getPerIconName()))
//            permission.setPerIconName(FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+permission.getPerIconName(),ProjectConfig.FILE_IMAGE_DIRECTORY+permission.getPerIconName()));
        permissionRepository.save(permission);
    }

    //删除权限
    @Override
    public void delPermissionByPerId(String perId) {
        //查出所有子级
        List<Permission> permissions =  permissionRepository.findAllByPerParentId(perId);
        //通过所有子级递归查出所有子孙级
        permissions.addAll(getAllPermissionByPerId(permissions));
        //查出当前perId权限
        permissions.add(permissionRepository.findByPerId(perId));
        Permission permission = new Permission();
        //删除所有(包括子孙级权限)
        permissionRepository.deleteAll(permissions);
    }
    //批量删除权限
    @Override
    public void delAllPermissionByPerId(List<String> perIds) {
        List<Permission> permissions = new ArrayList<>();
        for(int i=0;i<perIds.size();i++){
            //查出每一个的子级
            List<Permission> result = permissionRepository.findAllByPerParentId(perIds.get(i));
            permissions.addAll(result);
            //通过每一个的子级查出所有子孙级
            permissions.addAll(getAllPermissionByPerId(result));
        }
        //查出所有当前级权限
        permissions.addAll(permissionRepository.findAllByPerIdIn(perIds));
        //删除所有
        permissionRepository.deleteAll(permissions);
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //递归查出所有子孙级权限
    private List<Permission> getAllPermissionByPerId(List<Permission> permissions){
        List<Permission>  result=  new ArrayList<>();
        for (int i=0;i<permissions.size();i++){
            //递归查询所有子集权限并加入集合中
            result.addAll(getAllPermissionByPerId(permissionRepository.findAllByPerParentId(permissions.get(i).getPerId())));
        }
        return result;
    }
    //处理更新单个权限
    private void updatePermission(Permission updatePermission){
        Permission permission = permissionRepository.getOne(updatePermission.getPerId());
        BeanUtils.copyNotNullProperties(updatePermission,permission);
        permissionRepository.save(permission);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

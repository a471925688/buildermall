package com.noah_solutions.repository;

import com.noah_solutions.entity.Admin;
import com.noah_solutions.pojo.CartUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    //根据id查询权限
    Admin findByAdminId(String adminId);

    //通過多個id查詢
    List<Admin> findAllByAdminIdIn(List<String> adminId);
    //通过登录id查询管理员
    Admin findByLoginId(String loginId);
    //通过用户名信息查询管理员
    Admin findByLogin_LoginUserName(String loginUserName);

    @Query("select a.adminId,a.adminRealName from Admin  a where a.adminType = 2")
    List selectAllSupplierName();

    //检测用户名和邮箱是否存在
    Boolean existsByLogin_LoginUserNameOrAdminEmail(String loginUserName,String adminEmail);


    @Query("select sum(lg.loginCount) from Admin ad join ad.login lg where ad.adminType=?1")
    Long getCountLoginByAdminType(Integer adminType);

    //查询所有管理员(此方法不返回权限)
    @Query("select new Admin(u.adminId,u.roleId,u.adminType,u.adminIsProhibit,u.adminEmail,u.adminRealName,u.adminGender,u.adminPhone,u.adminAddress,u.adminHeadPortraitUrl,u.adminCreateTime,u.role.roleName,u.login)" +
            " from Admin u")
    List<Admin> findAllAdmin();

    //查询角色等于roleId的管理员(此方法不返回权限)
    @Query("select new Admin(u.adminId,u.roleId,u.adminType,u.adminIsProhibit,u.adminEmail,u.adminRealName,u.adminGender,u.adminPhone,u.adminAddress,u.adminHeadPortraitUrl,u.adminCreateTime,u.role.roleName,u.login)" +
            " from Admin u where u.roleId=:roleId")
    List<Admin> findAllAdminByRoleId(@Param("roleId")String roleId);

    //分组查询所有角色数量
    @Query("select a.role.roleName,count(a.adminId),a.role.roleNameEng from Admin a group by a.roleId")
    List findAllCountGroupByRoleId();

    //改变管理员状态
    @Modifying
    @Query("update Admin a set a.adminIsProhibit =?1 where a.adminId = ?2")
    void updateAdminIsProhibit(Boolean adminIsProhibit,String adminId);

    //检测邮箱是否存在
    Boolean existsByAdminEmail(String email);

    @Query("select admin.adminEmail from Admin  admin where admin.adminId=?1")
    String findEmailById(String adminId);


//    @Query("select new com.noah_solutions.pojo.CartUser(ad.cartUserId,ad.adminRealName,ad.sign,ad.adminHeadPortraitUrl,ad.status) from Admin  ad where ad.adminId=?1")
//    Page<CartUser> findCartUserById(String adminId, Pageable pageable);
//
//
//    @Query("select new com.noah_solutions.pojo.CartUser(ad.cartUserId,ad.adminRealName,ad.sign,ad.adminHeadPortraitUrl,ad.status) from Admin  ad where ad.adminId=?1")
//    CartUser findCartUserById(String adminId);

}

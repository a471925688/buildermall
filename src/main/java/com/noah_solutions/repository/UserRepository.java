package com.noah_solutions.repository;

import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.User;
import com.noah_solutions.pojo.CartUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    //根据id查询权限
    User findByUserId(String userId);
    //通過多個id查詢
    List<User> findAllByUserIdIn(List<String> userId);
    //通过登录id查询用户
    User findByLoginId(String loginId);
    //通过郵箱查询用户
    User findByUserEmail(String email);
    //通过用户名信息查询用户
    User findByLogin_LoginUserName(String loginUserName);
    //检测用户名和邮箱是否存在
    Boolean existsByLogin_LoginUserNameOrUserEmail(String loginUserName, String userEmail);


    //改变用户状态
    @Modifying
    @Query("update User a set a.userIsProhibit =?1 where a.userId = ?2")
    void updateUserIsProhibit(Boolean userIsProhibit, String userId);

    //检测邮箱是否存在
    Boolean existsByUserEmail(String email);

    @Query("select sum(lg.loginCount) from User u join u.login lg")
    Long getCountLogin();

    @Query("select user.userEmail from User  user where user.userId=?1")
    String findEmailById(String adminId);

//    @Query("select new com.noah_solutions.pojo.CartUser(u.cartUserId,u.userRealName,u.sign,u.userHeadPortraitUrl,u.status)  from User  u  where u.userId = ?1")
//    CartUser findCartUserById(String userId);

//    @Query("select new com.noah_solutions.pojo.CartUser(u.cartUserId,u.userRealName,u.sign,u.userHeadPortraitUrl,u.status)  from User  u  where u.userId = ?1")
//    Page<CartUser> findCartUserById(String userId, Pageable pageable);
//
//    @Query("select new com.noah_solutions.pojo.CartUser(u.cartUserId,u.userRealName,u.sign,u.userHeadPortraitUrl,u.status)  from User  u ")
//    Page<CartUser> findCartUser(Pageable pageable);

}

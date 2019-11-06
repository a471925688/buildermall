package com.noah_solutions.repository;

import com.noah_solutions.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login,String> {
    //根据id查询登录
    Login findByLoginId(String loginId);
    //根据用户名查询用户
    Login findByLoginUserName(String loginUserName);
    //检测用户名是否存在
    Boolean existsByLoginUserName(String loginUserName);

    @Modifying
    @Query("update Login l set l.loginCount=l.loginCount+1 where l.loginId=?1")
    void addLoginCount(String loginId);
}
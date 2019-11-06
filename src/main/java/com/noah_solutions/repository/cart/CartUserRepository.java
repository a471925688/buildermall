package com.noah_solutions.repository.cart;

import com.noah_solutions.entity.chat.CartUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartUserRepository extends JpaRepository<CartUser,String> {
    @Query("select new com.noah_solutions.pojo.CartUser(cu.cartUserId,cu.admin.adminRealName,cu.sign,cu.admin.adminHeadPortraitUrl,cu.status) from  CartUser  cu where  cu.adminId = ?1")
    com.noah_solutions.pojo.CartUser selectByAdminId(String adminId);

    @Query("select new com.noah_solutions.pojo.CartUser(cu.cartUserId,cu.user.userRealName,cu.sign,cu.user.userHeadPortraitUrl,cu.status) from  CartUser  cu where  cu.userId = ?1")
    com.noah_solutions.pojo.CartUser selectByUserId(String userId);


    @Query("select new com.noah_solutions.pojo.CartUser(cu.cartUserId,cu.user.userRealName,cu.sign,cu.user.userHeadPortraitUrl,cu.status) from  CartUser  cu where  cu.cartUserId like CONCAT('%',?1,'%') " +
            "or cu.user.userRealName like CONCAT('%',?1,'%') or cu.user.userEmail like CONCAT('%',?1,'%') or cu.user.userPhone like CONCAT('%',?1,'%')")
    Page<com.noah_solutions.pojo.CartUser> searchAllUser(String searchVal, Pageable pageable);

    @Query("select count(cu) from  CartUser  cu where  cu.cartUserId like CONCAT('%',?1,'%') " +
            "or cu.user.userRealName like CONCAT('%',?1,'%') or cu.user.userEmail like CONCAT('%',?1,'%') or cu.user.userPhone like CONCAT('%',?1,'%')")
    Long countSearchAllUser(String searchVal);

    @Query("select new com.noah_solutions.pojo.CartUser(cu.cartUserId,cu.admin.adminRealName,cu.sign,cu.admin.adminHeadPortraitUrl,cu.status) from  CartUser  cu where  cu.adminId like CONCAT('%',?1,'%') " +
            "or cu.admin.adminRealName  like CONCAT('%',?1,'%') or  cu.admin.adminEmail like CONCAT('%',?1,'%') or cu.admin.adminPhone like CONCAT('%',?1,'%') ")
    Page<com.noah_solutions.pojo.CartUser> searchAllAdmin(String searchVal, Pageable pageable);

    boolean existsByUserId(String userId);
    boolean existsByAdminId(String adminId);

    boolean existsByCartUserIdAndStatus(String cartUserId,String status);


    @Query("select new com.noah_solutions.pojo.CartUser(cu.cartUserId,cu.user.userRealName,cu.sign,cu.user.userHeadPortraitUrl,cu.status) from  CartUser  cu where  cu.cartUserId = ?1")
    com.noah_solutions.pojo.CartUser selectUserById(String cartUserId);
    @Query("select new com.noah_solutions.pojo.CartUser(cu.cartUserId,cu.admin.adminRealName,cu.sign,cu.admin.adminHeadPortraitUrl,cu.status) from  CartUser  cu where  cu.cartUserId = ?1")
    com.noah_solutions.pojo.CartUser selectAdminById(String cartUserId);

    @Modifying
    @Query("update  CartUser  cu set cu.status = ?2 where cu.cartUserId=?1")
    void updateStatus(String cartUserId,String status);


    @Modifying
    @Query("update CartUser cu set cu.status = 0 where  1 = 1")
    void setOfflineAll();


}

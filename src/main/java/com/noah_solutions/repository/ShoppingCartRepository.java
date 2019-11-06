package com.noah_solutions.repository;

import com.noah_solutions.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,String> {
    Long countByUserId(String userId);
//    void deleteAllByUserIdAndShopCartType(String userId,Integer type);

    void deleteAllByUserIdAndShopCartName(String userId,String name);
    //查詢購物車商品
    List<ShoppingCart> findAllByUserIdAndShopCartType(String userId,Integer type);
    List<ShoppingCart> findAllByUserIdAndShopCartName(String userId,String shopCartName);

    @Query("select distinct sp.shopCartName from ShoppingCart  sp where sp.userId=?1 and sp.shopCartType=2")
    List<String> selectAllItemName(String userId);

    void  deleteAllByShopCartIdIn(List<String> ids);
}

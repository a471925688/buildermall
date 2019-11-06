package com.noah_solutions.service;

import com.noah_solutions.entity.ProductConfig;
import com.noah_solutions.entity.ShoppingCart;

import java.util.List;

public interface IShoppingCartService {
    //添加購物車
    ShoppingCart addShoppingCart(ShoppingCart shoppingCart)throws Exception;
    //添加項目
    void addItem(ShoppingCart shoppingCart)throws Exception;
    void addShoppingCart(List<ShoppingCart> shoppingCarts,String userId)throws Exception;
    //查詢購物車產品數量
    Long selectCountShoppingCart(String userId)throws Exception;
    //查詢購物車配置詳情
    ProductConfig selectProductConfigs(String productConfigId);
    //查詢是否包郵
    Boolean selectProductIsFreight(String productId);
    //情況購物車
    void cleanShoppingCart(String userId,String name);

    List<ShoppingCart> selectShoppingCarByUserId(String userId);

    //查詢所有項目名稱
    List<String> selectAllItemNameByUserId(String userId);

    List<ShoppingCart> selectProductByUserIdAndItemName(String userId,String name);

    //刪除通過id購物車產品
    void delShoppingCatById(String shopCartId);

    void updateShoppingCart(ShoppingCart shoppingCart);
}

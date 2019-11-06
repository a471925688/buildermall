package com.noah_solutions.service.impl;

import com.noah_solutions.entity.ProductConfig;
import com.noah_solutions.entity.ShoppingCart;
import com.noah_solutions.repository.ProductConfigRepository;
import com.noah_solutions.repository.ProductRepository;
import com.noah_solutions.repository.ShoppingCartRepository;
import com.noah_solutions.service.IShoppingCartService;
import com.noah_solutions.util.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.List;

import static com.noah_solutions.common.ProjectConfig.ShoppingCartType.CART;
import static com.noah_solutions.common.ProjectConfig.ShoppingCartType.ITEM;

@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Resource
    private ShoppingCartRepository shoppingCartRepository;

    @Resource
    private ProductConfigRepository productConfigRepository;


    @Resource
    private ProductRepository productRepository;
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添购物车
    @Override
    @Transactional
    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart)throws Exception {
        shoppingCart.setShopCartType(CART.getValue());
        shoppingCart.setShopCartName(CART.queryValue());
        return shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    //添加項目
    @Override
    @Transactional
    public void addItem(ShoppingCart shoppingCart)throws Exception {
        shoppingCart.setShopCartType(ITEM.getValue());
        shoppingCartRepository.save(shoppingCart);
        return;
    }

    @Override
    public void addShoppingCart(List<ShoppingCart> shoppingCarts,String userId) throws Exception {
        for (ShoppingCart s:shoppingCarts
             ) {
            s.setUserId(userId);
            addShoppingCart(s);
        }
    }

    @Override
    @Transactional
    public void updateShoppingCart(ShoppingCart shoppingCart) {
        ShoppingCart old = shoppingCartRepository.getOne(shoppingCart.getShopCartId());
        BeanUtils.copyNotNullProperties(shoppingCart,old);
        shoppingCartRepository.save(old);
    }


    @Override
    public Long selectCountShoppingCart(String userId) throws Exception {
        return shoppingCartRepository.countByUserId(userId);
    }

    @Override
    public ProductConfig selectProductConfigs(String productConfigId) {
        return productConfigRepository.findByProductConfigId(productConfigId);
    }

    @Override
    public Boolean selectProductIsFreight(String productId) {
        return productRepository.findProductIsFreight(productId);
    }

    @Override
    @Transactional
    public void cleanShoppingCart(String userId,String name) {
        shoppingCartRepository.deleteAllByUserIdAndShopCartName(userId, name);
    }

    @Override
    public List<ShoppingCart> selectShoppingCarByUserId(String userId) {
        return shoppingCartRepository.findAllByUserIdAndShopCartType(userId,1);
    }

    @Override
    public List<String> selectAllItemNameByUserId(String userId) {
        return shoppingCartRepository.selectAllItemName(userId);
    }

    @Override
    public List<ShoppingCart> selectProductByUserIdAndItemName(String userId, String name) {
        return shoppingCartRepository.findAllByUserIdAndShopCartName(userId,name);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    @Override
    public void delShoppingCatById(String shopCartId) {
            shoppingCartRepository.deleteById(shopCartId);
    }




    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

package com.noah_solutions.common;

import com.noah_solutions.entity.*;
import com.noah_solutions.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class IdManage {

    @Resource
    private ProductRepository productRepository;
    @Resource
    private ProductSizeRepository productSizeRepository;
    @Resource
    private ProductSpecificationsRepository productSpecificationsRepository;
    @Resource
    private ProductConfigRepository productConfigRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderProductRepository orderProductRepository;


    //產品下一個自增id
    private static String productId;
    //產品大小下一個自增id
    private static String productSizeId;
    //產品其他選擇下一個自增id
    private static String productSpecificationsId;
    //產品配置下一個自增id
    private static String productConfigId;
    //訂單下一個自增id
    private static String orderId;
    //訂單產品下一個自增id
    private static String orderProductId;


    public  synchronized String getProductNextId(){
        String id = "";
        if(StringUtils.isEmpty(productId)){
            Product product = productRepository.findFirstByOrderByProductIdDesc();
            if(product==null){
                productId=1+"";
            }else {
                productId =  Integer.parseInt(product.getProductId())+1+"";
            }
        }
        id=productId;
        productId= Integer.parseInt(productId)+1+"";
        return id;
    }

    public  synchronized String getProductSizeNextId(){
        String id = "";
        if(StringUtils.isEmpty(productSizeId)){
            ProductSize productSpecifications = productSizeRepository.findFirstByOrderByProductSizeIdDesc();
            if(productSpecifications==null){
                productSizeId=1+"";
            }else {
                productSizeId =  Integer.parseInt(productSpecifications.getProductSizeId())+1+"";
            }
        }
        id=productSizeId;
        productSizeId= Integer.parseInt(productSizeId)+1+"";
        return id;
    }


    public  synchronized String getProductSpecificationsNextId(){
        String id = "";
        if(StringUtils.isEmpty(productSpecificationsId)){
            ProductSpecifications  productSpecifications = productSpecificationsRepository.findFirstByOrderByProductSpecificationsIdDesc();
            if(productSpecifications==null){
                productSpecificationsId=1+"";
            }else {
                productSpecificationsId =  Integer.parseInt(productSpecifications.getProductSpecificationsId())+1+"";
            }

        }
        id=productSpecificationsId;
        productSpecificationsId= Integer.parseInt(productSpecificationsId)+1+"";
        return id;
    }

    public  synchronized String getProductConfigNextId(){
        String id = "";
        if(StringUtils.isEmpty(productConfigId)){
            ProductConfig productConfig = productConfigRepository.findFirstByOrderByProductConfigIdDesc();
            if(productConfig==null){
                productConfigId=1+"";
            }else {
                productConfigId =  Integer.parseInt(productConfig.getProductConfigId())+1+"";
            }
        }
        id=productConfigId;
        productConfigId= Integer.parseInt(productConfigId)+1+"";
        return id;
    }

    public  synchronized String getOrderNextId(){
        String id = "";
        if(StringUtils.isEmpty(orderId)){
            Order order = orderRepository.findFirstByOrderByOrderIdDesc();
            if(order==null){
                orderId=1+"";
            }else {
                orderId =  Integer.parseInt(order.getOrderId())+1+"";
            }
        }
        id=orderId;
        orderId= Integer.parseInt(orderId)+1+"";
        return id;
    }



    public  synchronized String getOrderProductNextId(){
        String id = "";
        if(StringUtils.isEmpty(orderProductId)){
            OrderProduct orderProduct = orderProductRepository.findFirstByOrderByOrderProductIdDesc();
            if(orderProduct==null){
                orderProductId=1+"";
            }else {
                orderProductId =  Integer.parseInt(orderProduct.getOrderProductId())+1+"";
            }
        }
        id=orderProductId;
        orderProductId= Integer.parseInt(orderProductId)+1+"";
        return id;
    }
}

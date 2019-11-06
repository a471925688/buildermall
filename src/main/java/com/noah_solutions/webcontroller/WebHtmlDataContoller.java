package com.noah_solutions.webcontroller;


import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.DataCache;
import com.noah_solutions.common.HtmlDataCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Order;
import com.noah_solutions.entity.Product;
import com.noah_solutions.entity.ShoppingCart;
import com.noah_solutions.entity.User;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.IOrderService;
import com.noah_solutions.service.IProductService;
import com.noah_solutions.service.IShoppingCartService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import com.noah_solutions.util.IdGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 頁面跳轉數據處理
 */
@RestController
@RequestMapping("/webData")
public class WebHtmlDataContoller {

    @Resource
    private IShoppingCartService shoppingCartService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IProductService productService;


    @ApiOperation(value="緩存checkout頁面數據(單個產品)", notes="緩存checkout頁面數據(單個產品)")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/saveCheckOutOneData.do")
    public JSONObject saveCheckOutOneData(ShoppingCart shoppingCart, HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        shoppingCart.setProductConfigs(shoppingCartService.selectProductConfigs(shoppingCart.getProductConfigId()));
        shoppingCart.setProductIsFreight(shoppingCartService.selectProductIsFreight(shoppingCart.getProductId()));
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        HtmlDataCache.setHtmlDate(session.getId(),shoppingCarts,-1);
        shoppingCarts.add(shoppingCart);
        getAndCheckLoginUser(session,ProjectConfig.URL_REDIRECT_CHECKOUT).getUserId();
        return CodeAndMsg.SUCESS.getJSON();
    }

//    @ApiOperation(value="緩存checkout頁面數據(多個產品)", notes="緩存checkout頁面數據(多個產品)")
//    @ApiResponses({
//            @ApiResponse(code = 1, message = "ok"),
//            @ApiResponse(code = 0, message = "平台異常"),
//    })
//    @PostMapping("/saveCheckOutMultipleData.do")
//    public JSONObject saveCheckOutMultipleData(@RequestParam("shoppingCarts") String shoppingCarts, HttpSession session, HttpServletResponse response){
//        response.setHeader("Access-Control-Allow-Credentials","true");
//        HtmlDataCache.setHtmlDate(session.getId(),JSONObject.parseArray(shoppingCarts,ShoppingCart.class),-1);
//        return CodeAndMsg.SUCESS.getJSON();
//    }
    @ApiOperation(value="緩存checkout頁面數據(購物車)", notes="緩存checkout頁面數據(購物車)")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/saveCheckOutMultipleData.do")
    public JSONObject saveCheckOutMultipleData( @RequestParam("itemName")String itemName,HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        if(itemName.equals(ProjectConfig.ShoppingCartType.CART.queryValue())){
            shoppingCarts = (List<ShoppingCart>) session.getAttribute(ProjectConfig.USER_CART);
        }else {
            shoppingCarts = shoppingCartService.selectProductByUserIdAndItemName(getAndCheckLoginUser(session,null).getUserId(),itemName);
        }
        HtmlDataCache.setHtmlDate(session.getId(),shoppingCarts,-1);
        getAndCheckLoginUser(session,ProjectConfig.URL_REDIRECT_CHECKOUT).getUserId();
        return CodeAndMsg.SUCESS.getJSON();
    }
    @ApiOperation(value="緩存checkout頁面數據(我的項目)", notes="緩存checkout頁面數據(購物車)")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/saveItemMultipleData.do")
    public JSONObject saveItemMultipleData( HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        HtmlDataCache.setHtmlDate(session.getId(),session.getAttribute(ProjectConfig.USER_CART),-1);
        getAndCheckLoginUser(session,ProjectConfig.URL_REDIRECT_CHECKOUT).getUserId();
        return CodeAndMsg.SUCESS.getJSON();
    }

    @ApiOperation(value="緩存checkout頁面數據(再次購買)", notes="緩存checkout頁面數據(再次購買)")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/repurchase.do")
    public JSONObject repurchase( String orderId,HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        Order order = orderService.getOrderById(orderId);
        order.getOrderProducts().forEach(v->{
            ShoppingCart shoppingCart = new ShoppingCart();
            Product product = productService.getProductById(v.getProductId());
            BeanUtils.copyNotNullProperties(product,shoppingCart);
            shoppingCart.setShopCartId("tem"+ DateUtil.getNo(7));
            shoppingCart.setProductName(product.getProductTitle());
            shoppingCart.setProductNameEng(product.getProductTitleEng());
            shoppingCart.setProductPhonePath(v.getProductThumbnailPhonePath());
            shoppingCart.setProductConfigs(shoppingCartService.selectProductConfigs(v.getProductConfigId()));
            shoppingCart.setProductNum(v.getProductQuantity()+"");
            shoppingCarts.add(shoppingCart);
        });
        HtmlDataCache.setHtmlDate(session.getId(),shoppingCarts,-1);
        getAndCheckLoginUser(session,ProjectConfig.URL_REDIRECT_CHECKOUT).getUserId();
        return CodeAndMsg.SUCESS.getJSON();
    }



    @ApiOperation(value="獲取頁面數據", notes="獲取頁面數據")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getHtmlData.do")
    public JSONObject getHtmlData(@RequestParam(value = "key",required = false) String key, HttpSession session, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        DataCache data = HtmlDataCache.getHtmlObj(StringUtils.isEmpty(key)?session.getId():key);
        if(data==null){
            return CodeAndMsg.KEY_TIME_OUT.getJSON(new ArrayList<>());
        }else if(data.getUser()!=null){
            session.setAttribute(ProjectConfig.LOGIN_USER,data.getUser());
        }else if(data.getAdmin()!=null){
            session.setAttribute(ProjectConfig.LOGIN_ADMIN,data.getAdmin());
        }
        return CodeAndMsg.SUCESS.getJSON(data.getData());
    }


    @ApiOperation(value="緩存url", notes="緩存url")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/setRedirectUrl.do")
    public JSONObject setRedirectUrl(@RequestParam(value = "url") String url, HttpSession session, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        session.setAttribute(ProjectConfig.URL_REDIRECT,url);
        return CodeAndMsg.SUCESS.getJSON();
    }





    @ApiOperation(value="刪除checkout產品", notes="刪除checkout產品")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/delCheckOutData.do")
    public JSONObject delCheckOutData(@RequestParam(value = "shopCartId") String shopCartId, HttpSession session, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) HtmlDataCache.getHtmlDate(session.getId());
        for (ShoppingCart s:shoppingCarts
             ) {
            if(s.getShopCartId().equals(shopCartId)){
                shoppingCarts.remove(s);
                break;
            }
        }
        HtmlDataCache.updateHtmlDate(session.getId(),shoppingCarts);
        return CodeAndMsg.SUCESS.getJSON();
    }



    private User getAndCheckLoginUser(HttpSession session,String redirectUrl)throws Exception{
        if(session.getAttribute(ProjectConfig.LOGIN_USER)==null){
            if(!StringUtils.isEmpty(redirectUrl))
                session.setAttribute(ProjectConfig.URL_REDIRECT,redirectUrl);
            throw new CustormException(CodeAndMsg.ERROR_CHECK_USER);
        }
        return (User) session.getAttribute(ProjectConfig.LOGIN_USER);
    }
}

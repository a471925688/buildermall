package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.ProductTable;
import com.noah_solutions.common.*;
import com.noah_solutions.entity.*;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.*;
import com.noah_solutions.thirdPartyInterface.DeBangExpress;
import com.noah_solutions.thirdPartyInterface.ShunFengExpress;
import com.noah_solutions.util.BeanUtils;
import io.swagger.annotations.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.noah_solutions.common.ProjectConfig.LOGIN_USER;
import static com.noah_solutions.common.ProjectConfig.ShoppingCartType.CART;
import static com.noah_solutions.common.ProjectConfig.USER_CART;
import static com.noah_solutions.ex.CodeAndMsg.*;

@RestController
@RequestMapping("/webUser")
@Api(value = "Web端客户相關控制器")
public class WebUserContoller{
    @Resource
    private IProductService productService;
    @Resource
    private IUserService userService;
    @Resource
    private IShoppingCartService shoppingCartService;
    @Resource
    private IPlaceService placeService;
    @Resource
    private IEmailService emailService;



    @PostMapping("/login.do")
    public JSONObject login(@RequestParam("loginUserName") String loginUserName,
                              @RequestParam("loginPassWord") String loginPassWord,
                              HttpServletResponse response,
                              HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = userService.userLogin(loginUserName,loginPassWord);
        session.setAttribute(LOGIN_USER,user);
        if(session.getAttribute(USER_CART)==null){//合併購物車
            session.setAttribute(USER_CART,new ArrayList<ShoppingCart>());
        }
        shoppingCartService.addShoppingCart((List<ShoppingCart>) session.getAttribute(USER_CART),user.getUserId());//把臨時購物車產品加入數據庫
        session.setAttribute(USER_CART,shoppingCartService.selectShoppingCarByUserId(user.getUserId()));//重新設置臨時購物車

        if(session.getAttribute(ProjectConfig.URL_REDIRECT)!=null){
            return SUCESS.getJSON(session.getAttribute(ProjectConfig.URL_REDIRECT));
        }
        return SUCESS.getJSON("index.html");
    }

    //註冊用戶用户
    @PostMapping("/registUser.do")
    public JSONObject registUser(User user,HttpServletResponse response,HttpSession session)throws Exception {
        response.setHeader("Access-Control-Allow-Credentials","true");
        session.setAttribute(ProjectConfig.LOGIN_USER,userService.registerUser(user));
        return CodeAndMsg.ADDSUCESS.getJSON(session.getAttribute(ProjectConfig.URL_REDIRECT));
    }



    @ApiOperation(value="加入购物车", notes="添加购物车")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/addShoppingCart.do")
    public JSONObject addShoppingCart(ShoppingCart shoppingCart, HttpServletResponse response,HttpSession session)throws Exception{
        List<ShoppingCart> shoppingCarts = null;
        Object cart = session.getAttribute(USER_CART);
        if(cart==null){
            shoppingCarts = new ArrayList<>();
        }else {
            shoppingCarts = (List<ShoppingCart>) cart;
        }
        shoppingCart.setProductIsFreight(shoppingCartService.selectProductIsFreight(shoppingCart.getProductId()));
        shoppingCart.setProductConfigs(shoppingCartService.selectProductConfigs(shoppingCart.getProductConfigId()));
        if(session.getAttribute(LOGIN_USER)!=null){//判断是否登录
            shoppingCart.setUserId(((User)session.getAttribute(LOGIN_USER)).getUserId());
            shoppingCart = shoppingCartService.addShoppingCart(shoppingCart);
        }
        shoppingCarts.add(shoppingCart);
        session.setAttribute(USER_CART,shoppingCarts);
        response.setHeader("Access-Control-Allow-Credentials","true");
        return HANDLESUCESS.getJSON();
    }




    @ApiOperation(value="加入項目", notes="加入項目")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/addItem.do")
    public JSONObject addItem(ShoppingCart shoppingCart,@RequestParam("itemName")String itemName, HttpServletResponse response,HttpSession session)throws Exception{
        shoppingCart.setUserId(((User)session.getAttribute(LOGIN_USER)).getUserId());
        shoppingCart.setShopCartName(itemName);
        shoppingCart.setProductIsFreight(shoppingCartService.selectProductIsFreight(shoppingCart.getProductId()));
        shoppingCart.setProductConfigs(shoppingCartService.selectProductConfigs(shoppingCart.getProductConfigId()));
        shoppingCartService.addItem(shoppingCart);
        response.setHeader("Access-Control-Allow-Credentials","true");
        return HANDLESUCESS.getJSON();
    }

    @ApiOperation(value="編輯購物車", notes="編輯購物車")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/editCartProduct.do")
    public JSONObject editCartProduct(ShoppingCart shoppingCart,HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<ShoppingCart> shoppingCarts = null;
        shoppingCarts = ((List<ShoppingCart>) session.getAttribute(USER_CART));
        for(int i=0;i<shoppingCarts.size();i++){
            if(shoppingCarts.get(i).getShopCartId().equals(shoppingCart.getShopCartId())){
                shoppingCarts.get(i).setProductConfigs(shoppingCartService.selectProductConfigs(shoppingCart.getProductConfigId()));
                BeanUtils.copyNotNullProperties(shoppingCart,shoppingCarts.get(i));
                break;
            }
        }
        if(session.getAttribute(LOGIN_USER)!=null){//判断是否登录
            shoppingCartService.updateShoppingCart(shoppingCart);
        }
        session.setAttribute(USER_CART,shoppingCarts);
        return HANDLESUCESS.getJSON( );
    }


    @ApiOperation(value="編輯checkout緩存", notes="編輯checkout緩存")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/editCheckOutProduct.do")
    public JSONObject editCheckOutProduct(ShoppingCart shoppingCart,HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
       List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) HtmlDataCache.getHtmlObj(session.getId()).getData();
       shoppingCarts.forEach(v->{
           if(v.getShopCartId().equals(shoppingCart.getShopCartId())){
               v.setProductNum(shoppingCart.getProductNum());
               v.setProductConfigId(shoppingCart.getProductConfigId());
               v.setProductConfigs(shoppingCartService.selectProductConfigs(shoppingCart.getProductConfigId()));
               v.setFreight(null);
               v.setFreightCharges(new HashMap<>());
           }
       });
        return HANDLESUCESS.getJSON( );
    }

    @ApiOperation(value="获取购物车商品", notes="获取购物车商品")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getShoppingCart.do")
    public JSONObject getShoppingCart(ShoppingCart shoppingCart, HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        String userId = getAndCheckLoginUser(session).getUserId();
        if(shoppingCart.getShopCartName().equals(ProjectConfig.ShoppingCartType.CART.queryValue())){
            shoppingCarts = session.getAttribute(USER_CART)==null?new ArrayList<>(): (List<ShoppingCart>) session.getAttribute(USER_CART);
        }else {
            shoppingCarts = shoppingCartService.selectProductByUserIdAndItemName(userId,shoppingCart.getShopCartName());
        }
        return SUCESS.getJSON(shoppingCarts);
    }
    @ApiOperation(value="获取所有项目名称(项目)", notes="获取所有项目名称(项目)")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getAllItemName.do")
    public JSONObject getAllItemName( HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        return SUCESS.getJSON(shoppingCartService.selectAllItemNameByUserId(getAndCheckLoginUser(session).getUserId()) );
    }

    @ApiOperation(value="获取购物车商品數量", notes="获取购物车商品數量")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getCountShoppingCart.do")
    public JSONObject getCountShoppingCart(HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        return SUCESS.getJSON( Long.valueOf(session.getAttribute(USER_CART)==null?0: ((List<ShoppingCart>)session.getAttribute(USER_CART)).size()));
    }


    @ApiOperation(value="刪除購物車產品", notes="刪除購物車產品")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/removeCartProduct.do")
    public JSONObject removeCartProduct(@RequestParam("shopCartId") String shopCartId,HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<ShoppingCart> shoppingCarts = null;
        shoppingCarts = ((List<ShoppingCart>) session.getAttribute(USER_CART));
        for(int i=0;i<shoppingCarts.size();i++){
            if(shoppingCarts.get(i).getShopCartId().equals(shopCartId)){
                shoppingCarts.remove(i);
                break;
            }
        }
        if(session.getAttribute(LOGIN_USER)!=null){
            shoppingCartService.delShoppingCatById(shopCartId);
        }
        session.setAttribute(USER_CART,shoppingCarts);
        return HANDLESUCESS.getJSON( );
    }

    @ApiOperation(value="清空購物車", notes="清空購物車")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/cleanShoppingCart.do")
    public JSONObject cleanShoppingCart(@RequestParam("itemName")String itemName, HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        if(itemName.equals(ProjectConfig.ShoppingCartType.CART.queryValue())){
            session.removeAttribute(USER_CART);
            if(session.getAttribute(LOGIN_USER)!=null){
                shoppingCartService.cleanShoppingCart(((User)session.getAttribute(LOGIN_USER)).getUserId(),CART.queryValue());
            }
        }else {
            shoppingCartService.cleanShoppingCart(((User)session.getAttribute(LOGIN_USER)).getUserId(),itemName);
        }

        return HANDLESUCESS.getJSON( );
    }

    @ApiOperation(value="獲取登錄用戶信息", notes="獲取登錄用戶信息")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getLoginUser.do")
    public JSONObject getLoginUser(HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        return SUCESS.getJSON(session.getAttribute(LOGIN_USER) );
    }


    @ApiOperation(value="清空Session緩存", notes="清空Session緩存")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/cleanSession.do")
    public JSONObject cleanSession(HttpServletResponse response,HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        session.removeAttribute(LOGIN_USER);
        session.removeAttribute(USER_CART);
        HtmlDataCache.romveHtmlDate(session.getId());
        return GOTO_LOGIN.getJSON();
    }


    @ApiOperation(value="新增收貨地址", notes="新增收貨地址")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/saveReceivingInfo.do")
    public JSONObject saveReceivingInfo(ReceivingInfo receivingInfo,HttpServletResponse response, HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = getAndCheckLoginUser(session);
        receivingInfo.setUserId(user.getUserId());
        ReceivingInfo newReceivingInfo = userService.addReceivingInfo(receivingInfo);
        user.getReceivingInfos().add(newReceivingInfo);
        return HANDLESUCESS.getJSON(newReceivingInfo);
    }
    @ApiOperation(value="刪除收貨地址", notes="刪除收貨地址")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })

    @PostMapping("/delReceivingInfo.do")
    public JSONObject delReceivingInfo(@RequestParam("recId") String recId,HttpServletResponse response, HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = getAndCheckLoginUser(session);
        userService.delReceivingInfoByRecId(recId);
        for (ReceivingInfo r : user.getReceivingInfos()) {
            if(r.getRecId().equals(recId)){
                user.getReceivingInfos().remove(r);
                break;
            }
        }
        return HANDLESUCESS.getJSON();
    }



    @ApiOperation(value="計算運費", notes="計算運費")
    @ApiImplicitParams({
            @ApiImplicitParam( paramType = "query", name = "type",dataType = "Integer",value = "快遞類型", required = true),
            @ApiImplicitParam( paramType = "query", name = "toPlaceId",dataType = "String",value = "目的地", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/computedFreight.do")
    public JSONObject computedFreight(@RequestParam("type") Integer type,@RequestParam(value = "recId",required = false) String recId,
                                      @RequestParam(value = "addrId",required = false) String addrId,HttpServletResponse response, HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = getAndCheckLoginUser(session);
        if(StringUtils.isEmpty(addrId)){
            for (ReceivingInfo r: user.getReceivingInfos()) {
                if(r.getRecId().equals(recId)){
                    computedFreight(type,r.getRecAddr().substring(r.getRecAddr().lastIndexOf("/")+1),session.getId());
                }
            }
        }else {
            computedFreight(type,addrId,session.getId());
        }

        return SUCESS.getJSON();
    }

    @ApiOperation(value="提交訂單", notes="新增訂單")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/addOrder.do")
    public JSONObject addOrder(Order order,@RequestParam("lang")String lang,@RequestParam("cur") String cur,HttpServletRequest request,HttpServletResponse response, HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        DealingSlip dealingSlip = userService.webAddOrder(lang,cur,order,getAndCheckLoginUser(session),session);
        if(dealingSlip!=null){
            return HANDLESUCESS.getJSON(dealingSlip);
        }else {
            return SUCESS.getJSON();
        }
    }





    @ApiOperation(value="付款", notes="付款")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/payment.do")
    public JSONObject payment(@RequestParam("orderIds[]") List orderIds,
                              @RequestParam("lang") String lang,
                              @RequestParam("cur") String cur,
                              HttpServletResponse response,
                              HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        DealingSlip dealingSlip = userService.paymentOrder(lang,cur,orderIds,getAndCheckLoginUser(session).getUserId());
        if(dealingSlip!=null){
            return HANDLESUCESS.getJSON(dealingSlip);
        }else {
            return GOTO_INDEX.getJSON();
        }
    }


    @ApiOperation(value="非會員購買", notes="非會員購買")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/nonMemberPurchase.do")
    public JSONObject nonMemberPurchase(@RequestParam("lang") String lang,@RequestParam("email") String email,HttpSession session,HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        userService.nonMemberPurchase(lang,email,HtmlDataCache.getHtmlObj(session.getId()));
        return HANDLESUCESS.getJSON();
    }

    @ApiOperation(value="修改密碼(發送郵件)", notes="修改密碼(發送郵件)")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/modifyPasswordByEmail.do")
    public JSONObject  modifyPasswordByEmail(@RequestParam("lang") String lang,@RequestParam("email") String email,HttpSession session,HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        userService.modifyPasswordByEmail(lang,email);
        return HANDLESUCESS.getJSON();
    }

    @ApiOperation(value="修改密碼", notes="修改密碼")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/modifyPassword.do")
    public JSONObject  modifyPassword(@RequestParam("key") String key,@RequestParam("newPassWord") String newPassWord,HttpSession session,HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        session.setAttribute(ProjectConfig.LOGIN_USER,userService.modifyPassword((User) HtmlDataCache.getAndRomveHtmlDate(key),newPassWord));
        return GOTO_INDEX.getJSON();
    }


    @ApiOperation(value="用户编辑", notes="修改资料")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/editUserInfo.do")
    public JSONObject  editUserInfo(@RequestParam("loginName")String loginName,
                                    @RequestParam("userEmail")String userEmail,
                                    @RequestParam("userRealName")String userRealName,
                                    @RequestParam("userPhone")String userPhone,HttpSession session,HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User oldUser = getAndCheckLoginUser(session);
        oldUser.setUserEmail(userEmail);
        oldUser.setUserPhone(userPhone);
        oldUser.getLogin().setLoginUserName(loginName);
        oldUser.setUserRealName(userRealName);
        session.setAttribute(ProjectConfig.LOGIN_USER,userService.editUserInfo(oldUser));
        return SUCESS.getJSON();
    }

    @ApiOperation(value="产品评价", notes="产品评价")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/productEvaluation.do")
    public JSONObject  productEvaluation(@RequestParam("productEvaluationJson")String productEvaluationJson,HttpSession session,HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        ProductEvaluation productEvaluation =  JSONObject.parseObject(productEvaluationJson, ProductEvaluation.class);
        productService.productEvaluation(productEvaluation);
        return SUCESS.getJSON();
    }


    @ApiOperation(value="查詢當前用戶訂單", notes="查詢當前用戶訂單")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getOrderByCont.do")
    public JSONObject getOrderByCont(Order order,
                                     @RequestParam(value = "title",required = false) String title,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("limit") Integer limit,
                                     HttpServletResponse response,
                                     HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        order.setUserId(getAndCheckLoginUser(session).getUserId());
        return CodeAndMsg.SUCESS.getJSON(userService.selectOrder(order,title,PageRequest.of(page,limit,new Sort(Sort.Direction.DESC, "orderId"))));
    }


    @ApiOperation(value="取消訂單", notes="取消訂單")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/cancelOrder.do")
    public JSONObject cancelOrder(@RequestParam("orderId") String orderId,
                                     HttpServletResponse response,
                                     HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        userService.cancelOrder(getAndCheckLoginUser(session),orderId);
        return HANDLESUCESS.getJSON();
    }

    @ApiOperation(value="確認收貨", notes="確認收貨")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/receivingGoodsOrder.do")
    public JSONObject receivingGoodsOrder(@RequestParam("orderId") String orderId,
                                  HttpServletResponse response,
                                  HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        userService.receivingGoodsOrder(getAndCheckLoginUser(session),orderId);
        return HANDLESUCESS.getJSON();
    }

    @ApiOperation(value="退款申請", notes="訂單退款申請")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/refundOrder.do")
    public JSONObject refundOrder(@RequestParam("orderId") String orderId,
                                  @RequestParam("orderRecExplanation") String orderRecExplanation,
                                          HttpServletResponse response,
                                          HttpSession session)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        userService.refundOrder(getAndCheckLoginUser(session),orderId,orderRecExplanation);
        return HANDLESUCESS.getJSON();
    }

    ///////////////////////////////////////////業務邏輯///////////////////////////////////////////////////
    private void computedFreight(Integer type,String toPlaceId,String key)throws Exception{
        try {

        }catch (Exception e){

        }
        List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) HtmlDataCache.getHtmlDate(key);
        if(shoppingCarts==null){
            throw  new CustormException(GOTO_INDEX);
        }
        Double totolePrice = getTypeTotlePrice("613",shoppingCarts);
        for (ShoppingCart s:shoppingCarts){
            try {
                if(s.getProductIsFreight()){
                    s.setFreight("0");
                }else if(totolePrice>500&&s.getProductTypeId().contains("613")){
                    s.setFreight("0");
                } else if(StringUtils.isEmpty(s.getFreightCharges().get(type+"/"+toPlaceId))){
                    String fromAddrs = s.getProductDeliveryArea();//取出發貨地
                    List<String> placeIds = new ArrayList<>();//保存所有出發地id
                    Integer freight= 0;
                    if(fromAddrs.indexOf(",")!=-1){
                        String[] str = fromAddrs.split(",");
                        for (String data:str) {
                            placeIds.add(data.substring(data.lastIndexOf("/")+1));
                        }
                    }else {
                        placeIds.add(fromAddrs.substring(fromAddrs.lastIndexOf("/")+1));
                    }
                    for (String addr : placeIds) {
                        if(PlaceCache.getPlaceById(addr).getType()==ProjectConfig.PlaceType.OVERSEAS.getValue()&&addr.equals(placeIds.get(placeIds.size()-1))){
                            throw new CustormException("国外地区运费计算失败!提交訂單後请等待商家报价並以郵件訂單通知<br>Freight calculation in foreign regions failed! After submitting the order, please wait for the quotation of the merchant and notify the order by mail.",1);
                        }
                        if(PlaceCache.getPlaceById(toPlaceId).getType()==ProjectConfig.PlaceType.OVERSEAS.getValue()){
                            throw new CustormException("国外地区运费计算失败!提交訂單後请等待商家报价並以郵件訂單通知<br>Freight calculation in foreign regions failed! After submitting the order, please wait for the quotation of the merchant and notify the order by mail.",1);
                        }
                        switch (type) {
                            case 1:
                            case 2: {
                                String d = DeBangExpress.getDeBangFreight(type, PlaceCache.getPlaceById(addr), PlaceCache.getPlaceById(toPlaceId), 0.001, s.getProductConfigs().getProductConfigWeight());
                                if (freight == 0 || freight > Double.valueOf(d)) {//取最低價格
                                    freight = Integer.valueOf(d);
                                }
                                break;
                            }
                            case 3:
                            case 4: {
                                ShunFengPlace sPlace = placeService.getShunFengPlaceByPlaceId(addr);
                                ShunFengPlace oPlace = placeService.getShunFengPlaceByPlaceId(toPlaceId);
                                String d = ShunFengExpress.getShunFengFreight(type, sPlace.getSfCode(), oPlace.getSfCode(), getQueryType(sPlace.getPlaceType(), oPlace.getPlaceType()), s.getProductConfigs().getProductConfigWeight());
                                if (freight == 0 || freight > Double.valueOf(d)) {//取最低價格
                                    freight = Integer.valueOf(d);
                                }

                            }
                        }
                    }
                    s.getFreightCharges().put(type+"/"+toPlaceId,freight);
                    s.setFreight(freight+"");
                }else {
                    s.setFreight(s.getFreightCharges().get(type+"/"+toPlaceId)+"");
                }
            }catch (CustormException ce){
                s.setFreight("?????");
                throw  ce;
            }
            catch (Exception e){
                s.setFreight("?????");
                throw new CustormException("運費計算失敗,下單后請等待商家報價!<br>Freight calculation failed. Please wait for quotation after placing order.",1);
            }
        }
    }

    //获取shoopingCarts中指定类型的产品总价格
    private Double getTypeTotlePrice(String type,List<ShoppingCart> shoppingCarts) {
        AtomicReference<Double> price = new AtomicReference<>(Double.valueOf(0));
        shoppingCarts.forEach(v->{
            if(v.getProductTypeId().contains(type))
                price.updateAndGet(v1 -> v1 + v.getProductConfigs().getProductConfigDiscountPrice()*Integer.valueOf(v.getProductNum()));
        });
        return price.get();
    }

    private String getCookie(HttpServletRequest request,String name){
        String value = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(name)){
                value = cookie.getValue();
            }
        }
        return value;
    }

    /**
     * 	顺丰
     * @param originType
     * @param dest
     * @return
     */
    private String getQueryType (Integer originType,Integer dest) {
        if(originType==3&&dest==3){//出發地為內地，目的地為內地
			/*if('heavyCargo' == this.rateType){//判斷是否重貨，重貨為1，正常為2
				return '1';
			}else{
				return '2'
			}*/
            return "2";
        }else if( originType == 3 && dest==2){//出發地為內地，目的地為港澳
            return "3";
        }else if( originType==2 && dest==3){//出發地為港澳，目的地為內地
            return "4";
        }else if(originType==3 && dest==1 || originType==1 && dest==3 || originType==1 && dest==1){//出發地為內地，目的地為國際  || 出發地為國際，目的地為內地 || 出發地為港澳，目的地為國際
            return "5";
        }else if(originType==2 && dest==1 || originType==1 && dest==2){//出發地為港澳，目的地為國際 || 或者出發地為國際，目的地為港澳
            return "6";
        }else if(originType==2 && dest==3){//或者出發地為港澳，目的地為港澳
            return "7";
        }else if(originType==2 && dest==2){//或者出發地為港澳，目的地為港澳
            return "12";
        }else {//其他
            return "8";
        }
    }

    protected User getAndCheckLoginUser(HttpSession session)throws Exception{
        if(session.getAttribute(ProjectConfig.LOGIN_USER)==null)
            throw new CustormException(CodeAndMsg.ERROR_CHECK_USER);
        return (User) session.getAttribute(ProjectConfig.LOGIN_USER);
    }
}

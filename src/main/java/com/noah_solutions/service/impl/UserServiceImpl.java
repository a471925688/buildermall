package com.noah_solutions.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.github.houbb.paradise.common.util.StringUtil;
import com.noah_solutions.bean.DataCache;
import com.noah_solutions.common.HtmlDataCache;
import com.noah_solutions.common.IdManage;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.*;
import com.noah_solutions.entity.chat.CartUser;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.*;
import com.noah_solutions.repository.cart.CartUserRepository;
import com.noah_solutions.service.ICartService;
import com.noah_solutions.service.IEmailService;
import com.noah_solutions.service.IUserService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import com.noah_solutions.util.IdGenerator;
import com.noah_solutions.util.MD5Util;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.*;

import static com.noah_solutions.common.ProjectConfig.OrderAddrType.*;
import static com.noah_solutions.common.ProjectConfig.OrderPaymentMethod.ONLINE;
import static com.noah_solutions.common.ProjectConfig.OrderPaymentStatus.UNPAID;
import static com.noah_solutions.common.ProjectConfig.OrderRecordType.*;
import static com.noah_solutions.common.ProjectConfig.OrderState.CANCELLED;
import static com.noah_solutions.common.ProjectConfig.OrderState.COMPLETED;
import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.HKD;
import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.USD;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private LoginRepository loginRepository;
    @Resource
    private ReceivingInfoRepository receivingInfoRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderRecordRepository orderRecordRepository;

    @Resource
    private OrderDlslRepository orderDlslRepository;

    @Resource
    private  DiscountCodeRepository discountCodeRepository;

    @Resource
    private DealingSlipRepository dealingSlipRepository;

    @Resource
    private  ProductRepository productRepository;

    @Resource
    private ProductConfigRepository productConfigRepository;

    @Resource
    private ShoppingCartRepository shoppingCartRepository;

    @Resource
    private IdManage idManage;

    @Resource
    private IEmailService emailService;

    @Resource
    private ICartService cartService;

    @Resource
    private TransitRepository transitRepository;

    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有用户
    @Override
    public List<User> selectAllUser() {
        return userRepository.findAll();
    }

    //分页查询所有用户（带条件）
    @Override
    public Page<User> selectUserPageByCont(User condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  userRepository.findAll(Example.of(condition,matcher),pageable);
    }

    //查询用户(通过用户id)
    @Override
    public User selectUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }


    //查询用户数量(带条件)
    @Override
    public Long selectCountUserByCont(User condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  userRepository.count(Example.of(condition,matcher));
    }

    //查询登錄次數
    @Override
    public Long getCountLogin() {
        return  userRepository.getCountLogin();
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添加用户
    @Override
    @Transactional
    public void addUser(User user, Login login, List<ReceivingInfo> receivingInfos)throws Exception {
        if(StringUtils.isEmpty(user.getUserGender())){
                user.setUserGender("保密");
        }

        //判断用户邮箱是否重复
        if(userRepository.existsByLogin_LoginUserNameOrUserEmail(login.getLoginUserName(),user.getUserEmail()))
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        //MD5密码加密
        login.setLoginPassWord(MD5Util.getStringMD5(login.getLoginPassWord()));
        //设置登录信息
        user.setLogin(loginRepository.saveAndFlush(login));
        //保存用户信息
        user = userRepository.saveAndFlush(user);
        //设置收货地址关联的用户id
        String userId = user.getUserId();

        receivingInfos.forEach((v)->v.setUserId(userId));
        //保存
        receivingInfoRepository.saveAll(receivingInfos);
        cartService.addCart(new CartUser("暫無簽名",userId,null));

    }
    //更新用户
    @Override
    @Transactional
    public void updateUser(User user, Login login,List<ReceivingInfo> receivingInfos)throws Exception {
        //取出user
        User userEntity  = userRepository.getOne(user.getUserId());
        //更新user
        BeanUtils.copyNotNullProperties(user,userEntity);

        if(!login.getLoginPassWord().startsWith("******")){
            login.setLoginPassWord(MD5Util.getStringMD5(login.getLoginPassWord()));        //MD5密码加密
        }else {
            login.setLoginPassWord(null);        //MD5密码加密
        }

        //更新login
        BeanUtils.copyNotNullProperties(login,userEntity.getLogin());
        //设置收货地址关联的用户id
        for(int i =0;i<receivingInfos.size();i++){
            if(receivingInfos.get(i)!=null)
                receivingInfos.get(i).setUserId(user.getUserId());
        }

        userEntity.setReceivingInfos(receivingInfos);
        //保存user
        userRepository.save(userEntity);
    }
    //删除用户
    @Override
    @Transactional
    public void delUserByUserId(String userId) {
        userRepository.deleteById(userId);
    }
    //批量删除用户
    @Override
    @Transactional
    public void delAllUserByUserId(List<String> userIds) {
        List<User> users = userRepository.findAllByUserIdIn(userIds);
        userRepository.deleteAll(users);
    }

    //改变用户状态
    @Override
    @Transactional
    public void changeUserIsProhibit(Boolean userIsProhibit,String userId)throws Exception{
        userRepository.updateUserIsProhibit(userIsProhibit,userId);
    }

    public User registerUser(User user)throws Exception{
        addUser(user,user.getLogin(),new ArrayList<>());
        return userRepository.findByLogin_LoginUserName(user.getLogin().getLoginUserName());
    }


    //用户登录
    @Override
    @Transactional
    public User userLogin(String loginUserName,String loginPassWord)throws Exception{
        User user = userRepository.findByLogin_LoginUserName(loginUserName);
        //查询用户名是否存在
        if(user==null)
            throw new CustormException(CodeAndMsg.USER_NOTFINDUSERNAME);
        //检测密码是否正确
        if(!user.getLogin().getLoginPassWord().equals(MD5Util.getStringMD5(loginPassWord)))
            throw new CustormException(CodeAndMsg.USER_PASSWORDERROR);
        //检测是否被禁用
        if(user.getUserIsProhibit())
            throw new CustormException(CodeAndMsg.USER_BANNED);
        loginRepository.addLoginCount(user.getLogin().getLoginId());
        user.setCartUser(cartService.getCartUserByUserId(user.getUserId()));
        return  user;
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////







    //////////////////////////////前端部分  start//////////////////////////////////////////////////////////
    //////////////////////////////前端部分  start//////////////////////////////////////////////////////////




    @Override
    public ReceivingInfo addReceivingInfo(ReceivingInfo receivingInfo) {
        return receivingInfoRepository.saveAndFlush(receivingInfo);
    }

    @Override
    public void delReceivingInfoByRecId(String recId) {
        receivingInfoRepository.deleteById(recId);
    }

    //非會員購買
    @Override
    @Transactional
    public User nonMemberPurchase(String lang,String email, DataCache data) throws Exception{
        if(data == null)
            throw new CustormException(CodeAndMsg.ERROR_TIME_OUT);
        User user = userRepository.findByUserEmail(email);
        Boolean isNewUser = false;
        if(user==null){
            Login login = new Login(email,MD5Util.getStringMD5("888888"));
            user = new User();
            user.setLogin(login);
            user.setUserEmail(email);
            user.setUserGender("secrecy");
            user.setUserRealName("");
            isNewUser = true;
            user = userRepository.saveAndFlush(user);
            cartService.addCart(new CartUser("暫無簽名",user.getUserId(),null));
        }
        data.setUser(user);
        emailService.nonMemberPurchase(lang,data,isNewUser);
        return null;
    }
    //修改密碼（發送郵件）
    @Override
    public void modifyPasswordByEmail(String lang, String email)throws Exception{
        User user = userRepository.findByUserEmail(email);
        if(user==null)
            throw new CustormException(CodeAndMsg.USER_NOTFINDUSERNAME);
        emailService.modifyPasswordByEmail(lang, user);
    }
    //修改密碼
    @Override
    public User modifyPassword(User user,String newPassWord)throws Exception{
        user.getLogin().setLoginPassWord(MD5Util.getStringMD5(newPassWord));
        return userRepository.saveAndFlush(user);
    }
    //编辑用户
    @Override
    public User editUserInfo(User user)throws Exception{
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public DealingSlip webAddOrder(String lang,String cur,Order order,User user, HttpSession session)throws Exception {
        if(HtmlDataCache.getHtmlDate(session.getId())==null)
            throw new CustormException(CodeAndMsg.ERROR_TIME_OUT);//頁面超時
        if(StringUtils.isEmpty(cur)||cur.equals(USD.getValue()+"")){
            cur = HKD.getValue()+"";
        }
        ReceivingInfo bullAddr=null;//賬單地址
        ReceivingInfo delAddr=null;//配送地址
        Transit transit = null;//集運地址
        List<Order> orders = new ArrayList<>();//訂單
        List<OrderRecord> orderRecords = new ArrayList<>();//訂單操作記錄
        Set<DealingSlip> dealingSlips = new HashSet<>();
        for (ReceivingInfo r:  user.getReceivingInfos()) {
            if(r.getRecId().equals(order.getOrderBillAddrId()))//取出賬單地址信息
                bullAddr = r;
            if(r.getRecId().equals(order.getOrderDelAddrId()))//取出收貨地址信息
                delAddr = r;
        }
        if(!StringUtils.isEmpty(order.getTransitId())){
            transit = transitRepository.getOne(order.getTransitId());
        }

        order.setUserId(user.getUserId());
        //拆分訂單
        List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) HtmlDataCache.getHtmlDate(session.getId());
        String itemName = shoppingCarts.get(0).getShopCartName();
        List<String> shoppingCartIds = new ArrayList<>();
        Map<String,Set<OrderProduct>> stringListMap = getOrderProduct(shoppingCarts,user.getUserId(),shoppingCartIds);

        Double orderTotleAmount = Double.valueOf(0);
        for (Map.Entry<String, Set<OrderProduct>> entry : stringListMap.entrySet()) {
            Order newOrder = new Order();
            BeanUtils.copyNotNullProperties(order,newOrder);
            newOrder.setAdminId( entry.getKey());//設置供應商id
            newOrder.setOrderId(idManage.getOrderNextId());//設置訂單id
            newOrder.setOrderNo(IdGenerator.INSTANCE.nextId());
            newOrder.setOrderPaymentStatus(UNPAID.getValue());
            newOrder.setOrderState(ProjectConfig.OrderState.UNPAID.getValue());
            newOrder.setDealingSlips(dealingSlips);
            newOrder.setOrderState(ProjectConfig.OrderState.UNPAID.getValue());
            newOrder.setShopCartName(itemName);
            Set<OrderProduct> products = entry.getValue();//獲取訂單產品
            Double totlePrice = Double.valueOf(0);
            Double totleFreight = Double.valueOf(0);
            for (OrderProduct o:products) {
                o.setOrderId(newOrder.getOrderId());
                totlePrice+=o.getProductPrice()*o.getProductQuantity();//
                if(o.getProductFreightCharges()!=null){
                    totleFreight+=o.getProductFreightCharges();//當前訂單總運費
                }else{
                    newOrder.setOrderState(ProjectConfig.OrderState.UNQUOTED.getValue());
                    totleFreight = Double.valueOf(0);
                    continue;
                }

            }
            Double totle = getDiscountAmount(totlePrice,order.getOrderDiscountCode());//折后價格
            orderTotleAmount+=totle+totleFreight;
            newOrder.setOrderTotalPrice(String.format("%.2f", totle+totleFreight));//設置總價
            newOrder.setOrderPreferentialAmount(String.format("%.2f", totlePrice-totle));//設置優惠金額
            newOrder.setOrderAddrs(getOrderAddr(newOrder.getOrderId(),bullAddr,delAddr,transit));//設置訂單地址
            newOrder.setOrderProducts(products);//設置訂單產品
            newOrder.setOrderRefundNum(0);
            newOrder.setUser((User) session.getAttribute(ProjectConfig.LOGIN_USER));
            orders.add(newOrder);
            newOrder.setOrderCreateTime(DateUtil.getStringDate());
            newOrder.setCurType(Integer.valueOf(cur));
            if(!newOrder.getOrderState().equals(ProjectConfig.OrderState.UNQUOTED.getValue())){
                newOrder.setTotalFreightCharges(new DecimalFormat("#.00").format(totleFreight));//設置總運費
                emailService.newOrder(lang,newOrder);
            }else {
                newOrder.setTotalFreightCharges(null);//設置總運費
            }
            orderRecords.add(new OrderRecord(NEWORDER.getValue(),null,newOrder.getUserId(),newOrder.getOrderId(),NEWORDER.queryValue()));
        }

        DealingSlip dealingSlip = null;
        if(order.getOrderPaymentMethod()== ONLINE.getValue()){
            //創建交易號
            dealingSlip  = new DealingSlip(IdGenerator.INSTANCE.nextId(),orderTotleAmount,orderTotleAmount*ProjectConfig.TodayRate, cur+"","");
            dealingSlips.add(dealingSlip);
        }
        orderRepository.saveAll(orders);//保持訂單
        orderRecordRepository.saveAll(orderRecords);//保持訂單操作記錄
        shoppingCartRepository.deleteAllByShopCartIdIn(shoppingCartIds);//移除購物車
        session.setAttribute(ProjectConfig.USER_CART,shoppingCartRepository.findAllByUserIdAndShopCartType(user.getUserId(),1));
        HtmlDataCache.delHtmlDate(session.getId());
        return dealingSlip;
    }




    //訂單二次付款
    @Override
    @Transactional
    public DealingSlip paymentOrder(String lang,String cur,List<String>  orderIds,String userId)throws Exception{
        String  totle= orderRepository.findOrderTotleAmount(orderIds);
        Double totleAmount = Double.valueOf(0);
        if(!StringUtils.isEmpty(totle)){
            totleAmount = Double.valueOf(String.format("%.2f", Double.valueOf( totle )));
        }
        String deslId = IdGenerator.INSTANCE.nextId();//生成本次交易號
        List<OrderDlsl> orderDlsls = new ArrayList<>();
        for (String orderId: orderIds
             ) {
            orderDlsls.add(new OrderDlsl(orderId,deslId));
        }
        DealingSlip dealingSlip = null;
        if(StringUtils.isEmpty(cur)||cur.equals(USD.getValue()+"")){
            cur=ProjectConfig.PayDollarCurType.HKD.getValue()+"";
        }
        //創建交易號
        dealingSlip  = new DealingSlip(deslId,totleAmount,totleAmount*ProjectConfig.TodayRate, cur+"","");
        dealingSlipRepository.save(dealingSlip);
        orderDlsls.forEach(v->orderDlslRepository.inserOrderDlsl(v.getOrderId(),v.getDeslNo()));
        return dealingSlip;
    }

    @Override
    public Page<Order> selectOrder(Order order,String title,Pageable pageable) {
        if(!StringUtils.isEmpty(title)){
            return orderRepository.selectOrderUserTable(order.getUserId(),title,pageable);
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("orderCreateTime", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("lang")
                .withIgnorePaths("cur");
        return  orderRepository.findAll(Example.of(order,matcher),pageable);
    }

    //取消訂單
    @Override
    @Transactional
    public void cancelOrder(User user, String orderId) {
        orderRepository.updateOrderStateByUserId(user.getUserId(),CANCELLED.getValue(),orderId);
        orderRecordRepository.save(new OrderRecord(ProjectConfig.OrderRecordType.CANCELLED.getValue(),null,user.getUserId(),orderId,ProjectConfig.OrderRecordType.CANCELLED.queryValue()));
    }


    //確認收貨
    @Override
    @Transactional
    public void receivingGoodsOrder(User user, String orderId) {
        orderRepository.updateOrderStateByUserId(user.getUserId(),COMPLETED.getValue(),orderId);
        orderRecordRepository.save(new OrderRecord(RECEIVINGGOODS.getValue(),null,user.getUserId(),orderId,RECEIVINGGOODS.queryValue()));
    }


    //退款申請處理
    @Override
    @Transactional
    public void refundOrder(User user, String orderId,String orderRecExplanation) {
        orderRecordRepository.save(new OrderRecord(REFUNDAPPLICATION.getValue(),null,user.getUserId(),orderId,orderRecExplanation));
        orderRepository.updateOrderRefundNum(orderId);
    }

    //////////////////////////////前端部分  end//////////////////////////////////////////////////////////
    //////////////////////////////前端部分  end//////////////////////////////////////////////////////////














    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    private  Double getDiscountAmount(Double totleAmount,String discountCodeNo){
        DiscountCode discountCode = null;
        if(!StringUtils.isEmpty(discountCodeNo)){
            discountCode = discountCodeRepository.findByDiscountCodeNo(discountCodeNo);
        }
        if(discountCode!=null){
            if(discountCode.getDiscountCodeMode().equals(ProjectConfig.DiscountCodeMode.DISCOUNT.getValue())){
                totleAmount = totleAmount * Double.valueOf(discountCode.getDiscountCodeContent())/10;
            }
        }
        return totleAmount;
    }

    private Map<String,Set<OrderProduct>> getOrderProduct(List<ShoppingCart> products,String userId,List<String> shoppingCartIds){
        //獲取訂單產品
        Map<String,Set<OrderProduct>> orderProductSet = new HashMap<>();
        for (ShoppingCart s:products) {
            if(!StringUtils.isEmpty(s.getShopCartId())&&!s.getShopCartId().startsWith("temp")){
                shoppingCartIds.add(s.getShopCartId());
            }
            OrderProduct orderProduct = new OrderProduct();
            Product product = productRepository.findByProductId(s.getProductConfigs().getProductId());
            BeanUtils.copyNotNullProperties(product,orderProduct);
            BeanUtils.copyNotNullProperties(s,orderProduct);
            orderProduct.setOrderProductId(idManage.getOrderProductNextId());
            orderProduct.setOrderProductParams(getOrderProductParam(orderProduct.getOrderProductId(),s.getProductConfigs()));//設置規格參數
            orderProduct.setProductPrice(s.getProductConfigs().getProductConfigDiscountPrice());//設置價格
            orderProduct.setProductWeight(Double.valueOf(s.getProductConfigs().getProductConfigWeight()));//設置重量
            orderProduct.setProductConfigId(s.getProductConfigId());
            if(com.noah_solutions.util.StringUtils.isNumber(s.getFreight())){
                orderProduct.setProductFreightCharges(Double.valueOf(s.getFreight())*Integer.valueOf(s.getProductNum()));//設置運費
            }else {
                orderProduct.setProductFreightCharges(null);//設置運費
            }
            orderProduct.setProductQuantity(Integer.valueOf(s.getProductNum()));//設置數量
            orderProduct.setUserId(userId);
                orderProduct.setDeliveryTime(product.getProductServiceDate());
            productConfigRepository.subtractProductConfig(s.getProductNum(),s.getProductConfigId());
            productRepository.addProductSelloutNum(s.getProductNum(),s.getProductConfigs().getProductId());
            if(!orderProductSet.containsKey(product.getAdminId()))
                orderProductSet.put(product.getAdminId(),new HashSet<>());
            orderProductSet.get(product.getAdminId()).add(orderProduct);
            s.getProductConfigs().setProductConfigStock( Integer.valueOf(s.getProductConfigs().getProductConfigStock())-Integer.valueOf(s.getProductNum())+"");
        }
        return orderProductSet;
    }

    //獲取訂單產品規格參數
    private Set<OrderProductParam> getOrderProductParam(String orderProductId,ProductConfig products){
        Set<OrderProductParam> orderProductSet = new HashSet<>();
        if(!StringUtils.isEmpty(products.getProductSizeId()))
            orderProductSet.add(new OrderProductParam(products.getProductSize().getProductSizeContent(),products.getProductSize().getProductSizeContentEng(),"尺寸","Size",orderProductId));
        products.getProductSpecificationsSet().forEach(v->orderProductSet.add(new OrderProductParam(v.getProductSpecificationsContent(),v.getProductSpecificationsContentEng(),v.getProductSpecificationsName(),v.getProductSpecificationsNameEng(),orderProductId)));
        return orderProductSet;
    }

    //訂單地址
    private Set<OrderAddr> getOrderAddr(String orderId,ReceivingInfo billAddr,ReceivingInfo delAddr,Transit transit){
        Set<OrderAddr> orderAddrs = new HashSet<>();
        orderAddrs.add(new OrderAddr(billAddr.getRecCompany(),billAddr.getRecName(),billAddr.getRecPhome(),billAddr.getRecAddr(),billAddr.getRecDetailedAddr(), BILL.getValue(),orderId));
        orderAddrs.add(new OrderAddr(delAddr.getRecCompany(),delAddr.getRecName(),delAddr.getRecPhome(),delAddr.getRecAddr(),delAddr.getRecDetailedAddr(), RECADDR.getValue(),orderId));
        if(transit!=null)
            orderAddrs.add(new OrderAddr(transit.getName(),transit.getRecName(),transit.getRecPhone(),transit.getAddrId(),transit.getDetailedAddr(), TRANSIT.getValue(),orderId));
        return  orderAddrs;
    }


    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

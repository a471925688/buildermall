package com.noah_solutions.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 2018 11 30 lijun
 * 存放常用的配置信息
 */
@Component
public class ProjectConfig {
    //項目路徑
    public   static String  HOST_NAME;
    //Web項目路徑
    public   static String  WEB_HOST_NAME;
    //临时文件存放目录
    public   static String  FILE_TEMPORARY_DIRECTORY;
    //图片文件存放目录
    public   static String  FILE_IMAGE_DIRECTORY;
    //產品評價文件存放目录
    public   static String  FILE_EVALUATION_IMAGE_DIRECTORY;
    //影片文件存放目录
    public   static String  FILE_VIDEO_DIRECTORY;
    //目录文件存放目录
    public   static String  FILE_CONTENTS_DIRECTORY;
    //规格文件存放目录
    public   static String  FILE_SPECIFICATIONS_DIRECTORY;
    //PD模板存放目录
    public   static String  FILE_PDF_DIRECTORY;
    //產品編輯器資源路徑
    public   static String UPLOAD_PATH_LAYEDIT;
    ////////////////////////admin start///////////////////////////
    //保存验证码的值
    public static final String VALIDATECODE="VALIDATECODE";
    //保存管理员登录
    public static final String LOGIN_ADMIN="admin";
    //保存客户登录
    public static final String LOGIN_USER="user";
    //购物车
    public static final String USER_CART = "userCart";
    //重定向登录页面
    public static final String URL_REDIRECT_LOGIN = "redirect:index";
    //重定向主页
    public static final String URL_REDIRECT_MAIN = "redirect:main";
    public static final String ADMIN_URL_REDIRECT = "admin_url_redirect";
    ////////////////////////admin start///////////////////////////




    ////////////////////////web start///////////////////////////
    public static final String URL_REDIRECT = "url_redirect";
    //重定向購買頁面
    public static final String URL_REDIRECT_CHECKOUT = "checkout.html";
    //重定向購首頁
    public static final String URL_REDIRECT_INDEX = "index.html";








    ////////////////////////web end///////////////////////////




    ////////////////////////SysLog end///////////////////////////




    ////////////////////////pdf start ///////////////////////////
    public final static String INVOICE_FORM_CN = "invoice_form(cn).ftl";
    public final static String INVOICE_FORM_CNN = "invoice_form(cnn).ftl";
    public final static String INVOICE_FORM_EN = "invoice_form.ftl";
    public final static String RECEIPT_FORM_CN = "official_receipt_cn.ftl";
    public final static String RECEIPT_FORM_CNN = "official_receipt_cnn.ftl";
    public final static String RECEIPT_FORM_EN = "official_receipt.ftl";
    public final static String INVOICE_NOTICE_EMAIL = "email_item.ftl";
    public final static String INVOICE_DELIVERY_NOTE_CN = "delivery_note_cn.ftl";
    public final static String INVOICE_DELIVERY_NOTE_CNN = "delivery_note_cnn.ftl";
    public final static String INVOICE_DELIVERY_NOTE_EN = "delivery_note.ftl";
    public final static String INVOICE_PURCHASE_ORDER_EN = "purchase_order.ftl";
    public final static String INVOICE_PURCHASE_ORDER_CN = "purchase_order_cn.ftl";
    public final static String INVOICE_PURCHASE_ORDER_CNN = "purchase_order_cnn.ftl";
    ////////////////////////pdf end ///////////////////////////


    ////////////////////////其他 start ///////////////////////////
    public  static Double TodayRate = 0.884064;// 今日利率
    public  static Double TodayRateUSD = 0.144427;// 今日利率(美元)
    ////////////////////////其他 end ///////////////////////////








    @Value("${file.directory.pdf}")
    public void setFilePdfDirectory(String filePdfDirectory) {
        FILE_PDF_DIRECTORY = filePdfDirectory;
    }

    @Value("${file.directory.temporary}")
    public  void setFileTemporaryDirectory(String fileTemporaryDirectory) {
        FILE_TEMPORARY_DIRECTORY = fileTemporaryDirectory;
    }
    @Value("${file.directory.image}")
    public  void setFileImageDirectory(String fileImageDirectory) {
        FILE_IMAGE_DIRECTORY = fileImageDirectory;
    }
    @Value("${file.directory.video}")
    public  void setFileVideoDirectory(String fileVideoDirectory) {
        FILE_VIDEO_DIRECTORY = fileVideoDirectory;
    }

    @Value("${file.directory.contents}")
    public  void setFileContentsDirectory(String fileContentsDirectory) {
        FILE_CONTENTS_DIRECTORY = fileContentsDirectory;
    }
    @Value("${file.directory.specifications}")
    public  void setFileSpecificationsDirectory(String fileSpecificationsDirectory) {
        FILE_SPECIFICATIONS_DIRECTORY = fileSpecificationsDirectory;
    }
    @Value("${file.directory.specifications}")
    public  void setUploadPathLayedit(String uploadPathLayedit) {
        UPLOAD_PATH_LAYEDIT = uploadPathLayedit;
    }
    @Value("${file.directory.evaluation}")
    public  void setFileEvaluationImageDirectory(String fileEvaluationImageDirectory) {
        FILE_EVALUATION_IMAGE_DIRECTORY = fileEvaluationImageDirectory;
    }


    @Value("${host_name}")
    public  void setHostName(String hostName) {
        HOST_NAME = hostName;
    }

    public static String getWebHostName() {
        return WEB_HOST_NAME;
    }
    @Value("${web_host_name}")
    public  void setWebHostName(String webHostName) {
        WEB_HOST_NAME = webHostName;
    }


    /**
     * 用户类型1.管理员.2.供应商.3.会员
     */
    public static enum UserType {
        ADMIN(0), SUPPLIER(1), MEMBER(2);
        private final Integer id;
        UserType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 是否禁用用户1.是. 0.否
     */
    public static enum UserIsProhibit {
        Prohibit(1),Enable(0);
        private final Integer id;
        UserIsProhibit(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 日志操作人类型1.管理员. 2.供应商
     */
    public static enum AdminType {
        ADMIN(1),  SUPPLIER(2);
        private final Integer id;
        AdminType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 國家枚舉
     */
    public static enum Country {
        CHINA("CN"),  BRITAIN("UK");
        private final String id;
        Country(String id) { this.id = id; }
        public String getValue() { return id; }
    }
    /**
     *  語言 1.繁體，2.英文,3.簡體
     */
    public static enum Language {
        CN("cn"),  EN("en"),CNN("cnc");
        private final String id;
        Language(String id) { this.id = id; }
        public String getValue() { return id; }
    }

    /**
     * 圖片分組類型 1,產品,2.廣告圖,3.產品評論
     */
    public static enum ImageGroupType {
        PRODUCT(1),ADVERTISING(2),PRODUCTEVALUATION(3);
        private final Integer id;
        ImageGroupType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 圖片類型 1.主圖,2.詳圖
     */
    public static enum ImageType {
        COVER(1),DETAIL(2);
        private final Integer id;
        ImageType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 文件類型 1,圖片,2.影片
     */
    public static enum FileType {
        IMAGE(1),VIDEO(2),CONTENTS(3),SPECIFICATIONS(4);
        private final Integer id;
        FileType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 订单状态(0.待報價,1,待付款,2.待发货.3.待確認.4.已完成,5.已取消,6.待退款,7.已退款,)
     */
    public static enum OrderState {
        UNQUOTED(8),UNPAID(1),SHIP(2),RECEIPT(3),COMPLETED(4),CANCELLED(5),PENDINGREFUND(6),REFUNDED(7);
        private final Integer id;
        OrderState(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }

    /**
     * 订单退款處理状态(0.無退款申請,1.申請中,2.已同意,3.已拒絕)
     */
    public static enum OrderRefundState {
        NOAPPLICATION(0),APPLICATION(1),PASS(2),REFUSE(3);
        private final Integer id;
        OrderRefundState(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
    }


    /**
     * 付款方式(1.線上付款,2.銀行轉賬)
     */
    public static enum OrderPaymentMethod {
        BANK_TRANSFER(1),ONLINE(2);
        private final Integer id;
        OrderPaymentMethod(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "銀行過數";
                case 2:return "線上支付";
            }
            return "";
        }
        public static String queryValue(Integer key,String lang) {
            switch (key){
                case 1:
                    return "銀行過數";
                case 2:return "線上支付";
            }
            return "";
        }





    }

    /**
     * 優惠方式(1.折扣方式,)
     */
    public static enum DiscountCodeMode {
        DISCOUNT(1);
        private final Integer id;
        DiscountCodeMode(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "折扣方式";
            }
            return "";
        }
    }

    /**
     * 付款狀態(1.未付款.2.已付款.3.已退款)
     */
    public static enum OrderPaymentStatus {
        UNPAID(1),PAID(2),REFUNDED(3);
        private final Integer id;
        OrderPaymentStatus(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public static String queryValue(Integer key) {
            switch (key){
                case 1:return "未付款";
                case 2:return "已付款";
                case 3:return "已退款";
            }
            return "全部訂單";
        }
    }
    /**
     * 訂單地址類型(1.賬單地址.2.發貨地址)
     */

    public static enum  OrderAddrType{
        BILL(1),RECADDR(2),TRANSIT(3);
        private final Integer id;
        OrderAddrType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "賬單地址";
                case 2:return "發貨地址";
                case 3:return "集運地址";
            }
            return "";
        }
    }

    public static enum  OrderRecordType{
        NEWORDER(1),PAYED(2),Shipment(3),RECEIVINGGOODS(4),CANCELLED(5),REFUNDAPPLICATION(6),REFUND(7),UNREFUND(8),ADMIN_UPDATE(100);
        private final Integer id;
        OrderRecordType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "新訂單";
                case 2:return "付款成功";
                case 3:return "訂單發貨";
                case 4:return "收貨確認";
                case 5:return "取消訂單";
                case 6:return "申請退款";
                case 7:return "退款操作";
                case 8:return "拒絕退款";
                case 100:return "修改了訂單信息";
            }
            return "";
        }
    }

    public static enum  OrderSignedStatus{
        ISSIGNED(1),NOSIGNED(0);
        private final Integer id;
        OrderSignedStatus(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "已签收";
                case 0:return "未签收";
            }
            return "";
        }
    }

    /**
     * 交易貨幣類型 344.港幣, 156.人民幣
     */
    public static enum  PayDollarCurType{
        CNY(156),HKD(344),USD(840);
        private final Integer id;
        PayDollarCurType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 344:return "HKD$";
                case 156:return "¥";
                case 840:return "USD$";
            }
            return "";
        }

        public static  String queryValue(Integer id) {
            switch (id){
                case 344:return "HKD$";
                case 156:return "¥";
                case 840:return "USD$";
            }
            return "";
        }
    }

    public static enum  PayDollarSuccessCode{
        SUCCESS(0),FAILURE(1);
        private final Integer id;
        PayDollarSuccessCode(Integer id) { this.id = id; }
        public Integer getValue() { return id; }

    }

    /**
     * 交易狀態（1.交易中,2.交易成功,3.交易失敗,4.幣種不匹配）
     */
    public static enum  DealingSlipState{
        ONPAY(1),PAYSUCCESS(2),PAYFAILURE(3),MISMATCH(4),TIMEOUT(5),ABNORMAL(6);
        private final Integer id;
        DealingSlipState(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "交易中";
                case 2:return "交易成功";
                case 3:return "交易失敗";
                case 4:return "交易失敗,交易金額或幣種與實際不匹配";
                case 5:return "交易超时";
                case 6:return "交易异常,此笔交易异常,請手动处理";
            }
            return "";
        }
    }
    /**
     * PDF模板文件名(1.訂單.2.發貨單.3.收據,4.採購單)
     */

    public static enum  PdfFileType{
        INOVICE(1),DELIVERY_NOTE(2),RECEITP(3),PURCHASE_ORDER(4);
        private final Integer id;
        PdfFileType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }

    }

    /**
     * 購物車類型(1.購物車,2.項目)
     */
    public static enum  ShoppingCartType{
        CART(1),ITEM(2);
        private final Integer id;
        ShoppingCartType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "購物車";
                case 2:return "項目";
            }
            return "";
        }
    }

    /**
     * 产品审核状态
     */
    public static enum TemProductState {
        Unaudited(1),PASS(2),AUDITFAILURE(3);
        private final Integer id;
        TemProductState(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "待审核";
                case 2:return "审核通过";
                case 3:return "审核失败";
            }
            return "";
        }
    }

    /**
     * 产品审核状态
     */
    public static enum SupplierApplyState {
        Unaudited(1),PASS(2),AUDITFAILURE(3);
        private final Integer id;
        SupplierApplyState(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "待审核";
                case 2:return "审核通过";
                case 3:return "审核失败";
            }
            return "";
        }
    }


    /**
     * SysMessage類型
     */
    public static enum CartMessageType {
        ADDFRIEND(1),AGREEFRIEND(2),AUDREFUSE(3);
        private final Integer id;
        CartMessageType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "申请添加你为好友";
                case 2:return "已经同意你的好友申请";
                case 3:return "拒绝您的好友申请";
            }
            return "";
        }
    }

    /**
     * SysMessage處理狀態
     */
    public static enum CartMessageStatus {
        UNTREATED(1),PASS(2),REFUSE(3);
        private final Integer id;
        CartMessageStatus(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "未處理(不用處理)";
                case 2:return "通過（成功）";
                case 3:return "拒絕（失敗）";
            }
            return "";
        }
    }


    /**
     * 推送消息類型
     */
    public static enum SendMessageType {
        CART(1),ADDFRIEND(2),UPDATEMSG(3),DELFRIEND(4),ONLINE(5),OFFLINE(6),OFFLINEMESSAGE(7);
        private final Integer id;
        SendMessageType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "聊天消息推送";
                case 2:return "同意添加好友推送";
                case 3:return "更新系統消息推送";
                case 4:return "刪除好友推送";
                case 5:return "好友上线";
                case 6:return "好友下线";
                case 7:return "離線消息";
            }
            return "";
        }
    }


    /**
     * 推送消息類型
     */
    public static enum CartUserStatus {
        OFFLINE(0),ONLINE(1);
        private final Integer id;
        CartUserStatus(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 0:return "offline";
                case 1:return "online";

            }
            return "";
        }
        public static String queryValue(Integer id) {
            switch (id){
                case 0:return "offline";
                case 1:return "online";
            }
            return "";
        }
    }


    /**
     * 聊天用戶id前綴
     */
    public static enum CartIdPrefix {
        ADMIN(1),USER(2);
        private final Integer id;
        CartIdPrefix(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "admin_";
                case 2:return "user_";
            }
            return "";
        }
        public static String queryValue(Integer id) {
            switch (id){
                case 1:return "admin_";
                case 2:return "user_";
            }
            return "";
        }
    }

    /**
     * 集運類型
     */
    public  enum TransitType {
        WEB(1),USER(2);
        private final Integer id;
        TransitType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "官方集運";
                case 2:return "用戶集運";
            }
            return "";
        }

    }
    /**
     * 集運類型
     */
    public  enum PlaceType {
        OVERSEAS(1),HKANDMACAO(2),MAINLAND(3);
        private final Integer id;
        PlaceType(Integer id) { this.id = id; }
        public Integer getValue() { return id; }
        public String queryValue() {
            switch (id){
                case 1:return "海外";
                case 2:return "港澳";
                case 3:return "大陆";
            }
            return "";
        }

    }




}

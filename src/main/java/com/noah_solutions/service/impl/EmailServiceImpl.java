package com.noah_solutions.service.impl;

import com.github.houbb.paradise.common.util.StringUtil;
import com.itextpdf.text.pdf.BaseFont;
import com.noah_solutions.bean.DataCache;
import com.noah_solutions.common.HtmlDataCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.*;
import com.noah_solutions.entity.System;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.OrderRepository;
import com.noah_solutions.service.IEmailService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import com.noah_solutions.util.IdGenerator;
import com.noah_solutions.util.SendMailUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.noah_solutions.common.ProjectConfig.*;
import static com.noah_solutions.common.ProjectConfig.PdfFileType.*;


@Service
@Async
public class EmailServiceImpl implements IEmailService {
    @Resource
    private OrderRepository orderRepository;

    //發貨提醒
    @Override
    @Async
    public void shippingReminder(String lang,String orderId)throws Exception {
        Order order = orderRepository.findByOrderId(orderId);
        String title = "";
        String countent = "";
        if(lang.equals(Language.CN.getValue())){
            title = "發貨提醒";
            countent ="訂單號:"+order.getOrderId()+"<br>已付款,請盡快安排發貨";
        }else if(lang.equals(Language.CNN.getValue())){
            title = "發貨提醒";
            countent ="訂單號:"+order.getOrderId()+"<br>已付款,請盡快安排發貨";
        }
        sendPdfEmail( lang,order.getAdmin().getAdminEmail(),title,countent,PURCHASE_ORDER.getValue(),orderHandle(order));
    }
    //新訂單
    @Override
    @Async
    public void newOrder(String lang,Order order)throws Exception {
        if(StringUtils.isEmpty(lang)){
            lang=Language.CN.getValue();
        }
        String title = "INOVICE";
        String countent = "*** This is an automatically generated email, please do not reply ***";
        if(lang.equals(Language.CN.getValue())){
            title = "INOVICE";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }else if(lang.equals(Language.CNN.getValue())){
            title = "INOVICE";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }
        sendPdfEmail(lang,order.getUser().getUserEmail(),title,countent,INOVICE.getValue(),orderHandle(order));

    }

    //發貨單
    @Override
    @Async
    public void shipmentOrder(String orderId)throws Exception {
        Order order = orderRepository.findByOrderId(orderId);
        String title = "DELIVERY_NOTE";
        String countent = "*** This is an automatically generated email, please do not reply ***";
        if(order.getLang().equals(Language.CN)){
            title = "DELIVERY_NOTE";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }else if(order.getLang().equals(Language.CNN)){
            title = "DELIVERY_NOTE";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }
        sendPdfEmail(order.getLang(),order.getUser().getUserEmail(),title,countent,DELIVERY_NOTE.getValue(),orderHandle(order));
    }

    //訂單通知
    @Override
    @Async
    public void newOrderReminder(String lang,String orderId)throws Exception {
        Order order = orderRepository.findByOrderId(orderId);
        sendPdfEmail(lang,order.getAdmin().getAdminEmail(),"您有新的訂單","訂單號:"+order.getOrderId(),PURCHASE_ORDER.getValue(),orderHandle(order));
    }


    //非會員購買
    @Override
    @Async
    public void nonMemberPurchase(String lang,DataCache data,Boolean newUser)throws Exception {
        String chekcoutKey = IdGenerator.INSTANCE.nextId();//購買頁面的key值
        String passwordKey = IdGenerator.INSTANCE.nextId();//修改密碼的key值
        DataCache dataCache = new DataCache(data.getData(),new Date().getTime()+60*60L*1000,-1);
        dataCache.setUser(data.getUser());
        HtmlDataCache.setHtmlObj(chekcoutKey, dataCache);
        HtmlDataCache.setHtmlObj(passwordKey, new DataCache(data.getUser(),new Date().getTime()+60*60L*1000,1));
        String subject = "Email Address Verification";
        String chockoutUrl = ProjectConfig.WEB_HOST_NAME + "checkout.html?key=" + chekcoutKey;
        String modifyPassWordUrl = ProjectConfig.WEB_HOST_NAME + "resetPwd.html?key=" + passwordKey;
        String content = "<html>";
        content += "<body>";
        content += "<h3><a href='"+""+"'>Buildermall.com.hk</a></h3>";
        content += "</br>";
        content += "<p>Please <a href= '" + chockoutUrl + "'>click here</a> to verify your email address and activate your account !</p>";
        if(newUser){
            content += "<p>Username:<span>"+data.getUser().getUserEmail()+"<span></p> <p>Initial password:<span>888888<span></p>";
        }else{
            content += "<p>You are already a user of this site</p>";
        }
        content += "</br>\"<p>Please <a href= '" + modifyPassWordUrl + "'>click here</a> to Modify your password!</p>\";";
        content += "<p>*** This is an automatically generated email, please do not reply ***</p>";
        content += "</body>";
        content += "</html>";
        sendEmail(1, data.getUser().getUserEmail(),subject,content,null);
    }
    //修改秘密
    @Override
    @Async
    public void modifyPasswordByEmail(String lang, User user)throws Exception {
        String passwordKey = IdGenerator.INSTANCE.nextId();//修改密碼的key值
        HtmlDataCache.setHtmlObj(passwordKey, new DataCache(user,new Date().getTime()+60*60L*1000,1));
        String modifyPassWordUrl = ProjectConfig.WEB_HOST_NAME + "resetPwd.html?key=" + passwordKey;
        String content = "<h3><a href='http://www.buildermall.com/' rel='noopener' target='_blank'>Buildermall.com.hk</a></h3><br><br>";
        content+="<p>Please <a href='"+modifyPassWordUrl+"' rel='noopener' target='_blank'>click here</a> to reset your password !</p><br><br>";
        content+="<p>*** This is an automatically generated email, please do not reply ***</p>";
        sendEmail(1, user.getUserEmail(),"BuilderMall Reset Password Verification",content,null);
    }
    //發送收據
    @Override
    @Async
    public void sendReceipt(DealingSlip dealingSlip)throws Exception {
        Order order = new Order();
        Set<OrderProduct> orderProducts = new HashSet<>();
        List<Order> orders = orderRepository.findOrderByDeslNo(dealingSlip.getDeslNo());
        if(orders.size()>0){
            BeanUtils.copyNotNullProperties(orders.get(0),order);
            order.setOrderNo(dealingSlip.getDeslNo());
            order.setOrderTotalPrice(dealingSlip.getDeslAmount()+"");
        }
        orders.forEach(v->orderProducts.addAll(v.getOrderProducts()));
        String title = "OFFICIAL RECEIPT";
        String countent = "*** This is an automatically generated email, please do not reply ***";
        if(order.getLang().equals(Language.CN)){
            title = "OFFICIAL RECEIPT";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }else if(order.getLang().equals(Language.CNN)){
            title = "OFFICIAL RECEIPT";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }
        sendPdfEmail(order.getLang(),order.getUser().getUserEmail(),title,countent,RECEITP.getValue(),orderHandle(order));


        if(order.getLang().equals(Language.CN.getValue())){
            title = "PURCHASE_ORDER";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }else if(order.getLang().equals(Language.CNN.getValue())){
            title = "PURCHASE_ORDER";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }else {
            title = "PURCHASE_ORDER";
            countent ="*** This is an automatically generated email, please do not reply ***";
        }
        sendPdfEmail(order.getLang(),order.getAdmin().getAdminEmail(),title,countent,PURCHASE_ORDER.getValue(),orderHandle(order));
    }

    @Override
    public void excessReminder(DealingSlip dealingSlip, System system)throws Exception {
        SendMailUtil.sendQQMail(1, system.getSystemEmail(),"交易超額提醒","交易號:"+dealingSlip.getDeslPayRef(),null);
    }

    @Override
    public void evaluateEmail(String lang,String order, User user)throws Exception {
        DataCache dataCache = new DataCache(order,new Date().getTime()+60*60*24*7L,10);
        dataCache.setUser(user);
        String key = HtmlDataCache.setHtmlObj(dataCache);
        String url = WEB_HOST_NAME+"userControl.html?key="+key;
        String title = "Product evaluation reminder";
        String countent = "You have completed orders that have not been evaluated, <a href='"+url+"'>   click  here  </a> to evaluate!";
        if(lang.equals(Language.CN.getValue())){
            title = "產品評價提醒";
            countent ="您有未評價的已完成訂單,<a href='"+url+"'>    點擊这里   </a> 前往評價!";
        }else if(lang.equals(Language.CNN.getValue())){
            title = "产品评价提醒";
            countent ="您有未評估的已完成訂單，<a href='"+url+"'>   點擊这里   </a> 前往評估！";
        }
        sendEmail(1, user.getUserEmail(),title,countent,new HashMap<>());
    }

    @Override
    public void sendCartMesseng(String email)throws Exception {
        String title = "聊天消息提醒";
        String countent = "<span>您有新的聊天消息需要处理,请<a href='"+ HOST_NAME+"'>点击</a>查看</span>";
        sendEmail(1, email,title,countent,new HashMap<>());

    }


    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    /**
     *
     * @param lang 語言
     * @param to 目的地
     * @param title 標題
     * @param content 內容
     * @param pdfType 要發送的pdf類型
     * @throws Exception
     */
    private  void sendPdfEmail(String lang,String to,String title,String content,Integer pdfType,  Map<String, Object> opdf)throws Exception{
        String tempFile = "";
        Map<String,ByteArrayDataSource> map = new HashMap<>();
        switch (pdfType){
            case 1:{
                if(lang.equals(Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                    tempFile = INVOICE_FORM_CN;
                }else if(lang.equals(Language.CNN.getValue())) {
                    tempFile = INVOICE_FORM_CNN;
                }else {
                    tempFile = INVOICE_FORM_EN;
                }
                break;
            }
            case 2:{
                if(lang.equals(Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                    tempFile = INVOICE_DELIVERY_NOTE_CN;
                }else if(lang.equals(Language.CNN.getValue())) {
                    tempFile = INVOICE_DELIVERY_NOTE_CNN;
                }else {
                    tempFile = INVOICE_DELIVERY_NOTE_EN;
                }
                break;
            }
            case 3:{

                if(lang.equals(Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                    tempFile = RECEIPT_FORM_CN;
                }else if(lang.equals(Language.CNN.getValue())) {
                    tempFile = RECEIPT_FORM_CNN;
                }else {
                    tempFile = RECEIPT_FORM_EN;
                }
                break;
            }
            case 4:{
                if(lang.equals(Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                    tempFile = INVOICE_PURCHASE_ORDER_CN;
                }else if(lang.equals(Language.CNN.getValue())) {
                    tempFile = INVOICE_PURCHASE_ORDER_CNN;
                }else {
                    tempFile = INVOICE_PURCHASE_ORDER_EN;
                }
                break;
            }
        }

        ClassLoader classLoader = getClass().getClassLoader();
        // ClassLoader classLoader = cls.getClassLoader();
        File file = new File(ProjectConfig.FILE_PDF_DIRECTORY +"/"+tempFile);

        if (file.exists()) {
            Configuration configuration = new Configuration(
                    Configuration.VERSION_2_3_21);
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(new File(ProjectConfig.FILE_PDF_DIRECTORY ));
            Template tpl = configuration.getTemplate(tempFile);
            StringWriter writer = new StringWriter();
            tpl.process(opdf, writer);
            writer.flush();
            String html = writer.toString();
            ITextRenderer render = new ITextRenderer();
            //添加字体，以支持中文
            render.getFontResolver().addFont(ProjectConfig.FILE_PDF_DIRECTORY + "report/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            render.getFontResolver().addFont(ProjectConfig.FILE_PDF_DIRECTORY + "report/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            render.getFontResolver().addFont(ProjectConfig.FILE_PDF_DIRECTORY + "report/msjh.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            ByteArrayOutputStream oo = new ByteArrayOutputStream();
            render.setDocumentFromString(html);
            render.layout();
            render.createPDF(oo);
            render.finishPDF();
            map.put(title+".pdf",new ByteArrayDataSource(oo.toByteArray(), "application/octet-stream"));
            sendEmail(1, to,title,html,null);
        } else {
            throw new CustormException(CodeAndMsg.FILE_PDF_NOTFIND);
        }
    }

    private  Map<String, Object> orderHandle(Order order){
        OrderAddr orderAddr = new OrderAddr();
        for (OrderAddr o:order.getOrderAddrs()) {
            if(o.getOrderAddrType()== OrderAddrType.RECADDR.getValue())
                orderAddr = o;
        }
        Map<String, Object> opdf = new HashMap<String, Object>();
        order.getOrderProducts().forEach(v->v.setLang(order.getLang()));
        opdf.put("data", order);
        opdf.put("addr", orderAddr);
        opdf.put("webPath",ProjectConfig.WEB_HOST_NAME);
        opdf.put("adminPath",ProjectConfig.HOST_NAME);
        opdf.put("curDate", DateUtil.getStringDateShort());
        return opdf;
    }

    private void sendEmail(Integer type,String to, String title, String content, Map<String,ByteArrayDataSource> dataSource) throws  Exception, UnsupportedEncodingException {
        SendMailUtil.sendQQMail(1, to,title,content,dataSource);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////


}

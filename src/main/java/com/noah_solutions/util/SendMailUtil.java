package com.noah_solutions.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.Map;
import java.util.Properties;

@Component
public class SendMailUtil {
    private static String inlandHost;
    private static String inlandport;
    private static String inlandName;
    private static String inlandPW;

    private static String hkHost;
    private static String hkPort;
    private static String hkName;
    private static String hkPW;

    @Value("${email.inland.host}")
    public void setInlandHost(String inlandHost) {
        SendMailUtil.inlandHost = inlandHost;
    }
    @Value("${email.inland.port}")
    public void setInlandport(String inlandport) {
        SendMailUtil.inlandport = inlandport;
    }
    @Value("${email.inland.userName}")
    public void setInlandName(String inlandName) {
        SendMailUtil.inlandName = inlandName;
    }
    @Value("${email.inland.passWord}")
    public void setInlandPW(String inlandPW) {
        SendMailUtil.inlandPW = inlandPW;
    }
    @Value("${email.hk.host}")
    public void setHkHost(String hkHost) {
        SendMailUtil.hkHost = hkHost;
    }
    @Value("${email.hk.port}")
    public void setHkPort(String hkPort) {
        SendMailUtil.hkPort = hkPort;
    }
    @Value("${email.hk.userName}")
    public void setHkName(String hkName) {
        SendMailUtil.hkName = hkName;
    }
    @Value("${email.hk.passWord}")
    public void setHkPW(String hkPW) {
        SendMailUtil.hkPW = hkPW;
    }


    public static void sendQQMail(Integer type,String recipients, String title, String cont, Map<String,ByteArrayDataSource> dataSource) throws Exception {
        type=2;
        String sender = "";
        String pw ="";
        switch (type){
            case 1:{
                sender = inlandName;
                pw = inlandPW;
                break;
            }
            case 2:{
                sender = hkName;
                pw = hkPW;
                break;
            }
        }

        Properties properties = properties(type);
        // 得到回话对象
        String finalSender = sender;
        String finalPw = pw;
        Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalSender, finalPw);
            }
        });
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(sender,"BuilderMall","UTF-8"));
        // 设置收件人邮箱地址
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));//一个收件人
        // 设置邮件标题
        message.setSubject(title);
        // 设置邮件内容
//
//        message.setText(cont);
        //整封邮件的MINE消息体
        MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
        if (dataSource != null) {
            for (String key : dataSource.keySet()) {
                MimeBodyPart attch = new MimeBodyPart(); // 附件
                attch.setDataHandler(new DataHandler(dataSource.get(key)));
                attch.setFileName(key);
                msgMultipart.addBodyPart(attch);         // 将附件添加到MIME消息体中
            }

        }
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(cont, "text/html;charset=utf-8");
        msgMultipart.addBodyPart(htmlPart);        // 设置邮件内容
        message.setContent(msgMultipart);
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(sender, pw);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    protected static Properties properties(Integer type)throws Exception {
        String host="";
        String port="";
        String name="";
        String pw = "";
        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
        switch (type){
            case 1:{
                host = inlandHost;
                port = inlandport;
                name = inlandName;
                pw = inlandPW;
//                MailSSLSocketFactory sf = new MailSSLSocketFactory();
//                properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
//                properties.put("mail.smtp.ssl.socketFactory", sf);
                properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                properties.setProperty("mail.smtp.socketFactory.port", port);
                break;
            }
            case 2:{
                host = hkHost;
                port = hkPort;
                name = hkName;
                pw = hkPW;
                properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                properties.setProperty("mail.smtp.socketFactory.port", port);
                break;
            }
        }
        properties.put("mail.smtp.host", host);// 主机名
        properties.put("mail.smtp.port", port);// 端口号
        properties.put("mail.user", name);
        properties.put("mail.password", pw);


        return properties;


    }
}

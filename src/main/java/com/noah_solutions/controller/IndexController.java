package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.IAdminService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static com.noah_solutions.common.ProjectConfig.ADMIN_URL_REDIRECT;
import static com.noah_solutions.common.ProjectConfig.URL_REDIRECT;
import static org.omg.CORBA.ORB.init;

@RestController
public class IndexController {
    @Resource
    private IAdminService adminService;

    @Resource
    private ProductTypeCache productTypeCache;
    //用户登录
    @PostMapping("/login.do")
    public  ModelAndView login(@RequestParam("loginUserName") String loginUserName,
                               @RequestParam("loginPassWord") String loginPassWord,
                               @RequestParam(value = "param",required = false) String param,
                               @RequestParam("verifyCode") String verifyCode,HttpSession session)throws Exception{
        if(!StringUtils.isEmpty(param))
            session.setAttribute(ADMIN_URL_REDIRECT,ProjectConfig.URL_REDIRECT_MAIN+(StringUtils.isEmpty(param)?"":"?"+param));//保存跳轉鏈接url
        ModelAndView modelAndView = new ModelAndView();
        if(session.getAttribute(ProjectConfig.VALIDATECODE)==null||!session.getAttribute(ProjectConfig.VALIDATECODE).equals(verifyCode)){
            throw new CustormException(CodeAndMsg.ADMIN_LOGINERROR,ProjectConfig.URL_REDIRECT_LOGIN);
        }
        session.setAttribute(ProjectConfig.LOGIN_ADMIN,adminService.adminLogin(loginUserName,loginPassWord));
        session.removeAttribute(ProjectConfig.VALIDATECODE);
        Object url = session.getAttribute(ADMIN_URL_REDIRECT);
        if(!StringUtils.isEmpty(url)){
            modelAndView.setViewName((String) url);
            session.removeAttribute(ADMIN_URL_REDIRECT);
        }else {
            modelAndView.setViewName(ProjectConfig.URL_REDIRECT_MAIN);
        }
        return modelAndView;
    }

    //重新加載緩存
    @GetMapping("/clearAache.do")
    public JSONObject clearAache(HttpSession session)throws Exception{
        productTypeCache.init();
        return CodeAndMsg.SUCESS.getJSON();
    }

    //获取随机验证码
    @RequestMapping(value = "/verifyCode")
    public void verifyCode(HttpServletRequest req, HttpServletResponse resp,HttpSession session)
            throws ServletException, java.io.IOException {
        init();
        // 定义图像buffer
        Integer width = 80;
        Integer height = 38;
        Integer fontHeight = 25;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        g.setFont(font);
        // 画边框。
        // g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < 4; i++) {
            // 得到随机产生的验证码数字。
//            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
            String strRand = random.nextInt(10)+"";
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(198);
            green = random.nextInt(233);
            blue = random.nextInt(109);
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * 14, 28);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        session.setAttribute(ProjectConfig.VALIDATECODE, randomCode.toString());
        //indexRedisService.setMess2Reids(UPSConstant.VALIDATECODE + "-" + serialNo, randomCode.toString());
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}

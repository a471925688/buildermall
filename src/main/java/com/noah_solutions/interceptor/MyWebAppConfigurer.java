package com.noah_solutions.interceptor;

import com.noah_solutions.common.ProjectConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Configuration
    public class MyWebAppConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            /**
             * @Description: 对文件的路径进行配置,创建一个虚拟路径/Path/** ，即只要在<img src="/Path/picName.jpg" />便可以直接引用图片
             *这是图片的物理路径 "file:/+本地图片的地址"
             * @Date： Create in 14:08 2017/12/20
             */
            registry.addResourceHandler("/temp/**").addResourceLocations("file:"+ ProjectConfig.FILE_TEMPORARY_DIRECTORY);
            registry.addResourceHandler("/image/**").addResourceLocations("file:"+ ProjectConfig.FILE_IMAGE_DIRECTORY);
            registry.addResourceHandler("/evaluation/**").addResourceLocations("file:"+ ProjectConfig.FILE_EVALUATION_IMAGE_DIRECTORY);
            registry.addResourceHandler("/video/**").addResourceLocations("file:"+ProjectConfig.FILE_VIDEO_DIRECTORY);
            registry.addResourceHandler("/contents/**").addResourceLocations("file:"+ProjectConfig.FILE_CONTENTS_DIRECTORY);
            registry.addResourceHandler("/specifications/**").addResourceLocations("file:"+ProjectConfig.FILE_SPECIFICATIONS_DIRECTORY);
            registry.addResourceHandler("/layedit/**").addResourceLocations("file:"+ProjectConfig.UPLOAD_PATH_LAYEDIT);


            super.addResourceHandlers(registry);
        }
    }
//    // 访问图片方法
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
//            String imagesPath = MyWebAppConfigurer.class.getClassLoader().getResource("").getPath();
//            if (imagesPath.indexOf(".jar") > 0) {
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//            } else if (imagesPath.indexOf("classes") > 0) {
//                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
//            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
//            mImagesPath = imagesPath;
//        }
//        LoggerFactory.getLogger(MyWebAppConfigurer.class).info("imagesPath=" + mImagesPath);
//        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
//        super.addResourceHandlers(registry);
//    }

    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        List<String> list = new ArrayList<>();
        list.add("/assets/**");
        list.add("/css/**");
        list.add("/font/**");
        list.add("/images/**");
        list.add("/js/**");
        list.add("/text/**");

        list.add("/index");
        list.add("/login.do");
        list.add("/verifyCode");
        list.add("/webProduct/**");
        list.add("/webUser/**");
        list.add("/webData/**");
        list.add("/webPlace/**");
        list.add("/webOrder/**");
        list.add("/webCart/**");
        list.add("/webAdvertising/**");
        list.add("/webSupplier/**");
        list.add("/webTransit/**");
        list.add("/pay/**");
        list.add("/swagger-ui.html");
        list.add("/video/**");
        list.add("/image/**");
        list.add("/evaluation/**");
        list.add("/contents/**");
        list.add("/specifications/**");
        list.add("/layedit/**");
        list.add("/file/**");
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/**").excludePathPatterns(list);//拦截所有的Admin的方法
        super.addInterceptors(registry);

    }
}

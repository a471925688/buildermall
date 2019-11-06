package com.noah_solutions.interceptor;//package com.nykj.interceptor;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 用户的拦截器
 */
public class AdminInterceptor implements  HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
     //">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）"
        Admin admin =  (Admin) request.getSession().getAttribute("admin");
        if (admin==null){
            response.sendRedirect(ProjectConfig.HOST_NAME);//判断用户登录的为空就跳转到登录的页面
            return false;
        }else {
            return true; //只有返回true才会继续操作下去
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //* System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //*System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}

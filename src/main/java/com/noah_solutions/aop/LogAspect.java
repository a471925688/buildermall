package com.noah_solutions.aop;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.SysLog;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.ISysLogService;
import com.noah_solutions.util.HttpUtil;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * 2018 12 5 lijun
 * 切面处理
 */
@Component
@Aspect
public class LogAspect {
    @Resource
    private ISysLogService sysLogService;

    private static final Logger logger = LoggerFactory
            .getLogger(LogAspect.class);
    private static String[] types = { "java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float" };
    private static Properties properties;
    static {
        InputStream in=null;
        File file;
        try {
            file = ResourceUtils.getFile("classpath:logconfig.properties");
           //file = new File("logconfig.properties");
            if(!file.exists()){
                file.createNewFile();
            }
            in = new FileInputStream(file);
            properties = new Properties();
           /* properties.load(in);*/
            properties.load(new InputStreamReader(in, "GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //////////////////////////////////切入点定义部分  start//////////////////////////////////////////////////////////////////////////
    //////////////////////////////////切入点定义部分  start//////////////////////////////////////////////////////////////////////////

    /**
     * 页面访问切入点
     */
    @Pointcut("execution(public org.springframework.web.servlet.ModelAndView com.*.controller.*.*(..))")
    public void ViewCell(){}

    /**
     * 登录方法的切入点
     */
    @Pointcut("execution(public * com.*.controller.IndexController.login(..))")
    public void loginCell(){}

    /**
     * MainController切入点
     */
    @Pointcut("execution(public * com.*.controller.MainController.*(..))")
    public void MainViewCell(){}

    /**
     * 执行添加或更新任务的切入点
     */
    @Pointcut("execution(public * com.*.controller.*Controller.add*(..))")
    public void addCell(){}

    /**
     * 执行删除任务的切入点
     */
    @Pointcut("execution(public * com.*.controller.*Controller.del*(..))")
    public void delCell(){}
    //////////////////////////////////切入点定义部分  end//////////////////////////////////////////////////////////////////////////
    //////////////////////////////////切入点定义部分  end//////////////////////////////////////////////////////////////////////////










    //////////////////////////////////日志切入部分  start//////////////////////////////////////////////////////////////////////////
    //////////////////////////////////日志切入部分  start//////////////////////////////////////////////////////////////////////////

    /**
     * 业务日志切入点(后置通知)
     * @param joinPoint
     * @throws Throwable
     */
    @AfterReturning(value = "ViewCell()||MainViewCell()||addCell()||delCell()||loginCell()", argNames = "joinPoint")
    public void ColltrollerCell(JoinPoint joinPoint) throws Throwable {
        logHandle(joinPoint,0,null);
    }
    /**
     * 业务日志切入点(异常通知)
     * @param joinPoint
     * @throws Throwable
     */
    @AfterThrowing(value = "ViewCell()||MainViewCell()||addCell()||delCell()||loginCell()", throwing = "e",argNames = "joinPoint,e")
    public void ColltrollerCellEx(JoinPoint joinPoint,Throwable e) throws Throwable {
        logHandle(joinPoint,1,e);
    }
    //////////////////////////////////日志切入部分  end//////////////////////////////////////////////////////////////////////////
    //////////////////////////////////日志切入部分  end//////////////////////////////////////////////////////////////////////////
//    @AfterReturning(value = "addCell()")
//    public void addHandleCell() throws Throwable {
//    }
//    @AfterReturning(value = "delCell()")
//    public void delHandleCell() throws Throwable {
//    }








    //////////////////////////////////业务方法处理部分  start//////////////////////////////////////////////////////////////////////////
    //////////////////////////////////业务方法处理部分  start//////////////////////////////////////////////////////////////////////////

    //业务日志处理处
    private void logHandle(JoinPoint joinPoint,Integer state,Throwable e)throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String requestPath = request.getRequestURI().replace(request.getContextPath(),"");

        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = null;
        clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String clazzSimpleName = clazz.getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);

        String logContent = writeLogInfo(paramNames, joinPoint);

        Logger logger = LoggerFactory.getLogger(clazzName);
        logger.info("clazzName: "+clazzName+", methodName:"+methodName+", param:"+ logContent);

        SysLog log = new SysLog();
        log.setLogContent(properties.getProperty(requestPath.replace("/buildermall","")));
        //判断是否后台操作
        if(clazzSimpleName.equals("webcontroller")){
            log.setLogModule("前端");
        }else {
            log.setLogModule("后台");
            //设置日志类型为1（管理员操作）
            log.setLogUserType(1);
            Admin admin = (Admin) session.getAttribute(ProjectConfig.LOGIN_ADMIN);
            if(admin!=null){
                log.setLogUserId(admin.getAdminId());
                log.setLogUserName(admin.getLogin().getLoginUserName());
            }
        }
        //设置请求控制器名
        log.setLogController(clazzSimpleName);
        //设置请求方法名
        log.setLogMethod(methodName);
        if(methodName.equals("login")&&state==1){
            log.setLogUserName((String) getLoginUserName(joinPoint));
        }
        //获取ip
        String ip = HttpUtil.getIPAddress(request);
        //设置ip
        if(ip.equals("0:0:0:0:0:0:0:1")){
            ip="localhost";
        }
        log.setLogIp(ip);
        //封装设置请求json
        log.setLogJson("clazzName: "+clazzName+", methodName:"+methodName+", param:"+ logContent);
        //设置请求状态(0.成功  1.失败)
        log.setLogState(state);
        if(state==1){
            if(e instanceof CustormException){
                log.setLogEx("业务异常["+e.getClass().getSimpleName()+"]:[code:"+((CustormException) e).getCode()+",msg:"+e.getMessage());
            }else {
                log.setLogEx("系统异常["+e.getClass().getSimpleName()+"]:[msg:"+e.getMessage());
            }
        }
        StringBuilder logParam = new StringBuilder();
        log.setLogParam(logContent);
        sysLogService.saveSysLog(log);
    }

    //获取登录用户名
    private Object getLoginUserName(JoinPoint joinPoint){
        Object[] paramValues = joinPoint.getArgs();
        for(int i=0;i<paramValues.length;i++){
            if(paramValues[i].getClass().equals(Login.class)){
                return ((Login)paramValues[i]).getLoginUserName();
            }
        }
        return null;
    }



        //访问参数封装
    private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean clazzFlag = true;
        sb.append("{");
        for(int k=0; k<args.length; k++){
            Object arg = args[k];
            sb.append("\""+paramNames[k]+"\"");
            // 获取对象类型
            if(arg==null)
                continue;
            String typeName = arg.getClass().getTypeName();
            for (String t : types) {
                if (t.equals(typeName)) {
                    sb.append(":" + arg+"; ");
                }
            }
            if (clazzFlag) {
                sb.append(getFieldsValue(arg));
            }
            sb.append(",");
        }
        if(sb.toString().endsWith(","))
            sb = new StringBuilder(sb.toString().substring(0,sb.length()-1));
        return sb.toString()+"}";
    }

    /**
     * 得到方法参数的名称
     * @param cls
     * @param clazzName
     * @param methodName
     * @return
     * @throws Exception
     */
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++){
            paramNames[i] = attr.variableName(i + pos);	//paramNames即参数名
        }
        return paramNames;
    }

    /**
     * 得到参数的值
     * @param obj
     */
    public static String getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();
        for (String t : types) {
            if(t.equals(typeName))
                return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(":{");
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : types) {
                    if (f.getType().getName().equals(str)){
                        if(f.get(obj)==null){
                            sb.append("\""+f.getName() + "\":null,");
                        }else{
                            sb.append("\""+f.getName() + "\":\"" + f.get(obj)+"\",");
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(sb.toString().endsWith("{")){
            sb = new StringBuilder(sb.toString().replace("{","null"));
        }else {
            sb = new StringBuilder(sb.substring(0,sb.length()-1));
            sb.append("}");
        }

        return sb.toString();
    }

    /**
     * 得到操作信息
     * @param joinPoint
     * @throws Exception
     */
    public void adminOptionContent(JoinPoint joinPoint) throws Exception {
        StringBuffer rs = new StringBuffer();
        String className = null;
        int index = 1;
        Object[] args = joinPoint.getArgs();
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型：" + className + "，值：");
            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                System.out.println(methodName);
                // 判断是不是get方法
                if (methodName.indexOf("get") == -1) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                    if (rsValue == null) {// 没有返回值
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                // 将值加入内容中
                rs.append("(" + methodName + " : " + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        System.out.println(rs.toString());
    }

    /**
     * 获取参数名
     * @param clazzName
     * @param methodName
     * @throws Exception
     */
    private void getParamterName(String clazzName, String methodName)
            throws Exception {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attr.variableName(i + pos);
        // paramNames即参数名
        for (int i = 0; i < paramNames.length; i++) {
            System.out.println(paramNames[i]);
        }
    }


    //////////////////////////////////业务方法处理部分  end//////////////////////////////////////////////////////////////////////////
    //////////////////////////////////业务方法处理部分  end//////////////////////////////////////////////////////////////////////////


}
package com.noah_solutions.controller;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.User;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;

import javax.servlet.http.HttpSession;

/**
 * Controller基类
 * Created by
 */
public class BaseController {

    protected User getAndCheckLoginUser(HttpSession session)throws Exception{
        if(session.getAttribute(ProjectConfig.LOGIN_USER)==null)
            throw new CustormException(CodeAndMsg.ERROR_CHECK_USER);
        return (User) session.getAttribute(ProjectConfig.LOGIN_USER);
    }
    protected String getLoginUserId(HttpSession session){
        String userId = null;
        Object obj = session.getAttribute(ProjectConfig.LOGIN_USER);
        if(obj!=null){
            userId = ((User)obj).getUserId();
        }
        return userId;
    }

    protected Admin getAndCheckLoginAdmin(HttpSession session)throws Exception{
        if(session.getAttribute(ProjectConfig.LOGIN_ADMIN)==null)
            throw new CustormException(CodeAndMsg.ERROR_CHECK_USER);
        return (Admin) session.getAttribute(ProjectConfig.LOGIN_ADMIN);
    }
    protected String getLoginAdminId(HttpSession session){
        String userId = null;
        Object obj = session.getAttribute(ProjectConfig.LOGIN_ADMIN);
        if(obj!=null){
            userId = ((User)obj).getUserId();
        }
        return userId;
    }

}

package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.controller.BaseController;
import com.noah_solutions.entity.Transit;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.ITransitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/webTransit")
public class WebTransitController extends BaseController {

    @Resource
    private ITransitService transitService;

    @ApiOperation(value="獲取當前用戶集運地址", notes="獲取當前用戶集運地址")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/listByUserId.do")
    public JSONObject listByUserId(HttpServletResponse response, HttpSession session){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(transitService.listByUserId(getLoginUserId(session)));
    }




    @ApiOperation(value="添加集運點", notes="添加集運點")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/add.do")
    public JSONObject add(Transit transit,HttpServletResponse response, HttpSession session){
        response.setHeader("Access-Control-Allow-Credentials","true");
        transit.setUserId(getLoginUserId(session));
        transit.setType(ProjectConfig.TransitType.USER.getValue());
        transitService.add(transit);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }



    @ApiOperation(value="刪除集運點", notes="刪除集運點")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/del.do")
    public JSONObject del(@RequestParam("id") String id,HttpServletResponse response, HttpSession session){
        response.setHeader("Access-Control-Allow-Credentials","true");
        transitService.del(id,getLoginUserId(session));
        return CodeAndMsg.DELSUCESS.getJSON();
    }

}

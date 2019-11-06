package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.entity.AdvertisingType;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IAdvertisingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/webAdvertising")
public class WebAdvertising {

    @Resource
    private IAdvertisingService advertisingService;

    @ApiOperation(value="获取輪播圖通過類型Id", notes="获取輪播圖通過類型Id")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getAllAdvertisingByTypeId.do")
    public JSONObject getAllAdvertisingByTypeId(@RequestParam("advertisingTypeId") String advertisingTypeId, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(advertisingService.selectAllByImageGroupId(advertisingTypeId));
    }

}

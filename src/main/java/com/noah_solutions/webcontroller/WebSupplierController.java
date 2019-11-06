package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.SupplierApply;
import com.noah_solutions.entity.SupplierApplyProduct;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IAdvertisingService;
import com.noah_solutions.service.ISupplierApplyService;
import com.noah_solutions.util.BeanUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/webSupplier")
public class WebSupplierController {

    @Resource
    private IAdvertisingService advertisingService;
    @Resource
    private ISupplierApplyService supplierApplyService;


    @ApiOperation(value="供应商申请审核", notes="供应商申请审核")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/addSupplierApply.do")
    public JSONObject getAllAdvertisingByTypeId(String supplierApplyJson,HttpServletResponse response){
        SupplierApply supplierApply = JSONObject.parseObject(supplierApplyJson,SupplierApply.class);
        response.setHeader("Access-Control-Allow-Credentials","true");
        supplierApplyService.add(supplierApply);
        return CodeAndMsg.SUCESS.getJSON(supplierApply);
    }

}

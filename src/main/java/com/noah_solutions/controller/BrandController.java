package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Brand;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IBrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.noah_solutions.common.ProjectConfig.LOGIN_ADMIN;

/**
 * 2018 12 20 lijun
 * 品牌相關控制器
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Resource
    private IBrandService brandService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //添加品牌页面
    @GetMapping("/addBrandView")
    public ModelAndView addBrandView(){
        return new ModelAndView("product/brand/addOrUpdateBrand");
    }

    //编辑品牌页面
    @GetMapping("/editBrandView")
    public ModelAndView editBrandView(@RequestParam("brandId")String brandId){
        return new ModelAndView("product/brand/addOrUpdateBrand","brandId",brandId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有品牌
    @RequestMapping("/getAllBrand.do")
    public JSONObject getAllBrand(){
        return CodeAndMsg.SUCESS.getJSON(brandService.selectAllBrand());
    }
    //根據當前管理員id查询所有品牌
    @RequestMapping("/getAllBrandByAdmin.do")
    public JSONObject getAllBrandByAdmin(HttpSession session){
        Admin admin = (Admin) session.getAttribute(ProjectConfig.LOGIN_ADMIN);
        if(admin.getAdminType().equals(ProjectConfig.AdminType.ADMIN.getValue())){
            return CodeAndMsg.SUCESS.getJSON(brandService.selectAllBrand());
        }else {
            return CodeAndMsg.SUCESS.getJSON(brandService.selectAllBrandByAdmin(admin.getAdminId()));
        }

    }
    //根据条件分页获取品牌信息
    @RequestMapping("/getBrandPageByCont.do")
    public JSONObject getBrandPageByCont(Brand condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,HttpSession session){
        condition.setAdminId(chekckAdminType(session));
        Page<Brand> brands = brandService.selectBrandPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "brandId")));
        return CodeAndMsg.SUCESS.getJSON(brands.getContent()).fluentPut("count",brands.getTotalElements());
    }
    //查询单个品牌
    @RequestMapping("/getBrandById.do")
    public JSONObject getBrandById(@RequestParam("brandId") String brandId)throws Exception {
        return CodeAndMsg.SUCESS.getJSON(brandService.selectBrandByBrandId(brandId));
    }
    //查询品牌数量
    @RequestMapping("/getCountBrand.do")
    public JSONObject getCountBrand(){
        return CodeAndMsg.SUCESS.getJSON(brandService.selectCountBrandByCont(new Brand()));
    }
    //查询品牌数量
    @RequestMapping("/getCountBrandByCont.do")
    public JSONObject getCountBrandByCont(Brand brand){
        return CodeAndMsg.SUCESS.getJSON(brandService.selectCountBrandByCont(brand));
    }

    //查询國內品牌数量
    @RequestMapping("/getCountBrandIsCN.do")
    public JSONObject getCountBrandIsCN(Brand brand){

        return CodeAndMsg.SUCESS.getJSON(brandService.selectCountBrandIsCN());
    }
    //查询國外品牌数量
    @RequestMapping("/getCountBrandIsNotCN.do")
    public JSONObject getCountBrandIsNotCN(Brand brand){

        return CodeAndMsg.SUCESS.getJSON(brandService.selectCountBrandIsNotCN());
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加品牌
    @RequestMapping("/addBrand.do")
    public JSONObject addBrand(Brand brand, HttpSession session)throws Exception {
        brand.setAdminId(((Admin)session.getAttribute(ProjectConfig.LOGIN_ADMIN)).getAdminId());
        brandService.addBrand(brand);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }
    //编辑品牌
    @RequestMapping("/editBrand.do")
    public JSONObject editBrand(Brand brand)throws Exception {
        brandService.updateBrand(brand);
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    
    //删除单个品牌
    @PostMapping("/delBrandById.do")
    public JSONObject delBrandById(@RequestParam("brandId") String brandId)throws Exception {
        brandService.delBrandByBrandId(brandId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个品牌
    @PostMapping("/delAllBrandById.do")
    public JSONObject delAllBrandById(@RequestParam("brandIds[]") List<String> brandIds)throws Exception {
        brandService.delAllBrandByBrandId(brandIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


    //檢測管理員類型,并返回供應商id
    private String chekckAdminType(HttpSession session){
        String adminId = null;
        Admin admin = (Admin)session.getAttribute(LOGIN_ADMIN);
        if(admin.getAdminType()==ProjectConfig.AdminType.SUPPLIER.getValue())
            adminId = admin.getAdminId();
        return adminId;
    }


}

package com.noah_solutions.service.impl;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.chat.CartUser;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.AdminRepository;
import com.noah_solutions.repository.LoginRepository;
import com.noah_solutions.repository.RoleRepository;
import com.noah_solutions.repository.cart.CartUserRepository;
import com.noah_solutions.service.IAdminService;
import com.noah_solutions.service.ICartService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.MD5Util;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
    @Resource
    private AdminRepository adminRepository;
    @Resource
    private LoginRepository loginRepository;
    @Resource
    private RoleRepository roleRepository;
//    @Resource
//    private CartUserRepository cartUserRepository;
    @Resource
    private ICartService cartService;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有管理员
    @Override
    public List<Admin> selectAllAdmin() {
        return adminRepository.findAllAdmin();
    }

    @Override
    public List<Admin> getAllAdminByCont(Admin admin) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  adminRepository.findAll(Example.of(admin,matcher));
    }

    //分页查询所有管理员（带条件）
    @Override
    public Page<Admin> selectAdminPageByCont(Admin condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  adminRepository.findAll(Example.of(condition,matcher),pageable);
    }
    //查询所有管理员（通过角色id）
    @Override
    public List<Admin> selectAllAdminByRoleId(String roleId) {
        return adminRepository.findAllAdminByRoleId(roleId);
    }

    //查询管理员(通过管理员id)
    @Override
    public Admin selectAdminByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId);
    }

    /**
     * 分组查询每种角色用户数量
     * @return 返回角色名和数量的集合
     */
    @Override
    public List selecrAllCountGroupByRoleId() {
        return  adminRepository.findAllCountGroupByRoleId();
    }

    //查询管理员数量(带条件)
    @Override
    public Long selectCountAdminByCont(Admin condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  adminRepository.count(Example.of(condition,matcher));
    }

    //查詢登錄次數
    @Override
    public Long getCountLoginByAdminType(Integer adminType){
        return  adminRepository.getCountLoginByAdminType(adminType);
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添加管理员
    @Override
    @Transactional
    public void addAdmin(Admin admin, Login login)throws Exception {
        //判断用户邮箱是否重复
        if(adminRepository.existsByLogin_LoginUserNameOrAdminEmail(login.getLoginUserName(),admin.getAdminEmail()))
            throw new CustormException(CodeAndMsg.ADMIN_ADDERROR);
        //设置管理员权限
        admin.setRole(roleRepository.getOne(admin.getRoleId()));
        //MD5密码加密
        login.setLoginPassWord(MD5Util.getStringMD5(login.getLoginPassWord()));
        //设置登录信息
        admin.setLogin(loginRepository.saveAndFlush(login));
        //保存管理员
        admin = adminRepository.saveAndFlush(admin);
        cartService.addCart(new CartUser("暫無簽名",null,admin.getAdminId()));
//        if(!cartUserRepository.existsByAdminId(admin.getAdminId()))
//            cartUserRepository.save(new CartUser("暫無簽名",null,admin.getAdminId()));

    }
    //更新管理员
    @Override
    @Transactional
    public void updateAdmin(Admin admin, Login login)throws Exception {
        //取出admin
        Admin adminEntity  = adminRepository.getOne(admin.getAdminId());
        //更新admin
        BeanUtils.copyNotNullProperties(admin,adminEntity);
        //MD5密码加密
        login.setLoginPassWord(MD5Util.getStringMD5(login.getLoginPassWord()));
        //更新login
        BeanUtils.copyNotNullProperties(login,adminEntity.getLogin());
        //更新权限
        admin.setRole(roleRepository.getOne(admin.getRoleId()));
        //保存admin
        adminRepository.save(adminEntity);
    }
    //删除管理员
    @Override
    public void delAdminByAdminId(String adminId) {
        adminRepository.deleteById(adminId);
    }
    //批量删除管理员
    @Override
    public void delAllAdminByAdminId(List<String> adminIds) {
        List<Admin> admins = adminRepository.findAllByAdminIdIn(adminIds);
        //删除所有
        adminRepository.deleteAll(admins);
    }
    //改变用户状态
    @Override
    @Transactional
    public void changeAdminIsProhibit(Boolean adminIsProhibit,String adminId)throws Exception{
        adminRepository.updateAdminIsProhibit(adminIsProhibit,adminId);
    }
    //修改密码
    @Override
    @Transactional
    public void updateLoginPassWord(String oldPassWord, String newPassWord, Admin admin)throws Exception{
        //检测旧密码是否正确
        if(!admin.getLogin().getLoginPassWord().equals(MD5Util.getStringMD5(oldPassWord))){
            throw new CustormException(CodeAndMsg.ADMIN_PASSWORDERROR);
        }
        admin.getLogin().setLoginPassWord(MD5Util.getStringMD5(newPassWord));
        adminRepository.save(admin);
    }
    //修改当前管理员信息
    @Override
    @Transactional
    public  void editCurrentAdminInfo(Admin adminInfo, Login login, Admin admin)throws Exception{
        BeanUtils.copyNotNullProperties(adminInfo,admin);
        BeanUtils.copyNotNullProperties(login,admin.getLogin());
        adminRepository.save(admin);
    }

    //管理员登录
    @Override
    @Transactional
    public Admin adminLogin(String loginUserName,String loginPassWord)throws Exception{
        Admin admin = adminRepository.findByLogin_LoginUserName(loginUserName);
        //查询用户名是否存在
        if(admin==null)
            throw new CustormException(CodeAndMsg.ADMIN_NOTFINDUSERNAME);
        //检测密码是否正确
        if(!admin.getLogin().getLoginPassWord().equals(MD5Util.getStringMD5(loginPassWord)))
            throw new CustormException(CodeAndMsg.ADMIN_PASSWORDERROR, ProjectConfig.URL_REDIRECT_LOGIN);
        //检测是否被禁用
        if(admin.getAdminIsProhibit())
            throw new CustormException(CodeAndMsg.ADMIN_BANNED, ProjectConfig.URL_REDIRECT_LOGIN);
        loginRepository.addLoginCount(admin.getLogin().getLoginId());
        admin.setCartUser(cartService.getCartUserByAdminId(admin.getAdminId()));
        return  admin;
    }

    @Override
    public List getAllSupplierName() {
        return adminRepository.selectAllSupplierName();
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

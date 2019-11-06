package com.noah_solutions.service.impl;

import com.noah_solutions.entity.DiscountCode;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.DiscountCodeRepository;
import com.noah_solutions.service.IDiscountCodeService;
import com.noah_solutions.util.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service("discountCodeService")
public class DiscountCodeServiceImpl implements IDiscountCodeService {
    @Resource
    private DiscountCodeRepository discountCodeRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有優惠碼
    @Override
    public List<DiscountCode> selectAllDiscountCode() {
        return discountCodeRepository.findAll();
    }

    //分页查询所有優惠碼（带条件）
    @Override
    public Page<DiscountCode> selectDiscountCodePageByCont(DiscountCode condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  discountCodeRepository.findAll(Example.of(condition,matcher),pageable);
    }

    //查询優惠碼(通过優惠碼id)
    @Override
    public DiscountCode selectDiscountCodeByDiscountCodeId(String discountCodeId) {
        return discountCodeRepository.findByDiscountCodeId(discountCodeId);
    }


    //查询優惠碼数量(带条件)
    @Override
    public Long selectCountDiscountCodeByCont(DiscountCode condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  discountCodeRepository.count(Example.of(condition,matcher));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添加優惠碼
    @Override
    @Transactional
    public void addDiscountCode(DiscountCode discountCode)throws Exception {

        //判断優惠碼邮箱是否重复
        if(discountCodeRepository.existsByDiscountCodeNo(discountCode.getDiscountCodeNo()))
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        //保存優惠碼信息
        discountCode = discountCodeRepository.saveAndFlush(discountCode);
    }
    //更新優惠碼
    @Override
    @Transactional
    public void updateDiscountCode(DiscountCode discountCode)throws Exception {
        //取出discountCode
        DiscountCode discountCodeEntity  = discountCodeRepository.getOne(discountCode.getDiscountCodeId());
        //更新discountCode
        BeanUtils.copyNotNullProperties(discountCode,discountCodeEntity);
        discountCodeRepository.save(discountCodeEntity);
    }
    //删除優惠碼
    @Override
    @Transactional
    public void delDiscountCodeByDiscountCodeId(String discountCodeId) {
        discountCodeRepository.deleteById(discountCodeId);
    }
    //批量删除優惠碼
    @Override
    @Transactional
    public void delAllDiscountCodeByDiscountCodeId(List<String> discountCodeIds) {
        List<DiscountCode> discountCodes = discountCodeRepository.findAllByDiscountCodeIdIn(discountCodeIds);
        discountCodeRepository.deleteAll(discountCodes);
    }

    //改变優惠碼状态
    @Override
    @Transactional
    public void changeDiscountCodeIsProhibit(Boolean discountCodeIsProhibit,String discountCodeId)throws Exception{
        discountCodeRepository.updateDiscountCodeIsProhibit(discountCodeIsProhibit,discountCodeId);
    }





    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////

    

    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

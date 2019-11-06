package com.noah_solutions.service;

import com.noah_solutions.entity.DiscountCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDiscountCodeService {

    //查询所有優惠碼
    List<DiscountCode> selectAllDiscountCode();

    Page<DiscountCode> selectDiscountCodePageByCont(DiscountCode condition, Pageable pageable);

    //通过id查询单个優惠碼
    DiscountCode selectDiscountCodeByDiscountCodeId(String discountCodeId);

    //根据条件获取总数
    Long selectCountDiscountCodeByCont(DiscountCode condition);

    //添加優惠碼
    void addDiscountCode(DiscountCode discountCode)throws Exception;
    //更新優惠碼
    void updateDiscountCode(DiscountCode discountCode)throws Exception;
    //改变優惠碼状态
    void changeDiscountCodeIsProhibit(Boolean discountCodeIsProhibit, String discountCodeId)throws Exception;

    //删除单个優惠碼
    void delDiscountCodeByDiscountCodeId(String discountCodeId);
    //批量删除優惠碼
    void delAllDiscountCodeByDiscountCodeId(List<String> discountCodeIds);

}

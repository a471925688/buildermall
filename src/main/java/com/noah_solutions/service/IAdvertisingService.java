package com.noah_solutions.service;

import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.TbImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAdvertisingService {

    Page<TbImage> selectImagePageByCont(TbImage condition, Pageable pageable);
    //通过id查询单个廣告圖
    TbImage selectImageByImageId(String imageId);

    List<TbImage> selectAllByImageGroupId(String imageGroupId);
    //分组查询所有廣告圖数量（通过权限id）
    List selecrAllCountGroupByImageGroupId();
    //根据条件获取总数
    Long selectCountImageByCont(TbImage condition);
    //添加廣告圖
    void addImage(TbImage image)throws Exception;
    //更新廣告圖
    void updateImage(TbImage image)throws Exception;
    //删除单个廣告圖
    void delImageByImageId(String imageId);
    //批量删除廣告圖
    void delAllImageByImageId(List<String> imageIds);

}

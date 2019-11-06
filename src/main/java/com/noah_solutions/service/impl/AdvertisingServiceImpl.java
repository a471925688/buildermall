package com.noah_solutions.service.impl;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.TbImage;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.AdvertisingTypeRepository;
import com.noah_solutions.repository.TbImageRepository;
import com.noah_solutions.service.IAdvertisingService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.FileUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

import static com.noah_solutions.common.ProjectConfig.ImageGroupType.ADVERTISING;

@Service("imageService")
public class AdvertisingServiceImpl implements IAdvertisingService {
    @Resource
    private TbImageRepository tbImageRepository;

    @Resource
    private AdvertisingTypeRepository advertisingTypeRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //分页查询所有廣告圖（带条件）
    @Override
    public Page<TbImage> selectImagePageByCont(TbImage condition, Pageable pageable) {
        condition.setImageGroupType(ADVERTISING.getValue());
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  tbImageRepository.findAll(Example.of(condition,matcher),pageable);
    }

    //查询廣告圖(通过廣告圖id)
    @Override
    public TbImage selectImageByImageId(String imageId) {
        return tbImageRepository.findByImageId(imageId);
    }

    @Override
    public List<TbImage> selectAllByImageGroupId(String imageGroupId) {
        return tbImageRepository.findAllByImageGroupIdAndImageGroupType(imageGroupId,ADVERTISING.getValue());
    }

    @Override
    public List selecrAllCountGroupByImageGroupId() {
        return tbImageRepository.findAllCountGroupByImageGroupId();
    }


    //查询廣告圖数量(带条件)
    @Override
    public Long selectCountImageByCont(TbImage condition) {
        condition.setImageGroupType(ADVERTISING.getValue());
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  tbImageRepository.count(Example.of(condition,matcher));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添加廣告圖
    @Override
    @Transactional
    public void addImage(TbImage image)throws Exception {
        image.setImageGroupType(ADVERTISING.getValue());
        Integer maxNum = advertisingTypeRepository.getMaxNumberById(image.getImageGroupId());
        if(maxNum<=tbImageRepository.countByImageGroupIdAndImageGroupType(image.getImageGroupId(),image.getImageGroupType())){
            throw new CustormException("添加失敗，超過最大數量",1);
        }
        copyImage(image);
        tbImageRepository.save(image);
    }
    //更新廣告圖
    @Override
    @Transactional
    public void updateImage(TbImage image)throws Exception {
        //取出image
        TbImage imageEntity  = tbImageRepository.getOne(image.getImageId());
        //更新image
        BeanUtils.copyNotNullProperties(image,imageEntity);
        //保存image
        copyImage(imageEntity);
        tbImageRepository.save(imageEntity);
    }
    //删除廣告圖
    @Override
    public void delImageByImageId(String imageId) {
        tbImageRepository.deleteById(imageId);
    }
    //批量删除廣告圖
    @Override
    public void delAllImageByImageId(List<String> imageIds) {
        List<TbImage> images = tbImageRepository.findAllByImageIdIn(imageIds);
        //删除所有
        tbImageRepository.deleteAll(images);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    private void copyImage(TbImage image)throws Exception{
        if(!new File(ProjectConfig.FILE_IMAGE_DIRECTORY+image.getImagePath()).exists()){//判斷是否新增
            FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+image.getImagePath(),ProjectConfig.FILE_IMAGE_DIRECTORY+image.getImagePath());//臨時文件夾複製到正式文件夾
            FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+"thumbnail-"+image.getImagePath(),ProjectConfig.FILE_IMAGE_DIRECTORY+"thumbnail-"+image.getImagePath());//臨時文件夾複製到正式文件夾
            image.setImageThumbnailsPath("thumbnail-"+image.getImagePath());
            new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+image.getImagePath()).delete();//刪除臨時文件
            new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+"thumbnail-"+image.getImagePath()).delete();//刪除臨時文件
        }
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}

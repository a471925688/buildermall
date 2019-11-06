package com.noah_solutions.repository;

import com.noah_solutions.entity.TbImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbImageRepository extends JpaRepository<TbImage,String> {
    TbImage findFirstByImageGroupTypeAndImageTypeAndImageGroupId(Integer imageGroupType,Integer imageType,String imageGroupId);
    TbImage findByImageId(String imageId);

    List<TbImage> findAllByImageIdIn(List<String> imageIds);

    List<TbImage> findAllByImageGroupIdAndImageGroupType(String imageGroupId,Integer imageGroupType);

    //分组查询所有数量
    @Query("select at.advertisingTypeName,count(i.imageId),at.advertisingTypeId from TbImage i join com.noah_solutions.entity.AdvertisingType at on at.advertisingTypeId = i.imageGroupId where i.imageGroupType=2 group by i.imageGroupId")
    List findAllCountGroupByImageGroupId();

    Long countByImageGroupIdAndImageGroupType(String imageGroupId,Integer imageGroupType);

}

package com.noah_solutions.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

//
//@Entity(name = "tb_image")
//@Table(appliesTo = "tb_image",comment = "图片表")
//@Data
@Entity
@Table(name ="tb_image",
        indexes = {@Index(columnList = "imageType"),@Index(columnList = "imageGroupId"),@Index(columnList = "imageGroupType")} )
@DynamicUpdate
public class TbImage  implements Serializable {
    private static final long serialVersionUID = 8737397365640763584L;
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10) AUTO_INCREMENT NOT NULL COMMENT '图片ID'")
    private String  imageId;
    @Column(columnDefinition = "VARCHAR(300)  COMMENT '图片名稱'")
    private String imageName;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '图片地址'")
    private String imagePath;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '圖片點擊的跳轉連接'")
    private String imageUrl;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '縮略圖地址'")
    private String imageThumbnailsPath;
    @Column(columnDefinition = "INT(1) COMMENT '图片类型（1.主图，2附图）'")
    private Integer  imageType;
    @Column(columnDefinition = "INT(10) COMMENT '所属组的id'")
    private String  imageGroupId;
    @Column(columnDefinition = "INT(2)  COMMENT '所属组的类型(1.品牌，2.產品)'",updatable = false)
    private Integer  imageGroupType;
    @Column(columnDefinition = "INT(10) COMMENT '排序'")
    private String  imageOrder;
    @Column(columnDefinition = "DATETIME    DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  imageCreatedate;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable = false)
    private String  imageUpdatedate;


    public TbImage() {
    }

    public TbImage(String imageName, String imagePath,String imageThumbnailsPath,  Integer imageType, String imageGroupId, Integer imageGroupType, String imageOrder) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imageThumbnailsPath = imageThumbnailsPath;
        this.imageType = imageType;
        this.imageGroupId = imageGroupId;
        this.imageGroupType = imageGroupType;
        this.imageOrder = imageOrder;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageThumbnailsPath() {
        return imageThumbnailsPath;
    }

    public void setImageThumbnailsPath(String imageThumbnailsPath) {
        this.imageThumbnailsPath = imageThumbnailsPath;
    }

    public String getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public String getImageGroupId() {
        return imageGroupId;
    }

    public void setImageGroupId(String imageGroupId) {
        this.imageGroupId = imageGroupId;
    }

    public Integer getImageGroupType() {
        return imageGroupType;
    }

    public void setImageGroupType(Integer imageGroupType) {
        this.imageGroupType = imageGroupType;
    }

    public String getImageCreatedate() {
        return imageCreatedate;
    }

    public void setImageCreatedate(String imageCreatedate) {
        this.imageCreatedate = imageCreatedate;
    }

    public String getImageUpdatedate() {
        return imageUpdatedate;
    }

    public void setImageUpdatedate(String imageUpdatedate) {
        this.imageUpdatedate = imageUpdatedate;
    }
}
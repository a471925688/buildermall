package com.noah_solutions.entity;

import javax.persistence.*;
import java.io.Serializable;

//
//@Entity(name = "tb_file")
//@Table(appliesTo = "tb_file",comment = "文件表")
//@Data
@Entity
@Table(name ="tb_file",
        indexes = {@Index(columnList = "fileType"),@Index(columnList = "fileGroupId"),@Index(columnList = "fileGroupType")} )
public class TbFile  implements Serializable {
    private static final long serialVersionUID = 8737397365640763584L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10) AUTO_INCREMENT NOT NULL COMMENT '文件ID'")
    private Integer  fileId;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '文件名稱'")
    private String fileName;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '文件地址'")
    private String filePath;
    @Column(columnDefinition = "INT(1) COMMENT '文件类型（....）'")
    private Integer  fileType;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '縮略圖地址'")
    private String fileThumbnailsPath;
    @Column(columnDefinition = "INT(10) COMMENT '所属组的id'")
    private String  fileGroupId;
    @Column(columnDefinition = "INT(10) COMMENT '排序'")
    private String  fileOrder;
    @Column(columnDefinition = "INT(2) NOT NULL COMMENT '所属组的类型(1.產品)'",updatable = false)
    private Integer  fileGroupType;
    @Column(columnDefinition = "DATETIME    DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  fileCreatedate;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable = false)
    private String  fileUpdatedate;


    public TbFile() {
    }

    public TbFile(String fileName, String filePath,String fileThumbnailsPath, Integer fileType, String fileGroupId,Integer fileGroupType ,String fileOrder) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileThumbnailsPath = fileThumbnailsPath;
        this.fileGroupId = fileGroupId;
        this.fileOrder = fileOrder;
        this.fileGroupType = fileGroupType;
    }

    public String getFileThumbnailsPath() {
        return fileThumbnailsPath;
    }

    public void setFileThumbnailsPath(String fileThumbnailsPath) {
        this.fileThumbnailsPath = fileThumbnailsPath;
    }

    public String getFileOrder() {
        return fileOrder;
    }

    public void setFileOrder(String fileOrder) {
        this.fileOrder = fileOrder;
    }

    public Integer getFileGroupType() {
        return fileGroupType;
    }

    public void setFileGroupType(Integer fileGroupType) {
        this.fileGroupType = fileGroupType;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileGroupId() {
        return fileGroupId;
    }

    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }

    public String getFileCreatedate() {
        return fileCreatedate;
    }

    public void setFileCreatedate(String fileCreatedate) {
        this.fileCreatedate = fileCreatedate;
    }

    public String getFileUpdatedate() {
        return fileUpdatedate;
    }

    public void setFileUpdatedate(String fileUpdatedate) {
        this.fileUpdatedate = fileUpdatedate;
    }
}
package com.noah_solutions.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.ProductTable;
import com.noah_solutions.bean.ProductUnit;
import com.noah_solutions.common.IdManage;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.common.TablePage;
import com.noah_solutions.entity.*;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.*;
import com.noah_solutions.service.IProductService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.FileUtils;
import com.noah_solutions.util.PictureUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.File;
import java.lang.System;
import java.util.*;

@Service("productService")
public class ProductServiceImpl implements IProductService {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private ProductRepository productRepository;


    @Resource
    private ProductSizeRepository productSizeRepository;

    @Resource
    private ProductSpecificationsRepository productSpecificationsRepository;

    @Resource
    private ProductConfigRepository productConfigRepository;

    @Resource
    private ProductTypeCache productTypeCache;

    @Resource
    private TbImageRepository imageRepository;

    @Resource
    private BrandRepository brandRepository;

    @Resource
    private TemProductRepository temProductRepository;

    @Resource
    private OrderProductRepository orderProductRepository;
    @Resource
    private ProductEvaluationRepository productEvaluationRepository;
    @Resource
    private ProductBrowseRecordsRepository productBrowseRecordsRepository;
    @Resource
    private IdManage idManage;
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有管理员基本信息
    @Override
    public List<Product> selectAllProduct() {
        return productRepository.findAll();
    }

    //分页查询所有商品基本信息（带条件）
    @Override
    public Page<Product> selectProductPageByCont(Product condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Page<Product> productInfos = productRepository.findAll(Example.of(condition,matcher),pageable);
        return  productInfos;
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.getOne(id);
    }


    //    通過產品類型id分頁查詢所有產品
    @Override
    public TablePage<ProductTable> selectProductPageByProductTypeId(Product product, PageRequest queryquery) {
        return selectProductPageByProduct(product,null,queryquery,null,null);
    }

    //    通過產品類型id分頁查詢所有產品
    @Override
    public Long   selectCountProductByCont(Product product){
        ExampleMatcher matcher = ExampleMatcher.matching();
        return   productRepository.count(Example.of(product,matcher));
    }
    @Override
    public Long   selectTotalSold(){
        return   productRepository.selectTotalSold();
    }

    @Override
    public List<ProductUnit> selectAllProductUnit() {
        return productRepository.findAllGroupByUnit();
    }

    @Override
    public TablePage<ProductTable> selectProductPageByProductCont(Product product, PageRequest queryquery) {
        return selectProductPageByProduct(product,null,queryquery,null,null);
    }


//    @Override
//    public List<ProductTableProper> productText() {
//        Criteria<Event> c = new Criteria<Event>();
//        c.add(Restrictions.like("productTypeParentsId.", "", true));
//        List<ProductTableProper> propers = productRepository.findAll(c);
//        return propers;
//    }

    @Override
    public Product selectProductById(String productId,String userId) {
        if(!StringUtils.isEmpty(userId))
            productBrowseRecordsRepository.save(new ProductBrowseRecords(productId,userId));
        return productRepository.getOne(productId);
    }










    //******************************************華麗的分割線**********************************************
                ///////////////////////web端 start//////////////////////////////////////

    @Override
    public TablePage<ProductTable> selectProductPageByContent(String adminId,String productTypeId,String brandId, String searchVal, PageRequest pageable,Integer order){
        Product product = new Product();
        product.setProductTypeId(productTypeId);
        product.setBrandId(brandId);
        product.setAdminId(adminId);
        return selectProductPageByProduct(product,searchVal,pageable,order,true);
    }


    @Override
    public Page<ProductEvaluation> selectProductEvaluationPageByProductId(String productId, Pageable of) {
        Page<ProductEvaluation> productEvaluations = productEvaluationRepository.findAllInfoByProductIdAndIsReview(productId,false,of);
        productEvaluations.getContent().forEach(v->v.setProductEvaluation(productEvaluationRepository.findAllInfoByUserIdAndOrderProductIdAndIsReview(v.getUserId(),v.getOrderProductId(),true)));
        return productEvaluations;
    }

    @Override
    public Page<ProductTable> getProductBrowse(String loginUserId,Integer page,Integer limit) {
        Page<ProductTable> productTables=null;
        if(!StringUtils.isEmpty(loginUserId)){
            productTables =  productRepository.findProductBrowse(loginUserId,PageRequest.of(page-1,limit));
        }
        if(productTables==null){
            productTables = productRepository.selectProductOrderBySelloutNum(PageRequest.of(page-1,limit));
        }else {
            if(productTables.getSize()<10){
                productTables.getContent().addAll(productRepository.selectProductOrderBySelloutNum(PageRequest.of(page,10-productTables.getSize())).getContent());
            }
        }

        productTables.forEach(v->{
            TbImage image = imageRepository.findFirstByImageGroupTypeAndImageTypeAndImageGroupId(ProjectConfig.ImageGroupType.PRODUCT.getValue(),ProjectConfig.ImageType.COVER.getValue(),v.getProductId());
            if(image!=null){
                v.setProductPhonePath(image.getImageThumbnailsPath());
            }
        });
        return productTables;
    }

    @Override
    public Product getCopyProduct(String productId) {
        Map ids = new HashMap();
        Product product = new Product();
        HashMap<String,String> sizeIds = new HashMap();
        HashMap<String,String>  specificationsIds = new HashMap();
        Product old = productRepository.findByProductId(productId);
        BeanUtils.copyNotNullProperties(old,product);
        product.setProductId(idManage.getProductNextId());
        product.getProductSizes().forEach(v->{
            String id = idManage.getProductSizeNextId();
            sizeIds.put(v.getProductSizeId(),id);
            v.setProductSizeId(id);
            v.setProductId(product.getProductId());
        });
        product.getProductSpecificationss().forEach(v->{
            String id = idManage.getProductSpecificationsNextId();
            specificationsIds.put(v.getProductSpecificationsId(),id);
            v.setProductSpecificationsId(id);
            v.setProductId(product.getProductId());
        });
        product.getProductConfigs().forEach(v->{
            v.setProductConfigId(null);
            v.setProductSizeId(sizeIds.get(v.getProductSizeId()));
            v.setProductId(product.getProductId());
            v.setProductConfigUrl(null);
        });
        product.setProductContentsFile(new HashSet<>());
        product.setProductSpecificationsFile(new HashSet<>());
        product.setProductVideo(new HashSet<>());
        product.setCoverImgs(new HashSet<>());
        product.setDetailsImgNames(new HashSet<>());
        return product;
    }

    ///////////////////////web端 end//////////////////////////////////////

    //******************************************華麗的分割線**********************************************














    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    @Override
    @Transactional
    public void addProduct(Product product) throws Exception{
        saveImage(product.getDetailsImgNames());//圖片處理
        saveFile(product.getProductVideo(),ProjectConfig.FileType.VIDEO.getValue());
        saveFile(product.getProductContentsFile(),ProjectConfig.FileType.CONTENTS.getValue());
        saveFile(product.getProductSpecificationsFile(),ProjectConfig.FileType.SPECIFICATIONS.getValue());
        productSpecificationsRepository.saveAll(product.getProductSpecificationss());
        productSizeRepository.saveAll(product.getProductSizes());
        productConfigRepository.saveAll(product.getProductConfigs());
        product.setAdminId(brandRepository.getOne(product.getBrandId()).getAdminId());
        productRepository.save(product);
    }



    @Override
    @Transactional
    public void changeProductState(Boolean productState, String productId) {
        productRepository.updateProductState(productState,productId);
    }

    @Override
    @Transactional
    public void updateProduct(Product product)throws Exception {
        saveImage(product.getDetailsImgNames());
        saveFile(product.getProductVideo(),ProjectConfig.FileType.VIDEO.getValue());
        saveFile(product.getProductContentsFile(),ProjectConfig.FileType.CONTENTS.getValue());
        saveFile(product.getProductSpecificationsFile(),ProjectConfig.FileType.SPECIFICATIONS.getValue());
        productSizeRepository.saveAll(product.getProductSizes());
        productSpecificationsRepository.saveAll(product.getProductSpecificationss());
        productConfigRepository.saveAll(product.getProductConfigs());
        Product productEntity = productRepository.getOne(product.getProductId());
        if(productEntity!=null){
            BeanUtils.copyNotNullProperties(product,productEntity);
        }else {
            productEntity = product;
        }
        product.setAdminId(brandRepository.getOne(product.getBrandId()).getAdminId());
        productRepository.save(productEntity);
    }

    //刪除Size
    @Override
    public void delProductSizeByProductSizeId(String productSizeId) {
        if(productSizeRepository.existsByProductSizeId(productSizeId))
            productSizeRepository.deleteById(productSizeId);
    }

    //刪除Specifications
    @Override
    public void delProductSpecificationsByProductSpecificationsId(String productSpecificationsId) {
        if(productSpecificationsRepository.existsByProductSpecificationsId(productSpecificationsId))
            productSpecificationsRepository.deleteById(productSpecificationsId);
    }

    //刪除Config
    @Override
    public void delProductConfigByProductConfigId(String productConfigId) {
        if(productConfigRepository.existsByProductConfigId(productConfigId))
            productConfigRepository.deleteById(productConfigId);
    }
    //批量刪除Config
    @Override
    @Transactional
    public void delAllProductConfigByProductConfigId(List productConfigIds) {
        productConfigRepository.deleteAllByProductConfigIdIn(productConfigIds);
    }

    @Override
    public void delProductByProductId(String productId) {
            productRepository.deleteById(productId);
    }

    @Override
    @Transactional
    public void delAllProductByProductId(List<String> productIds) {
        productRepository.deleteAllByProductIdIn(productIds);
    }


    //審核通過
    @Override
    @Transactional
    public void auditPass(List<String> temProductIds) {
        temProductIds.forEach(v-> {
            try {
                updateProduct(JSONObject.parseObject(temProductRepository.findByTemProductId(v).getTemProductJson(), Product.class));
                temProductRepository.updateTemProductState(ProjectConfig.TemProductState.PASS.getValue(),v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    @Transactional
    public void auditReject(List<String> temProductIds) {
        temProductIds.forEach(v-> {
            try {
                temProductRepository.updateTemProductState(ProjectConfig.TemProductState.AUDITFAILURE.getValue(),v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    //保存产品评价
    @Override
    @Transactional
    public void productEvaluation(ProductEvaluation productEvaluation)throws Exception {
        Set<TbImage> tbImages = productEvaluation.getDetailsImgNames();
        ProductEvaluation productEvaluationinfo = orderProductRepository.getProductEvaluationInfo(productEvaluation.getOrderProductId());
        if(productEvaluationRepository.existsByOrderProductIdAndUserId(productEvaluation.getOrderProductId(),productEvaluationinfo.getUserId())){
            productEvaluation.setIsReview(true);
        }else {
            productEvaluation.setIsReview(false);
        }
        BeanUtils.copyNotNullProperties(productEvaluationinfo,productEvaluation);
        productEvaluation = productEvaluationRepository.save(productEvaluation);
        saveProductEvaluationImage(tbImages,productEvaluation.getProductEvaluationId());
        imageRepository.saveAll(tbImages);
        orderProductRepository.updateOrderProductEvaluationState(productEvaluation.getOrderProductId());
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //保存產品圖片
    private void saveImage(Set<TbImage> images)throws Exception{
        for (TbImage image :images) {
            if(StringUtils.isEmpty(image.getImageId())){//判斷是否新增
//                String fileName = image.getImagePath().substring(image.getImagePath().indexOf("fileName=")+"fileName=".length());//截出文件名
                FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+image.getImagePath(),ProjectConfig.FILE_IMAGE_DIRECTORY+image.getImagePath());//臨時文件夾複製到正式文件夾
                PictureUtil.reduceImg(ProjectConfig.FILE_IMAGE_DIRECTORY+image.getImagePath(),ProjectConfig.FILE_IMAGE_DIRECTORY +"thumbnail-"+image.getImagePath(),179,160);
//                FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+"thumbnail-"+image.getImagePath(),ProjectConfig.FILE_IMAGE_DIRECTORY+"thumbnail-"+image.getImagePath());//臨時文件夾複製到正式文件夾
                image.setImageThumbnailsPath("thumbnail-"+image.getImagePath());
                new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+image.getImagePath()).delete();//刪除臨時文件
//                new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+"thumbnail-"+image.getImagePath()).delete();//刪除臨時文件
            }
        }
    }

    private void saveFile(Set<TbFile> files,Integer type)throws Exception{

        String path = "";
        switch (type){

            case 2:{
                path = ProjectConfig.FILE_VIDEO_DIRECTORY;
                break;
            }
            case 3:{
                path = ProjectConfig.FILE_CONTENTS_DIRECTORY;
                break;
            }
            case 4:{
                path = ProjectConfig.FILE_SPECIFICATIONS_DIRECTORY;
                break;
            }
            default:{
                throw  new CustormException(CodeAndMsg.ERROR);
            }
        }
        for (TbFile file :files) {
            if(StringUtils.isEmpty(file.getFileId())){//判斷是否新增
//                String fileName = file.getFilePath().substring(file.getFilePath().indexOf("fileName=")+"fileName=".length());//截出文件名
                FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+file.getFilePath(),path+file.getFilePath());//臨時文件夾複製到正式文件夾
//                file.setFilePath(file.getFilePath().replace("textFileDown","fileDown"));//改變訪問路徑為正式路徑
                if(type==2){
                    FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+"thumbnail-"+file.getFilePath()+".png",path+"thumbnail-"+file.getFilePath()+".png");//臨時文件夾複製到正式文件夾
                    file.setFileThumbnailsPath("thumbnail-"+file.getFilePath()+".png");
                }
                new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+file.getFilePath()).delete();//刪除臨時文件
            }
        }
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////



    private TablePage<ProductTable> selectProductPageByProduct(Product product,String searchVal, PageRequest queryquery,Integer order,Boolean productState) {
        String productTypeId = product.getProductTypeId();
//        ProductType productType = productTypeCache.getProductTypesById(product.getProductTypeId());
//        if(productType==null){
//            if(!StringUtils.isEmpty(product.getProductTypeId()))
//                throw new CustormException(CodeAndMsg.ERROR);
//            parentsId = "";
//        }else {
//            parentsId =(StringUtils.isEmpty(productType.getProductTypeParentsId())?"":productType.getProductTypeParentsId())+productType.getProductTypeId()+"|";
//        }



        String sql = "SELECT NEW com.noah_solutions.bean.ProductTable(p.productId,p.productTitle,p.productTitleEng,p.productMaterial, " +
                "             p.productMaterialEng, p.productFeatures, p.productFeaturesEng,p.productPriceStart,p.productPriceEnd," +
                "             p.productDisPriceStart, p.productDisPriceEnd, p.productDescribe, p.productDescribeEng, p.productState, " +
                "             p.productSelloutNum, p.productCreateTime, p.productIsDiscount,p.productIsFreight,p.productUnit, p.productUnitEng,p.admin, p.brand,p.productTypeId) " +
                "            FROM Product p";
        String sqlCount = "SELECT COUNT(p) from Product p ";
        String sqlWhere="";
        String sqlOrder = " order by p.productId desc";
        if(!StringUtils.isEmpty(productState)){
            sqlWhere+= " WHERE p.productState=:productState";
        }
        if(!StringUtils.isEmpty(productTypeId)){
            if(!StringUtils.isEmpty(sqlWhere)){
                sqlWhere+= " And ";
            }else {
                sqlWhere+= " WHERE ";
            }
            sqlWhere+= " p.productTypeId  LIKE CONCAT('%',:productTypeIds,'%')";
        }
        if(!StringUtils.isEmpty(searchVal)||!StringUtils.isEmpty(product.getProductTitle())){
            if(StringUtils.isEmpty(searchVal))
                searchVal=product.getProductTitle();
            if(!StringUtils.isEmpty(sqlWhere)){
                sqlWhere+= " And ";
            }else {
                sqlWhere+= " WHERE ";
            }
            sqlWhere+= " (p.productTitle LIKE  CONCAT('%',:searchVal,'%') or p.productTitleEng  LIKE CONCAT('%',:searchVal,'%')) ";
        }
        if(!StringUtils.isEmpty(product.getBrandId())){
            if(!StringUtils.isEmpty(sqlWhere)){
                sqlWhere+= " And";
            }else {
                sqlWhere+= " WHERE ";
            }
            sqlWhere+= "  p.brandId = :brandId ";
        }

        if(!StringUtils.isEmpty(product.getAdminId())){
            if(!StringUtils.isEmpty(sqlWhere)){
                sqlWhere+= " And";
            }else {
                sqlWhere+= " WHERE ";
            }
            sqlWhere+= "   p.adminId = :adminId ";
        }


        if(!StringUtils.isEmpty(product.getProductCreateTime())){
            if(StringUtils.isEmpty(searchVal))
                searchVal=product.getProductTitle();
            if(!StringUtils.isEmpty(sqlWhere)){
                sqlWhere+= " And ";
            }else {
                sqlWhere+= " WHERE ";
            }
            sqlWhere+= "  p.productCreateTime LIKE  CONCAT(:productCreateTime,'%') ";
        }

        if(order!=null){
            String orderName = "";
            switch (order){
                case 2:sqlOrder = " order by p.productSelloutNum desc";break;
                case 3:sqlOrder = " order by p.productId desc";break;
                case 4:sqlOrder = " order by p.productDisPriceStart asc";break;
                case 5:sqlOrder = " order by p.productDisPriceStart desc";break;
            }

        }

        TypedQuery<ProductTable> query = entityManager.createQuery(sql+sqlWhere+sqlOrder, ProductTable.class);
        TypedQuery<Long> queryCount = entityManager.createQuery(sqlCount+sqlWhere, Long.class);//查询数量

        if(!StringUtils.isEmpty(productState)) {
            query.setParameter("productState", productState);
            queryCount.setParameter("productState", productState);
        }
        if(!StringUtils.isEmpty(productTypeId)){
            query.setParameter("productTypeIds",productTypeId);
            queryCount.setParameter("productTypeIds",productTypeId);
        }

        if(!StringUtils.isEmpty(searchVal)){
            query.setParameter("searchVal",searchVal);
            queryCount.setParameter("searchVal",searchVal);
        }
        if(!StringUtils.isEmpty(product.getBrandId())){
            query.setParameter("brandId",product.getBrandId());
            queryCount.setParameter("brandId",product.getBrandId());
        }
        if(!StringUtils.isEmpty(product.getProductCreateTime())){
            query.setParameter("productCreateTime",product.getProductCreateTime());
            queryCount.setParameter("productCreateTime",product.getProductCreateTime());
        }
        if(!StringUtils.isEmpty(product.getAdminId())){
            query.setParameter("adminId",product.getAdminId());
            queryCount.setParameter("adminId",product.getAdminId());
        }

        int page = (int) queryquery.getOffset();
        int limit = queryquery.getPageSize();
        List<ProductTable> result = query.setFirstResult(page).setMaxResults(limit).getResultList();


        System.out.println();
        result.forEach(v->{
            TbImage image = imageRepository.findFirstByImageGroupTypeAndImageTypeAndImageGroupId(ProjectConfig.ImageGroupType.PRODUCT.getValue(),ProjectConfig.ImageType.COVER.getValue(),v.getProductId());
            if(image!=null){
                v.setProductPhonePath(image.getImageThumbnailsPath());
            }
        });
        return new TablePage<>(queryCount.getSingleResult(),queryquery.getPageNumber(),queryquery.getPageSize(),result);
    }

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

    @Override
    @Transactional
    public void initProductType() {
        List<Object[]>  products = productRepository.getProductTypeId();
        for (Object[] v:products) {
            String productTypeId = (String) v[1];

            if(productTypeId.indexOf("|")==-1){
                ProductType productType = ProductTypeCache.getProductTypesById(productTypeId);
                String parentId = productType.getProductTypeParentId();
                productTypeId = productTypeId+"|";
                while (!parentId.equals("0")){
                    ProductType productType1 = ProductTypeCache.getProductTypesById(parentId);
                    parentId = productType1.getProductTypeParentId();
                    productTypeId = productType1.getProductTypeId()+"|"+productTypeId;
                }
                productRepository.setProductTypeId(productTypeId, (String) v[0]);
            }

        }
    }




    //保存產品评价圖片
    private void saveProductEvaluationImage(Set<TbImage> images,String productEvaluationId)throws Exception{
        for (TbImage image :images) {
            if(StringUtils.isEmpty(image.getImageId())){//判斷是否新增
                FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+image.getImagePath(),ProjectConfig.FILE_EVALUATION_IMAGE_DIRECTORY+image.getImagePath());//臨時文件夾複製到正式文件夾
                PictureUtil.reduceImg(ProjectConfig.FILE_EVALUATION_IMAGE_DIRECTORY+image.getImagePath(),ProjectConfig.FILE_TEMPORARY_DIRECTORY +"thumbnail-"+image.getImagePath(),179,160);
                image.setImageThumbnailsPath("thumbnail-"+image.getImagePath());
                image.setImageGroupType(ProjectConfig.ImageGroupType.PRODUCTEVALUATION.getValue());
                image.setImageGroupId(productEvaluationId);
                new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+image.getImagePath()).delete();//刪除臨時文件
            }
        }
    }

}

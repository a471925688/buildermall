//package com.noah_solutions.service.impl;
//
//import com.noah_solutions.common.IdManage;
//import com.noah_solutions.common.ProjectConfig;
//import com.noah_solutions.entity.*;
//import com.noah_solutions.entity.oldEntity.*;
//import com.noah_solutions.repository.AdminRepository;
//import com.noah_solutions.repository.LoginRepository;
//import com.noah_solutions.repository.ProductTypeRepository;
//import com.noah_solutions.repository.RoleRepository;
//import com.noah_solutions.repository.old.SupplierUserRepository;
//import com.noah_solutions.service.IDataDockingService;
//import com.noah_solutions.service.IProductService;
//import com.noah_solutions.util.CollectionUtil;
//import com.noah_solutions.util.FileUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.io.FileInputStream;
//import java.io.ObjectInputStream;
//import java.util.*;
//
//import static com.noah_solutions.common.ProjectConfig.FileType.*;
//
///**
// * 2019 2 18 lijun
// * buildermall數據對接
// * 對接前請先清空表
// */
//@Service
//public class DataDockingServiceImpl implements IDataDockingService {
//
//    @Resource
//    private SupplierUserRepository supplierUserRepository;
//
//    @Resource
//    private ProductTypeRepository productTypeRepository;
//    @Resource
//    private LoginRepository loginRepository;
//    @Resource
//    private AdminRepository adminRepository;
//    @Resource
//    private IProductService productService;
//    @Resource
//    private RoleRepository roleRepository;
//    @Resource
//    private IdManage idManage;
//
//    @Override
//    public void dockingSuppliers() throws Exception{
//        List<Admin> admins = new ArrayList<>();//所有供应商
//        List<SupplierUserEntity> supplierUserEntities = supplierUserRepository.findAll();
//        for (SupplierUserEntity s :supplierUserEntities) {
////            admins.add(new Admin(s.getId()+"",roleRepository.findByRoleName("供应商管理员"),loginRepository.saveAndFlush(new Login(s.getEmail(),s.getPwd(),s.getId()+"")),2,s.getEmail(),s.getName_tc(),"保密",null,null,null));
//            for (ItemsEntity item:s.getItemsEntities()
//                 ) {
//                Set<ProductSize> productSizeSet = new HashSet<>();//尺寸信息
//                Set<ProductSpecifications> productSpecifications = new HashSet<>();//其他选择信息
//                Set<ProductConfig> productConfigs = new HashSet<>();//配置信息
//                Set<TbImage> detailsImgNames = new HashSet<>();//图片信息
//                Set<TbFile> productVideo = new HashSet<>();//影片信息
//                Set<TbFile> productContentsFile = new HashSet<>();//目录信息
//                Set<TbFile> productSpecificationsFile = new HashSet<>();//规格信息
//                Map<Integer,String> newSpecification1Ids = new HashMap();//保持新的规格id
//                Map<Integer,String> newSpecification2Ids = new HashMap();//保持新的规格id
//                Map<Integer,String> newSpecification3Ids = new HashMap();//保持新的规格id
//                Map<Integer,String> newColorIds = new HashMap();//保持新的规格id(颜色)
//                Double productPriceStart = Double.MAX_VALUE;
//                Double productPriceEnd = 0.0;
//                if(item.getId()==20){
//                    System.out.println("...");
//                }
//
//                Product product = new Product();
//
//
//                //取出老版的产品尺寸信息
//                for (ItemSizeEntity itemsize:item.getSizeSet()) {
//                    productSizeSet.add(new ProductSize(itemsize.getId()+"",itemsize.getName_tc(),itemsize.getName_eng(),100.00,item.getId()+""));
//                }
//                //取出选择1
//                for (ItemSpec1Entity itemSpec1Entity:item.getSpec1Set()) {
//                    newSpecification1Ids.put(itemSpec1Entity.getId(),idManage.getProductSpecificationsNextId());
//                    productSpecifications.add(new ProductSpecifications(newSpecification1Ids.get(itemSpec1Entity.getId()),itemSpec1Entity.getName_tc(),itemSpec1Entity.getName_eng(),"选择 1","Selection 1",itemSpec1Entity.getItems_id()));
//                }
//                //取出选择2
//                for (ItemSpec2Entity itemSpec2Entity:item.getSpec2Set()) {
//                    newSpecification2Ids.put(itemSpec2Entity.getId(),idManage.getProductSpecificationsNextId());
//                    productSpecifications.add(new ProductSpecifications(newSpecification2Ids.get(itemSpec2Entity.getId()),itemSpec2Entity.getName_tc(),itemSpec2Entity.getName_eng(),"选择 2","Selection 2",itemSpec2Entity.getItems_id()));
//                }
//                //取出选择3
//                for (ItemSpec3Entity itemSpec3Entity:item.getSpec3Set()) {
//                    newSpecification3Ids.put(itemSpec3Entity.getId(),idManage.getProductSpecificationsNextId());
//                    productSpecifications.add(new ProductSpecifications(newSpecification3Ids.get(itemSpec3Entity.getId()),itemSpec3Entity.getName_tc(),itemSpec3Entity.getName_eng(),"选择 3","Selection 3",itemSpec3Entity.getItems_id()));
//                }
//                //取出颜色
//                for (ItemColorEntity itemColorEntity:item.getColorSet()) {
//                    newColorIds.put(itemColorEntity.getId(),idManage.getProductSpecificationsNextId());
//                    productSpecifications.add(new ProductSpecifications(newColorIds.get(itemColorEntity.getId()),itemColorEntity.getName_tc(),itemColorEntity.getName_eng(),"颜色","Color",itemColorEntity.getItems_id()));
//                }
//                //取出老版的图片信息
//                for (int i = 0;i<item.getPhotosSet().size();i++){
//                    ItemPhotosEntity itemPhotosEntity = (ItemPhotosEntity) CollectionUtil.setTurnList(item.getPhotosSet()).get(i);
//                    Integer imageType = 1;
//                    if(i>0){
//                        imageType = 2;
//                    }
//                    FileUtils.copyFile("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/itemphotos/"+itemPhotosEntity.getPath(),ProjectConfig.FILE_IMAGE_DIRECTORY+itemPhotosEntity.getPath());
//                    FileUtils.copyFile("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/itemphotos/"+itemPhotosEntity.getThumbnails(),ProjectConfig.FILE_IMAGE_DIRECTORY+itemPhotosEntity.getThumbnails());
//                    detailsImgNames.add(new TbImage(itemPhotosEntity.getName(),itemPhotosEntity.getPath(),itemPhotosEntity.getThumbnails(),imageType,item.getId()+"", ProjectConfig.FileType.IMAGE.getValue(),itemPhotosEntity.getSort_order()+""));
//                }
//                //取出老版的影片信息
//                for (int i = 0;i<item.getVideoSet().size();i++){
//                    ItemVideoEntity itemVideoEntity = (ItemVideoEntity) CollectionUtil.setTurnList(item.getVideoSet()).get(i);
//                    FileUtils.copyFile("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/itemphotos/"+itemVideoEntity.getThumbnails(),ProjectConfig.FILE_VIDEO_DIRECTORY+itemVideoEntity.getThumbnails());
//                    FileUtils.copyFile("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/itemvideo/"+itemVideoEntity.getPath(),ProjectConfig.FILE_VIDEO_DIRECTORY+itemVideoEntity.getPath());
//                    productVideo.add(new TbFile(itemVideoEntity.getName(),itemVideoEntity.getPath(),itemVideoEntity.getThumbnails(),VIDEO.getValue(),item.getId()+"",1 ,(i+1)+""));
//                }
//                //取出老版的目录和规格信息
//                for (int i = 0;i<item.getFileSet().size();i++){
//                    ItemFilesEntity itemFilesEntity = (ItemFilesEntity) CollectionUtil.setTurnList(item.getFileSet()).get(i);
//
//                    Integer imageType = 1;
//                    if(itemFilesEntity.getType_id()==2){
//                        FileUtils.copyFile("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/itemfiles/"+itemFilesEntity.getPath(),ProjectConfig.FILE_CONTENTS_DIRECTORY+itemFilesEntity.getPath());
//                        productContentsFile.add(new TbFile(itemFilesEntity.getName(),itemFilesEntity.getPath(),null,CONTENTS.getValue(),item.getId()+"", 1,(productVideo.size()+1)+""));
//                    }else {
//                        FileUtils.copyFile("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/itemfiles/"+itemFilesEntity.getPath(),ProjectConfig.FILE_SPECIFICATIONS_DIRECTORY+itemFilesEntity.getPath());
//                        productSpecificationsFile.add(new TbFile(itemFilesEntity.getName(),itemFilesEntity.getPath(),null,SPECIFICATIONS.getValue(),item.getId()+"", 1,(productVideo.size()+1)+""));
//                    }
//                }
//                //取出配置信息
//                for (ItemRelationsEntity itemRelationsEntity:item.getRelationsSet()) {
//                    productPriceStart = productPriceStart>itemRelationsEntity.getPrice().doubleValue()?itemRelationsEntity.getPrice().doubleValue():productPriceStart;
//                    productPriceEnd = productPriceEnd<itemRelationsEntity.getPrice().doubleValue()?itemRelationsEntity.getPrice().doubleValue():productPriceEnd;
//
//                    Set<ProductSpecifications> productSpecificationsSet = new HashSet<>();
//                    ItemColorEntity itemColorEntity = itemRelationsEntity.getItemColorEntity();
//                    ItemSpec1Entity itemSpec1Entity = itemRelationsEntity.getItemSpec1Entity();
//                    ItemSpec2Entity itemSpec2Entity = itemRelationsEntity.getItemSpec2Entity();
//                    ItemSpec3Entity itemSpec3Entity = itemRelationsEntity.getItemSpec3Entity();
//                    if(itemColorEntity!=null){
//                        productSpecificationsSet.add(new ProductSpecifications(newColorIds.get(itemColorEntity.getId()),itemColorEntity.getName_tc(),itemColorEntity.getName_eng(),"顏色","color",itemColorEntity.getItems_id()));
//                    }
//                    if(itemSpec1Entity!=null)
//                        productSpecificationsSet.add(new ProductSpecifications(newSpecification1Ids.get(itemSpec1Entity.getId()),itemSpec1Entity.getName_tc(),itemSpec1Entity.getName_eng(),"选择 1","Selection 1",itemSpec1Entity.getItems_id()));
//                    if(itemSpec2Entity!=null)
//                        productSpecificationsSet.add(new ProductSpecifications(newSpecification2Ids.get(itemSpec2Entity.getId()),itemSpec2Entity.getName_tc(),itemSpec2Entity.getName_eng(),"选择 2","Selection 2",itemSpec2Entity.getItems_id()));
//                    if(itemSpec3Entity!=null)
//                        productSpecificationsSet.add(new ProductSpecifications(newSpecification3Ids.get(itemSpec3Entity.getId()),itemSpec3Entity.getName_tc(),itemSpec3Entity.getName_eng(),"选择 3","Selection 3",itemSpec3Entity.getItems_id()));
//                    String sizeId = itemRelationsEntity.getItemsSizeId()>0? itemRelationsEntity.getItemsSizeId()+"":null;
//                    productConfigs.add(new ProductConfig(itemRelationsEntity.getPrice().doubleValue(),itemRelationsEntity.getPrice().doubleValue(),itemRelationsEntity.getStock()+"",itemRelationsEntity.getItem().getId()+"",sizeId,productSpecificationsSet));
//                }
//                //拼接类型
//                String productTypeDescribe = "";
//                if(!item.getCattype1().getName_tc().equals("-")){
//                    productTypeDescribe+=item.getCattype1().getName_tc();
//                }
//                if(!item.getCattype2().getName_tc().equals("-")){
//                    productTypeDescribe=productTypeDescribe+"->"+item.getCattype2().getName_tc();
//                }
//                if(!item.getCattype3().getName_tc().equals("-")){
//                    productTypeDescribe=productTypeDescribe+"->"+item.getCattype3().getName_tc();
//                }
//                //查找并设置类型
//                product.setProductTypeId(productTypeRepository.findByProductTypeDescribeEndingWith(productTypeDescribe).getProductTypeId());
//                product.setBrandId(item.getBrands_id()+"");//设置品牌
//                product.setProductDeliveryArea("1/10086328343885/80002");//设置发货地址
//                product.setProductUnit(item.getUnit_tc());//设置产品单位
//                product.setProductTitle(item.getName_tc());//设置中文名称
//                product.setProductTitleEng(item.getName_eng());//设置英文名称
//                product.setProductMaterial(item.getRaw_material_tc());//设置材质
//                product.setProductMaterialEng(item.getRaw_material_eng());//设置材质
//                product.setProductFeatures(item.getFeatures_tc());//设置特点
//                product.setProductFeaturesEng(item.getFeatures_eng());//设置特点
//                product.setProductPriceStart(productPriceStart);//设置最低价格
//                product.setProductPriceEnd(productPriceEnd);//设置最高价格
//                product.setProductDisPriceStart(productPriceStart);//最低折扣价
//                product.setProductDisPriceEnd(productPriceEnd);//最高折扣价
//                product.setProductDescribe(item.getShort_desc_tc());//描述
//                product.setProductDescribeEng(item.getShort_desc_eng());//描述
//                product.setAdminId(item.getS_id()+"");//设置供应商
//                product.setProductSizes(productSizeSet);//设置尺寸信息
//                product.setProductSpecificationss(productSpecifications);//设置选择
//                product.setProductConfigs(productConfigs);//设置配置
//                product.setDetailsImgNames(detailsImgNames);//设置图片
//                product.setProductContentsFile(productContentsFile);//设置目录
//                product.setProductSpecificationsFile(productSpecificationsFile);//设置规格
//                product.setProductVideo(productVideo);//设置影片
//                product.setProductId(item.getId()+"");
//                product.setProductState(true);
//
//                //設置類人
//                if(!StringUtils.isEmpty( item.getContent())) {
//                    FileInputStream fis = new FileInputStream("D:/tomcat/eclipse-tomcat/apache-tomcat-8.0.52/webapps/bm_upload/hcontent/"+item.getContent());
//                    ObjectInputStream ois = new ObjectInputStream(fis);
//                    product.setProductContent((( noah.buildermall.serializable.bean.ItemHtmlContentSerialBean) ois.readObject()).getHtmlContent());
//                    product.setProductContentEng(product.getProductContent());
//
//                    ois.close();
//                }
//
//
//
//                productService.addProduct(product,null);
//            }
//        }
////        adminRepository.saveAll(admins);
//
//
//
//
//
//
//    }
//}

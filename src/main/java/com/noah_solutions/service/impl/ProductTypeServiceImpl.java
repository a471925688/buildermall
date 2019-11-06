package com.noah_solutions.service.impl;

import com.noah_solutions.common.PlaceCache;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.entity.Place;
import com.noah_solutions.entity.Product;
import com.noah_solutions.entity.ProductType;
import com.noah_solutions.repository.ProductTypeRepository;
import com.noah_solutions.service.IProductTypeService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("productTypeService")
public class ProductTypeServiceImpl implements IProductTypeService {
    @Resource
    private ProductTypeRepository productTypeRepository;
//
//    @Resource
//    private CattypeRepository cattypeRepository;





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //根据条件分页获取所有产品类型
    @Override
    public Page<ProductType> selectProductTypePageByCont(ProductType condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        productTypeRepository.count(Example.of(condition,matcher));
        return productTypeRepository.findAll(Example.of(condition,matcher),pageable);
    }
    //根据条件查询所有产品类型
    @Override
    public List<ProductType> selectProductTypeByCont(ProductType condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return productTypeRepository.findAll(Example.of(condition,matcher),new Sort(Sort.Direction.ASC, "productTypeLevel").and(new Sort(Sort.Direction.ASC,"productTypeOrder")));
    }


    //根據等級排序獲取查询所有产品类型
    @Override
    public List<ProductType> selectAllProductTypeOrderByLv() {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return productTypeRepository.findAll(new Sort(Sort.Direction.ASC, "productTypeLevel").and(new Sort(Sort.Direction.ASC,"productTypeOrder")));
    }
    //查询单个产品类型
    @Override
    public ProductType selectProductTypeByProductTypeId(String productTypeId) {
        return productTypeRepository.findByProductTypeId(productTypeId);
    }

    //查询总数
    @Override
    public Long selectCountProductTypeByCont(ProductType condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  productTypeRepository.count(Example.of(condition,matcher));
    }

    //查询总数
    @Override
    public List<ProductType> findFirst5OrderByProductSelloutNumDesc() {
        List<ProductType> productTypes = new ArrayList<>();
        List<String> typeIds = productTypeRepository.findFirst5OrderByProductSelloutNumDesc(PageRequest.of(0,5));
        typeIds.forEach(v->{
            String[] typeId = v.split("\\|");
            ProductType productType =null;
            for (String id: typeId) {
                ProductType productType1 = ProductTypeCache.getProductTypesById(id);
                if(productType1.getProductTypeIsParent().equals(0)){
                    productType = ProductTypeCache.getProductTypesById(id);
                    break;
                }
            }
            productTypes.add(productType);
        });
        return  productTypes;
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //保存产品类型
    @Override
    public void addProductType(ProductType productType)throws Exception {
        if(StringUtils.isEmpty(productType.getProductTypeParentId())){
            productType.setProductTypeParentId("0");
        }else {
            if(!productType.getProductTypeParentId().equals("0")){
                // 查出上一个节点
                ProductType parentProductType = productTypeRepository.getOne(productType.getProductTypeParentId());
                //把上一个节点设为父节点
                parentProductType.setProductTypeIsParent(1);
                //设置节点等级等于父节点等级+1
                productType.setProductTypeLevel(parentProductType.getProductTypeLevel()+1);
                productType.setProductTypeParentsId(parentProductType.getProductTypeParentsId());
            }
        }
        productType = productTypeRepository.save(productType);

        productType.setProductTypeParentsId(productType.getProductTypeParentsId()+productType.getProductTypeId()+"|");
    }

    //保存产品类型
    @Override
    public void editProductType(ProductType productType)throws Exception {
        ProductType old =  productTypeRepository.getOne(productType.getProductTypeId());
        BeanUtils.copyNotNullProperties(productType,old);
        old.setProductTypeUpdateTime(DateUtil.getStringDate());
        productTypeRepository.save(old);
    }

    //删除产品类型
    @Override
    public void delProductTypeByProductTypeId(String productTypeId) {
        //查出所有子级
        List<ProductType> productTypes =  productTypeRepository.findAllByProductTypeParentId(productTypeId);
        //通过所有子级递归查出所有子孙级
        productTypes.addAll(getAllProductTypeByProductTypeId(productTypes));
        //查出当前productTypeId产品类型
        productTypes.add(productTypeRepository.findByProductTypeId(productTypeId));
        ProductType productType = new ProductType();
        //删除所有(包括子孙级产品类型)
        productTypeRepository.deleteAll(productTypes);
    }
    //批量删除产品类型
    @Override
    public void delAllProductTypeByProductTypeId(List<String> productTypeIds) {
        List<ProductType> productTypes = new ArrayList<>();
        for(int i=0;i<productTypeIds.size();i++){
            //查出每一个的子级
            List<ProductType> result = productTypeRepository.findAllByProductTypeParentId(productTypeIds.get(i));
            productTypes.addAll(result);
            //通过每一个的子级查出所有子孙级
            productTypes.addAll(getAllProductTypeByProductTypeId(result));
        }
        //查出所有当前级产品类型
        productTypes.addAll(productTypeRepository.findAllByProductTypeIdIn(productTypeIds));
        //删除所有
        productTypeRepository.deleteAll(productTypes);
    }


    @Override
    public void initProductTypeDetailsEng() {
        Integer i = 1;
        List<ProductType> productTypes= ProductTypeCache.getProductTypesByLevel(i);
        do {
            productTypes.forEach(v->{
                if(v.getProductTypeParentId().equals("0")){
                    v.setProductTypeDescribeEng(v.getProductTypeNameEng());
                }else {
                    ProductType productType = productTypeRepository.getOne(v.getProductTypeParentId());
                    v.setProductTypeDescribeEng(productType.getProductTypeDescribeEng()+"->"+v.getProductTypeNameEng());
                }
                productTypeRepository.saveAndFlush(v);
            });

            i++;
        } while ( (productTypes = ProductTypeCache.getProductTypesByLevel(i))!=null);
        productTypeRepository.flush();
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //递归查出所有子孙级产品类型
    private List<ProductType> getAllProductTypeByProductTypeId(List<ProductType> productTypes){
        List<ProductType>  result=  new ArrayList<>();
        for (int i=0;i<productTypes.size();i++){
            //递归查询所有子集产品类型并加入集合中
            result.addAll(getAllProductTypeByProductTypeId(productTypeRepository.findAllByProductTypeParentId(productTypes.get(i).getProductTypeId())));
        }
        return result;
    }
    //处理更新单个产品类型
    private void updateProductType(ProductType updateProductType){
        ProductType productType = productTypeRepository.getOne(updateProductType.getProductTypeId());
        BeanUtils.copyNotNullProperties(updateProductType,productType);
        productTypeRepository.save(productType);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////



    //////////////////////////////特殊业务部分  start//////////////////////////////////////////////////////////
    //////////////////////////////特殊业务部分  start//////////////////////////////////////////////////////////
    //数据对接
//    @Override
//    public void copyOldType() {
//       List<Cattype> cattypes = cattypeRepository.findAllByParentIdIsNull();
//       List<ProductType> productTypes = new ArrayList<>();
//       String id1="";
//       String id2="";
//
//       if(productTypeRepository.existsByProductTypeName("建築")){
//            id1 = productTypeRepository.findByProductTypeName("建築").getProductTypeId();
//       }else {
//           ProductType productType = new ProductType();
//           productType.setProductTypeName("建築");
//           productType.setProductTypeNameEng("Architecture");
//           productType.setProductTypeLevel(1);
//           productType.setProductTypeIsParent(1);
//           productType.setProductTypeParentId("0");
//           productType.setProductTypeOrder(1);
//           productType.setProductTypeDescribe("建築");
//           id1 = productTypeRepository.saveAndFlush(productType).getProductTypeId();
//       }
//
//        if(productTypeRepository.existsByProductTypeName("裝修")){
//            id2 = productTypeRepository.findByProductTypeName("裝修").getProductTypeId();
//        }else {
//            ProductType productType = new ProductType();
//            productType.setProductTypeName("裝修");
//            productType.setProductTypeNameEng("Renovation");
//            productType.setProductTypeLevel(1);
//            productType.setProductTypeIsParent(1);
//            productType.setProductTypeParentId("0");
//            productType.setProductTypeOrder(1);
//            productType.setProductTypeDescribe("裝修");
//            id2 = productTypeRepository.saveAndFlush(productType).getProductTypeId();
//        }
//       for(int i=0;i<cattypes.size();i++){
//           if (cattypes.get(i).getCatId().equals("0")){
//               continue;
//           }
//           ProductType productType;
//           if(productTypeRepository.existsByProductTypeName(cattypes.get(i).getName_tc())){
//               productType = productTypeRepository.findByProductTypeName(cattypes.get(i).getName_tc());
//           }else{
//               productType = new ProductType();
//           }
//           productType.setProductTypeName(cattypes.get(i).getName_tc());
//           productType.setProductTypeNameEng(cattypes.get(i).getName_eng());
//           productType.setProductTypeOrder(i);
//           productType.setProductTypeIsParent(cattypes.get(i).getCattype().size()>0?1:0);
//           productType.setProductTypeLevel(2);
//
//           if (cattypes.get(i).getCatId().equals("1")){
//               productType.setProductTypeParentId(id1);
//               productType.setProductTypeParentsId(id1+"|");
//               productType.setProductTypeDescribe("建築->"+productType.getProductTypeName());
//           }else {
//               productType.setProductTypeParentId(id2);
//               productType.setProductTypeParentsId(id2+"|");
//               productType.setProductTypeDescribe("裝修->"+productType.getProductTypeName());
//           }
//           productType = productTypeRepository.saveAndFlush(productType);
//           if(cattypes.get(i).getCattype().size()>0){
//               recursionSave(productType,cattypes.get(i).getCattype());
//           }
//       }
//        System.out.println("对接数据完成");
//    }
    //递归保存子集
//    private void recursionSave(ProductType productType,List<Cattype> cattypes){
//        for(int i=0;i<cattypes.size();i++){
//            ProductType newProductType;
//            if(productTypeRepository.existsByProductTypeNameAndProductTypeParentId(cattypes.get(i).getName_tc(),productType.getProductTypeId())){
//                newProductType = productTypeRepository.findByProductTypeNameAndProductTypeParentId(cattypes.get(i).getName_tc(),productType.getProductTypeId());
//            }else{
//                newProductType = new ProductType();
//            }
//            newProductType.setProductTypeName(cattypes.get(i).getName_tc());
//            newProductType.setProductTypeNameEng(cattypes.get(i).getName_eng());
//            newProductType.setProductTypeOrder(i);
//            newProductType.setProductTypeIsParent(cattypes.get(i).getCattype().size()>0?1:0);
//            newProductType.setProductTypeLevel(productType.getProductTypeLevel()+1);
//            newProductType.setProductTypeParentId(productType.getProductTypeId());
//            newProductType.setProductTypeParentsId(productType.getProductTypeParentsId()+productType.getProductTypeId()+"|");
//            newProductType = productTypeRepository.saveAndFlush(newProductType);
//            newProductType.setProductTypeDescribe(productType.getProductTypeDescribe()+"->"+newProductType.getProductTypeName());
//            if(cattypes.get(i).getCattype().size()>0){
//                recursionSave(newProductType,cattypes.get(i).getCattype());
//            }
//        }
//
//    }
    @Override
    public   void initProductTypePraentIds(){
        initProductTypePraentIds(1);
    }

    private void initProductTypePraentIds(int index){

        List<ProductType> productTypes = ProductTypeCache.getProductTypesByLevel(index);
        if(productTypes==null)
            return;
        for (ProductType v:productTypes) {
            if(v.getProductTypeParentId().equals("0")){
                v.setProductTypeParentsId(null);
            }else {
                String parent = ProductTypeCache.getProductTypesById(v.getProductTypeParentId()).getProductTypeParentsId();
                parent = StringUtils.isEmpty(parent)?"":parent;
                v.setProductTypeParentsId(parent+v.getProductTypeParentId()+"|");
            }
            initProductTypePraentIds(++index);
            productTypeRepository.save(v);
        }

    }

}

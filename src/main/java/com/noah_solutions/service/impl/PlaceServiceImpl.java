package com.noah_solutions.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.PlaceCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.ShunFengPlace;
import com.noah_solutions.entity.Place;
import com.noah_solutions.repository.ShunFengPlaceRepository;
import com.noah_solutions.repository.PlaceRepository;
import com.noah_solutions.service.IPlaceService;
import com.noah_solutions.thirdPartyInterface.BaiduTranslation;
import com.noah_solutions.thirdPartyInterface.ShunFengExpress;
import com.noah_solutions.util.ZJFConverterDemo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 2019 1 9 lijun
 * 地點處理業務層
 */
@Service("placeService")
public class PlaceServiceImpl implements IPlaceService {

/*    @Resource
    private LocalPlaceRepository localPlaceRepository;*/

    @Resource
    private PlaceRepository placeRepository;

    @Resource
    private ShunFengPlaceRepository shunFengPlaceRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////






    //////////////////////////////特殊處理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////特殊處理部分  start//////////////////////////////////////////////////////////
    //初始化順豐地址信息
    @Override
    public void initShunFengPlace()throws Exception {
        //調用順豐api獲取所有順豐地址信息
        List<ShunFengPlace> shunFengPlaces = shunFengPlaceList(ShunFengExpress.FIRST_COUNTRY_CODE,new HashSet<>());
        //獲取本地的地址（老版本中的）
        for (Place place:placeRepository.findAll()){
            //遍歷順豐地址關聯本地地址
            for(ShunFengPlace shunFengPlace:shunFengPlaces){
                //判斷兩個名稱是否相同
                if(ZJFConverterDemo.SimToTra(shunFengPlace.getSfName()).equals(ZJFConverterDemo.SimToTra(place.getNameTc()))&&!shunFengPlace.getSfLevel().equals("1")&&shunFengPlace.getSfParentId().equals(place.getParentId())){
                    //相同則設置id關聯
                    shunFengPlace.setPlaceId(place.getId());
                    break;
                }
            }
        }
        //保存所有處理好的順豐地址信息
        shunFengPlaceRepository.saveAll(shunFengPlaces);
    }
    //遞歸獲取所有順豐地址信息
    private List<ShunFengPlace> shunFengPlaceList(String countryCode, Set<String> codeS)throws Exception{
        List <ShunFengPlace> shunFengPlaces = new ArrayList<>();
        // 根據code獲取所有地址
        JSONArray jsonArray = ShunFengExpress.getAllCountryPlace(countryCode);
        //遍歷地址
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            //判斷地址是否重複
            if(codeS.contains(jsonObject.getString(ShunFengExpress.CODE)))
                continue;//重複則跳出本次循環
            //把本次地址的code放進set集合中
            codeS.add(jsonObject.getString(ShunFengExpress.CODE));
            //添加順豐地址對象
            shunFengPlaces.add(new ShunFengPlace(
                            jsonObject.getString(ShunFengExpress.ID),
                            jsonObject.getString(ShunFengExpress.CODE),
                            jsonObject.getString(ShunFengExpress.NAME),
                            jsonObject.getString(ShunFengExpress.LEVEL),
                            jsonObject.getString(ShunFengExpress.PARENT_ID),
                            jsonObject.getString(ShunFengExpress.PARENT_CODE)
                    )
            );
            //添加順豐地址對象
            if(Integer.parseInt( jsonObject.getString(ShunFengExpress.LEVEL))<4)
                shunFengPlaces.addAll(shunFengPlaceList(jsonObject.getString(ShunFengExpress.CODE),codeS));
        }
        return shunFengPlaces;
    }

    @Override
    public void initPlaceDetails(){
        recursionSetPlaceDetails("0","");
        placeRepository.flush();
    }

   private void  recursionSetPlaceDetails(String parentId,String parentDatails){
       List<Place> places = placeRepository.findAllByParentId(parentId);
       for (Place p:places
       ) {
           if(StringUtils.isEmpty(parentDatails)){
               p.setDetails(p.getNameTc());
           }else {
               p.setDetails(parentDatails+"-"+p.getNameTc());
           }
//           p.setDetails(parentDatails+"-"+p.getNameTc());
           recursionSetPlaceDetails(p.getId(),p.getDetails());
       }
    }

    @Override
    public void initPlaceByShunFengPlace()throws Exception {
        //調用順豐api獲取所有順豐地址信息
        List<Place> oldplace = placeRepository.findAll();
        List<ShunFengPlace> shunFengPlaces = shunFengPlaceRepository.findAll();
        for (Place place:oldplace){
            Boolean isExist = false;
            for (ShunFengPlace shunFengPlace:shunFengPlaces){
                //對比順豐庫中是否存在此地址信息
                if(ZJFConverterDemo.SimToTra(shunFengPlace.getSfName()).equals(ZJFConverterDemo.SimToTra(place.getNameTc()))){
                    //存在則設位true
                    isExist = true;
                    break;
                }
            }
            if(!isExist)
                placeRepository.deleteById(place.getId());//刪除不存在的
        }
        placeRepository.flush();//立即更新到數據庫

        List<Place> newPlaces = new ArrayList<>();
        for (Place place:placeRepository.findAll()){
            ShunFengPlace place1 = shunFengPlaceRepository.findByPlaceId(place.getId());
            List<ShunFengPlace> shunFengPlaces1 = shunFengPlaceRepository.findAllBySfParentId(place1.getSfId());
            newPlaces.addAll(placeList(shunFengPlaces1,place.getId()));
        }
        //保存所有處理好的順豐地址信息
        placeRepository.saveAll(newPlaces);
    }


    //遞歸同步順地址信息到本地庫
    private List<Place> placeList(List<ShunFengPlace> shunFengPlaces,String parentId)throws Exception{
        List<Place> places = new ArrayList<>();
        for (ShunFengPlace shunFengPlace:shunFengPlaces){
            String parentId1 = shunFengPlace.getSfParentId();
            if(parentId!=null){
                parentId1 = parentId;
            }
            places.add(new Place(shunFengPlace.getSfId(),shunFengPlace.getSfName(),"",shunFengPlace.getSfCode(),"1",parentId1,shunFengPlace.getSfLevel(),shunFengPlace.getPlaceType()));
            List<ShunFengPlace> shunFengPlaces1 = new ArrayList<>();
            shunFengPlaces1.addAll(shunFengPlace.getShunFengPlaceSet());
            places.addAll(placeList(shunFengPlaces1,null));
        }
        return places;
    }


    //設置順豐類型
    @Override
    public void setShunFenPlaceType()throws Exception{
        List<ShunFengPlace> shunFengPlaces =  shunFengPlaceRepository.findAllBySfParentCode("A000000000");
        for (ShunFengPlace shunFengPlace:shunFengPlaces) {
            if(ZJFConverterDemo.SimToTra(shunFengPlace.getSfName()).equals(ZJFConverterDemo.SimToTra("中國"))) {
                shunFengPlace.setPlaceType(3);
                recursionSetShunFengPlaceType(shunFengPlace.getShunFengPlaceSet(),3);
            }else if(shunFengPlace.getSfLevel().equals("2")){
                shunFengPlace.setPlaceType(2);
                recursionSetShunFengPlaceType(shunFengPlace.getShunFengPlaceSet(),2);
            }else {
                shunFengPlace.setPlaceType(1);
                recursionSetShunFengPlaceType(shunFengPlace.getShunFengPlaceSet(),1);
            }
        }
        shunFengPlaceRepository.flush();
    }
    //同步海外地址到本地
    @Override
    @Transactional
    public  void initOverseas() throws Exception{
        List<ShunFengPlace> shunFengPlaces = shunFengPlaceRepository.findAllByPlaceTypeAndSfLevel(1,"1");
        for (ShunFengPlace v:shunFengPlaces) {
            Place place = new Place(v.getSfId(),v.getSfName(),"",v.getSfCode(),"1",null,v.getSfLevel(),v.getPlaceType());
            if(place.getId().equals("1")){
                place.setId("13");
            }
            place = placeRepository.saveAndFlush(place);
            v.setPlaceId(place.getId());
            List<ShunFengPlace> shunFengPlaces1 = shunFengPlaceRepository.findAllBySfParentId(v.getSfId());
            placeRepository.saveAll( placeList(shunFengPlaces1,place.getId()));
        }




    }
    private void recursionSetShunFengPlaceType(Collection<ShunFengPlace> shunFengPlaces, Integer placeType){
        for (ShunFengPlace shunFengPlace:shunFengPlaces) {
            shunFengPlace.setPlaceType(placeType);
            recursionSetShunFengPlaceType(shunFengPlace.getShunFengPlaceSet(),placeType);
        }

    }

    @Override
    public List<Place> getAllProvincePlace() {
        return placeRepository.findAllByLevelOrId("2","1");
    }

    //初始化英文名称
    @Override
    public void translationPlace()throws Exception {
        placeRepository.findAll().forEach(v->{
            if(StringUtils.isEmpty(v.getNameEng()))
                v.setNameEng(BaiduTranslation.query(v.getNameTc()));
        });
        placeRepository.flush();
    }
    //初始化英文详情
    @Override
    public void initPlaceDetailsEng() {
        Integer i = 1;
        List<Place> places=PlaceCache.getPlaceByLv(i+"");
        do {
            places.forEach(v->{
                if(v.getParentId().equals("0")){
                    v.setDetailsEng(v.getNameEng());
                }else {
                    Place parent = placeRepository.getOne(v.getParentId());
                    v.setDetailsEng(parent.getDetailsEng()+"-"+v.getNameEng());
                }
                placeRepository.saveAndFlush(v);
            });

            i++;
        } while ( (places = PlaceCache.getPlaceByLv(i+""))!=null);
        placeRepository.flush();
    }

    @Override
    public ShunFengPlace getShunFengPlaceByPlaceId(String placeId) {
        return shunFengPlaceRepository.findByPlaceId(placeId);
    }


    //////////////////////////////特殊處理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////特殊處理部分  end//////////////////////////////////////////////////////////



}

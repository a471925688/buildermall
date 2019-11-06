package com.noah_solutions.common;

import com.noah_solutions.entity.Place;
import com.noah_solutions.repository.PlaceRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlaceCache {
    @Resource
    private PlaceRepository placeRepository;

    //所有省級地址
    private  static  List<Place> provincePlaces ;

    private static List<Place> placeAllLst ;//所有地址 list

    private static Map<String,Place> placeAllMap;//所有地址 map

    private static Map<String,List<Place>> placeChildMap;//子類地址 map(僅一級子類)

    private static Map<String,List<Place>> placeLevelMap;


    public   void init(){
        provincePlaces = new ArrayList<>();
        placeAllLst = new ArrayList<>();
        placeAllMap = new HashMap<>();
        placeChildMap = new HashMap<>();
        placeLevelMap = new HashMap<>();
        placeAllLst = placeRepository.findAll();
        for (Place p:placeAllLst) {
            placeAllMap.put(p.getId(),p);
            if(!placeChildMap.containsKey(p.getParentId())){
                placeChildMap.put(p.getParentId(),new ArrayList<>());
            }
            placeChildMap.get(p.getParentId()).add(getNewPlace(p));
            if(p.getLevel().equals("2")||p.getId().equals("1")){
                provincePlaces.add(p);
            }

            if(!placeLevelMap.containsKey(p.getLevel())){
                placeLevelMap.put(p.getLevel(),new ArrayList<>());
            }
            placeLevelMap.get(p.getLevel()).add(getNewPlace(p));
        }
    }

    public  static List<Place> getProvincePlaces(){
        return provincePlaces;
    }

    public  static Place getPlaceById(String id){
        return placeAllMap.get(id);
    }

    public static List<Place> getPlaceChildById(String placeId){
        if(placeId.equals("null")){
            return placeChildMap.get(null);
        }
        return placeChildMap.get(placeId);
    }

    public static List<Place> getLv1Place(){
        return placeLevelMap.get("1");
    }
    public static List<Place> getPlaceByLv(String lv){
        return placeLevelMap.get(lv);
    }

    private Place getNewPlace(Place place){
        Place  place1 = new Place();
        place1.setId(place.getId());
        place1.setDetails(place.getDetails());
        place1.setCountryCode(place.getCountryCode());
        place1.setLevel(place.getLevel());
        place1.setNameEng(place.getNameEng());
        place1.setNameTc(place.getNameTc());
        place1.setParentId(place.getParentId());
        place1.setStatus(place.getStatus());
        place1.setType(place.getType());
        return place1;
    }
}

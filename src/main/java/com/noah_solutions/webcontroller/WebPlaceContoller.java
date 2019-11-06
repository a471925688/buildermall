package com.noah_solutions.webcontroller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.PlaceCache;
import com.noah_solutions.entity.Place;
import com.noah_solutions.ex.CodeAndMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/webPlace")
public class WebPlaceContoller {

    @RequestMapping("/getLv1Place.do")
    public JSONObject getLv1Place(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(PlaceCache.getLv1Place());
    }

    @RequestMapping("/getPlaceChildById.do")
    public JSONObject getPlaceChildById(@RequestParam("id") String id,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(PlaceCache.getPlaceChildById(id));
    }


    @RequestMapping("/getPlace2ChildById.do")
    public JSONObject getPlace2ChildById(@RequestParam("id") String id,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        List<Place> places = PlaceCache.getPlaceChildById(id.equals("86")?"null":id);
        return CodeAndMsg.SUCESS.getJSON(   placeHandle(places,id));
    }
    public JSONObject placeHandle(List<Place> places,String id){
        if(places==null){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray place1 = new JSONArray();
        JSONArray  place2 = new JSONArray();
        JSONArray  place3 = new JSONArray();
            places.forEach(v->{
                if(id.equals("86")) {
                    if (v.getType() == 1) {
                        place3.add(v);
                    } else if (v.getType() == 2) {
                        place2.add(v);
                    } else {
                        place1.addAll(PlaceCache.getPlaceChildById(v.getId()));
                    }
                }else {
                    jsonObject.fluentPut(v.getId(), v.getNameTc());
                }
            });
         if(id.equals("86")) {
            jsonObject.fluentPut("海外", place3);
            jsonObject.fluentPut("港澳", place2);
            jsonObject.fluentPut("大陸", place1);
        }
        return jsonObject;
    }
}

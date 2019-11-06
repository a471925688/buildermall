package com.noah_solutions.common;

import com.noah_solutions.bean.DataCache;

import java.util.*;

/**
 * 緩存前端頁面跳轉時所需要的一些數據
 */
public class HtmlDataCache {
    private  static  Map<String, DataCache> htmlDates = new Hashtable<>();
    private static Long defaultTimeOut = 60*5L;//默認超時時間
    private static Integer defaultUseNum = 1; //默認使用次數

    /**
     * 設置頁面數據緩存
     * @param data 保存的數據
     * @return key值
     */
    public static String setHtmlDate(Object data){
        return setHtmlDate(data,defaultTimeOut,defaultUseNum);
    }

    /**
     *
     * @param data 保存的數據
     * @param timeOut 超時時間 (單位：秒)
     * @return key值
     */
    public static String setHtmlDate(Object data,Long timeOut){
        return setHtmlDate(data,timeOut*1000,defaultUseNum);
    }

    /**
     *
     * @param data 保存的數據
     * @param timeOut 超時時間 (單位：秒)
     * @param useNum 使用次數
     * @return
     */
    public  static synchronized String setHtmlDate(Object data,Long timeOut,Integer useNum){
        String key = UUID.randomUUID().toString();
        htmlDates.put(key,new DataCache(data,new Date().getTime()+timeOut*1000,useNum));
        return key;
    }


    /**
     *
     * @param data 保存的數據
     * @return
     */
    public static void setHtmlDate(String key,Object data){
        htmlDates.put(key,new DataCache(data,new Date().getTime()+defaultTimeOut*1000,defaultUseNum));
    }
    public static void setHtmlObj(String key,DataCache dataCache){
        htmlDates.put(key,dataCache);
    }
    public static String setHtmlObj(DataCache dataCache){
        String key = UUID.randomUUID().toString();
        htmlDates.put(key,dataCache);
        return key;
    }
    /**
     *
     * @param data 保存的數據
     * @return
     */
    public static void setHtmlDate(String key,Object data,Integer useNum){
        htmlDates.put(key,new DataCache(data,new Date().getTime()+defaultTimeOut*1000,useNum));
    }
    /**
     * 獲取緩存數據
     * @param key
     * @return
     */
    public static Object getHtmlDate(String key){
        DataCache dataCache = htmlDates.get(key);
        Object data = null;
        if(dataCache!=null){

            if(dataCache.getExpirationTime()<new Date().getTime()||dataCache.getUseNum() == 0){
                htmlDates.remove(key);
            }else {
                if(dataCache.getUseNum()>0){
                    dataCache.setUseNum(dataCache.getUseNum()-1);
                }
                data = dataCache.getData();
            }
        }

        return data;
    }
    /**
     * 獲取并移除緩存數據
     * @param key
     * @return
     */
    public static Object getAndRomveHtmlDate(String key){
        DataCache dataCache = htmlDates.get(key);
        htmlDates.remove(key);
        return dataCache.getData();
    }

    /**
     * 移除緩存數據
     * @param key
     * @return
     */
    public static void romveHtmlDate(String key){
        htmlDates.remove(key);
    }

    /**
     * 獲取緩存對象
     * @param key
     * @return
     */
    public static DataCache getHtmlObj(String key){
        DataCache dataCache = htmlDates.get(key);
        if(dataCache!=null){
            if(dataCache.getExpirationTime()<new Date().getTime()||dataCache.getUseNum() == 0){
                htmlDates.remove(key);
            }else {
                if(dataCache.getUseNum()>0){
                    dataCache.setUseNum(dataCache.getUseNum()-1);
                }
            }
        }
        return htmlDates.get(key);
    }

    /**
     * 刪除數據
     * @param key
     */
    public static void delHtmlDate(String key){
        htmlDates.remove(key);
    }

    /**
     * 刪除過期數據
     */
    public  static  void delTimeOutData(){
        for (Iterator<Map.Entry<String, DataCache>> it = htmlDates.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, DataCache> dataCache = it.next();
            if(dataCache.getValue().getExpirationTime()<new Date().getTime())
                it.remove();
        }
    }



    public static void updateHtmlDate(String key,Object data){
        DataCache dataCache = htmlDates.get(key);
        dataCache.setData(data);
        htmlDates.put(key,dataCache);
    }
}

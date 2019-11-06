package com.noah_solutions.ex;

import com.alibaba.fastjson.JSONObject;

public interface IExceptionEnums {
    Integer getCode();

    String getMsg();

    JSONObject getJSON();

    JSONObject getJSON(Object data);

    JSONObject getCartJSON(Object data,Integer type);
    JSONObject getCartJSON(Integer type);
}

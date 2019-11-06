package com.noah_solutions.ex;

import com.alibaba.fastjson.JSONObject;

/**
 * 2018 12 1 lijun
 * 枚举类（存放常量属性）
 */
public enum CodeAndMsg  implements IExceptionEnums {
    SUCESS(0,"OK"),
    ERROR(1,"平台异常"),
    GOTO_INDEX(111,"跳轉到主頁"),
    ERROR_TIME_OUT(111,"請求頁面超時"),
    GOTO_LOGIN(112,"跳轉到登录页面"),
    KEY_TIME_OUT(113,"當前連接已失效"),
    ERROR_CHECK_USER(222,"用戶驗證失敗"),
    HANDLESUCESS(0,"操作成功"),
    ADDSUCESS(0,"添加成功"),
    EDITSUCESS(0,"编辑成功"),
    DELSUCESS(0,"删除成功"),
    SUCESSUPLOAD(0,"文件上传成功"),
    ERRORUPLOAD(1,"文件上传失败"),
    ADMIN_ADDERROR(1001,"新增管理员失败(用户名或邮箱已存在)"),
    ADMIN_NOTFINDUSERNAME(1002,"用户名不存在"),
    ADMIN_PASSWORDERROR(1003,"密码错误"),
    ADMIN_BANNED(1004,"账号被禁用"),
    ADMIN_LOGINERROR(1101,"验证码错误!"),
    USER_ADDERROR(1001,"新增用戶失败(用户名或邮箱已存在)"),
    USER_NOTFINDUSERNAME(1002,"用户名不存在"),
    USER_PASSWORDERROR(1003,"密码错误"),
    USER_BANNED(1004,"账号被禁用"),
    DISCOUNTCODE_ADDERROR(1101,"新增優惠碼失敗(優惠碼已存在)"),
    ORDER_REFUND(1201,"自動退款失敗,請手動處理"),
    BRAND_ADDERROR(2001,"新增品牌失敗(品牌名已存在)"),
    COLOR_ADDERROR(2101,"新增顏色失敗(顏色名稱已存在)"),
    FILE_PDF_NOTFIND(3001,"郵件發送失敗PDF模板不存在");




    public int code;
    public String message;

    private CodeAndMsg(int code, String message){
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public JSONObject getJSON() {
        return new JSONObject().fluentPut("code", code)
                .fluentPut("msg",message);
    }

    @Override
    public JSONObject getJSON(Object data) {
        if(data!=null){
            return new JSONObject().fluentPut("code", code)
                    .fluentPut("msg",message)
                    .fluentPut("data",data);
        }else {
            return getJSON();
        }
    }

    @Override
    public JSONObject getCartJSON(Object data,Integer type) {
        if(data!=null){
            return new JSONObject().fluentPut("code", code)
                    .fluentPut("msg",message)
                    .fluentPut("data",data)
                    .fluentPut("type",type);
        }else {
            return getJSON().fluentPut("type",type);
        }
    }

    @Override
    public JSONObject getCartJSON(Integer type) {
        return new JSONObject().fluentPut("code", code)
                    .fluentPut("msg",message)
                    .fluentPut("type",type);

    }
}

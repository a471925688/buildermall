var ServiceUtil = null;
var serviceHandleUtil=function () {
    
}
serviceHandleUtil.prototype={
    getParameterByName:function (name,url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    },
    //弹出html页面
    getServiceHtml:function(url,paramm,title){
        $.get(url,paramm, function (html) {
            layer.open({
                type: 1,//这就是定义窗口类型的属性
                title: title,
                shadeClose : false,
                area: ['700px','600px'],
                content: html//'传入任意的文本或html' //这里content
                //是一个普通的String 比如："<div>哈哈</div>"
            });
        })
    },
    postHandle: function (layer, url, data,fun) {
        $.post(url, data, function (obj) {
            if(fun){
                    fun(obj);
            }else{
                if(layer){
                    var icon = 2;
                    if(!obj.code){
                        icon = 1
                    }
                    layer.msg(obj.msg, {
                        icon: icon,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                }
            }

            // $("#search_btn").click();
        });

    },

    postHandleReturnData: function (url,doc,data) {
        data = data?data:{}
        $.post(url, data, function (obj) {
            if(!obj.code){
                $(doc).text(obj.data);
            }
        });

    },
    postAsyncHandle:function(url,data){
        $.ajaxSettings.async = false;
        if(!data){
            data ={};
        }
        $.post(url,data,function (obj) {
            if(!obj.code){
                data=obj.data;
            }
        })
        $.ajaxSettings.async = true;
        return data;
    },
    layer_show:function(title,url,w,h,type) {
        title = title?title:false;
        url=url?url:"404.html";
        w=w?w:$(window).width()*0.9;
        h=h?h:($(window).height() - 50);
        type=type?type:2;
      var   index = layer.open({
            type: type,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.4,
            title: title,
            content: url
        });
    },
    openHtml:function(title,html,w,h){
        w=w?w:$(window).width()*0.9;
        h=h?h:($(window).height() - 50);

        layer.open({
            title: title
            ,content: html
            // type: 1,//这就是定义窗口类型的属性
            // title: title,
            // shadeClose : false,
            // area: [w + 'px', h + 'px'],
            // content: html//'传入任意的文本或html' //这里content
            //是一个普通的String 比如："<div>哈哈</div>"
        });
    },
    //校验权限
    checkPermission:function (path) {
        if (localStorage.getItem("permissions").indexOf(path) == -1) {
            return false;
        }
        return true;
    },
    //校验权限并弹出提示框
    checkPermissionAndPopup:function (path) {
        if(!ServiceUtil.checkPermission(path)){
            layer.msg('权限不足');
            return false;
        }
        return true;
    },
    //时间设置
    currentTime:function() {
        var d = new Date(), str = '  ';
        str += d.getFullYear() + '/';
        str += d.getMonth() + 1 + '/';
        str += d.getDate() + '/';
        str += d.getHours() + ':';
        str += d.getMinutes() + ':';
        str += d.getSeconds() + ' ';
        return str;
    },
    //获取时间
    curentTime:function () {
        var now = new Date();

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒

        var clock = year + "-";

        if (month < 10)
            clock += "0";

        clock += month + "-";

        if (day < 10)
            clock += "0";

        clock += day + " ";

        if (hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;
        return (clock);
    },
    //判數組斷數據是否重複
    isRepeat:function(array) {
        var hash = {};
        for (var i in array) {
            if (array[i] != "") {
                if (hash[array[i]])
                    return true;
                hash[array[i]] = true;
            }
        }
        return false;
    },
    //去除数组中的空值
    trimSpace:function(array) {
        for (var i = 0; i < array.length; i++) {
            if (array[i] == "" || typeof(array[i]) == "undefined") {
                array.splice(i, 1);
                i = i - 1;

            }
        }
        return array;
    },
    //數組去重複
    uniquearr:function(arr) {
        var hash = [];
        for (var i = 0; i < arr.length; i++) {
            var isexist = false;
            for (var n = 0; n < hash.length; n++) {
                if (JSON.stringify(arr[i]) == JSON.stringify(hash[n])) {
                    isexist = true;
                    break;
                }
            }
            if (!isexist) {
                hash.push(arr[i]);
            }
        }
        return hash;
    },
    copyObj:function (obj) {
        if (obj === null) return null
        if (typeof obj !== 'object') return obj;
        if (obj.constructor === Date) return new Date(obj);
        if (obj.constructor === RegExp) return new RegExp(obj);
        var newObj = new obj.constructor();  //保持继承链
        for (var key in obj) {
            if (obj.hasOwnProperty(key)) {   //不遍历其原型链上的属性
                var val = obj[key];
                newObj[key] = typeof val === 'object' ? arguments.callee(val) : val; // 使用arguments.callee解除与函数名的耦合
            }
        }
        return newObj;
    },
    generateUUID:function () {
        var d = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = (d + Math.random() * 16) % 16 | 0;
            d = Math.floor(d / 16);
            return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
        });
        return uuid;
    },
    loadImageToBlob:function(url, callback) {

        if (!url || !callback) return false;

        var xhr = new XMLHttpRequest();

        xhr.open('get', url, true);

        xhr.responseType = 'blob';

        xhr.onload = function () {

            // 注意这里的this.response 是一个blob对象 就是文件对象

            callback(this.status == 200 ? this.response : false);
        }

        xhr.send();

        return true;

    },
    compareJson1:function (json1,json2) {//判断两个对象是否拥有相同的值
        for (attr in json1) {
            if (json1[attr] != json2[attr]&&json2[attr])
                return false; //判断键值
        }
        for (attr in json2) {
            if (json1[attr] != json2[attr]&&json1[attr])
                return false; //判断键值
        }//再把json2遍历判断
        return true;
    }


}
window.ServiceUtil=new serviceHandleUtil();
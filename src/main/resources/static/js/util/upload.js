var uploadUtil = null;
var Upload = function () {
};
Upload.prototype = {
    /** 单文件上传
     * @auto lmm
     * @param id 组装位置
     * @param callback 回调方法
     * @param ext 格式
     * @version v1.0
     * @return
     */
    fileUpload :function(id,callback,removecallback,showimgs,ext){
        $("#"+id).fileinput({
            language: 'zh', //设置语言
            uploadUrl:  $("#contextPath").val()+"/file/uploadImages", //上传的地址
            allowedFileExtensions : ['jpd','gif','bmp','png','jpeg'],//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showCaption: true,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            autoReplace: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            dropZoneEnabled: false,//是否显示拖拽区域
            showPreview:true,
            showRemove:false,
            dropZoneEnabled: false
        }).on("filebatchselected", function(event, files) {
            $(this).fileinput("upload");
        }).on("fileuploaded", function(event, data) {
            if(data.response)
            {
                if(callback){
                    callback(data.response);
                }
            }
        }).on("filesuccessremove", function(event, data) {
            removecallback($("#"+data).find("img").attr("title"));
        }).on("filecleared", function(event, data) {
            removecallback();
        });
    },


    /**
     * 多视频上傳
     * @param id 容器id
     * @param type 文件類型 1.圖片,2.影片
     * @param callback 上傳成功後的回調方法
     * @param removecallback 移除時的回調方法
     * @param showimgs 初始化顯示的圖片
     * @param ext 支持的文件類型
     */
    videosUpload : function(id,type,callback,removecallback,showimgs,ext) {
        showimgs = showimgs ? showimgs : []
        console.log(showimgs)
        $("#" + id).fileinput({
            language: 'zh', //设置语言
            uploadUrl: $("#contextPath").val() + "/file/uploadAllVideos", //上传的地址
            allowedFileExtensions: ext,//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            uploadAsync: false,
            overwriteInitial: false, //不覆盖已存在的图片
            uploadExtraData: function (previewId, index) {
                //注意这里，传参是json格式,后台直接使用对象属性接收，比如employeeCode，我在RatingQuery 里面直接定义了employeeCode属性，然后最重要的也是
                //最容易忽略的，传递多个参数时，不要忘记里面大括号{}后面的分号，这里可以直接return {a:b}; 或者{a:b}都可以，但必须要有大括号包裹
                var data = {
                    type: type
                };
                return data;
            },

            //dropZoneEnabled: false,//是否显示拖拽区域
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 10, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            initialPreview: showimgs,
            // initialPreviewConfig: [{
            //     caption: 'desert.jpg',// 展示的图片名称  
            //     width: "100%",// 图片高度  
            //     url: "{:U('localhost/delete')}",// 预展示图片的删除调取路径  
            //     key: 100,// 可修改 场景2中会用的  
            //     extra: {id: 100} //调用删除路径所传参数   
            //  }]
        }).on('filebatchselected', function (event, data, id, index) {
            $(this).fileinput("upload");
            console.log("this:")
            console.log(this)
            console.log("data:" + data[0])
            console.log("id:" + id)
            console.log("index:" + index);
        }).on("filebatchuploadsuccess", function (event, data, previewId, index) {
            console.log("添加成功");
            console.log(data.response)
            console.log("previewId:" + previewId)
            console.info("index:" + index)
            if (data.response) {
                if (callback) {
                    callback(data.response);
                }
            }
        }).on("filesuccessremove", function (event, data) {
            var fileName = $("#" + data).attr("title");
            if (!fileName) {
                fileName = $("#" + data).find(".kv-file-content").children().eq(0).attr("title");
            }
            removecallback(fileName);
        }).on("filecleared", function (event, data) {
            removecallback();
        });
    },



/**
     * 多图片上傳
     * @param id 容器id
     * @param type 文件類型 1.圖片,2.影片
     * @param callback 上傳成功後的回調方法
     * @param removecallback 移除時的回調方法
     * @param showimgs 初始化顯示的圖片
     * @param ext 支持的文件類型
     */
    imagesUpload : function(id,type,callback,removecallback,showimgs,ext){
        showimgs = showimgs?showimgs:[]
        console.log(showimgs)
        $("#"+id).fileinput({
            language: 'zh', //设置语言
            uploadUrl: $("#contextPath").val()+"/file/uploadAllImages", //上传的地址
            allowedFileExtensions: ext,//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            uploadAsync:false,
            overwriteInitial: false, //不覆盖已存在的图片
            uploadExtraData:function (previewId, index) {
                //注意这里，传参是json格式,后台直接使用对象属性接收，比如employeeCode，我在RatingQuery 里面直接定义了employeeCode属性，然后最重要的也是
                //最容易忽略的，传递多个参数时，不要忘记里面大括号{}后面的分号，这里可以直接return {a:b}; 或者{a:b}都可以，但必须要有大括号包裹
                var data = {
                    type : type
                };
                return data;
            },

            //dropZoneEnabled: false,//是否显示拖拽区域
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 10, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            initialPreview: showimgs,
            // initialPreviewConfig: [{
            //     caption: 'desert.jpg',// 展示的图片名称  
            //     width: "100%",// 图片高度  
            //     url: "{:U('localhost/delete')}",// 预展示图片的删除调取路径  
            //     key: 100,// 可修改 场景2中会用的  
            //     extra: {id: 100} //调用删除路径所传参数   
            //  }]
        }).on('filebatchselected', function (event, data, id, index) {
            $(this).fileinput("upload");
            console.log("this:")
            console.log(this)
            console.log("data:"+data[0])
            console.log("id:"+id)
            console.log("index:"+index);
        }).on("filebatchuploadsuccess", function(event, data,previewId,index) {
            console.log("添加成功");
            console.log(data.response)
            console.log("previewId:"+previewId)
            console.info("index:"+index)
            if(data.response)
            {
                if(callback){
                    callback(data.response);
                }
            }
        }).on("filesuccessremove", function(event, data) {
            var fileName = $("#"+data).attr("title");
            if(!fileName){
                fileName = $("#"+data).find(".kv-file-content").children().eq(0).attr("title");
            }
            removecallback(fileName);
        }).on("filecleared", function(event, data) {
            removecallback();
        });
    },


    /**
     * 多文件上傳
     * @param id 容器id
     * @param type 文件類型 1.圖片,2.影片
     * @param callback 上傳成功後的回調方法
     * @param removecallback 移除時的回調方法
     * @param showimgs 初始化顯示的圖片
     * @param ext 支持的文件類型
     */
    filesUpload : function(id,type,callback,removecallback,showimgs,ext){
        showimgs = showimgs?showimgs:[]
        console.log(showimgs)
        $("#"+id).fileinput({
            language: 'zh', //设置语言
            uploadUrl: $("#contextPath").val()+"/file/uploadAllFiles", //上传的地址
            allowedFileExtensions: ext,//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            uploadAsync:false,
            overwriteInitial: false, //不覆盖已存在的图片
            uploadExtraData:function (previewId, index) {
            //注意这里，传参是json格式,后台直接使用对象属性接收，比如employeeCode，我在RatingQuery 里面直接定义了employeeCode属性，然后最重要的也是
            //最容易忽略的，传递多个参数时，不要忘记里面大括号{}后面的分号，这里可以直接return {a:b}; 或者{a:b}都可以，但必须要有大括号包裹
                var data = {
                    type : type
                };
                return data;
            },

            //dropZoneEnabled: false,//是否显示拖拽区域
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 10, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount:true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            initialPreview: showimgs,
            // initialPreviewConfig: [{
            //     caption: 'desert.jpg',// 展示的图片名称  
            //     width: "100%",// 图片高度  
            //     url: "{:U('localhost/delete')}",// 预展示图片的删除调取路径  
            //     key: 100,// 可修改 场景2中会用的  
            //     extra: {id: 100} //调用删除路径所传参数   
            //  }]
        }).on('filebatchselected', function (event, data, id, index) {
            $(this).fileinput("upload");
            console.log("this:")
            console.log(this)
            console.log("data:"+data[0])
            console.log("id:"+id)
            console.log("index:"+index);
        }).on("filebatchuploadsuccess", function(event, data,previewId,index) {
            console.log("添加成功");
            console.log(data.response)
            console.log("previewId:"+previewId)
            console.info("index:"+index)
            if(data.response)
            {
                if(callback){
                    callback(data.response);
                }
            }
        }).on("filesuccessremove", function(event, data) {
            var fileName = $("#"+data).attr("title");
            if(!fileName){
                fileName = $("#"+data).find(".kv-file-content").children().eq(0).attr("title");
            }
            removecallback(fileName);
        }).on("filecleared", function(event, data) {
            removecallback();
        });
    },
    edit_image:function (id,path,con) {
        ("#"+id).fileinput({
            uploadUrl: "upload", //上传到后台处理的方法
            uploadAsync: false, //设置同步，异步 （同步）
            language: 'zh', //设置语言
            overwriteInitial: false, //不覆盖已存在的图片
            //下面几个就是初始化预览图片的配置
            initialPreviewAsData: true,
            initialPreviewFileType: 'image',
            initialPreview: path, //要显示的图片的路径
            initialPreviewConfig: [
                {caption: "People-1.jpg", size: 576237, width: "120px", url: "/site/file-delete", key: 1},
            ]
        });
    }

}
uploadUtil= new Upload();

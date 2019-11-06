var Advertising;
var advertisingHandle=function () {

}
advertisingHandle.prototype={
    tableIns:{},
    //初始化廣告圖页面
    initAdvertisingView:function () {
        //初始化廣告圖数量
        $.post($("#contextPath").val()+'/advertising/getCountAdvertising.do', {}, function (obj) {
            if(!obj.code)
                $("#countType b").text(obj.data);
        })
        //初始化左侧权限列表
        $.post($("#contextPath").val()+'/advertising/getAllCountGroupByImageGroupId.do', {}, function (obj) {
            if(!obj.code){
                var count = 0;
                for(var i=0;i<obj.data.length;i++){
                    count+=obj.data[i][1];
                    $(".b_P_Sort_list").append("<li onclick='Advertising.resettable("+obj.data[i][2]+")'><i class=\"fa fa-image pink \"></i> <a href=\"#\"><span class='lang' key=''>"+obj.data[i][0]+"</span>（"+obj.data[i][1]+"）</a></li>");
                }
                $(".b_P_Sort_list a").eq(0).html("<span class='lang' >"+"全部"+"</span>（"+count+"）");
            }
        })
        //初始化廣告圖列表
        var data = {};
        layui.use(['table','form'], function() {
            var table = layui.table
                ,form=layui.form;
                Advertising.tableIns = table.render({
                elem: '#advertisingList'
                , height: $(window).height()-120
                , url: $("#contextPath").val()+'/advertising/getAdvertisingPageByCont.do' //数据接口
                , title: '廣告圖表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,defaultToolbar: [ 'print', 'exports']
                , limit: 15
                ,cellMinWidth: 80
                ,cols: [[ //表头
                    {type: 'checkbox'}
                    , {field: 'imageGroupId', title: '廣告圖类型', align: 'center',  width:150,templet: function (d) {
                            return ServiceUtil.postAsyncHandle($("#contextPath").val()+"/advertisingType/getAdvertisingTypeById.do",{advertisingTypeId:d.imageGroupId}).advertisingTypeName;
                        }}
                    , {field: 'imageOrder', title: '排序',width:80,  align: 'center', sort: true}
                    , {field: 'imagePath', title: '圖片',  align: 'center',width:200,templet: function (d) {
                            var id = ServiceUtil.generateUUID();
                            return "<img src='"+$("#contextPath").val()+"/image/"+d.imagePath+"' onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' style='width: auto;height: 80px;max-width: 100%;max-height: 100%;'>"

                        }}
                    , {field: 'imageUrl', title: '鏈接',  align: 'center',templet: function (d) {
                            return "<a style='color: blue' href='"+d.imageUrl+"'>"+d.imageUrl+"</a>"
                        }}

                    , {
                        title: '<span class="lang" key="operation">操作</span>',  align: 'center', width:120,templet: function (d) {
                            var html = "";
                            if(ServiceUtil.checkPermission("/advertising/editAdvertisingView")){
                                html += "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\" '><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/advertising/delAdvertisingById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                            }
                            return html;
                        }
                    }
                ]]
                ,done: function(res, curr, count){
                    //如果是异步请求数据方式，res即为你接口返回的信息。
                    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                    // console.log(res);
                    //
                    // //得到当前页码
                    // console.log(curr);
                    //
                    // //得到数据总量
                    // console.log(count);
                    common.initLang();
                }
            });

            //监听头工具栏事件
            table.on('toolbar(advertisingList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("/advertising/addAdvertisingView")){
                            Advertising.addAdvertisingView();
                        }break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("/advertising/editAdvertisingView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            Advertising.addAdvertisingView();
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/advertising/delAllAdvertisingById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">廣告圖</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].advertisingRealName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].imageId);
                                }
                                param.advertisingIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val()+"/advertising/delAllAdvertisingById.do",param);
                                Advertising.tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(advertisingList)', function(obj) {//注：tool 是工具条事件名，advertisingList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">廣告圖</label> ]: '+' <label style="color: blue"></label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.advertisingId = data.imageId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/advertising/delAdvertisingById.do",param);
                        });
                        break;
                    }
                    case "edit":{
                        Advertising.editAdvertisingView(data.imageId);
                    }
                }
            });
        })
    },
    //弹出添加廣告圖页面
    addAdvertisingView:function(){
        ServiceUtil.layer_show('<span class="lang" key="add_advertisingistrator">'+common.getDataByKey("add_advertisingistrator")+'</span>',$("#contextPath").val()+'/advertising/addAdvertisingView',900,700)
    },
    //弹出编辑廣告圖页面
    editAdvertisingView:function(advertisingId){
        ServiceUtil.layer_show('<span class="lang" key="editorial_advertisingistrator">'+common.getDataByKey("editorial_advertisingistrator")+'</span>',$("#contextPath").val()+'/advertising/editAdvertisingView?advertisingId='+advertisingId,900,700)
    },

    //初始化添加或修改廣告圖页面信息
    initAddOrUpdateAdvertising:function(){
        layui.use(['form', 'layedit','upload'], function() {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                ,upload = layui.upload

            var mark = 700/200;
            //初始化图标上传
            Upload.uploadImgCropper(upload, "uploadic", $("#contextPath").val()+"/file/uploadImages", {}, "jpg|gif|bmp|png|jpeg","#imgRatio");

            //初始化页面数据
            if($("#imageId").val()){
                $.post( $("#contextPath").val()+"/advertising/getAdvertisingById.do", {'advertisingId':$("#imageId").val()}, function (obj) {
                    if(!obj.code){
                        Advertising.checkAdveryisingTypeSelect(obj.data.imageGroupId,form);
                        //表单初始赋值
                        form.val('initAdvertising', {
                            "imageGroupId": obj.data.imageGroupId+""
                            ,"imageOrder": obj.data.imageOrder
                            ,"imageUrl": obj.data.imageUrl
                            ,"imagePath": obj.data.imagePath
                        })
                        Upload.showImgHandle("uploadic",$("#contextPath").val()+"/file/fileDown?fileName="+obj.data.imagePath);
                    }
                })
            }else {
                Advertising.checkAdveryisingTypeSelect(1,form);
            }
            form.on('select(advertisingTypeId)', function(data){
                console.log($(data.elem).find("option[value="+data.value+"]"))
                console.log($(data.elem).find("option[value="+data.value+"]").attr("imgratio"))
                console.log(data)
                $("#imgRatio").val($(data.elem).find("option[value="+data.value+"]").attr("imgratio"))
                // if(data.value == 1){
                //     $("#searchSessionNum").attr("disabled","true");
                //     form.render('select');

                // }else{
                //     $("#searchSessionNum").removeAttr("disabled");
                //     form.render('select');//select是固定写法 不是选择器
                // }
            });

            //表单提交
            form.on('submit(submitAdvertising)', function(data){
                var datas = data.field;
                if($("#imageId").val()){
                    datas.imageId = $("#imageId").val()
                }
                var url = datas.imageId?$("#contextPath").val()+"/advertising/editAdvertising.do":$("#contextPath").val()+"/advertising/addAdvertising.do"
                $.post(url,datas,function(obj){
                    layer.msg(obj.msg);
                    if(!obj.code){
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }
                });
                return false;
            });

        })

    },

    //下拉框内容
    checkAdveryisingTypeSelect:function (typeId,form) {
        $.post( $("#contextPath").val()+"/advertisingType/getAllAdvertisingType.do", {}, function (obj) {
            if(!obj.code){
                $("#imageGroupId").html("");
                for(var i = 0;i<obj.data.length;i++){
                    if(typeId&&typeId==obj.data[i].advertisingTypeId){
                        $("#imgRatio").val(obj.data[i].advertisingTypeImgRatio)
                        $("#imageGroupId").append("<option selected value='"+obj.data[i].advertisingTypeId+"' imgratio='"+obj.data[i].advertisingTypeImgRatio+"'><span>"+obj.data[i].advertisingTypeName+"</span></option>");
                    }else {
                        $("#imageGroupId").append("<option value='"+obj.data[i].advertisingTypeId+"' imgratio='"+obj.data[i].advertisingTypeImgRatio+"'><span>"+obj.data[i].advertisingTypeName+"</span></option>");
                    }
                }
                if(!typeId){
                    $("#imgRatio").val( $("#imageGroupId").find("option[value="+ $("#imageGroupId").val()+"]").attr("imgratio"))
                }
                form.render();
            }
            common.initLang();
        })
    },
    resettable:function (groupId) {
        Advertising.tableIns.reload({
            where:{imageGroupId:groupId}
        });
    }
    
}
Advertising=new advertisingHandle();
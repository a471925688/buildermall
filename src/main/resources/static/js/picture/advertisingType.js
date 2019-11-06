var AdvertisingType;
var advertisingTypeHandle=function () {
    
}
advertisingTypeHandle.prototype={

    initAdvertisingTypeInfo:function () {
        //初始化类型数量
        $.post('advertisingType/getCountAdvertisingType.do', {}, function (obj) {
            if(!obj.code)
                $("#countType b").text(obj.data);
        })
        //初始化類型列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use('table', function() {
            var table = layui.table;
            var tableIns = table.render({
                elem: '#advertisingTypeList'
                , height: $(window).height()-100
                , url: $("#contextPath").val()+'/advertisingType/getAdvertisingTypePageByCont.do' //数据接口
                , title: '類型表'
                , page: true //开启分页
                ,defaultToolbar: [ 'print', 'exports']
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,where: data

                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'advertisingTypeId', title: 'ID', width:100, sort: true, fixed: 'left'}
                    , {field: 'advertisingTypeName', title: '<span class="lang" key="name">類型名稱</span>', align: 'center',  width:160,templet: function (d) {
                            return '<span class="lang" key="'+d.advertisingTypeName+";"+d.advertisingTypeNameEng+'">'+d.advertisingTypeName+'</span>'
                        }}
                    , {field: 'advertisingTypeDescribe', title: '<span class="lang" key="description">描述</span>',  align: 'center',templet: function (d) {
                            return '<span class="lang" key="'+d.advertisingTypeDescribe+";"+d.advertisingTypeDescribeEng+'">'+d.advertisingTypeDescribe+'</span>'
                        }}
                    , {field: 'advertisingTypeNumber', title: '圖片數量',  align: 'center'}
                    , {field: 'advertisingTypeImgRatio', title: '尺寸比列',  align: 'center'}
                    , {field: 'advertisingTypeCreateTime', title: '<span class="lang" key="create_time">创建时间</span>',  align: 'center', sort: true}
                    , {field: 'advertisingTypeUpdateTime', title: '<span class="lang" key="update_time">更新时间</span>',hide:true,  align: 'center',sort: true}
                    , {
                        fixed: 'right', title: '<span class="lang" key="operation">操作</span>',  align: 'center', width:130,templet: function (d) {
                            var html=""
                            //判断是否有编辑权限
                            if(ServiceUtil.checkPermission("advertisingType/editAdvertisingTypeView")){
                                html += "<span><a  href='#' class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            //判断是否有删除权限
                            if(ServiceUtil.checkPermission("advertisingType/delAdvertisingTypeById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                            }
                            return html?html:'<span class="lang" key="no_advertisingType">无权限</span>';
                        }
                    }
                ]]
                ,done: function(res, curr, count){

                    common.initLang();
                }
            });
            $(".layui-table-fixed").hide();
            //监听头工具栏事件
            table.on('toolbar(advertisingTypeList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("advertisingType/editAdvertisingTypeView")) {
                            AdvertisingType.addAdvertisingTypeView();
                        }
                        break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("advertisingType/editAdvertisingTypeView")){
                            break;
                        }
                        else if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            AdvertisingType.editAdvertisingTypeView(data[0].advertisingTypeId);
                        }
                        break;
                    case 'delete':
                        if (!ServiceUtil.checkPermissionAndPopup("/advertisingType/delAllAdvertisingTypeById.do")) {
                            break;
                        }
                        else if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">類型</label> ]: ' + ' <label style="color: blue">' + checkStatus.data[0].advertisingTypeName + '</label>...合计: <label style="color: blue">' + checkStatus.data.length + '个</label>' + ' 吗？', {
                                icon: 3,
                                title: '提示信息'
                            }, function (index) {
                                layer.close(index);
                                var param = {}
                                var data = [];
                                for (var i = 0; i < checkStatus.data.length; i++) {
                                    data.push(checkStatus.data[i].advertisingTypeId);
                                }
                                param.advertisingTypeIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val() + "/advertisingType/delAllAdvertisingTypeById.do", param);
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(advertisingTypeList)', function(obj) {//注：tool 是工具条事件名，advertisingTypeList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">類型</label> ]: '+' <label style="color: blue">'+data.advertisingTypeName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.advertisingTypeId = data.advertisingTypeId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/advertisingType/delAdvertisingTypeById.do",param);
                        });

                        break;
                    }
                    case 'edit':{
                        AdvertisingType.editAdvertisingTypeView(data.advertisingTypeId);
                        break;
                    }
                }
            });
        })
    },
    //弹出添加廣告類型页面
    addAdvertisingTypeView:function(){
        ServiceUtil.layer_show('<span class="lang"  >'+"添加類型"+'</span>',$("#contextPath").val()+'/advertisingType/addAdvertisingTypeView',700)
    },
    //弹出添加廣告類型页面
    editAdvertisingTypeView:function(id){
        ServiceUtil.layer_show('<span class="lang"  >'+"添加類型"+'</span>',$("#contextPath").val()+'/advertisingType/editAdvertisingTypeView?advertisingTypeId='+id,700)
    },
    //初始化添加或修改類型页面信息
    initAddAdvertisingType:function(){
        layui.use(['form','upload'],function() {
            var form = layui.form,
                $ = layui.jquery,
                upload = layui.upload
            // //初始化图标上传
            // Upload.uploadImg(upload, "uploadic", $("#contextPath").val()+"file/uploadImages", {}, "jpg|gif|bmp|png|jpeg");
            //初始化页面数据
            if($("#advertisingTypeId").val()){
                $.post( $("#contextPath").val()+"/advertisingType/getAdvertisingTypeById.do", {'advertisingTypeId':$("#advertisingTypeId").val()}, function (obj) {
                    if(!obj.code){
                        form.val('initAdvertisingType', {
                            "advertisingTypeName": obj.data.advertisingTypeName
                            ,"advertisingTypeNumber": obj.data.advertisingTypeNumber
                            ,"advertisingTypeDescribe": obj.data.advertisingTypeDescribe
                            ,'advertisingTypeImgRatio':obj.data.advertisingTypeImgRatio
                        })
                    }
                })
            }

            form.on('submit(submitAdvertisingType)', function(data){
                var datas = data.field;
                // datas.perIconName=$("#imagePath").val();
                var url = !datas.perId?$("#contextPath").val()+"/advertisingType/addAdvertisingType.do":$("#contextPath").val()+"/advertisingType/editAdvertisingType.do"
                $.post(url,datas,function(obj){
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                    layer.msg(obj.msg);
                });
                return false;
            });
        })
    },
    //添加類型
    addAdvertisingTypeSubmit:function(){
        var param = {
            advertisingTypeName: $("#advertisingTypeName").val(),
            advertisingTypeId: $("#advertisingTypeId").val()?$("#advertisingTypeId").val():null,
            advertisingTypeDescribe:$("#advertisingTypeDescribe").val(),
        }
        var url  = param.advertisingTypeId?$("#contextPath").val()+"/advertisingType/addAdvertisingType.do":$("#contextPath").val()+"/advertisingType/editAdvertisingType.do";
        ServiceUtil.postHandle(layer,url,param);
        history.back()
    },

}
AdvertisingType=new advertisingTypeHandle();
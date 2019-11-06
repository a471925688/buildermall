var ProductType;
var productTypeHandle=function () {

}
productTypeHandle.prototype={
    //初始化产品类型列表
    initProductTypeInfo:function () {
        var data = {};
        data.productTypeParentId=$("#productTypeId").val();
        layui.use('table', function() {
            var table = layui.table;
            var tableIns = table.render({
                elem: '#productTypeList'
                , height: 420
                , url: $("#contextPath").val()+'/productType/getProductTypePageByCont.do' //数据接口
                , title: '产品类型表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'productTypeId', title: 'ID', width:60, sort: true, fixed: 'left'}
                    , {field: 'productTypeName', title: '<span class="lang" key="name"></span>', align: 'center',  width:190,templet: function (d) {
                                return "<span class='lang' key='"+d.productTypeName+";"+d.productTypeNameEng+"'></span>"
                        }}
                    , {field: 'productTypeOrder', title: '<span class="lang" key="sort"></span>',  align: 'center',sort: true}
                     , {field: 'productTypeDescribe', title: '<span class="lang" key="description"></span>', align: 'center',templet: function (d) {
                            return "<span class='lang' key='"+d.productTypeDescribe+";"+d.productTypeDescribeEng+"'></span>"
                        }}
                    , {field: 'productTypeCreateTime', title: '<span class="lang" key="create_time"></span>',  align: 'center', sort: true}
                    , {
                        fixed: 'right', title: '<span class="lang" key="operation"></span>',  align: 'center', width:130,templet: function (d) {
                            var html
                            if(ServiceUtil.checkPermission("/productType/editProductTypeView")){
                                html = "<span><a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\" title=\"編輯\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/productType/delProductTypeById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\" title=\"刪除\">&#xe640;</i></a></span>";
                            }

                            return html?html:'<span class="lang" key="no_permission"></span>';
                        }
                    }
                ]]
                ,done: function(res, curr, count){
                    common.initLang();
                }
            });
            //监听头工具栏事件
            table.on('toolbar(productTypeList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("productType/addProductTypeView")){
                            ProductType.addProductTypeView(0);
                        }

                        break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("productType/editProductTypeView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            ProductType.editProductTypeView(checkStatus.data[0].productTypeId);
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/productType/delAllProductTypeById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">管理员</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].productTypeName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].productTypeId);
                                }
                                param.productTypeIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer,"../productType/delAllProductTypeById.do",param);
                                //刷新父页面
                                parent.location.reload();
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(productTypeList)', function(obj) {//注：tool 是工具条事件名，productTypeList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">产品类型</label> ]: '+' <label style="color: blue">'+data.productTypeName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.productTypeId = data.productTypeId;
                            ServiceUtil.postHandle(layer,"../productType/delProductTypeById.do",param);
                            //刷新父页面
                            parent.location.reload();
                        });

                        break;
                    }
                    case 'edit':{
                        ProductType.editProductTypeView(data.productTypeId);
                        break;
                    }
                }
            });
        })
    },
    //弹出添加产品类型页面
    addProductTypeView:function(isParentId){
        var productTypeParentId;
        //isParentId为假表示添加当前兄弟产品类型,为真表示添加子产品类型
        if(!isParentId){
            productTypeParentId = $("#productTypeId").val();
        }else {
            productTypeParentId = $("#productTypeParentId").val();
        }
        console.log("当前选择的id:"+productTypeParentId);
        ServiceUtil.layer_show('添加产品类型',$("#contextPath").val()+'/productType/addProductTypeView?productTypeParentId='+productTypeParentId,700);
    },
    //初始化产品类型页面
    initAddProductType:function(){
        layui.use(['form','upload'],function() {
            var form = layui.form,
                $ = layui.jquery,
                upload = layui.upload
            // //初始化图标上传
            // Upload.uploadImg(upload, "uploadic", $("#contextPath").val()+"file/uploadImages", {}, "jpg|gif|bmp|png|jpeg");
            //初始化页面数据
            if($("#productTypeId").val()){
                $.post( $("#contextPath").val()+"/productType/getProductTypeById.do", {'productTypeId':$("#productTypeId").val()}, function (obj) {
                    if(!obj.code){
                        form.val('initProductType', {
                            "productTypeParentId": obj.data.productTypeParentId
                            ,"productTypeName": obj.data.productTypeName
                            ,"productTypeNameEng": obj.data.productTypeNameEng
                            ,"productTypePath": obj.data.productTypePath
                            ,"productTypeDescribe": obj.data.productTypeDescribe
                            ,"productTypeDescribeEng": obj.data.productTypeDescribeEng
                            ,"productTypeIsParent": obj.data.productTypeIsParent
                            ,"productTypeIconName": obj.data.productTypeIconName
                            ,"productTypeOrder": obj.data.productTypeOrder,
                            'productTypeLevel':obj.data.productTypeLevel
                        })

                    }
                })
            }

            form.on('submit(submitProductType)', function(data){
                var datas = data.field;
                datas.productTypeId = $("#productTypeId").val()
                // datas.productTypeIconName=$("#imagePath").val();
                var url = !datas.productTypeId?$("#contextPath").val()+"/productType/addProductType.do":$("#contextPath").val()+"/productType/editProductType.do"
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
    //弹出编辑产品类型页面
    editProductTypeView:function(productTypeId){
        ServiceUtil.layer_show('编辑产品类型',$("#contextPath").val()+'/productType/editProductTypeView?productTypeId='+productTypeId,700)
    }
}
ProductType=new productTypeHandle();

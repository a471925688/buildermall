var Permission;
var permissionHandle=function () {
    
}
permissionHandle.prototype={
    //初始化权限列表
    initPermissionInfo:function () {
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use('table', function() {
            var table = layui.table;
            var tableIns = table.render({
                elem: '#permissionList'
                , height: $(window).height()-100
                , url: $("#contextPath").val()+'/permission/getPermissionPageByCont.do' //数据接口
                , title: '权限表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'perId', title: 'ID', width:60, sort: true, fixed: 'left'}
                    , {field: 'perName', title: '<span class="lang" key="module_name">模块名称<span>', align: 'center',  width:120,templet: function (d) {
                            return '<span class="lang" key="'+d.perName+";"+d.perNameEng+'">'+d.perName+'</span>'
                        }}
                    , {field: 'perPath', title: '<span class="lang" key="module_path">模块路径</span>',  align: 'center'}
                    , {field: 'perOrder', title: '<span class="lang" key="sort">排序</span>',  align: 'center',sort: true}
                    , {field: 'perIconName', title: '<span class="lang" key="icon">图标</span>',  align: 'center',templet: function (d) {
                            return "<i class='"+d.perIconName+"'></i>"
                        }}
                    , {field: 'perDescribe', title: '<span class="lang" key="description">描述</span>', align: 'center',templet: function (d) {
                            return '<span class="lang" key="'+d.perDescribe+";"+d.perDescribeEng+'">'+d.perDescribe+'</span>'
                        }}
                    , {field: 'orderCreateTime', title: '<span class="lang" key="create_time">创建时间</span>',  align: 'center', sort: true}
                    , {field: 'perUpdateTime', title: '<span class="lang" key="create_time">更新时间</span>',  align: 'center', sort: true}
                    , {
                        fixed: 'right', title: '<span class="lang" key="operation">操作</span>',  align: 'center', width:130,templet: function (d) {
                            var html
                            if(ServiceUtil.checkPermission("/permission/editPermissionView")){
                                html = "<span><a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/permission/delPermissionById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                            }

                            return html?html:'<span class="lang" key="no_permission">无权限</span>';
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
            table.on('toolbar(permissionList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("permission/addPermissionView")){
                            Permission.addPermissionView(0);
                        }

                        break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("permission/editPermissionView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            Permission.editPermissionView(checkStatus.data[0].perId);
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/permission/delAllPermissionById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">管理员</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].perName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].perId);
                                }
                                param.perIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer,"../permission/delAllPermissionById.do",param);
                                //刷新父页面
                                parent.location.reload();
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(permissionList)', function(obj) {//注：tool 是工具条事件名，permissionList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">权限</label> ]: '+' <label style="color: blue">'+data.perName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.perId = data.perId;
                            ServiceUtil.postHandle(layer,"../permission/delPermissionById.do",param);
                            //刷新父页面
                            parent.location.reload();
                        });

                        break;
                    }
                    case 'edit':{
                        Permission.editPermissionView(data.perId);
                        break;
                    }
                }
            });
        })
    },
    //弹出添加权限页面
    addPermissionView:function(isParentId){
        var perParentId;
        //isParentId为假表示添加当前兄弟权限,为真表示添加子权限
        if(!isParentId){
            perParentId = $("#perId").val();
        }else {
            perParentId = $("#perParentId").val();
        }
        console.log("当前选择的id:"+perParentId);
        ServiceUtil.layer_show('添加权限',$("#contextPath").val()+'/permission/addPermissionView?perParentId='+perParentId)
    },
    //初始化权限页面
    initAddPermission:function(){
        layui.use(['form','upload'],function() {
            var form = layui.form,
                $ = layui.jquery,
                upload = layui.upload
            // //初始化图标上传
            // Upload.uploadImg(upload, "uploadic", $("#contextPath").val()+"file/uploadImages", {}, "jpg|gif|bmp|png|jpeg");
            //初始化页面数据
            if($("#perId").val()){
                $.post( $("#contextPath").val()+"/permission/getPermissionById.do", {'perId':$("#perId").val()}, function (obj) {
                    if(!obj.code){
                        form.val('initPermission', {
                            "perParentId": obj.data.perParentId
                            ,"perName": obj.data.perName
                            ,"perNameEng": obj.data.perNameEng
                            ,"perPath": obj.data.perPath
                            ,"perDescribe": obj.data.perDescribe
                            ,"perDescribeEng": obj.data.perDescribeEng
                            ,"perIsParent": obj.data.perIsParent
                            ,"perIconName": obj.data.perIconName
                            ,"perOrder": obj.data.perOrder


                        })
                    }
                })
            }

            form.on('submit(submitPermission)', function(data){
                var datas = data.field;
                // datas.perIconName=$("#imagePath").val();
                var url = !datas.perId?$("#contextPath").val()+"/permission/addPermission.do":$("#contextPath").val()+"/permission/editPermission.do"
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
    //弹出编辑权限页面
    editPermissionView:function(perId){
        ServiceUtil.layer_show('编辑权限',$("#contextPath").val()+'/permission/editPermissionView?perId='+perId)
    }
}
Permission=new permissionHandle();
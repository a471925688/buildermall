var Brand;
var brandHandle=function () {

}
brandHandle.prototype={
    //初始化品牌页面
    initBrandView:function () {
        //初始化品牌列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use(['table','form'], function() {
            var table = layui.table
                ,form=layui.form;
            var tableIns = table.render({
                elem: '#brandList'
                , height: $(window).height()-100
                , url: $("#contextPath").val()+'/brand/getBrandPageByCont.do' //数据接口
                , title: '品牌表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,defaultToolbar: [ 'print', 'exports']
                ,where: data
                ,cols: [[ //表头
                    { type: 'checkbox'}
                    ,{field: 'brandId', title: 'ID',  sort: true}
                    , {field: 'brandName', title: '<span class="lang" key="name_Chinese"></span>',   align: 'center', templet: function (d) {
                            return  "<a   class='lang' key='"+d.brandName+";"+d.brandNameEng+"' href='#' lay-event='details' style='color: #428bca'></a>";
                        }}
                    , {field: 'brandLogoPath', title: '<span class="lang" key="icon"></span>',  align: 'center',templet: function (d) {
                            return "<img src='"+$("#contextPath").val()+"/file/fileDown?fileName="+d.brandLogoPath+"' onclick='Upload.previewImg(\"#showImg"+d.brandId+"\")' id='showImg"+d.brandId+"' style='width: 100px;height: 80px'>"
                        }}
                    , {field: 'brandCountry', title: '<span class="lang" key="country"></span>',  align: 'center'}
                    , {field: 'brandCreateTime', title: '<span class="lang" key="create_time"></span>', hide:true, align: 'center'}
                    , {
                        fixed: 'right',title: '<span class="lang" key="operation"></span>',  align: 'center', width:120,templet: function (d) {
                            var html = "";
                            if(ServiceUtil.checkPermission("/brand/editBrandView")){
                                html += "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/brand/delBrandById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                            }
                            return html?html:"<span class='lang' key='no_permission'></span>";
                        }
                    }
                ]]
                ,done: function(res, curr, count){
                    common.initLang();
                }
            });
            // $(".layui-table-fixed").hide();
            // //监听指定开关
            // form.on('switch(switchTest)', function(data){
            //     if(!ServiceUtil.checkPermissionAndPopup("/brand/editBrandIsProhibit.do")){
            //        return;
            //     }
            //     var param = {
            //         brandIsProhibit:data.othis.text()=='禁用'?false:true,
            //         brandId:data.elem.value
            //     }
            //     $.post($("#contextPath").val()+"/brand/editBrandIsProhibit.do", param, function (obj){
            //
            //     })
            // });

            //监听头工具栏事件
            table.on('toolbar(brandList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("/brand/addBrandView")){
                            Brand.addBrandView();
                        }break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("/brand/editBrandView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            Brand.addBrandView();
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/brand/delAllBrandById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">品牌</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].brandRealName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].brandId);
                                }
                                param.brandIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val()+"/brand/delAllBrandById.do",param);
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(brandList)', function(obj) {//注：tool 是工具条事件名，brandList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">品牌</label> ]: '+' <label style="color: blue">'+data.brandRealName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.brandId = data.brandId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/brand/delBrandById.do",param);
                        });
                        break;
                    }
                    case "edit":{
                        Brand.editBrandView(data.brandId);
                    }
                }
            });

            //表单提交
            form.on('submit(searchBrand)', function(data){
                var datas={};
                var brandCreateTime = $("input[name=brandCreateTime]").val();
                var brandName = $("input[name=brandName]").val();
                if(brandCreateTime)
                    datas.brandCreateTime = brandCreateTime;
                if(brandName)
                    datas.brandName = brandName;

                tableIns.reload({
                    where:datas
                })
                return false;
            });
        })
    },
    //弹出添加品牌页面
    addBrandView:function(){
        ServiceUtil.layer_show(common.getDataByKey("add_brand"),$("#contextPath").val()+'/brand/addBrandView',700)
    },
    //弹出编辑品牌页面
    editBrandView:function(brandId){
        ServiceUtil.layer_show(common.getDataByKey("edit_brand"),$("#contextPath").val()+'/brand/editBrandView?brandId='+brandId,700)
    },
    //初始化添加或修改品牌页面信息
    initAddOrUpdateBrand:function(){
        layui.use(['form', 'layedit','upload'], function() {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                ,upload=layui.upload


            //初始化图标上传
            Upload.uploadImg(upload, "uploadic", $("#contextPath").val()+"/file/uploadImages", {}, "jpg|gif|bmp|png|jpeg");
            //初始化页面数据
            if($("#brandId").val()){
                $.post( $("#contextPath").val()+"/brand/getBrandById.do", {'brandId':$("#brandId").val()}, function (obj) {
                    if(!obj.code){
                        //表单初始赋值
                        Brand.checkASupperSelect(obj.data.adminId,form);
                        form.val('initBrand', {
                            "brandName": obj.data.brandName
                            ,"brandNameEng": obj.data.brandNameEng
                            ,'brandCountry':obj.data.brandCountry
                            ,"imagePath": obj.data.brandLogoPath
                            ,'brandDescribe': obj.data.brandDescribe
                        })
                        Upload.showImgHandle("uploadic",$("#contextPath").val()+"/file/fileDown?fileName="+obj.data.brandLogoPath);
                    }
                })
            }else {
                Brand.checkASupperSelect(null,form);
            }
            form.on('radio(brandTypeRadio)', function(data){
                Brand.checkRoleSelect(data.value,form);
            });

            //表单提交
            form.on('submit(submitBrand)', function(data){
                var datas = data.field;
                var url = datas.brandId?$("#contextPath").val()+"/brand/editBrand.do":$("#contextPath").val()+"/brand/addBrand.do"
                datas.brandLogoPath=$("#imagePath").val()
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
    checkASupperSelect:function (id,form) {
        $.post( $("#contextPath").val()+"/admin/getAllAdminByCont.do", {adminType:2}, function (obj) {
            if(!obj.code){
                $("#admin").html("");
                for(var i = 0;i<obj.data.length;i++){
                    if(id){
                        if(id&&id==obj.data[i].adminId){
                            $("#admin").append("<option selected value='"+obj.data[i].adminId+"'><span>"+obj.data[i].adminRealName+"</span></option>");
                        }
                    }else {
                        $("#admin").append("<option value='"+obj.data[i].adminId+"'><span>"+obj.data[i].adminRealName+"</span></option>");
                    }

                }
                form.render();
            }
            common.initLang();
        })
    }

}
Brand=new brandHandle();
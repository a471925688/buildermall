var Admin;
var adminHandle=function () {

}
adminHandle.prototype={
    //初始化管理员页面
    initAdminView:function () {
        //初始化管理员数量
        $.post($("#contextPath").val()+'/admin/getCountAdmin.do', {}, function (obj) {
            if(!obj.code)
                $("#countType b").text(obj.data);
        })
        //初始化左侧权限列表
        $.post($("#contextPath").val()+'/admin/getAllCountGroupByRoleId.do', {}, function (obj) {
            if(!obj.code){
                var count = 0;
                for(var i=0;i<obj.data.length;i++){
                    count+=obj.data[i][1];
                    $(".b_P_Sort_list").append("<li><i class=\"fa fa-users orange\"></i> <a href=\"#\"><span class='lang' key='"+obj.data[i][0]+";"+obj.data[i][2]+"'></span>（"+obj.data[i][1]+"）</a></li>");
                }
                $(".b_P_Sort_list a").eq(0).html("<span class='lang' key='all_administrators'>"+common.getDataByKey("all_administrators")+"</span>（"+count+"）");
            }
        })
        //初始化管理员列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use(['table','form'], function() {
            var table = layui.table
                ,form=layui.form;
            var tableIns = table.render({
                elem: '#adminList'
                , height: $(window).height()-120
                , url: $("#contextPath").val()+'/admin/getAdminPageByCont.do' //数据接口
                , title: '管理员表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,defaultToolbar: [ 'print', 'exports']
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'adminId', title: 'ID', width:80, sort: true, fixed: 'left'}
                    , {field: 'adminType', title: '<span class="lang" key="administrator_type">管理员类型<span></span>', align: 'center',  width:150,templet: function (d) {
                            switch (d.adminType) {
                                case 1:return "<span class='lang' key='administrators'>管理员</span>";
                                case 2:return "<span class='lang' key='supplier'>供应商</span>";
                            }
                        }}
                    , {field: 'role', title: '<span class="lang" key="role_name">角色名称</span>',  align: 'center', sort: true,templet: function (d) {
                            return '<span class="lang" key="'+d.role.roleName+";"+d.role.roleNameEng+'">'+d.role.roleName+'</span>'
                        }}
                    , {field: 'login', title: '<span class="lang" key="login_name">登录账号</span>',  align: 'center',templet: function (d) {
                            return d.login.loginUserName
                        }}
                    , {field: 'adminRealName', title: '<span class="lang" key="real_name">姓名</span>',  align: 'center'}
                    , {field: 'adminGender', title: '<span class="lang" key="gender">性别<span></span>',  align: 'center',templet: function (d) {
                            return "<span class='lang' key='"+d.adminGender+"'>"
                        }}
                    , {field: 'adminPhone', title: '<span class="lang" key="phone">电话</span>',  align: 'center'}
                    , {field: 'adminEmail', title: '<span class="lang" key="email">邮箱</span>',  align: 'center'}
                    , {field: 'adminAddress', title: '<span class="lang" key="address">地址</span>',  align: 'center'}
                    , {field: 'adminCreateTime', title: '<span class="lang" key="create_time">创建时间</span>',  align: 'center'}
                    , {
                        fixed: 'right', title: '<span class="lang" key="state">状态</span>', align: 'center', width: 120, templet: function (d) {
                            if(ServiceUtil.checkPermission("/admin/editAdminIsProhibit.do")){
                                if (d.adminIsProhibit) {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.adminId + "' lay-text='"+common.getDataByKey("disable_enable")+"'></a>";
                                } else {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\"checked=\"\" name=\"open\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.adminId + "' lay-text=\'"+common.getDataByKey("disable_enable")+"'></a>";
                                }
                            }else {
                                if (d.adminIsProhibit){
                                    return "<span class=\"lang\" key=\"prohibit\">禁用</span>"
                                }else {
                                    return "<span class=\"lang\" key=\"enable\">启用</span>"
                                }
                            }
                        }
                    }
                    , {
                        fixed: 'right', title: '<span class="lang" key="operation">操作</span>',  align: 'center', width:120,templet: function (d) {
                            var html = "";
                            if(ServiceUtil.checkPermission("/admin/editAdminView")){
                                html += "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/admin/delAdminById.do")){
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
            //监听指定开关
            form.on('switch(switchTest)', function(data){
                if(!ServiceUtil.checkPermissionAndPopup("/admin/editAdminIsProhibit.do")){
                   return;
                }

                var param = {
                    adminIsProhibit:!data.elem.checked,
                    adminId:data.elem.value
                }
                $.post($("#contextPath").val()+"/admin/editAdminIsProhibit.do", param, function (obj){

                })
            });
            //监听头工具栏事件
            table.on('toolbar(adminList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("/admin/addAdminView")){
                            Admin.addAdminView();
                        }break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("/admin/editAdminView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            Admin.addAdminView();
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/admin/delAllAdminById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">管理员</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].adminRealName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].adminId);
                                }
                                param.adminIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val()+"/admin/delAllAdminById.do",param);
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(adminList)', function(obj) {//注：tool 是工具条事件名，adminList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">管理员</label> ]: '+' <label style="color: blue">'+data.adminRealName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.adminId = data.adminId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/admin/delAdminById.do",param);
                        });
                        break;
                    }
                    case "edit":{
                        Admin.editAdminView(data.adminId);
                    }
                }
            });
        })
    },
    //弹出添加管理员页面
    addAdminView:function(){
        ServiceUtil.layer_show('<span class="lang" key="add_administrator">'+common.getDataByKey("add_administrator")+'</span>',$("#contextPath").val()+'/admin/addAdminView',700)
    },
    //弹出编辑管理员页面
    editAdminView:function(adminId){
        ServiceUtil.layer_show('<span class="lang" key="editorial_administrator">'+common.getDataByKey("editorial_administrator")+'</span>',$("#contextPath").val()+'/admin/editAdminView?adminId='+adminId,700)
    },
    //初始化添加或修改管理员页面信息
    initAddOrUpdateAdmin:function(){
        layui.use(['form', 'layedit'], function() {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit

            //自定义验证规则
            form.verify({
                pass: [/(.+){6,12}$/, '密码必须6到12位']
            });

            //初始化页面数据
            if($("#adminId").val()){
                $.post( $("#contextPath").val()+"/admin/getAdminById.do", {'adminId':$("#adminId").val()}, function (obj) {
                    if(!obj.code){
                        Admin.checkRoleSelect(obj.data.adminType,form);
                        //表单初始赋值
                        form.val('initAdmin', {
                            "loginUserName": obj.data.login.loginUserName
                            ,"loginPassWord": "******"
                            ,"roleId": obj.data.role.roleId
                            ,"adminRealName": obj.data.adminRealName
                            ,"adminPhone": obj.data.adminPhone
                            ,"adminEmail": obj.data.adminEmail
                            ,"adminAddress": obj.data.adminAddress
                            ,'adminGender':obj.data.adminGender
                            ,"adminType": obj.data.adminType+""
                        })
                    }
                })
            }else {
                Admin.checkRoleSelect(1,form);
            }
            form.on('radio(adminTypeRadio)', function(data){
                Admin.checkRoleSelect(data.value,form);
            });

            //表单提交
            form.on('submit(submitAdmin)', function(data){
                var datas = data.field;
                var url = datas.adminId?$("#contextPath").val()+"/admin/editAdmin.do":$("#contextPath").val()+"/admin/addAdmin.do"
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

    //改变角色下拉框内容
    checkRoleSelect:function (roleType,form) {
        $.post( $("#contextPath").val()+"/role/getAllRoleByCont.do", {'roleType':roleType}, function (obj) {
            if(!obj.code){
                $("#roleSelect").html("");
                for(var i = 0;i<obj.data.length;i++){
                    $("#roleSelect").append("<option value='"+obj.data[i].roleId+"'><span>"+common.getDataByKey(obj.data[i].roleName+";"+obj.data[i].roleNameEng)+"</span></option>");
                }
                form.render();
            }
            common.initLang();
        })
    }
}
Admin=new adminHandle();
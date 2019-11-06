var Role;
var roleHandle=function () {
    
}
roleHandle.prototype={

    initRoleInfo:function () {
        //初始化类型数量
        $.post('role/getCountRole.do', {}, function (obj) {
            if(!obj.code)
                $("#countType b").text(obj.data);
        })
        //初始化角色列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use('table', function() {
            var table = layui.table;
            var tableIns = table.render({
                elem: '#roleList'
                , height: $(window).height()-100
                , url: $("#contextPath").val()+'/role/getRolePageByCont.do' //数据接口
                , title: '角色表'
                , page: true //开启分页
                ,defaultToolbar: [ 'print', 'exports']
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,where: data

                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'roleId', title: 'ID', width:100, sort: true, fixed: 'left'}
                    , {field: 'roleName', title: '<span class="lang" key="role_name">角色名稱</span>', align: 'center',  width:160,templet: function (d) {
                            return '<span class="lang" key="'+d.roleName+";"+d.roleNameEng+'">'+d.roleName+'</span>'
                        }}
                    , {field: 'roleDescribe', title: '<span class="lang" key="description">描述</span>',  align: 'center',templet: function (d) {
                            return '<span class="lang" key="'+d.roleDescribe+";"+d.roleDescribeEng+'">'+d.roleDescribe+'</span>'
                        }}
                    , {field: 'roleCreateTime', title: '<span class="lang" key="create_time">创建时间</span>',  align: 'center', sort: true}
                    , {field: 'roleUpdateTime', title: '<span class="lang" key="update_time">更新时间</span>',  align: 'center',sort: true}
                    , {
                        fixed: 'right', title: '<span class="lang" key="operation">操作</span>',  align: 'center', width:130,templet: function (d) {
                            var html=""
                            //判断是否有编辑权限
                            if(ServiceUtil.checkPermission("role/editRoleView")){
                                html += "<span><a  href='"+$("#contextPath").val()+"/role/editRoleView?roleId="+d.roleId+"' class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            //判断是否有删除权限
                            if(ServiceUtil.checkPermission("role/delRoleById.do")){
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
            $(".layui-table-fixed").hide();
            //监听头工具栏事件
            table.on('toolbar(roleList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("role/editRoleView")) {
                            window.location.href=$("#contextPath").val()+"/role/addRoleView";
                        }
                        break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("role/editRoleView")){
                            break;
                        }
                        else if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            window.location.href=$("#contextPath").val()+"/role/editRoleView?roleId="+data[0].roleId;break;
                        }
                        break;
                    case 'delete':
                        if (!ServiceUtil.checkPermissionAndPopup("/role/delAllRoleById.do")) {
                            break;
                        }
                        else if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">管理员</label> ]: ' + ' <label style="color: blue">' + checkStatus.data[0].roleName + '</label>...合计: <label style="color: blue">' + checkStatus.data.length + '个</label>' + ' 吗？', {
                                icon: 3,
                                title: '提示信息'
                            }, function (index) {
                                layer.close(index);
                                var param = {}
                                var data = [];
                                for (var i = 0; i < checkStatus.data.length; i++) {
                                    data.push(checkStatus.data[i].roleId);
                                }
                                param.roleIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val() + "/role/delAllRoleById.do", param);
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(roleList)', function(obj) {//注：tool 是工具条事件名，roleList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">角色</label> ]: '+' <label style="color: blue">'+data.roleName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.roleId = data.roleId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/role/delRoleById.do",param);
                        });

                        break;
                    }
                    // case 'edit':{
                    //     Role.editRoleView(data.roleId);
                    //     break;
                    // }
                }
            });
        })
    },
    //初始化添加或修改角色页面信息
    initAddOrUpdateRole:function(){
        //初始化页面数据
        if($("#roleId").val()){
            $.post( $("#contextPath").val()+"/role/getRoleById.do", {'roleId':$("#roleId").val()}, function (obj) {
                if(!obj.code){
                    $("#roleName").val(obj.data.roleName)
                    $("#roleNameEng").val(obj.data.roleNameEng)
                    $("#roleDescribe").val(obj.data.roleDescribe)
                    $("#roleDescribeEng").val(obj.data.roleDescribeEng)
                    $("input[value="+obj.data.roleType+"]").click();
                    Role.initPermission(obj.data.permissions);
                    $("#submitRoletype").text("保存修改")
                }
            })
        }else {
            Role.initPermission();
        }



    },
    //添加角色
    addRoleSubmit:function(){
        var datas=[];
        $("#roleAllot input:checked").each(function () {
            datas.push(parseInt($(this).attr("id")));
        })
        var param = {
            roleName: $("#roleName").val(),
            roleNameEng:$("#roleNameEng").val(),
            roleId: $("#roleId").val()?$("#roleId").val():null,
            roleType:  $('input[name="roleType"]:checked ').val(),
            roleDescribe:$("#roleDescribe").val(),
            roleDescribeEng:$("#roleDescribeEng").val(),
            perIds: datas
        }
        var url  = datas.roleId?$("#contextPath").val()+"/role/addRole.do":$("#contextPath").val()+"/role/editRole.do";
        ServiceUtil.postHandle(layer,url,param);
        history.back()
    },
    /*編輯框字数限制*/
    checkLength:function (which) {
        var maxChars = 200; //
        if (which.value.length > maxChars) {
            layer.open({
                icon: 2,
                title: '提示框',
                content: '您出入的字数超多限制!',
            });
            // 超过限制的字数了就将 文本框中的内容按规定的字数 截取
            which.value = which.value.substring(0, maxChars);
            return false;
        } else {
            var curr = maxChars - which.value.length; //250 减去 当前输入的
            document.getElementById("sy").innerHTML = curr.toString();
            return true;
        }
    },

    //初始化分配權限中的所有權限
    initPermission:function (permissions) {
        $.post($("#contextPath").val()+"/permission/getAllPermissonOrderByLv.do", {}, function (obj) {
            if(!obj.code){
                var dl1html = "<dl class=\"permission-list\"></dl>";
                var dtHtml="\t<dt><label class=\"middle\"><input name=\"admin-Character-0\" class=\"ace\" id='inputPerId' type=\"checkbox\" ><span class=\"lbl lang\" key='perName;perNameEng'>perName</span></label></dt><dd id='perId'></dd>"
                for (var i=0;i<obj.data.length;i++){
                    var data = obj.data[i];
                    var id = "permission-Character-"+data.perId;
                    var html = dtHtml;
                    html = html.replace("perId",id).replace(/perNameEng/,data.perNameEng).replace(/perName/g,data.perName);
                    html = html.replace("inputPerId",data.perId);
                    var docm;
                    switch (data.perLevel) {
                        case 1:{
                            docm  =   $("#roleAllot .Select_Competence");
                            docm.append(dl1html);
                            docm.children().last().append(html);
                            break;
                        }
                        case 2:{
                            docm =  $("#permission-Character-"+data.perParentId);
                            dl1html = " <dl class='cl permission-list"+data.perLevel+"'>";
                            docm.append(dl1html);
                            docm.children().last().append(html);
                            break;
                        }
                        case 3:{
                            docm =  $("#permission-Character-"+data.perParentId);
                            dtHtml = "<label class=\"middle\"><input type=\"checkbox\" value=\"\" class=\"ace\" name=\"admin-Character-0-0-0\" id='"+data.perId+"'><span class=\"lbl lang\" key='"+data.perName+";"+data.perNameEng+"'></span></label>";
                            docm.append(dtHtml);
                            break;
                        }
                    }
                }
                common.initLang();
            }
            //处理按钮事件
            $(".permission-list dt input:checkbox").bind("click",(function(){
                $(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
                if($(this).parents("dd").length){
                    var checkedlength=$(this).parents("dd").find("dl dt input:checked").length;
                    if(checkedlength>0){
                        $(this).parents(".permission-list").find("dt").eq(0).find("input:checkbox").prop("checked",true);
                    }else {
                        $(this).parents(".permission-list").find("dt").eq(0).find("input:checkbox").prop("checked",false);
                    }

                }
            }));
            $(".permission-list2 dd input:checkbox").click(function(){
                var l =$(this).parent().parent().find("input:checked").length;
                var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
                if($(this).prop("checked")){
                    $(this).closest("dl").find("dt input:checkbox").prop("checked",true);
                    $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
                }
                else{
                    if(l==0){
                        $(this).closest("dl").find("dt input:checkbox").prop("checked",false);
                    }
                    if(l2==0){
                        $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
                    }
                }
            });
            //初始化选中状态
            if(permissions){
                permissions.forEach(function (permission) {
                    $("#"+permission.perId).prop("checked",true);
                })
            }
        });

    }
}
Role=new roleHandle();
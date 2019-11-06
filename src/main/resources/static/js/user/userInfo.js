var UserInfo;
var userHandle=function () {

}
userHandle.prototype={
    //送货地址的表格对象
    addrsTable:'',
    formSelects:{},
    //初始化用户页面
    initUserView:function () {
        //初始化用户列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use(['table','form','laydate'], function() {
            var table = layui.table
                ,form=layui.form
                ,laydate=layui.laydate;
            var tableIns = table.render({
                elem: '#userList'
                , height: 420
                , url: $("#contextPath").val()+'/user/getUserPageByCont.do' //数据接口
                , title: '用户表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,defaultToolbar: [ 'print', 'exports']
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'userId', title: 'ID', width:100, sort: true, fixed: 'left'}
                    , {field: 'login', title: '<span class="lang" key="login_name">登录账号</span>',  align: 'center',templet: function (d) {
                            return d.login.loginUserName
                        }}
                    , {
                        field: 'userRealName', title: '<span class="lang" key="real_name">姓名</span>', align: 'center', templet: function (d) {
                            return  "<a   class='' href='#' lay-event='details' style='color: #428bca'>"+d.userRealName+"</a>";
                        }
                    }
                    , {field: 'userGender', title: '<span class="lang" key="gender">性别</span>', sort: true, align: 'center'}
                    , {field: 'userPhone', title: '<span class="lang" key="phone">电话</span>',  align: 'center'}
                    , {field: 'userEmail', title: '<span class="lang" key="email">邮箱</span>',  align: 'center'}
                    , {field: 'userAddress', title: '<span class="lang" key="address">家庭住址</span>',  align: 'center'}
                    , {field: 'userCreateTime', title: '<span class="lang" key="create_time">创建时间</span>', sort: true, align: 'center'}
                    , {
                        fixed: 'right', title: '<span class="lang" key="state">状态</span>', align: 'center', width: 100,sort: true, templet: function (d) {
                            if(ServiceUtil.checkPermission("/user/editUserIsProhibit.do")){
                                if (d.userIsProhibit) {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.userId + "' lay-text='"+common.getDataByKey("disable_enable")+"'></a>";
                                } else {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\"checked=\"\" name=\"open\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.userId + "' lay-text=\'"+common.getDataByKey("disable_enable")+"'></a>";
                                }
                            }else {
                                if (d.userIsProhibit){
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
                            if(ServiceUtil.checkPermission("/user/editUserView")){
                                html += "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/user/delUserById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                            }
                            return html?html:"<span class=\"lang\" key=\"no_permission\">无权限</span>";
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
                if(!ServiceUtil.checkPermissionAndPopup("/user/editUserIsProhibit.do")){
                   return;
                }
                var param = {
                    userIsProhibit:!data.elem.checked,
                    userId:data.elem.value
                }
                $.post($("#contextPath").val()+"/user/editUserIsProhibit.do", param, function (obj){

                })
            });
            //监听头工具栏事件
            table.on('toolbar(userList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("/user/addUserView")){
                            UserInfo.addUserView();
                        }break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("/user/editUserView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            UserInfo.addUserView();
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/user/delAllUserById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">用户</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].userRealName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){

                                    data.push(checkStatus.data[i].userId);
                                }
                                param.userIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val()+"/user/delAllUserById.do",param);
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(userList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">用户</label> ]: '+' <label style="color: blue">'+data.userRealName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.userId = data.userId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/user/delUserById.do",param);
                        });
                        break;
                    }
                    case "edit":{
                        UserInfo.editUserView(data.userId);break;
                    }
                    case "details":{
                        ServiceUtil.layer_show(data.userRealName,$("#contextPath").val()+'/user/detailsUserView?userId='+data.userId,500,400);break;
                    }
                }
            });
        })
    },
    //弹出添加用户页面
    addUserView:function(){
        location.href=$("#contextPath").val()+'/user/addUserView';
        // ServiceUtil.layer_show('添加用户',$("#contextPath").val()+'/user/addUserView',600)
    },
    //弹出编辑用户页面
    editUserView:function(userId){
        location.href=$("#contextPath").val()+'/user/editUserView?userId='+userId;
        // ServiceUtil.layer_show('编辑用户',$("#contextPath").val()+'/user/editUserView?userId='+userId,600)
    },
    //初始化添加或修改用户页面信息
    initAddOrUpdateUser:function(){
        layui.config({
            base: $("#contextPath").val()+'/layui/' //此处路径请自行处理, 可以使用绝对路径
        }).extend({
            formSelects: 'formSelects-v4'
        }).use(['form', 'layedit','selectN','table','formSelects'], function() {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                ,formSelects = layui.formSelects
                ,table=layui.table
                ,selectN = layui.selectN;
            var tableData = [];
            UserInfo.formSelects = formSelects;

            var isInitPlace = false;//判斷地址下拉框是否已經渲染完
            var initPlaceData = false;//用於初始化地址下拉框的數據

            //初始化页面数据
            if($("#userId").val()){
                $.ajaxSettings.async = false;
                $.post( $("#contextPath").val()+"/user/getUserById.do", {'userId':$("#userId").val()}, function (obj) {
                    if(!obj.code){
                        //表单初始赋值
                        form.val('initUser', {
                            "loginUserName": obj.data.login.loginUserName
                            ,"loginPassWord": "******"
                            ,"userRealName": obj.data.userRealName
                            ,"userPhone": obj.data.userPhone
                            ,"userEmail": obj.data.userEmail
                            ,"userAddress": obj.data.userAddress
                            ,'userGender':obj.data.userGender
                            ,"userType": obj.data.userType+""
                        })
                        tableData=obj.data.receivingInfos;
                    }
                })

                $.ajaxSettings.async = true;
            }
            //初始化收货地址
            UserInfo.addrsTable = table.render({
                elem: '#addUserList'
                ,data:tableData
                ,cellMinWidth: 80
                ,toolbar: '#toolbarDemo'
                ,defaultToolbar: [ 'print', 'exports']
                ,text: {
                    none: '<span><a class="layui-btn layui-btn-danger layui-btn-xs lang" key="add_receiving_address" lay-event="addAddr" onclick="UserInfo.addReceivingInfo();">添加收货地址</a></span>' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
                }
                ,cols: [[ //表头
                    {field: 'recCompany', title: '<span class="lang" key="company">公司</span>', edit:true, align: 'center'}
                    ,{field: 'recName', title: '<span class="lang" key="real_name">收货人姓名</span>', edit:true, align: 'center'}
                    , {field: 'recPhome', title: '<span class="lang" key="phone">收货人电话</span>',edit:true, align: 'center'}
                    , {field: 'recAddr', title: '<span class="lang" key="region">收貨地區</span>',align: 'center',templet: function (d) {
                        var placeId = d.recAddr.substring(d.recAddr.lastIndexOf("/")+1);
                            return ServiceUtil.postAsyncHandle($("#contextPath").val()+"/place/getPlaceByPlaceId.do",{placeId:placeId}).details;
                        }}
                    , {field: 'recDetailedAddr', title: '<span class="lang" key="detailed_address">详细地址</span>', edit:true,align: 'center'}
                    , {field: 'recRemarks', title: '<span class="lang" key="remarks">备注</span>', edit:true, align: 'center'}
                    , {field: 'recCreateTime', title: '<span class="lang" key="create_time">创建时间</span>', sort: true,hide:true, align: 'center'}
                    , {
                        fixed: 'right', title: '<span class="lang" key="operation">操作</span>',  align: 'center', width:120,templet: function (d) {

                          return "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a><a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
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
            //自定义验证规则
            form.verify({
                pass: [/(.+){6,12}$/, '密码必须6到12位']
            });

            //监听头工具栏事件
            table.on('toolbar(addUserList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'addReceivingInfo':
                        UserInfo.addReceivingInfo();
                        break;
                };
            });
            table.on('tool(addUserList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">收货人</label> ]: '+' <label style="color: blue">'+data.recName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                        });
                        break;
                    }
                }
            });
            //表单提交
            form.on('submit(submitUser)', function(data){
                var data = data.field;
                data.jsonReceivingInfos = table.cache.addUserList;
                data.jsonReceivingInfos = JSON.stringify(data.jsonReceivingInfos);
                var url = data.userId?$("#contextPath").val()+"/user/editUser.do":$("#contextPath").val()+"/user/addUser.do"
                $.post(url,data,function(obj){
                    layer.msg(obj.msg);
                    if(!obj.code){
                        javascript :history.back(-1);
                    }

                });
                return false;
            });
        })

    },
    //添加收货地址
    addReceivingInfo:function () {
        layer.open({
            type: 1,
            title: "<span class='lang' key='add_receiving_address'>添加收货地址</span>",
            shadeClose: true,
            shade: 0.4,
            area: [$(window).width()*0.8+"px", ($(window).height()-220)+"px"],
            content: $("#addReceivingInfo").html(),
            btn: ['提交','返回'],
            yes: function(index,layero){
                //当点击‘确定’按钮的时候，获取弹出层返回的值
                console.log(layero);
                var data = layui.table.cache.addUserList;
                var newdata = {
                    recCompany:layero.find("#recCompany").val(),
                    recName:layero.find("#recName").val(),
                    recPhome:layero.find("#recPhome").val(),
                    // recRecCountry:layero.find("#recRecCountry").val(),
                    // recRegion:layero.find("#recRegion").val(),
                    // recCity:layero.find("#recCity").val(),
                    recAddr:UserInfo.formSelects.value('recAddr', 'val')[0],
                    recDetailedAddr:layero.find("#recDetailedAddr").val(),
                    recRemarks:layero.find("#recRemarks").val(),
                    recCreateTime:ServiceUtil.curentTime()
                }
                data.push(newdata);
                console.log(data);
                UserInfo.addrsTable.reload({
                    data:data
                })
                //打印返回的值，看是否有我们想返回的值。
                //最后关闭弹出层
                layer.close(index);
            },
            cancel: function(){
                //右上角关闭回调
            }
        });
        var isInitPlace = false;//判斷地址下拉框是否已經渲染完
        var initPlaceData = false;//用於初始化地址下拉框的數據
        UserInfo.formSelects.config('recAddr', {
            keyName: 'nameTc',            //自定义返回数据中name的key, 默认 name
            keyVal: 'id',            //自定义返回数据中value的key, 默认 value
            keyChildren: 'places',    //联动多选自定义children
            delay: 500,                 //搜索延迟时间, 默认停止输入500ms后开始搜索
            direction: 'auto',          //多选下拉方向, auto|up|down
            response: {
                statusCode: 0,          //成功状态码
                statusName: 'code',     //code key
                msgName: 'msg',         //msg key
                dataName: 'data'        //data key
            },
            success: function(id, url, searchVal, result){      //使用远程方式的success回调
                isInitPlace = true;
                UserInfo.formSelects.value('recAddr',initPlaceData, true);
            },
        }).data('recAddr', 'server', {
            url: $("#contextPath").val()+"/place/getAllProvincePlace.do",
            linkage: true,
            linkageWidth: 130
        });
    }
    // //初始化添加收货地址信息
    // initAddReceivingInfo:function () {
    //     layui.extend({selectN: '../layui/selectN',}).use(['form', 'selectN'], function() {
    //         var form = layui.form
    //             , layer = layui.layer
    //             , selectN = layui.selectN;
    //
    //         var catData = [{"id":1,"name":"周边旅游","children":[{"id":24,"name":"广东","status":0,"children":[{"id":7,"name":"广州"},{"id":23,"name":"潮州"}]}]},{"id":5,"name":"国内旅游","children":[{"id":8,"name":"华北地区","children":[{"id":9,"name":"北京"}]}]},{"id":6,"name":"出境旅游","children":[{"id":10,"name":"东南亚","children":[{"id":11,"name":"马来西亚","children":[{"id":20,"name":"沙巴","children":[{"id":21,"name":"美人鱼岛","children":[{"id":22,"name":"潜水"}]}]}]}]}]}];
    //         // 无限级分类-基本配置
    //         var catIns1 = selectN({
    //             //元素容器【必填】
    //             elem: '#cat_ids1'
    //             ,search:[false,true]
    //             //候选数据【必填】
    //             ,data: catData
    //             //数据的键名
    //             ,tips: ['请选择省','请选择市','请选择县']
    //             //数据的键名
    //             ,field:{idName:'id',titleName:'name',childName:'children'}
    //             //数据的键名
    //             ,field:{idName:'id',titleName:'name',childName:'children'}
    //         });
    //         // var catIns2 = selectN({
    //         //     //元素容器【必填】
    //         //     elem: '#cat_ids1'
    //         //     //候选数据【必填】
    //         //     ,data: catData
    //         //     //设置了长度
    //         //     ,width:null
    //         //     //默认值
    //         //     ,selected: [6,10,11]
    //         //
    //         //     //为真只取最后一个值
    //         //     ,last:true
    //         //
    //         //     //空值项提示，可设置为数组['请选择省','请选择市','请选择县']
    //         //     ,tips: '请选择'
    //         //
    //         //     //事件过滤器，lay-filter名 不设置与选择器相同(去#.)
    //         //     ,filter: ''
    //         //
    //         //     //input的name 不设置与选择器相同(去#.)
    //         //     ,name: 'cat2'
    //         //
    //         //     //数据分隔符
    //         //     ,delimiter: ','
    //         //
    //         //     //数据的键名
    //         //     ,field:{idName:'id',titleName:'name',childName:'children'}
    //         //
    //         //     //表单区分 form.render(type, filter); 为class="layui-form" 所在元素的 lay-filter="" 的值
    //         //     ,formFilter: null
    //         //
    //         // });
    //     })
    // },

}
UserInfo=new userHandle();
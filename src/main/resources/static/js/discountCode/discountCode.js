var DiscountCodeInfo;
var discountCodeHandle=function () {

}
discountCodeHandle.prototype={
    //送货地址的表格对象
    addrsTable:'',
    formSelects:{},
    //初始化優惠碼页面
    initDiscountCodeView:function () {
        //初始化優惠碼列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use(['table','form','laydate'], function() {
            var table = layui.table
                ,form=layui.form
                ,laydate=layui.laydate;
            var tableIns = table.render({
                elem: '#discountCodeList'
                , height: 420
                , url: $("#contextPath").val()+'/discountCode/getDiscountCodePageByCont.do' //数据接口
                , title: '優惠碼表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'discountCodeId', title: 'ID', width:100, sort: true, fixed: 'left'}
                    , {field: 'discountCodeNo', title: '<span class="lang" key="discount_code">優惠碼</span>', sort: true, align: 'center'}
                    , {field: 'discountCodeMode', title: '<span class="lang" key="mode">優惠方式</span>', sort: true, align: 'center', templet: function (d) {
                           return  "<span class='lang' key='"+common.key.discountCodeMode[d.discountCodeMode]+"'></span>";
                        }}
                    , {field: 'discountCodeContent', title: '<span class="lang" key="content">優惠內容</span>', sort: true, align: 'center', templet: function (d) {
                            return  d.discountCodeContent;
                        }}
                    , {field: 'discountCodeFiniteNum', title: '<span class="lang" key="effective_degree">有效次數</span>', sort: true, align: 'center', templet: function (d) {
                            if(d.discountCodeFiniteNum = -1){
                                return "<span class='lang' key='unlimited'>不限</span>";
                            }
                            return  d.discountCodeFiniteNum;
                        }}
                    , {field: 'discountCodeCreateTime', title: '<span class="lang" key="create_time">创建时间</span>', sort: true, align: 'center'}
                    , {
                        fixed: 'right', title: '<span class="lang" key="state">状态</span>', align: 'center', width: 100,sort: true, templet: function (d) {
                            if(ServiceUtil.checkPermission("/discountCode/editDiscountCodeIsProhibit.do")){
                                if (d.discountCodeIsProhibit) {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.discountCodeId + "' lay-text='"+common.getDataByKey("disable_enable")+"'></a>";
                                } else {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\"checked=\"\" name=\"open\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.discountCodeId + "' lay-text=\'"+common.getDataByKey("disable_enable")+"'></a>";
                                }
                            }else {
                                if (d.discountCodeIsProhibit){
                                    return "<span class=\"lang\" key=\"prohibit\">禁用</span>"
                                }else {
                                    return "<span class=\"lang\" key=\"enable\">启用</span>"
                                }
                            }
                        }
                    }
                    , {
                        fixed: 'right', title:  '<span class="lang" key="operation">操作</span>',  align: 'center', width:120,templet: function (d) {
                            var html = "";
                            if(ServiceUtil.checkPermission("/discountCode/editDiscountCodeView")){
                                html += "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/discountCode/delDiscountCodeById.do")){
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
                if(!ServiceUtil.checkPermissionAndPopup("/discountCode/editDiscountCodeIsProhibit.do")){
                   return;
                }
                var param = {
                    discountCodeIsProhibit:!data.elem.checked,
                    discountCodeId:data.elem.value
                }
                $.post($("#contextPath").val()+"/discountCode/editDiscountCodeIsProhibit.do", param, function (obj){

                })
            });
            //监听头工具栏事件
            table.on('toolbar(discountCodeList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("/discountCode/addDiscountCodeView")){
                            DiscountCodeInfo.addDiscountCodeView();
                        }break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("/discountCode/editDiscountCodeView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            DiscountCodeInfo.addDiscountCodeView();
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/discountCode/delAllDiscountCodeById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">優惠碼</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].discountCodeRealName+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){

                                    data.push(checkStatus.data[i].discountCodeId);
                                }
                                param.discountCodeIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val()+"/discountCode/delAllDiscountCodeById.do",param);
                                tableIns.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            table.on('tool(discountCodeList)', function(obj) {//注：tool 是工具条事件名，discountCodeList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">優惠碼</label> ]: '+' <label style="color: blue">'+data.discountCodeRealName+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.discountCodeId = data.discountCodeId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/discountCode/delDiscountCodeById.do",param);
                        });
                        break;
                    }
                    case "edit":{
                        DiscountCodeInfo.editDiscountCodeView(data.discountCodeId);break;
                    }
                    case "details":{
                        ServiceUtil.layer_show(data.discountCodeRealName,$("#contextPath").val()+'/discountCode/detailsDiscountCodeView?discountCodeId='+data.discountCodeId,500,400);break;
                    }
                }
            });
        })
    },
    //弹出添加優惠碼页面
    addDiscountCodeView:function(){
        ServiceUtil.layer_show('<span class="lang" key="add_discountCode">添加優惠碼</span>',$("#contextPath").val()+'/discountCode/addDiscountCodeView',600)
    },
    //弹出编辑優惠碼页面
    editDiscountCodeView:function(discountCodeId){
        ServiceUtil.layer_show('<span class="lang" key="edit_discountCode">编辑優惠碼</span>',$("#contextPath").val()+'/discountCode/editDiscountCodeView?discountCodeId='+discountCodeId,600)
    },
    //初始化添加或修改優惠碼页面信息
    initAddOrUpdateDiscountCode:function(){
        layui.use(['form'], function() {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                ,formSelects = layui.formSelects
                ,table=layui.table
                ,selectN = layui.selectN;
            var tableData = [];
            DiscountCodeInfo.formSelects = formSelects;

            var isInitPlace = false;//判斷地址下拉框是否已經渲染完
            var initPlaceData = false;//用於初始化地址下拉框的數據

            //初始化页面数据
            if($("#discountCodeId").val()){
                $.ajaxSettings.async = false;
                $.post( $("#contextPath").val()+"/discountCode/getDiscountCodeById.do", {'discountCodeId':$("#discountCodeId").val()}, function (obj) {
                    if(!obj.code){
                        //表单初始赋值
                        form.val('initDiscountCode', {
                            "discountCodeNo": obj.data.discountCodeNo
                            ,"discountCodeMode": obj.data.discountCodeMode
                            ,"discountCodeContent": obj.data.discountCodeContent
                            ,"discountCodeFiniteNum": obj.data.discountCodeFiniteNum
                        })
                        tableData=obj.data.receivingInfos;
                    }
                })

                $.ajaxSettings.async = true;
            }

            //自定义验证规则
            form.verify({
                pass: [/(.+){6,12}$/, '密码必须6到12位']
            });


            //表单提交
            form.on('submit(submitDiscountCode)', function(data){
                var data = data.field;
                if(data.discountCodeContent>=10){
                    layer.msg("折扣值不能大于10")
                    return false
                }else if(data.discountCodeContent<7){
                    layer.msg("折扣值不能小于7")
                    return false
                }
                var url = data.discountCodeId?$("#contextPath").val()+"/discountCode/editDiscountCode.do":$("#contextPath").val()+"/discountCode/addDiscountCode.do"
                $.post(url,data,function(obj){
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

}
DiscountCodeInfo=new discountCodeHandle();
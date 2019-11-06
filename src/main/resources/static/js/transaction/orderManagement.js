var Order;
var orderHandle=function () {

}
orderHandle.prototype={
    from:{},
    table:{},
    orderTable:{},
    // tableData:[],
    tableDataMap:{},
    //初始化訂單页面
    initOrderView:function () {
        var color = ["#D15B47","#87CEEB","#87B87F","#d63116","#ff6600","#2ab023","#1e3ae6","#c316a9","#13a9e1"]
        //初始化管理员数量
        ServiceUtil.postHandle(null,$("#contextPath").val()+'/order/getShoppingRatioByProductType.do',{},function (obj) {
            if(!obj.code){
                for(var i=0;i<obj.data.length;i++){
                    var dom = '<div class="proportion"> <div class="easy-pie-chart percentage" data-percent="'+obj.data[i].proportion+'" data-color="'+color[i]+'"><span class="percent">'+obj.data[i].proportion+'</span>%</div><span class="name lang" key="'+obj.data[i].name+";"+obj.data[i].nameEng+'"></span></div>';
                    $("#Order_form_style").find(".clearfix").append(dom);
                }
                $('.easy-pie-chart.percentage').each(function(){
                    $(this).easyPieChart({
                        barColor: $(this).data('color'),
                        trackColor: '#EEEEEE',
                        scaleColor: false,
                        lineCap: 'butt',
                        lineWidth: 10,
                        animate: oldie ? false : 1000,
                        size:103
                    }).css('color', $(this).data('color'));
                });
            }
        })
        Order.refreshOrderStateView();

        //初始化訂單列表
        var data = {};
        data.perParentId=$("#perId").val();
        layui.use(['table','form'], function() {
            Order.table =layui.table;
            Order.form =layui.form;
            Order.orderTable =  Order.table.render({
                elem: '#orderManagementList'
                , height: $(window).height()-350
                , url: $("#contextPath").val()+'/order/getOrderPageByCont.do' //数据接口
                , title: '訂單表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,defaultToolbar: [ 'print', 'exports']
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox',style:"height:70px",fixed: 'left'}
                    ,{field: 'orderNo', fixed: 'left',title: '<span class="lang" key="order_no">订单编号</span>',width:140,style:"height:70px",sort: true}
                    // ,{field: 'orderNo', title: '订单编号', width:100, sort: true, fixed: 'left'}
                    , {field: 'orderType', title: '<span class="lang" key="product">产品</span>', align: 'center',  width:250,templet: function (d) {
                           var html = "<span  lay-event='showProduct'>";
                           for (var i =0;i<d.orderProducts.length;i++){
                               if(i>0){
                                   html+="<i class=\"fa fa-plus\"></i>";
                               }
                               html+="<a href='#' title='点击可编辑'><img src='"+$("#contextPath").val()+"/file/fileDown?fileName="+d.orderProducts[i].productPhonePath+"' onclick='Order.showAllProduct("+d.orderId+")'  style='width: 50px;height: 50px'></a>";
                           }

                            return html+"</span>";
                        }}
                    , {field: 'orderPreferentialAmount', title: '<span class="lang" key="preferential_amount">优惠金額</span>',width:170,  align: 'center'}
                    , {field: 'orderTotalPrice', title: '<span class="lang" key="payment_amount">付款金額</span>', width:170,  align: 'center', sort: true}

                    , {field: 'orderState', title: '<span class="lang" key="order_status">订单状态</span>',width:160,   align: 'center',sort:true,templet: function (d) {
                            return "<span style='background-color: #00B83F;color: white;padding: 5px' class='lang' key='"+common.key.orderState[d.orderState]+"'></span>";
                        }}
                    , {field: 'orderPaymentStatus', title: '<span class="lang" key="payment_status">付款状态</span>', width:160,  align: 'center',templet: function (d) {
                            var data = common.key.orderPaymentStatus[d.orderPaymentStatus];
                            var color ="#00B83F";
                            if(!data){
                                data="abnormal_state"
                                color="#f4433694"
                            }
                            return "<span style='background-color: "+color+";color: white;padding: 5px' class='lang' key='"+data+"'></span>";

                        }}
                    , {field: 'orderCreateTime', title: '<span class="lang" key="create_time">时间</span>',width:172,  align: 'center'}
                    , {field: 'orderExplanation', title: '<span class="lang" key="explain">说明</span>',  align: 'center'}

                    , {
                        fixed: 'right',title: '<span class="lang" key="operation">操作</span>', style:"height:70px", align: 'center', width:380,templet: function (d) {
                            var html = "";

                            if(d.orderState==8&&ServiceUtil.checkPermission("/order/quotationOrder.do")){
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-warm lang\" key='quotation' lay-event=\"quotation\"><i class=\"layui-icon\" >運費報價</i></a>";
                            }
                            if(d.orderState==2&&ServiceUtil.checkPermission("/email/shippingReminder.do")){
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-warm lang\" key='shipping_reminder' lay-event=\"sendEmail\"><i class=\"layui-icon\" >發貨提醒</i></a>";
                            }
                            if(d.orderState==2&&ServiceUtil.checkPermission("/order/editOrdeShipment.do")){
                                html += "<a   class=\"layui-btn layui-btn-xs lang\" key='shipping' lay-event=\"sendProduct\"><i class=\"layui-icon\">发货</i></a>";
                            }
                            if(d.orderPaymentStatus==1&&ServiceUtil.checkPermission("order/cancelOrder.do")){
                                html += "<a   class=\"layui-btn layui-btn-xs lang\" key='cancel' lay-event=\"cancel\"><i class=\"layui-icon\">取消</i></a>";
                            }
                            html+='<a class="layui-btn layui-btn-primary layui-btn-xs lang" key="view_details" lay-event="detail" title="查看詳情">查看</a>';
                            if(ServiceUtil.checkPermission("order/editOrderView")){
                                html += "<a   class=\"layui-btn layui-btn-xs lang\" key='edit' lay-event=\"edit\"><i class=\"layui-icon\">编辑</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/order/delOrderById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs lang\" key='delete' lay-event=\"del\"><i class=\"layui-icon\">删除</i></a></span>";
                            }
                            return html?html:"<span class='lang' key='no_permission'>無權限</span>";
                        }
                    }

                ]],
                done : function(res, curr, count){
                    // Order.tableData = res.data;
                    res.data.forEach(function (v) {
                        Order.tableDataMap[v.orderId] = v;
                    })
                    common.initLang();
                }
            });
            //表单提交
            Order.form.on('submit(searchOrder)', function(data){
                var datas = data.field;
                var param ={};
                if(datas.orderNo)
                    param.orderNo=datas.orderNo
                if(datas.orderCreateTime)
                    param.orderCreateTime=datas.orderCreateTime
                Order.orderTable.reload({
                    where:param
                })
                return false;
            });

            //监听头工具栏事件
            Order.table.on('toolbar(orderManagementList)', function(obj){
                var checkStatus = Order.table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){

                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("/order/addOrderView")){
                            Order.addOrderView();
                        }break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("/order/editOrderView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            Order.addOrderView();
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/order/delAllOrderById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">訂單</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].orderNo+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].orderId);
                                }
                                param.orderIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer, $("#contextPath").val()+"/order/delAllOrderById.do",param);
                                Order.orderTable.reload();
                            });
                        }
                        break;
                };
            });
            //监听行工具事件
            Order.table.on('tool(orderManagementList)', function(obj) {//注：tool 是工具条事件名，orderList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case "sendEmail":{
                        ServiceUtil.postHandle(layer, $("#contextPath").val()+"/email/shippingReminder.do",{orderId:data.orderId},function () {
                            layer.msg(common.getDataByKey("shipping_reminder_msg"),{icon: 6,time:2000});
                        });
                        break;
                    }
                    case "sendProduct":{
                        console.log(data);
                        Order.sendProduct(obj,data);
                        break;
                    }
                    case "detail":{
                        Order.showOrderdetailView(data.orderId);
                        break;
                    }
                    case "quotation":{
                        Order.quotation(data.orderId);
                        break;
                    }
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">訂單</label> ]: '+' <label style="color: blue">'+data.orderNo+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.orderId = data.orderId;
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/order/delOrderById.do",param);
                        });
                        break;
                    }
                    case "edit":{
                        Order.editOrderView(data.orderId);
                        break;
                    }
                }
            });


        })
    },
    refreshOrderStateView:function(){
        // 初始化左侧訂單列表
        $.post($("#contextPath").val()+'/order/getCountOrderState.do', {}, function (obj) {
            if(!obj.code){
                var docm =   $(".b_P_Sort_list span");
                for(var i=0;i<docm.length;i++){
                    docm.eq(i).text(obj.data[i])
                }
            }
        })
    },
    refreshCurView:function(){
        Order.orderTable.reload();
        Order.refreshOrderStateView();
    },
    //弹出编辑訂單页面
    editOrderView:function(orderId){
        ServiceUtil.layer_show('<span class="lang" key="edit_order">编辑訂單</span>',$("#contextPath").val()+'/order/editOrderView?orderId='+orderId,460,400)
    },
    //設置運費
    quotation:function(id){
        var index = layer.open({
            type: 1,
            title: '<span class="lang" key="freight_charges">'+common.getDataByKey("freight_charges")+'</span>',
            maxmin: true,
            shadeClose:false,
            // area : ['40px' , ''],
            content:" <div style='margin-top: 10px;'><label style=\"width: 60px\" class=\"col-sm-2 control-label no-padding-right lang\" key=\"freight_charges\" for=\"form-field-1\"> 運費 </label>\n" +
                "                <div class=\"col-sm-9\"><input type=\"text\" id=\"freight\"  value='0' class=\"col-xs-10 col-sm-10\" style=\"margin-left:0px;\"></div>\n" +
                "            </div>",
            btn:['<span class="lang" key="page_determine">'+common.getDataByKey("page_determine")+'</span>','<span class="lang" key="cancel">'+common.getDataByKey("cancel")+'</span>'],
            yes: function(index, layero){

                    console.log()
                    ServiceUtil.postHandle(layer,$("#contextPath").val()+'/order/quotation.do',{orderId:id,freight:$("#freight").val()},function () {
                        layer.msg('郵件已發送!',{icon: 6,time:1000});
                        Order.refreshCurView();
                        layer.close(index);
                    });


            }
        });
    },
    //發貨
    sendProduct:function (obj,data) {
        var index = layer.open({
            type: 1,
            title: '<span class="lang" key="deliver_goods">'+common.getDataByKey("deliver_goods")+'</span>',
            maxmin: true,
            shadeClose:false,
            area : ['400px' , ''],
            content:$('#Delivery_stop'),
            btn:['<span class="lang" key="page_determine">'+common.getDataByKey("page_determine")+'</span>','<span class="lang" key="cancel">'+common.getDataByKey("cancel")+'</span>'],
            yes: function(index, layero){

                if(!$('#form-field-1').val()||!$('#form-field-select-1').val()){
                    layer.alert(common.getDataByKey("express_number_cannot_be_empty"),{
                        title: common.getDataByKey("prompt_box"),
                        icon:0,
                    })

                }else{
                    // Order.table.forEach(function (v) {
                    //     if(v.orderId == data.orderId){
                    //         v.order
                    //     }
                    // })
                    // Order.tableDataMap[data.orderId].orderState = 2;
                    // console.log(Order.tableData);
                    var updateDate ={
                        orderId:data.orderId,
                        orderState:3,
                        // orderPaymentMethod:$('#form-field-select-1').val(),
                        order_express_company_send:$('#form-field-select-1').val(),
                        orderExpressNo:$('#form-field-1').val()
                    }
                    ServiceUtil.postHandle(layer,$("#contextPath").val()+'/order/editOrdeShipment.do',updateDate,function () {
                        layer.msg('已发货!',{icon: 6,time:1000});
                        Order.refreshCurView();
                        layer.close(index);
                    });

                }

            }
        });

    },
    //初始化添加或修改訂單页面信息
    initAddOrUpdateOrder:function(){
        layui.use(['form', 'layedit'], function() {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
            //初始化页面数据
            if($("#orderId").val()){
                $.post( $("#contextPath").val()+"/order/getOrderById.do", {'orderId':$("#orderId").val()}, function (obj) {
                    if(!obj.code){
                        //表单初始赋值
                        form.val('initOrder', {
                            "orderTotalPrice": obj.data.orderTotalPrice
                            ,"orderPreferentialAmount": obj.data.orderPreferentialAmount
                            ,"orderState": obj.data.orderState
                            ,"orderPaymentStatus": obj.data.orderPaymentStatus
                        })
                    }
                })
            }else {
                Order.checkRoleSelect(1,form);
            }
            form.on('radio(orderTypeRadio)', function(data){
                Order.checkRoleSelect(data.value,form);
            });

            //表单提交
            form.on('submit(submitOrder)', function(data){
                var datas = data.field;
                var url = datas.orderId?$("#contextPath").val()+"/order/editCurrentOrderInfo.do":$("#contextPath").val()+"/order/addOrder.do"
                $.post(url,datas,function(obj){
                    layer.msg(obj.msg);
                    if(!obj.code){
                        parent.layer.closeAll("iframe");
                        //刷新父页面
                        parent.Order.refreshCurView();
                    }
                });
                return false;
            });
        })

    },
    //重新加載訂單
    getOrder:function (orderState) {
        Order.orderTable.reload({
            where:{orderState : orderState?orderState:null}
        })
    },
    //查看訂單詳情
    showOrderdetailView:function(orderId){
        location.href=$("#contextPath").val()+'/order/orderDetailView?orderId='+orderId;
    },
    //显示订单产品table
    showAllProduct:function (id) {
        var data = Order.tableDataMap[id];
        ServiceUtil.layer_show('产品详情',$('#orderProduct'),null,null,1)
            Order.table.render({
                elem: '#orderProductList'
                , height: $(window).height() - 350
                , title: '訂單表'
                , limit: 15
                , cellMinWidth: 80
                , data: data.orderProducts
                , cols: [[ //表头
                    {type: 'checkbox', fixed: 'right', style: "height:70px"}
                    , {
                        field: 'orderType', title: '产品', align: 'center', width: 250, templet: function (d) {
                            var html = "";
                            html += "<a href='#'><img src='" + $("#contextPath").val() + "/file/fileDown?fileName=" + d.productPhonePath + "'   style='width: 50px;height: 50px'></a>";

                            return html + "";
                        }
                    }
                    , {field: 'productTitle', title: '名称', align: 'center', sort: true}
                    , {field: 'orderPreferentialAmount', title: '优惠', align: 'center'}
                    // , {
                    //     field: 'orderState', title: '订单状态', align: 'center', templet: function (d) {
                    //         return "<span style='background-color: #00B83F;color: white;padding: 5px'>" + common.cn.orderState[d.orderState] + "</span>";
                    //     }
                    // }
                    , {
                        fixed: 'right',
                        title: '操作',
                        style: "height:70px",
                        align: 'center',
                        width: 350,
                        templet: function (d) {
                            var html = "";
                            html += "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"sendProduct\"><i class=\"layui-icon\">发货</i></a>";
                            html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">删除</i></a></span>";
                            return html ? html : "無權限";
                        }
                    }

                ]],

            });
    },


}
Order=new orderHandle();

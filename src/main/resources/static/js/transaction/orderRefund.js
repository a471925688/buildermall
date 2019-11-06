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
        // 初始化左侧訂單列表
        $.post($("#contextPath").val()+'/order/getCountOrderRefundState.do', {}, function (obj) {
            if(!obj.code){
                var docm =   $(".b_P_Sort_list span");
                for(var i=0;i<docm.length;i++){
                    docm.eq(i).text(obj.data[i])
                }
            }
        })
        //初始化退款訂單列表
        layui.use(['table','form'], function() {
            Order.table =layui.table;
            Order.form =layui.form;
            Order.orderTable =  Order.table.render({
                elem: '#orderManagementList'
                , height: $(window).height()-350
                , url: $("#contextPath").val()+'/order/selectRefundOrder.do' //数据接口
                , title: '訂單表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 15
                ,cellMinWidth: 80
                ,where: {
                    orderRefundState:$("#orderRefundState").val()
                }
                ,cols: [[ //表头
                    {type: 'checkbox',style:"height:70px",fixed: 'left'}
                    ,{field: 'orderNo', fixed: 'left',title: '<span class="lang" key="order_no"></span>',width:140,style:"height:70px",sort: true}
                    // ,{field: 'orderNo', title: '订单编号', width:100, sort: true, fixed: 'left'}
                    , {field: 'orderType', title: '<span class="lang" key="product"></span>', align: 'center',  width:250,templet: function (d) {
                           var html = "<span  lay-event='showProduct'>";
                           for (var i =0;i<d.orderProducts.length;i++){
                               if(i>0){
                                   html+="<i class=\"fa fa-plus\"></i>";
                               }
                               // html+="<a href='#'><img src='"+$("#contextPath").val()+"/file/fileDown?fileName="+d.orderProducts[i].productPhonePath+"' onclick='Order.showAllProduct("+d.orderId+")'  style='width: 50px;height: 50px'></a>";
                               html+="<a href='#'><img src='"+$("#contextPath").val()+"/file/fileDown?fileName="+d.orderProducts[i].productPhonePath+"'   style='width: 50px;height: 50px'></a>";
                           }

                            return html+"</span>";
                        }}
                    , {field: 'orderTotalPrice', title: '<span class="lang" key="transaction_amount"></span>',  align: 'center', sort: true}
                    , {field: 'orderRefundAmount', title: '<span class="lang" key="refund_amount"></span>',  align: 'center', sort: true}

                    , {field: 'orderRefundState', title: '<span class="lang" key="status"></span>',  align: 'center',sort:true,templet: function (d) {
                            return "<span style='background-color: #00B83F;color: white;padding: 5px' class='lang' key='"+common.key.orderRefundState[d.orderRefundState]+"'>"+common.getDataByKey(common.key.orderRefundState[d.orderRefundState])+"</span>";
                        }}
                    , {field: 'orderCreateTime', title: '<span class="lang" key="create_time"></span>',width:172,  align: 'center'}
                    , {
                        field: '', title: '<span class="lang" key="explain"></span>', align: 'center', templet: function (d) {
                            var html = "";
                            d.orderRecords.forEach(function (vlaue) {
                                if( vlaue.orderRecType == d.orderRefundState+5){
                                    html =  vlaue.orderRecExplanation;
                                }
                            })
                            return html;

                        }}
                    , {
                        fixed: 'right',title: '<span class="lang" key="operation"></span>', style:"height:70px", align: 'center', width:350,templet: function (d) {

                            var html = "";
                            html+='<a class="layui-btn layui-btn-primary layui-btn-xs lang" key="view_details" lay-event="detail" >'+common.getDataByKey("view_details")+'</a>';

                            if(d.orderRefundState==1&&ServiceUtil.checkPermission("/order/refundHandleOrder.do")){
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-normal\" lay-event=\"pass\"><i class=\"layui-icon lang\" key='agree'>"+common.getDataByKey("agree")+"</i></a>";
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"refuse\"><i class=\"layui-icon lang\" key='refuse'>"+common.getDataByKey("refuse")+"</i></a>";
                            }
                            return html?html:"<span class='lang' key='no_permission'></span>";
                        }
                    }

                ]],
                done : function(res, curr, count){
                    // Order.tableData = res.data;
                    res.data.forEach(function (v) {
                        Order.tableDataMap[v.orderId] = v;
                    })
                    // Order.tabledata = res.data;
                    // console.log(res);
                    // console.log(curr);
                    // console.log(count);
                    // do something...
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
                                tableIns.reload();
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
                    case "refuse":{
                        layer.confirm("  <label class=\"layui-form-label\" style='width: 100px;'>"+common.getDataByKey('reasons_for_refusal')+"  :</label>\n" +
                            "        <div class=\"layui-input-inline\">\n" +
                            "            <input type=\"text\" id=\"orderRecExplanation\" name=\"brandName\" lay-verify=\"text|required\"  autocomplete=\"off\" class=\"layui-input\">\n" +
                            "        </div>", {icon:2, title:common.getDataByKey("refusal_of_refund")},function (index) {
                            layer.close(index);
                            console.log($("#brandName").val());
                            //向服务端发送删除指令
                            var param = {
                                orderRecExplanation:$("#orderRecExplanation").val(),
                                orderId : data.orderId,
                                orderRecType:8
                            }
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/order/refundHandleOrder.do",param,function (rus) {
                                layer.msg(rus.msg);
                                data.orderRecords.push(param);
                                data.orderRefundState = 3;
                                obj.update(data);
                            },true);

                        });
                        break;
                    }
                    case "pass":{
                        layer.confirm(common.getDataByKey("page_determine")+'<label style="color: red">'+common.getDataByKey("agree_to_refund")+'</label> [ <label style="color: green">('+common.getDataByKey("amount")+': '+data.orderRefundAmount+')</label> ] <br><label>'+common.getDataByKey("order_no")+':</label>'+' <label style="color: blue">'+data.orderNo+'</label>'+' ？', {icon:3, title:common.getDataByKey("prompt_info")},function (index) {
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.orderId = data.orderId;

                            var param = {
                                orderId : data.orderId,
                                orderRecType:7
                            }
                            ServiceUtil.postHandle(layer, $("#contextPath").val()+"/order/refundHandleOrder.do",param,function (rus) {
                                layer.msg(rus.msg);
                                data.orderRecords.push(param);
                                data.orderRefundState = 2;
                                obj.update(data);
                            },true);
                        });
                        break;
                    }
                    case "detail":{
                        Order.showOrderdetailView(data.orderId);
                        break;
                    }


                }
            });


        })
    },
    //弹出编辑訂單页面
    editOrderView:function(orderId){
        ServiceUtil.layer_show('<span class="lang" key="edit_order"></span>',$("#contextPath").val()+'/order/editOrderView?orderId='+orderId,460,400)
    },
    //發貨
    // sendProduct:function (obj,data) {
    //     //彈出添加產品配置
    //     var index = layer.open({
    //         type: 1,
    //         title: '发货',
    //         maxmin: true,
    //         shadeClose:false,
    //         area : ['500px' , ''],
    //         content:$('#Delivery_stop'),
    //         btn:['确定','取消'],
    //         yes: function(index, layero){
    //             if($('#form-field-1').val()==""){
    //                 layer.alert('快递号不能为空！',{
    //                     title: '提示框',
    //                     icon:0,
    //                 })
    //
    //             }else{
    //                 // Order.table.forEach(function (v) {
    //                 //     if(v.orderId == data.orderId){
    //                 //         v.order
    //                 //     }
    //                 // })
    //                 // Order.tableDataMap[data.orderId].orderState = 2;
    //                 // console.log(Order.tableData);
    //                 var updateDate ={
    //                     orderId:data.orderId,
    //                     orderState:3,
    //                     orderPaymentMethod:$('#form-field-select-1').val(),
    //                     orderExpressNo:$('#form-field-1').val()
    //                 }
    //                 ServiceUtil.postHandle(layer,$("#contextPath").val()+'/order/editOrdeShipment.do',updateDate,function () {
    //                     layer.msg('已发货!',{icon: 6,time:1000});
    //                     Order.orderTable.reload();
    //                     layer.close(index);
    //                 });
    //
    //             }
    //
    //         }
    //     });
    //
    // },
    // //初始化添加或修改訂單页面信息
    // initAddOrUpdateOrder:function(){
    //     layui.use(['form', 'layedit'], function() {
    //         var form = layui.form
    //             , layer = layui.layer
    //             , layedit = layui.layedit
    //         //初始化页面数据
    //         if($("#orderId").val()){
    //             $.post( $("#contextPath").val()+"/order/getOrderById.do", {'orderId':$("#orderId").val()}, function (obj) {
    //                 if(!obj.code){
    //                     //表单初始赋值
    //                     form.val('initOrder', {
    //                         "orderTotalPrice": obj.data.orderTotalPrice
    //                         ,"orderPreferentialAmount": obj.data.orderPreferentialAmount
    //                         ,"orderState": obj.data.orderState
    //                         ,"orderPaymentStatus": obj.data.orderPaymentStatus
    //                     })
    //                 }
    //             })
    //         }else {
    //             Order.checkRoleSelect(1,form);
    //         }
    //         form.on('radio(orderTypeRadio)', function(data){
    //             Order.checkRoleSelect(data.value,form);
    //         });
    //
    //         //表单提交
    //         form.on('submit(submitOrder)', function(data){
    //             var datas = data.field;
    //             var url = datas.orderId?$("#contextPath").val()+"/order/editCurrentOrderInfo.do":$("#contextPath").val()+"/order/addOrder.do"
    //             $.post(url,datas,function(obj){
    //                 layer.msg(obj.msg);
    //                 if(!obj.code){
    //                     layer.closeAll("iframe");
    //                     //刷新父页面
    //                     parent.location.reload();
    //                 }
    //             });
    //             return false;
    //         });
    //     })
    //
    // },
    //重新加載訂單
    getOrder:function (orderRefundState) {
        Order.orderTable.reload({
            where:{orderRefundState : orderRefundState?orderRefundState:null}
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
var OrderDetail;
var orderDetailHandle=function () {

}
orderDetailHandle.prototype={
    //初始化訂單詳情页面
    initOrderDetailView:function () {
        $(".div_btn_top .show_btn_top").click(function () {
            $(this).hide();
            $(this).next().show();
            $(this).parents(".div_btn_top").nextAll().show();
        })
        $(".div_btn_top .hide_btn_top").click(function () {
            $(this).hide();
            $(this).prev().show();
            $(this).parents(".div_btn_top").nextAll().hide();
        })


    var html =" <div class=\"product_info clearfix\">\n" +
        "      <p><a href=\"#\" class=\"img_link\"><img src=\"productImg\" /></a></p>\n" +
        "      <span>\n" +
        "      <a href=\"#\" class=\"name_link\">productTitle</a>\n" +
        "      <b><lable class='lang' key='type'>類型</lable>：productType</b>\n" +
        "      content" +
        "      <p><label class='lang' key='type'>數量</label>：productQuantity (productUnit)</p>\n" +
        "      <p><label class='lang' key='price'>單價</label>：<b class=\"price\"><i>$</i>productPrice</b></p>  \n" +
        "      <p><label class='lang' key='freight_charges'>運費</label>：<i class=\"label label-success radius\">productFreightCharges</i></p>   \n" +
        "      </span>\n" +
        "      </div>";


        ServiceUtil.postHandle(null,$("#contextPath").val()+"/order/getOrderById.do",{orderId:$("#orderId").val()},function(obj){
            if(!obj.code){
                var productHtml = "";
                var totalNum =0;
                obj.data.orderProducts.forEach(function (dom) {
                    var contentHtml = "";
                    dom.orderProductParams.forEach(function (p) {
                        contentHtml+='<p><label class="lang" key="'+p.orderProductParamName+';'+p.orderProductParamEng+'"></label>：<label class="lang" key="'+p.orderProductParamContent+";"+p.orderProductParamContentEng+'"></label></p>'
                    })
                    totalNum+=dom.productQuantity;
                    productHtml+= html.replace("productTitle",dom.productTitle).replace("content",contentHtml)
                        .replace("productQuantity",dom.productQuantity).replace("productUnit",dom.productUnit)
                        .replace("productPrice",dom.productPrice).replace("productFreightCharges",dom.productFreightCharges)
                        .replace("productImg",$("#contextPath").val()+"/file/fileDown?fileName="+dom.productPhonePath)
                        .replace("productType",common.getDataByKey(dom.productTypeName+";"+dom.productTypeNameEng));
                        // .replace("productType",dom.productType.productTypeDescribe);

                })
                $("#orderProduct").append(productHtml);

                var billAddr = "";
                var recAddr= "";
                var transit= "";
                obj.data.orderAddrs.forEach(function (v) {
                    if(v.orderAddrType==1){
                        billAddr = v;
                    }else if(v.orderAddrType==2){
                        recAddr = v;
                    }else if(v.orderAddrType==3){
                        transit = v;
                    }

                })
                $("#userName").text(obj.data.user.userRealName);
                $("#userPhone").text(obj.data.user.userPhone);
                $("#orderDiscountCode").text(obj.data.orderDiscountCode);
                $("#orderRemark").text(obj.data.orderRemark);
                if(obj.data.orderInvoice==1){
                    $("#orderInvoice").text('是');
                    $("#orderInvoiceCorporateName").text(obj.data.orderInvoiceCorporateName);
                    $("#orderInvoiceBusinessLicense").text(obj.data.orderInvoiceBusinessLicense);
                }else{
                    $("#orderInvoice").text('否');
                }

                if(recAddr){
                    $("#orderAddrCompany").text(recAddr.orderAddrCompany);
                    $("#orderAddrName").text(recAddr.orderAddrName);
                    $("#orderAddrPhone").text(recAddr.orderAddrPhome);
                    $("#orderAddrAddr").html(recAddr.orderAddrAddrStr);
                    $("#orderAddrDetailedAddr").text(recAddr.orderAddrDetailedAddr);
                }
                if(transit){
                    $("#name").text(transit.orderAddrCompany);
                    $("#recName").text(transit.orderAddrName);
                    $("#phone").text(transit.orderAddrPhome);
                    $("#address").html(transit.orderAddrAddrStr);
                    $("#detailed_address").text(transit.orderAddrDetailedAddr);
                }
                $("#orderPaymentMethod").attr("key",common.key.orderPaymentMethod[obj.data.orderPaymentMethod]);
                $("#orderPaymentStatus").attr("key",common.key.orderPaymentStatus[obj.data.orderPaymentStatus]);
                $("#orderState").attr("key",common.key.orderState[obj.data.orderState]);
                $("#orderCreateTime").text(obj.data.orderCreateTime.split(" ")[0]);
                $("#orderExpressCompany").attr("key",common.key.orderExpressCompany[obj.data.orderExpressCompany]);
                $("#totalNum").text(totalNum);
                $("#orderNo").html(obj.data.orderNo)
                $("#orderTotalPrice").text("$"+obj.data.orderTotalPrice);
                //查询订单轨迹
                if(obj.data.orderExpressNo)
                    ServiceUtil.postHandle(null,$("#contextPath").val()+"/order/getNewTraceQuery.do",{orderExpressNo:obj.data.orderExpressNo},function(res){
                        if(!res.code){
                            $("#logistics .prompt").hide();
                            for(var i=0;i<res.data.length;i++){
                                var traceQuery = res.data[i];
                                $("#logistics ul").append(" <li>\n" +
                                    "                                 <p>"+traceQuery.time+"</p>\n" +
                                    "                                 <p>"+traceQuery.description+"</p>\n" +
                                    "                                 <span class=\"before\"></span><span class=\"after\"></span></li>")
                            }
                            console.log(res);
                        }
                        $("#logistics ul li").eq(0).addClass("first").append("<i class=\"mh-icon mh-icon-new\">");

                    })


                //渲染订单操作记录
                obj.data.orderRecords.forEach(function (v) {
                    var date = v.orderRecCreateTime;
                    var type =common.getDataByKey(common.key.orderRecordType[v.orderRecType]);
                    var operatorType = common.getDataByKey(common.key.orderOperatorType[v.adminId?v.admin.adminType:v.user?3:4]);
                    var operatorName = v.adminId?v.admin.adminRealName:v.user?v.user.userRealName:"系统操作";
                    var orderRecExplanation = v.orderRecExplanation;
                    $("#orderRecords ul").append(" <li>\n" +
                        "                                 <p>"+date+"</p>\n" +
                        "                                 <p>"+type+"("+operatorType+":"+operatorName+")  <span><span class='lang' key='operation_instructions'></span>:"+orderRecExplanation+"</span></p>\n" +
                        "                                 <span class=\"before\"></span><span class=\"after\"></span></li>")
                })
                $("#orderRecords ul li").eq(0).addClass("first").append("<i class=\"mh-icon mh-icon-new\">");


                //渲染交易记录
                obj.data.dealingSlips.forEach(function (v) {
                    var date = v.deslCreatTime;
                    var type =common.getDataByKey(common.key.deslAState[v.deslAState]);
                    var deslAmount = v.deslAmount;
                    var deslNo = v.deslNo;
                    var orderRecExplanation = v.orderRecExplanation;
                    $("#transactionRecord ul").append(" <li>\n" +
                        "                                 <p>"+date+"</p>\n" +
                        "                                 <p><span class='lang' key='transaction_number'></span>:"+deslNo+"(<span class='lang' key='amount'></span>："+deslAmount+"  ,<span class='lang' key='state'></span>:"+type+")<span style='margin-left: 10px'><span class='lang' key='explain'>说明</span>:"+(orderRecExplanation?orderRecExplanation:'')+"</span></p>\n" +
                        "                                 <span class=\"before\"></span><span class=\"after\"></span></li>")
                })
                $("#transactionRecord ul li").eq(0).addClass("first").append("<i class=\"mh-icon mh-icon-new\">");
                common.initLang();
            }
        })


    },

}
OrderDetail=new orderDetailHandle();
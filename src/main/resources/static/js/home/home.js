var Home;

var homeHandle=function () {
    
}
homeHandle.prototype={
    //初始化菜单
    initHomeView:function(){
        if(!ServiceUtil.checkPermission("product/addProductView")){
            $("#addProduct").remove();
        }
        if(!ServiceUtil.checkPermission("productTypeView")){
            $("#productTypeView").remove();
        }
        if(!ServiceUtil.checkPermission("adminInfoView")){
            $("#adminInfoView").remove();
        }
        if(!ServiceUtil.checkPermission("orderManagementView")){
            $("#orderManagementView").remove();
        }
        //查询商城用户
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/user/getCountUserByCont.do',"#user_num");
        //查询商城供应商
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/admin/getCountAdminByCont.do',"#suppliers_num",{adminType:2});
        //查询订单数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/order/getCountOrder.do',"#order_num");
        //交易金额
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/desl/getTotalSum.do',"#transaction_amount");
        //待付款订单数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/order/getCountOrderByCont.do',"#unpaid_order_num",{orderState:1});
        //待发货订单数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/order/getCountOrderByCont.do',"#unshipped_order_num",{orderState:2});
        //待收货订单数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/order/getCountOrderByCont.do',"#unReceiv_order_num",{orderState:3});
        //已成交订单数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/order/getCountOrderByCont.do',"#completed_order_num",{orderState:4});
        //已成交订单数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/order/getCountOrderByCont.do',"#pending_refund_order_num",{orderState:6});

        //商品总数
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/product/getCountProductByCont.do',"#product_totle");
        //售出总数
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/product/getTotalSold.do',"#total_sold");
        //上架数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/product/getCountProductByCont.do',"#itemUppershelf_num",{productState:1});
        //下架数量
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/product/getCountProductByCont.do',"#itemDownshelf_num",{productState:0});


        //管理員登錄次數
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/admin/getCountLoginByAdminType.do',"#admin_login",{adminType:1});
        //供應商登錄次數
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/admin/getCountLoginByAdminType.do',"#vendor_login",{adminType:2});
        //管理員登錄次數
        ServiceUtil.postHandleReturnData($("#contextPath").val()+'/user/getCountLogin.do',"#member_login");


        // $.get($("#contextPath").val()+"/permission/getAllPermissonByRoleId.do",{roleId:$("#roleId").val()}, function (obj) {
        //
        // })
    },
    // quickHandle:function (index) {
    //     switch (index) {
    //         case 1:{
    //             if(ServiceUtil.checkPermissionAndPopup("product/addProductView")){
    //                 location.href=$("#contextPath").val()+'/product/addProductView';
    //             }
    //             break;
    //         }
    //         case 2:{
    //             if(ServiceUtil.checkPermissionAndPopup("productView")){
    //                 location.href=$("#contextPath").val()+'/productView';
    //             }
    //             break;
    //         }
    //         case 3:{
    //             if(ServiceUtil.checkPermissionAndPopup("adminInfoView")){
    //                 location.href=$("#contextPath").val()+'/adminInfoView';
    //             }
    //             break;
    //         }
    //         case 4:{
    //             break;
    //         }
    //         case 5:{
    //             if(ServiceUtil.checkPermissionAndPopup("orderManagementView")){
    //                 location.href=$("#contextPath").val()+'/orderManagementView';
    //             }
    //             break;
    //         }
    //
    //     }
    //
    //
    //     window.event?window.event.cancelBubble=true:event.stopPropagation();
    // }

}
Home=new homeHandle();
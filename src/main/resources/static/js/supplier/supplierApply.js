var SupplierApply;
var supplierApplyHandle=function () {

}
supplierApplyHandle.prototype={
    from:{},
    table:{},
    supplierApplyTable:{},
    tableDataMap:{},
    //初始化产品页面
    initSupplierApplyView:function () {
        // 初始化左侧产品列表
        $.post($("#contextPath").val()+'/supplier/getCountSupplierApplyRefundState.do', {}, function (obj) {
            if(!obj.code){
                var docm =   $(".b_P_Sort_list span");
                for(var i=0;i<docm.length;i++){
                    docm.eq(i).text(obj.data[i])
                }
            }
        })
        if(!ServiceUtil.checkPermission("/supplier/productAudit.do")){
            $("#toolbarDemo1").remove();
        }
        layui.use(['table','form','carousel'], function() {
            SupplierApply.table =layui.table;
            SupplierApply.form =layui.form;
            var carousel = layui.carousel;
            SupplierApply.supplierApplyTable =  SupplierApply.table.render({
                elem: '#supplierApplyList'
                , height: $(window).height()-120
                , url: $("#contextPath").val()+'/supplier/getSupplierApplyPageByCont.do' //数据接口
                , title: '供应商申请表'
                , page: true //开启分页
                ,toolbar: '#toolbarDemo1'
                ,defaultToolbar: [ 'print', 'exports']
                , limit: 15
                ,cellMinWidth: 80
                ,where: {
                    state:$("#state").val()
                }
                ,cols: [[ //表头
                    {type: 'checkbox'}
                    ,{field: 'supplierApplyId', title: '<span class="lang" key="state"></span>', sort: true,templet: function (d) {
                            var style ="background-color: #009688; color: white;";
                            switch (d.state) {
                                case 2: var style ="background-color: #457b0e; color: white;";break;
                                case 3:var style ="background-color: red; color: white;";break;
                            }
                            return "<span  class='lang' style='"+style+"' key='"+common.key.temProductState[d.state]+"'></span>";
                        }}
                    , {field: 'name', title: '姓名', align: 'center' }
                    , {field: 'product.productDisPriceStart', title: '电话', align: 'center',  style:''}
                    , {field: 'product.productMaterial', title: '公司名', align: 'center',  style:''}
                    , {field: 'createTime', title: '公司成立时间',  align: 'center', sort: true}
                    , {field: 'createTime', title: '申请时间',  align: 'center', sort: true}
                    , {
                        fixed: 'right',title: '<span class="lang" key="operation"></span>',  align: 'center',  templet: function (d) {
                            var html = "";
                            if(d.state==1&&ServiceUtil.checkPermission("/supplier/supplierAudit.do")){
                                html+="  <a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看</a>";
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-normal\" lay-event=\"pass\"><i class=\"layui-icon lang\" key='agree'>"+common.getDataByKey("agree")+"</i></a>";
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"refuse\"><i class=\"layui-icon lang\" key='refuse'>"+common.getDataByKey("refuse")+"</i></a>";
                            }
                            return html?html:"  <a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看</a>";
                        }
                    }
                ]],
                done : function(res, curr, count){
                    common.initLang();
                }
            });

            //表单提交
            SupplierApply.form.on('submit(searchSupplierApply)', function(data){
                var datas = data.field;
                var param ={};
                if(datas.name)
                    param.name=datas.name
                if(datas.createTime)
                    param.createTime=datas.createTime
                SupplierApply.supplierApplyTable.reload({
                    where:param
                })
                return false;
            });

            //监听头工具栏事件
            SupplierApply.table.on('toolbar(supplierApplyList)', function(obj){
                var checkStatus = SupplierApply.table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){

                    case 'allPass':
                        // if(!ServiceUtil.checkPermissionAndPopup("/supplier/editSupplierApplyView")){
                        //     break;
                        // }
                        if(data.length === 0){
                            layer.msg('请至少选择一行');
                        } else {
                            var ids = [];
                            for (var i = 0;i<checkStatus.data.length;i++){
                                if(checkStatus.data[i].state==1)
                                 ids.push(checkStatus.data[i].supplierApplyId);
                            }
                            SupplierApply.allPass(ids);
                        }
                        break;
                    case 'allRefuse':
                        // if(!ServiceUtil.checkPermissionAndPopup("/supplier/editSupplierApplyView")){
                        //     break;
                        // }
                        if(data.length === 0){
                            layer.msg('请至少选择一行');
                        } else {
                            var ids = [];
                            for (var i = 0;i<checkStatus.data.length;i++){
                                if(checkStatus.data[i].state==1)
                                ids.push(checkStatus.data[i].supplierApplyId);
                            }
                            SupplierApply.allRefuse(ids);
                        }
                        break;
                };
            });
            //监听行工具事件
            SupplierApply.table.on('tool(supplierApplyList)', function(obj) {//注：tool 是工具条事件名，supplierApplyList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case "refuse":{
                        var supplierApplyIds=[]
                        supplierApplyIds.push(data.id)
                        if(supplierApplyIds.length)
                            SupplierApply.allRefuse(supplierApplyIds);
                        data.state = 3;
                        obj.update(data);
                        break;
                    }
                    case "pass":{
                        var supplierApplyIds=[]
                        supplierApplyIds.push(data.id)
                        if(supplierApplyIds.length)
                            SupplierApply.allPass(supplierApplyIds);

                        data.state = 2;
                        obj.update(data);
                        break;
                    }
                    case "detail":{
                        window.open($("#contextPath").val()+"/supplier/registerSuppiesDetail?id="+data.id,"aaa");
                    }

                }
            });


        })
    },
    allPass:function(ids){
        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/supplier/auditPass.do",{supplierApplyIds:ids})
    },
    allRefuse:function(ids){
        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/supplier/auditReject.do",{supplierApplyIds:ids})
    },
    getSupplierApply:function (state) {
        SupplierApply.supplierApplyTable.reload({
            where:{state : state?state:null}
        })
    }

}
SupplierApply=new supplierApplyHandle();
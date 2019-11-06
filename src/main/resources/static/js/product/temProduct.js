var TemProduct;
var temProductHandle=function () {

}
temProductHandle.prototype={
    from:{},
    table:{},
    temProductTable:{},
    tableDataMap:{},
    //初始化产品页面
    initTemProductView:function () {
        // 初始化左侧产品列表
        $.post($("#contextPath").val()+'/temProduct/getCountTemProductRefundState.do', {}, function (obj) {
            if(!obj.code){
                var docm =   $(".b_P_Sort_list span");
                for(var i=0;i<docm.length;i++){
                    docm.eq(i).text(obj.data[i])
                }
            }
        })
        if(!ServiceUtil.checkPermission("/temProduct/productAudit.do")){
            $("#toolbarDemo1").remove();
        }
        layui.use(['table','form','carousel'], function() {
            TemProduct.table =layui.table;
            TemProduct.form =layui.form;
            var carousel = layui.carousel;
            TemProduct.temProductTable =  TemProduct.table.render({
                elem: '#temProductList'
                , height: $(window).height()-120
                , url: $("#contextPath").val()+'/temProduct/getTemProductPageByCont.do' //数据接口
                , title: '产品审核表'
                , page: true //开启分页
                ,toolbar: '#toolbarDemo1'
                ,defaultToolbar: [ 'print', 'exports']
                , limit: 15
                ,cellMinWidth: 80
                ,where: {
                    temProductState:$("#temProductState").val()
                }
                ,cols: [[ //表头
                    {type: 'checkbox'}
                    ,{field: 'temProductId', title: '<span class="lang" key="state"></span>', width:100, sort: true,templet: function (d) {
                            var style ="background-color: #009688; color: white;";
                            switch (d.temProductState) {
                                case 2: var style ="background-color: #457b0e; color: white;";break;
                                case 3:var style ="background-color: red; color: white;";break;
                            }
                            return "<span  class='lang' style='"+style+"' key='"+common.key.temProductState[d.temProductState]+"'>"+d.product.productMaterial+"</span>";
                        }}
                    , {field: 'product.productTitle', title: '<span class="lang" key="title"></span>',style:"color:blue", align: 'center',  width:400,templet: function (d) {
                            return "  <a href='"+common.webPath+"product.html?product="+d.product.productId+"'><span style='color:blue' class='lang' key='"+d.product.productTitle+";"+d.product.productTitleEng+"'>"+d.product.productTitle+"</span></a>";
                        }}
                    , {field: 'product.productDisPriceStart', title: '<span class="lang" key="price"></span>(HDK$)', align: 'center',  style:'', width:140,templet: function (d) {
                            return d.product.productDisPriceStart;
                    }}
                    , {field: 'product.productMaterial', title: '<span class="lang" key="material"></span>', align: 'center',  style:'', width:140,templet: function (d) {
                            return "<span  class='lang' key='"+d.product.productMaterial+";"+d.product.productMaterialEng+"'>"+d.product.productMaterial+"</span>";
                        }}
                    , {field: 'productDescribe', title: '<span class="lang" key="picture"></span>',  width:200, align: 'center',templet: function (d) {
                            var html = "";
                            if(d.product.detailsImgNames.length){
                                var detailsImgNames;
                                for (var i=0;i<d.product.detailsImgNames.length;i++){
                                    var img = d.product.detailsImgNames[i];
                                    var path = $("#contextPath").val();
                                    var id = ServiceUtil.generateUUID();
                                    if(img.imageId){
                                        path+="/image/"+img.imagePath;
                                    }else {
                                        path+="/temp/"+img.imagePath;
                                    }
                                    detailsImgNames+="<div><img src='"+path+"'  onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' ></div>";
                                }
                                html="<div class=\"layui-carousel productImg\" >\n" +
                                    "  <div carousel-item>\n" +
                                    detailsImgNames+
                                    "  </div>\n" +
                                    "</div>"
                            }
                            return html;
                        }}
                    , {field: 'productDescribe', title: '<span class="lang" key="video"></span>',  width:200, align: 'center',templet: function (d) {
                            var html = "";
                            if(d.product.productVideo.length){
                                var detailsImgNames;
                                for (var i=0;i<d.product.productVideo.length;i++){
                                    var video = d.product.productVideo[i];
                                    var path = $("#contextPath").val();
                                    var id = ServiceUtil.generateUUID();
                                    if(video.fileId){
                                        path+="/video/";
                                    }else {
                                        path+="/temp/";
                                    }
                                    detailsImgNames+='<video class="kv-preview-data file-preview-video" controls="" style="width:213px;height:160px;" poster="'+path+video.fileThumbnailsPath+'" preload="none">' +
                                        '<source src="'+path+video.filePath+'" type="video/mp4">\n' +
                                        '<div class="file-preview-other">\n' +
                                        '<span class="file-other-icon"><i class="glyphicon glyphicon-king"></i></span>\n' +
                                        '</div>\n' +
                                        '</video>';;
                                }
                                html="<div class=\"layui-carousel productvideo\" >\n" +
                                    "  <div carousel-item>\n" +
                                    detailsImgNames+
                                    "  </div>\n" +
                                    "</div>"
                            }
                            return html;
                        }}
                    , {field: 'product.productFeatures', title: '<span class="lang" key="features"></span>', align: 'center',  style:'', width:140,templet: function (d) {
                            return "<span  class='lang' key='"+d.product.productFeatures+";"+d.product.productFeaturesEng+"'>"+d.product.productFeatures+"</span>";
                        }}
                    , {field: 'productDescribe', title: '<span class="lang" key="content"></span>',  width:186, align: 'center',templet: function (d) {
                            return "<span  class='lang' key='"+d.product.productDescribe+";"+d.product.productDescribeEng+"'></span>";
                        }}
                    , {field: 'productDescribe', title: '<span class="lang" key="describeC"></span>',  width:186, align: 'center',templet: function (d) {
                            return "<span  class='lang' key='"+d.product.productContent+";"+d.product.productContent+"'></span>";
                        }}
                    , {field: 'temProductCreateTime', title: '<span class="lang" key="submit_time"></span>',  align: 'center',width:186, sort: true}
                    , {
                        fixed: 'right',title: '<span class="lang" key="operation"></span>',  align: 'center', width:130,style:"height:200px", templet: function (d) {

                            var html = "";

                            if(d.temProductState==1&&ServiceUtil.checkPermission("/temProduct/productAudit.do")){
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-normal\" lay-event=\"pass\"><i class=\"layui-icon lang\" key='agree'>"+common.getDataByKey("agree")+"</i></a>";
                                html += "<a   class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"refuse\"><i class=\"layui-icon lang\" key='refuse'>"+common.getDataByKey("refuse")+"</i></a>";
                            }
                            return html?html:"<span class='lang' key='no_permission'></span>";
                        }
                    }
                ]],
                done : function(res, curr, count){
                    common.initLang();
                    $(".productImg").each(function () {
                        carousel.render({
                            elem: this
                            ,width: '100%' //设置容器宽度
                            ,height:'160px'
                            ,arrow: 'always' //始终显示箭头
                            ,indicator:"inside"
                            ,arrow:"hover"
                        });
                    })

                    carousel.render({
                        elem: '.productvideo'
                        ,width: '100%' //设置容器宽度
                        ,height:'160px'
                        ,arrow: 'always' //始终显示箭头
                        ,indicator:"inside"
                        ,arrow:"hover"
                    });



                }
            });
            //表单提交
            TemProduct.form.on('submit(searchTemProduct)', function(data){
                var datas = data.field;
                var param ={};
                if(datas.temProductNo)
                    param.temProductNo=datas.temProductNo
                if(datas.temProductCreateTime)
                    param.temProductCreateTime=datas.temProductCreateTime
                TemProduct.temProductTable.reload({
                    where:param
                })
                return false;
            });

            //监听头工具栏事件
            TemProduct.table.on('toolbar(temProductList)', function(obj){
                var checkStatus = TemProduct.table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){

                    case 'allPass':
                        // if(!ServiceUtil.checkPermissionAndPopup("/temProduct/editTemProductView")){
                        //     break;
                        // }
                        if(data.length === 0){
                            layer.msg('请至少选择一行');
                        } else {
                            var ids = [];
                            for (var i = 0;i<checkStatus.data.length;i++){
                                if(checkStatus.data[i].temProductState==1)
                                 ids.push(checkStatus.data[i].temProductId);
                            }
                            TemProduct.allPass(ids);
                        }
                        break;
                    case 'allRefuse':
                        // if(!ServiceUtil.checkPermissionAndPopup("/temProduct/editTemProductView")){
                        //     break;
                        // }
                        if(data.length === 0){
                            layer.msg('请至少选择一行');
                        } else {
                            var ids = [];
                            for (var i = 0;i<checkStatus.data.length;i++){
                                if(checkStatus.data[i].temProductState==1)
                                ids.push(checkStatus.data[i].temProductId);
                            }
                            TemProduct.allRefuse(ids);
                        }
                        break;
                };
            });
            //监听行工具事件
            TemProduct.table.on('tool(temProductList)', function(obj) {//注：tool 是工具条事件名，temProductList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event //获得 lay-event 对应的值
                    ,filter = obj.filter;
                switch (layEvent) {
                    case "refuse":{
                        var ids=[]
                        ids.push(data.temProductId)
                        if(ids.length)
                            TemProduct.allRefuse(ids);
                        // TemProduct.table.reload()
                        break;
                    }
                    case "pass":{
                        var ids=[]
                        if(ids.length)
                            ids.push(data.temProductId)
                        TemProduct.allPass(ids);
                        break;
                    }

                }
            });


        })
    },
    allPass:function(ids){
        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/temProduct/auditPass.do",{temProductIds:ids})
    },
    allRefuse:function(ids){
        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/temProduct/auditReject.do",{temProductIds:ids})
    },
    getTemProduct:function (state) {
        TemProduct.temProductTable.reload({
            where:{temProductState : state?state:null}
        })
    }

}
TemProduct=new temProductHandle();
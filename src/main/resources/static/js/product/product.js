var Product;
var productHandle=function () {

}
productHandle.prototype={
    jsId:0,
    productSizeTable:'',//保存產品尺寸table
    productSpecificationsTable:'',//保存其他選擇的table
    productConfigTable:'',//保存產品配置的table
    productImageTable:'',//保存產品圖片的table
    productVideoTable:'',//保存產品影片table
    productContentsFileTable:'',//保持产品目录table
    productSpecificationsFileTable:'',//保持产品规格table
    table:'',//保存產品列表的table
    addProductTable:'',
    form:'',//from表單
    formSelects:'',//下拉選擇框
    detailsImgNames:[],//保存詳圖上傳的圖片名
    coverimgName:'',//封面圖片名稱
    archivesFileNames:[],//保存檔案上傳的文件名
    isAdd:true,//是不是新增
    productData:{},//保持产品数据
    shearImg:{},//当前剪切的img标签
    productUnits:{},
    selectProductUnit:[],
    //初始化产品类型列表
    initProductInfo:function () {
        var data = {};
        data.productTypeId=$("#productId").val();
        layui.use(['table','form'], function() {
            var table = layui.table,
                form = layui.form;
            Product.table = table.render({
                elem: '#productList'
                , height: $(window).height()-220
                , url: $("#contextPath").val()+'/product/getProductPageByProduct.do' //数据接口
                ,defaultToolbar: [ 'print', 'exports']
                , title: '产品类型表'
                , page: true //开启分页
                , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                , limit: 10
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox'}
                    ,{field: 'productId', title: '<span class="lang" key="number"></span>', width:100, sort: true}
                    , {field: 'adminName', title: '<span class="lang" key="supplier"></span>', align: 'center',sort:true,  width:140}
                    // , {field: 'admin', title: '供應商', align: 'center',  width:160,templet: function (d) {
                    //     if(d.admin)
                    //         return d.admin.adminRealName;
                    //     }}
                    , {field: 'productPhonePath', title: '<span class="lang" key="product"></span>', align: 'center',height:100, width:130,templet: function (d) {
                        var id = ServiceUtil.generateUUID();
                            return "<img src='"+$("#contextPath").val()+"/image/"+d.productPhonePath+"' onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' style='width: 100px;height: 80px'>"
                        }}
                    , {field: 'brandLogoPath', title: '<span class="lang" key="brand"></span>', align: 'center',height:100, width:130,templet: function (d) {
                            var id = ServiceUtil.generateUUID();
                        return "<img src='"+$("#contextPath").val()+"/image/"+d.brandLogoPath+"' onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' style='width: 100px;height: 80px'>"
                        }}
                    , {field: 'productTypeName', title: '<span class="lang" key="type"></span>', align: 'center',style:"color:#e2b00b",  width:130,templet: function (d) {

                            return "<span class='lang' key='"+d.productTypeName+";"+d.productTypeNameEng+"'></span>";
                        }}

                    , {field: 'productTitle', title: '<span class="lang" key="title"></span>',style:"color:blue", align: 'center',  width:400,templet: function (d) {

                            return "  <a href='"+common.webPath+"product.html?product="+d.productId+"'><span style='color:blue' class='lang' key='"+d.productTitle+";"+d.productTitleEng+"'>"+d.productTitle+"</span></a>";
                        }}
                    // , {field: 'productTitleEng', title: '產品名称(英文)',hide:true, style:"color:blue", align: 'center',  width:400}
                    // , {field: 'productPriceStart', title: '<span class="lang" key="original_price"></span>', align: 'center', style:'text-decoration: line-through;', width:140,templet: function (d) {
                    //         return d.productPriceStart+" ~ "+d.productPriceEnd;
                    //     }}
                    // , {field: 'productPriceStart', title: '<span class="lang" key="discount_price"></span>', align: 'center',  style:'', width:140,templet: function (d) {
                    //
                    //         return "<span style='color:red'>"+d.productDisPriceStart+" ~ "+d.productDisPriceEnd+"</span>";
                    //     }}
                    , {field: 'productDisPriceStart', title: '<span class="lang" key="price"></span>(HDK$)', align: 'center',  style:'', width:140}
                    , {field: 'productDescribe', title: '<span class="lang" key="describe"></span>',  width:186, align: 'center',templet: function (d) {

                            return "<span  class='lang' key='"+d.productDescribe+";"+d.productDescribeEng+"'></span>";
                        }}
                    // , {field: 'productDescribeEng', title: '產品描述(英文)',hide:true,  width:186, align: 'center'}
                    , {field: 'productCreateTime', title: '<span class="lang" key="create_time"></span>',  align: 'center',width:186, sort: true}

                    ,{  fixed: 'right',title: '<span class="lang" key="status"></span>', align: 'center', width: 130, templet: function (d) {
                            if(ServiceUtil.checkPermission("/product/editProductState.do")){
                                if (!d.productState) {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.productId + "' lay-text='"+common.getDataByKey("lower_upper")+"'></a>";
                                } else {
                                    return "<span> <a  lay-event=\"check\"><input type=\"checkbox\"checked=\"\" name=\"open\" lay-skin=\"switch\" lay-filter=\"switchTest\" value='" + d.productId + "' lay-text=\'"+common.getDataByKey("lower_upper")+"'></a>";
                                }
                            }else {
                                if (!d.productState){
                                    return "<span class=\"lang\" key=\"lower_shelf\">下架</span>"
                                }else {
                                    return "<span class=\"lang\" key=\"upper_shelf\">上架</span>"
                                }
                            }
                        }
                    }
                    , {
                        fixed: 'right',title: '<span class="lang" key="operation"></span>',  align: 'center', width:130,style:"height:100px", templet: function (d) {
                            var html
                            if(ServiceUtil.checkPermission("/product/editProductView")){
                                html = "<span><a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\" >&#xe642;</i></a>";
                            }
                            if(ServiceUtil.checkPermission("/product/delProductById.do")){
                                html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\" >&#xe640;</i></a></span>";
                            }

                            return html?html:'<span class="lang" key="no_permission"></span>';
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
            form.on('switch(switchTest)', function(data){
                console.log(data)
                if(!ServiceUtil.checkPermissionAndPopup("/product/editProductState.do")){
                    return;
                }

                var param = {
                    productState:data.elem.checked,
                    productId:data.elem.value
                }
                $.post($("#contextPath").val()+"/product/editProductState.do", param, function (obj){
                    layer.msg(obj.msg);
                })
            });
            /*************** 华丽的分割线 ***************/
            //监听头工具栏事件
            table.on('toolbar(productList)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        if(ServiceUtil.checkPermissionAndPopup("product/addProductView")){
                            if(data.length > 1){
                                layer.msg('只能同时複製一个產品');
                                return
                            }
                            var productId = data.length?checkStatus.data[0].productId:"";
                            Product.addProductView(productId);

                        }

                        break;
                    case 'update':
                        if(!ServiceUtil.checkPermissionAndPopup("product/editProductView")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else if(data.length > 1){
                            layer.msg('只能同时编辑一个');
                        } else {
                            Product.editProductView(checkStatus.data[0].productId);
                        }
                        break;
                    case 'delete':
                        if(!ServiceUtil.checkPermissionAndPopup("/product/delAllProductById.do")){
                            break;
                        }
                        if(data.length === 0){
                            layer.msg('请选择一行');
                        } else {
                            layer.confirm('批量<label style="color: red">删除</label> [ <label style="color: green">产品</label> ]: '+' <label style="color: blue">'+checkStatus.data[0].productTitle+'</label>...合计: <label style="color: blue">'+checkStatus.data.length+'个</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                                layer.close(index);
                                var param ={}
                                var data = [];
                                for (var i = 0;i<checkStatus.data.length;i++){
                                    data.push(checkStatus.data[i].productId);
                                }
                                param.productIds = data;
                                //向服务端发送删除指令
                                ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delAllProductById.do",param,function (res) {
                                    if(!res.code)
                                    Product.table.reload();
                                });

                            });
                        }
                        break;
                };
            });

            /*************** 华丽的分割线 ***************/
            //监听行工具事件
            table.on('tool(productList)', function(obj) {//注：tool 是工具条事件名，productList 是 table 原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                switch (layEvent) {
                    case 'del':{
                        layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">产品类型</label> ]: '+' <label style="color: blue">'+data.productTitle+'</label>'+' 吗？', {icon:3, title:'提示信息'},function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                            //向服务端发送删除指令
                            var param={};
                            param.productId = data.productId;
                            ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delProductById.do",param);
                            //刷新父页面
                            // parent.location.reload();
                        });

                        break;
                    }
                    case 'edit':{
                        Product.editProductView(data.productId);
                        break;
                    }
                }
            });


            //表单提交
            form.on('submit(searchProduct)', function(data){
                var datas = data.field;
                Product.table.reload({
                    where:datas
                })
                return false;
            });
        })
    },
    //初始化添加产品页面
    initAddProduct:function(){
        layui.config({
            base: $("#contextPath").val()+'/layui/' //此处路径请自行处理, 可以使用绝对路径
        }).extend({
            formSelects: 'formSelects-v4'
        }).use(['form','upload','formSelects','element','table','layedit'],function() {
            var form = layui.form,
                $ = layui.jquery,
                upload = layui.upload,
                formSelects = layui.formSelects
                ,element = layui.element //元素操作
                ,table = layui.table
                ,layedit = layui.layedit;
            Product.addProductTable=table;
            Product.form=form;
            Product.formSelects=formSelects;

            layedit.set({
                //暴露layupload参数设置接口 --详细查看layupload参数说明
                uploadImage: {
                    url: $("#contextPath").val()+'/file/uploadLayedit',
                    accept: 'image',
                    acceptMime: 'image/*',
                    exts: 'jpg|png|gif|bmp|jpeg',
                    size: '10240'
                }
                , uploadVideo: {
                    url: $("#contextPath").val()+'/file/uploadLayedit',
                    accept: 'video',
                    acceptMime: 'video/*',
                    exts: 'mp4|flv|avi|rm|rmvb',
                    size: '20480'
                }
                //右键删除图片/视频时的回调参数，post到后台删除服务器文件等操作，
                //传递参数：
                //图片： imgpath --图片路径
                //视频： filepath --视频路径 imgpath --封面路径
                , calldel: {
                    url: $("#contextPath").val()+'/file/DeleteLayeditFile'
                }
                //开发者模式 --默认为false
                , devmode: true
                //插入代码设置
                , codeConfig: {
                    hide: true,  //是否显示编码语言选择框
                    default: 'javascript' //hide为true时的默认语言格式
                }
                , tool: [
                    'html', 'code', 'strong', 'italic', 'underline', 'del', 'addhr', '|', 'fontFomatt', 'colorpicker', 'face'
                    , '|', 'left', 'center', 'right', '|', 'link', 'unlink', 'image_alt','video',  'anchors'
                    , '|', 'fullScreen'
                ]
                , height: '90%'
            });
            var productContent = layedit.build('productContent');
            var productContentEng = layedit.build('productContentEng');
            var isInitPlace = false;//判斷地址下拉框是否已經渲染完
            var initPlaceData = false;//用於初始化地址下拉框的數據
            var placenName = common.getDataByKey("nameTc"+";"+"nameEng");
            formSelects.config('productDeliveryArea', {
                keyName: placenName,            //自定义返回数据中name的key, 默认 name
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
                    console.log(id);        //组件ID xm-select
                    console.log(url);       //URL
                    console.log(searchVal); //搜索的value
                    console.log(result);    //返回的结果
                    console.log(2);
                    formSelects.value('productDeliveryArea',initPlaceData, true);
                },
            }).data('productDeliveryArea', 'server', {
                url: $("#contextPath").val()+"/place/getAllProvincePlace.do",
                linkage: true,
                linkageWidth: 130
            });
            // //初始化图标上传
            // Upload.uploadImg(upload, "uploadic", $("#contextPath").val()+"file/uploadImages", {}, "jpg|gif|bmp|png|jpeg");

            /*************** 华丽的分割线 ***************/
            //监听Tab切换
            element.on('tab(demo)', function(data){

                //這是css的一個坑
                Product.resizeAddProudctTable();
            });
            //初始化產品單位選項
            ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/getAllProductUnit.do",{},function (obj) {
                if(obj.code){
                   layer.msg(obj.msg);
                   return;
                }
                Product.productUnits = obj.data;
                Product.formSelects.config('productUnit', {
                    keyName: 'productUnit',            //自定义返回数据中name的key, 默认 name
                    keyVal: 'productUnitEng',            //自定义返回数据中value的key, 默认 value
                }).data('productUnit', 'local', {
                    arr: Product.productUnits
                }).btns('productUnit', ['remove', 'skin', {
                    icon: 'layui-icon layui-icon-ok',   //自定义图标, 可以使用layui内置图标
                    name: '添加',
                    click: function(id){
                        layer.open({
                            type: 1,
                            title: "添加單位",
                            shadeClose: true,
                            shade: 0.4,
                            area: ["400px", '220px'],
                            content: $("#addProductUnit").html(),
                            btn: ['提交','返回'],
                            yes: function(index,layero){
                                //獲取新增的下來選擇內容
                                var newdata = {
                                    productUnit:layero.find("#productUnitName").val(),
                                    productUnitEng:layero.find("#productUnitNameEng").val()
                                }
                                //檢測類型是否重複添加
                                for(var i = 0 ;i<Product.productUnits.length;i++){
                                    if(Product.productUnits[i].productUnit == newdata.productUnit||Product.productUnits[i].productUnitEng==newdata.productUnitEng ){
                                        layer.msg('單位重複');
                                        return false;
                                    }
                                }
                                Product.productUnits.push(newdata)
                                //關閉彈出層
                                layer.close(index);
                                // 重新渲染下來選擇框
                                Product.formSelects.data('productUnit', 'local', {
                                    arr:Product.productUnits
                                }).value("productUnit",[newdata.productUnitEng]);
                            },
                            cancel: function(){
                                //右上角关闭回调
                            }
                        });
                    }
                }]);
                formSelects.value('productUnit', Product.selectProductUnit, true);
            })



            /*************** 华丽的分割线 初始化產品類型（樹形） ***************/
            //paramData用于保存树节点初始化时需要用到的一些参数信息
            var paramData={
                    docmid:"treeDemo",    //保存docmid用于树节点初始化时指定树节点的容器
                    TreeUrl:$("#contextPath").val()+'/productType/getAllProductType.do',//用于树节点初始化时异步请求所访问的url
                    id:"productTypeId", //用于树节点初始化id所对应的实体类属性名称
                    pId:"productTypeParentId",//用于树节点初始化父id所对应的实体类属性名称
                    isParent:"productTypeIsParent",        //用于树节点初始化时判断是否父节点所对应的实体类属性名称
                    perName:"productTypeName",    //用于树节点初始化时显示的名称所对应的实体类属性名称
                    perNameEng:"productTypeNameEng",    //用于树节点初始化时显示的名称所对应的实体类属性名称
                    tableFunction:Product.selectTypeCallBack,//傳入一個回調方法
                    onAsyncSuccess:Product.ztreeCallBack,
                    chkStyle:'checkbox'
                }
            ZtreeUtil.initAllTree(paramData);

            /*************** 华丽的分割线 ***************/
            //初始化类型数量
            $.post($("#contextPath").val()+'/productType/getCountProductTypeByCont.do', {'productTypeParentId':0}, function (obj) {
                if(!obj.code)
                    $("#countType b").text(obj.data);
            })
            /*************** 华丽的分割线 ***************/
            //渲染品牌下拉框
            var obj = ServiceUtil.postAsyncHandle($("#contextPath").val()+"/brand/getAllBrandByAdmin.do",{})
            if(obj){
                for(var i = 0;i<obj.length;i++){
                    $("#brandId").append("<option value='"+obj[i].brandId+"'>"+obj[i].brandName+"</option>");
                }
                form.render();
            }

            /*************** 华丽的分割线 ***************/
            //初始化页面数据
            if($("#productId").val()||$("#copyProductId").val()){

                var obj;
                if($("#productId").val()){
                    Product.isAdd= false;
                    obj = ServiceUtil.postAsyncHandle($("#contextPath").val()+"/product/getProductById.do", {'productId':$("#productId").val()})
                }else {
                    obj=ServiceUtil.postAsyncHandle($("#contextPath").val()+"/product/getCopyProduct.do", {'productId':$("#copyProductId").val()})
                }

                if(obj){
                    Product.productData=obj;
                    if(obj.productDeliveryArea){
                        initPlaceData = obj.productDeliveryArea.split(",")
                        if(isInitPlace){
                            formSelects.value('productDeliveryArea',initPlaceData, true);
                        }

                    }
                    paramData.checkedData=obj.productTypeId;
                    ZtreeUtil.checkMaterial(paramData);
                    $("#productType").text(obj.productTypeName);
                    Product.selectProductUnit.push(obj.productUnitEng)
                    formSelects.value('productUnit', Product.selectProductUnit, true);

                    if(obj.productReceivingArea)
                        formSelects.value('productReceivingArea',obj.productReceivingArea.split(","), true);
                    form.val('initProduct', {
                        "productId": obj.productId
                        ,"productTitle":obj.productTitle
                        ,"productTitleEng":obj.productTitleEng
                        ,"productMaterial":obj.productMaterial
                        ,"productMaterialEng":obj.productMaterialEng
                        ,"brandId":obj.brandId
                        ,"productUnit":obj.productUnit
                        ,"productPriceStart":obj.productPriceStart
                        ,"productPriceEnd":obj.productPriceEnd
                        ,"productDisPriceStart":obj.productDisPriceStart
                        ,"productDisPriceEnd":obj.productDisPriceEnd
                        ,'productIsFreight':obj.productIsFreight
                        ,"productState":obj.productState
                        ,"productFeatures":obj.productFeatures
                        ,"productFeaturesEng":obj.productFeaturesEng
                        ,"productDescribe":obj.productDescribe
                        ,"productDescribeEng":obj.productDescribeEng
                        ,"productTypeId":obj.productTypeId
                        ,'productServiceDate':obj.productServiceDate
                        ,"like[double]": obj.discountTypes&&obj.discountTypes.indexOf("1|")!=-1
                        ,"like[Promotion]": obj.discountTypes&&obj.discountTypes.indexOf("2|")!=-1
                        ,"like[NoFreight]": obj.discountTypes&&obj.discountTypes.indexOf("3|")!=-1
                        ,"like[Relief1]": obj.discountTypes&&obj.discountTypes.indexOf("4|")!=-1
                  })
                    layedit.setContent(productContent,obj.productContent)
                    layedit.setContent(productContentEng,obj.productContentEng)
                    //設置類型選中
                    // var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    // if(obj.discountTypes)
                    //     obj.productType.productTypeParentsId.split("|").forEach(function (value) {
                    //         if(value)
                    //             treeObj.expandNode(treeObj.getNodeByParam("productTypeId",value), true, false);
                    //     });
                }
            }else {
                var productId = ServiceUtil.postAsyncHandle($("#contextPath").val()+"/getProductNextId.do");
                Product.productData={
                    productId:productId,
                    detailsImgNames:[],
                    productVideo:[],
                    productContentsFile:[],
                    productSpecificationsFile:[],
                    productSizes:[],
                    productSpecificationss:[],
                    productConfigs:[]
                }
                $("#productId").val(productId)

            }
            uploadUtil.imagesUpload("detailsImg",1,Product.detailsimgcallback,Product.detailsimgdelcallback,null,['jpg','gif','bmp','png','jpeg']);
            uploadUtil.videosUpload("productVideo",2,Product.videocallback,Product.videodelcallback,null,['mp4']);
            uploadUtil.filesUpload("productContentsFile",3,Product.productcontentsfileocallback,Product.productcontentsfiledelcallback,null,['jpg','gif','bmp','png','jpeg','pdf']);
            uploadUtil.filesUpload("productSpecificationsFile",4,Product.productspecificationsfilecallback,Product.productspecificationsfiledelcallback,null,['jpg','gif','bmp','png','jpeg','pdf']);
            // uploadUtil.filesUpload("productVideo",Product.archivesfilecallback,Product.archivesfiledelcallback,null,['mp4']);
            /*************** 华丽的分割线 ***************/


            //表單提交
            form.on('submit(addProductSubmit)', function(data){
                var datas = data.field;
                datas.productUnit = formSelects.value('productUnit')[0].name;
                datas.productUnitEng = formSelects.value('productUnit')[0].value;
                var checkedType = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes();

                if(!checkedType||!checkedType.length){
                    layer.msg("請選擇產品類型");
                    return false;
                }
                var productTypeIds="";
                for(var i=0;i<checkedType.length;i++){
                    productTypeIds+=checkedType[i].productTypeId+"|";
                }
                $("#productTypeId").val(productTypeIds)
                datas.productTypeId =  $("#productTypeId").val();
                datas.productIsFreight = datas.productIsFreight?datas.productIsFreight:0;
                datas.productState = datas.productState?datas.productState:0;
                var discountTypes = "";
                $("#discountType").find("input:checkbox:checked").each(function () {
                    discountTypes+=$(this).val()+"|";
                })
                datas.discountTypes = discountTypes;
                datas.productSizes =  ServiceUtil.trimSpace(table.cache.productSizeList);
                datas.productSpecificationss = ServiceUtil.trimSpace(table.cache.productSpecificationsList);
                console.log(table.cache.productConfigList);
                datas.productConfigs = ServiceUtil.trimSpace(table.cache.productConfigList)
                console.log(datas.productConfigs);
                datas.detailsImgNames = Product.productData.detailsImgNames;
                datas.productVideo = Product.productData.productVideo;
                datas.productContentsFile = Product.productData.productContentsFile;
                datas.productSpecificationsFile = Product.productData.productSpecificationsFile;
                datas['productContent'] = layedit.getContent(productContent);
                datas['productContentEng'] = layedit.getContent(productContentEng);
                console.log(datas);
                var datajson = JSON.stringify(datas);

                datas = {
                    productInfo:datajson,
                    archivesFileNames:Product.archivesFileNames
                }
                var url = Product.isAdd?$("#contextPath").val()+"/product/addProduct.do":$("#contextPath").val()+"/product/editProduct.do"
                $.post(url,datas,function(obj){
                    if(!obj.code)
                        javascript :history.back(-1);
                    //返回上一級頁面
                    layer.msg(obj.msg);
                });
                return false;
            });

            //渲染產品尺寸table
            Product.initProductSizeTable();
            /*************** 华丽的分割线 ***************/
            //渲染其他選擇table
            Product.initProductSpecificationsTable();
            /*************** 华丽的分割线 ***************/
            //渲染產品配置table
            Product.initProductConfigTable();
            /*************** 华丽的分割线 ***************/
            //渲染產圖片table
            Product.initProductImageTable();
            /*************** 华丽的分割线 ***************/
            //渲染產影片table
            Product.initProductVideoTable();
            /*************** 华丽的分割线 ***************/
            //渲染產目录table
            Product.initProductContentsFileTable();
            /*************** 华丽的分割线 ***************/
            //渲染產规格table
            Product.initProductSpecificationsFileTable();
            /*************** 华丽的分割线 ***************/
            Product.resizeAddProudctTable();
        })

    },
    //渲染產品规格table
    initProductSpecificationsFileTable:function(){
        Product.productSpecificationsFileTable = Product.addProductTable.render({
            elem: '#productSpecificationsFileList'
            ,data:Product.productData.productSpecificationsFile
            ,cellMinWidth: 80
            // ,toolbar: '#toolbarDemo'
            ,defaultToolbar:['filter']
            ,limit:100
            ,cols: [[ //
                // {field: 'productSizeId', title: '編號', edit:true,align: 'center',sort:true}
                // ,{field: 'filePath', title: '点击下载', align: 'center',templet: function (d) {
                //         var url = $("#contextPath").val()+d.filePath;
                //         return '<video class="kv-preview-data file-preview-video" controls="" style="width:213px;height:160px;"><source src="'+url+'" type="video/mp4"> </video>'
                //         // return "<video style='width: 120px;height: 100px' controls autoplay=\"autoplay\" ><source src='"+url+"' type=\"video/mp4\" /></video>"
                //     }}
                ,{field: 'fileName',title: '名稱',edit:true, align: 'center'}
                ,{field: 'fileOrder', title: '排序', edit:true, align: 'center'}
                ,{field: 'fileId', title: '狀態', align: 'center',sort:true,templet: function (d) {
                        if(d.fileId){
                            return "現有"
                        }else {
                            return "<span style='color: #00B83F'>新增</span>"
                        }
                    }}
                , {
                    fixed: 'right', title: '操作',  align: 'center',templet: function (d) {
                        var html = "";

                        html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\" title=\"刪除\">刪除</i></a></span>";
                        return html;
                    }
                }
            ]]
        });

        Product.addProductTable.on('tool(productSpecificationsFileList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            console.info(obj)
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'del':{
                    obj.del();
                    for(var i = 0;i< Product.productData.productSpecificationsFile.length;i++){
                        if(Product.productData.productSpecificationsFile[i].filePath == data.filePath){
                            Product.productData.productSpecificationsFile.splice(i,1);
                            i--;
                        }
                    }
                    console.log(JSON.stringify(Product.productData.productSpecificationsFile.length))

                    break;
                }
            }
        });
    },
    //渲染產品目录table
    initProductContentsFileTable:function(){
        Product.productContentsFileTable = Product.addProductTable.render({
            elem: '#productContentsFileList'
            ,data:Product.productData.productContentsFile
            ,cellMinWidth: 80
            // ,toolbar: '#toolbarDemo'
            ,defaultToolbar:['filter']
            ,limit:100
            ,cols: [[ //
                // {field: 'productSizeId', title: '編號', edit:true,align: 'center',sort:true}
                // ,{field: 'filePath', title: '点击下载', align: 'center',templet: function (d) {
                //         var url = $("#contextPath").val()+d.filePath;
                //         return '<video class="kv-preview-data file-preview-video" controls="" style="width:213px;height:160px;"><source src="'+url+'" type="video/mp4"> </video>'
                //         // return "<video style='width: 120px;height: 100px' controls autoplay=\"autoplay\" ><source src='"+url+"' type=\"video/mp4\" /></video>"
                //     }}
                ,{field: 'fileName', title: '名稱',edit:true, align: 'center'}
                ,{field: 'fileOrder', title: '排序', edit:true, align: 'center'}
                ,{field: 'fileId', title: '狀態', align: 'center',sort:true,templet: function (d) {
                        if(d.fileId){
                            return "現有"
                        }else {
                            return "<span style='color: #00B83F'>新增</span>"
                        }
                    }}
                , {
                    fixed: 'right', title: '操作',  align: 'center',templet: function (d) {
                        var html = "";

                        html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\" title=\"刪除\">刪除</i></a></span>";
                        return html;
                    }
                }
            ]]
        });

        Product.addProductTable.on('tool(productContentsFileList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            console.info(obj)
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'del':{
                    obj.del();
                    for(var i = 0;i< Product.productData.productContentsFile.length;i++){
                        if(Product.productData.productContentsFile[i].filePath == data.filePath){
                            Product.productData.productContentsFile.splice(i,1);
                            i--;
                        }
                    }
                    console.log(JSON.stringify(Product.productData.productContentsFile.length))

                    break;
                }
            }
        });
    },
    //渲染產品影片table
    initProductVideoTable:function(){
        Product.productVideoTable = Product.addProductTable.render({
            elem: '#productVideoList'
            ,data:Product.productData.productVideo
            ,cellMinWidth: 80
            // ,toolbar: '#toolbarDemo'
            ,defaultToolbar:['filter']
            ,limit:100
            ,cols: [[ //
                // {field: 'productSizeId', title: '編號', edit:true,align: 'center',sort:true}
                ,{field: 'filePath', title: '影片',width:244, align: 'center',templet: function (d) {
                        var url = "";
                        var imgurl = "";
                        if(d.fileId){
                            url = $("#contextPath").val()+"/video/"+d.filePath;
                            imgurl = $("#contextPath").val()+"/video/"+d.fileThumbnailsPath;
                        }else {
                            url = $("#contextPath").val()+"/file/textFileDown?fileName="+d.filePath;
                            imgurl = $("#contextPath").val()+"/file/textFileDown?fileName="+d.fileThumbnailsPath;
                        }
                    // return '<video class="kv-preview-data file-preview-video" controls="" style="width:213px;height:160px;"><source src="'+url+'" type="video/mp4"> </video>'

                        return '<video class="kv-preview-data file-preview-video" controls="" style="width:213px;height:160px;" poster="'+imgurl+'" preload="none">' +
                            '<source src="'+url+'" type="video/mp4">\n' +
                            '<div class="file-preview-other">\n' +
                            '<span class="file-other-icon"><i class="glyphicon glyphicon-king"></i></span>\n' +
                            '</div>\n' +
                            '</video>';
                    }}
                ,{field: 'fileName', title: '名稱',edit:true, align: 'center'}
                ,{field: 'fileOrder', title: '排序', edit:true, align: 'center'}
                ,{field: 'fileId', title: '狀態', align: 'center',sort:true,templet: function (d) {
                        if(d.fileId){
                            return "現有"
                        }else {
                            return "<span style='color: #00B83F'>新增</span>"
                        }
                    }}
                , {
                     title: '操作',  align: 'center',templet: function (d) {
                        var html = "";

                        html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\" title=\"刪除\">刪除</i></a></span>";
                        return html;
                    }
                }
            ]]
        });

        // //监听头工具栏事件
        // Product.addProductTable.on('toolbar(productSizeList)', function(obj){
        //     var checkStatus = Product.addProductTable.checkStatus(obj.config.id)
        //         ,data = checkStatus.data; //获取选中的数据
        //     switch(obj.event){
        //         case 'addProductSize':
        //             Product.addProductSize();
        //             break;
        //     };
        // });
        Product.addProductTable.on('tool(productVideoList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            console.info(obj)
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'del':{
                    obj.del();
                    for(var i = 0;i< Product.productData.productVideo.length;i++){
                        if(Product.productData.productVideo[i].filePath == data.filePath){
                            Product.productData.productVideo.splice(i,1);
                            i--;
                        }
                    }
                    console.log(JSON.stringify(Product.productData.productVideo.length))

                    break;
                }
            }
        });
    },
    //渲染產品圖片table
    initProductImageTable:function(){
        Product.productImageTable = Product.addProductTable.render({
            elem: '#productImageList'
            ,data:Product.productData.detailsImgNames
            ,cellMinWidth: 80
            // ,toolbar: '#toolbarDemo'
            ,defaultToolbar:['filter']
            ,limit:200
            ,cols: [[ //
                // {field: 'productSizeId', title: '編號', edit:true,align: 'center',sort:true}
                ,{field: 'imagePath', title: '圖片', align: 'center',width:130,templet: function (d) {


                        var url = "";
                        if(d.imageId){
                            url = $("#contextPath").val()+"/file/fileDown?fileName="+d.imagePath;
                        }else {
                            url = $("#contextPath").val()+"/file/textFileDown?fileName="+d.imagePath;
                        }
                        var id = ServiceUtil.generateUUID();
                        // return "<img src='"+$("#contextPath").val()+"/file/fileDown?fileName="+d.brandLogoPath+"' onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' style='width: 100px;height: 80px'>"


                        return "<img src='"+url+"' onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' style='width: 100px;height: 80px'>"
                    }}
                , {field: 'imageName', title: '名稱',edit:true, align: 'center'}
                ,{field: 'imageOrder', title: '排序', edit:true, align: 'center'}
                ,{field: 'imageId', title: '狀態', align: 'center',sort:true,templet: function (d) {
                        if(d.imageId){
                           return "現有"
                        }else {
                            return "<span style='color: #00B83F'>新增</span>"
                        }
                    }}
                , {
                     title: '操作',  align: 'center',templet: function (d) {
                        var html = "";
                        html+="<span><a class=\"layui-btn layui-btn-xs\" lay-event=\"shear\"><i class=\"layui-icon\" title=\"編輯\">剪切編輯</i></a>";
                        if(d.imageType!=1){
                            html+="<a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\" title=\"編輯\">設為主圖</i></a>";
                        }
                        html += "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\" title=\"刪除\">刪除</i></a></span>";
                        return html;
                    }
                }
            ]]
        });

        // //监听头工具栏事件
        // Product.addProductTable.on('toolbar(productSizeList)', function(obj){
        //     var checkStatus = Product.addProductTable.checkStatus(obj.config.id)
        //         ,data = checkStatus.data; //获取选中的数据
        //     switch(obj.event){
        //         case 'addProductSize':
        //             Product.addProductSize();
        //             break;
        //     };
        // });
        Product.addProductTable.on('tool(productImageList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            console.info(obj)
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'edit':{
                    Product.productData.detailsImgNames.forEach(function (value) { value.imageType = 2 })
                    console.log("開始編輯")
                    console.log(JSON.stringify(data))
                    data.imageType=1;
                    obj.update(data)
                    console.log(JSON.stringify(data))
                    console.log(JSON.stringify(Product.productData.detailsImgNames[1]))
                    Product.productImageTable.reload({
                        data:Product.productData.detailsImgNames
                    })
                    break;
                }
                case 'shear':{
                    Product.shearImg = obj.tr.find("img");
                    var url=""
                    if(data.imageId){
                        url = $("#contextPath").val()+"/file/fileDown?fileName="+data.imagePath;
                    }else {
                        url = $("#contextPath").val()+"/file/textFileDown?fileName="+data.imagePath;
                    }
                    $("#tailoringImg").attr("src",url);

                    $('#tailoringImg').cropper('replace', url,false);//默认false，适应高度，不失真
                    // $(".tailoring-container").show();
                    cropperImage=data;
                    $(".tailoring-container").toggle();
                    break;
                }
                case 'del':{
                    obj.del();
                    for(var i = 0;i< Product.productData.detailsImgNames.length;i++){
                        if(Product.productData.detailsImgNames[i].imagePath == data.imagePath){
                            Product.productData.detailsImgNames.splice(i,1);
                            i--;
                        }
                    }
                    console.log(JSON.stringify(Product.productData.detailsImgNames.length))

                    break;
                }
            }
        });
    },
    //渲染產品尺寸table
    initProductSizeTable:function(){
        Product.productSizeTable = Product.addProductTable.render({
            elem: '#productSizeList'
            ,data:Product.productData.productSizes
            ,cellMinWidth: 80
            ,toolbar: '#toolbarDemo'
            ,defaultToolbar:['filter']
            ,limit:200
            ,text: {
                none: '<span><a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="addProductSize" onclick="Product.addProductSize();">添加尺寸</a></span>' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
            ,cols: [[ //
                {field: 'productSizeId', title: '編號',align: 'center',sort:true}
                ,{field: 'productSizeContent', title: '尺寸描述(中文)', edit:true, align: 'center'}
                ,{field: 'productSizeContentEng', title: '尺寸描述(英文)',edit:true, align: 'center'}
                // ,{field: 'productSizeLong', title: '长(mm)', edit:true, align: 'center'}
                // ,{field: 'productSizeWidth', title: '宽(mm)',edit:true, align: 'center'}
                // ,{field: 'productSizeHeight', title: '高(mm)',edit:true, align: 'center'}
                // ,{field: 'productSizeVolume', title: '體積(m3)',edit:true, align: 'center',sort:true }
                ,{field: 'recCreateTime', title: '创建时间', sort: true,hide:true, align: 'center'}
                ,{
                    fixed: 'right', title: '操作',  align: 'center', width:120,templet: function (d) {
                        return "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                    }
                }
            ]]
        });

        //监听头工具栏事件
        Product.addProductTable.on('toolbar(productSizeList)', function(obj){
            var checkStatus = Product.addProductTable.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'addProductSize':
                    Product.addProductSize();
                    break;
            };
        });
        Product.addProductTable.on('tool(productSizeList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'del':{
                    layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">尺寸</label> ]: '+' <label style="color: blue">'+data.productSizeContent+'</label>'+' 吗？（連帶刪除配置列表）', {icon:3, title:'<label style="color: red">無法恢復</label>'},function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构
                        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delProductSizeByProductSizeId.do",{productSizeId:data.productSizeId})
                        // var tableDatas = ServiceUtil.trimSpace(layui.table.cache.productConfigList);
                        for(var i =0;i<Product.productData.productSizes.length;i++){
                            if(ServiceUtil.compareJson1(Product.productData.productSizes[i],data)){
                                Product.productData.productSizes.splice(i,1);
                                break;
                            }
                        }
                        //遍歷產品配置信息
                        var deleProductConfigIds = [];
                        for (var i=0;i<Product.productData.productConfigs.length;i++){
                            var productConfigData = Product.productData.productConfigs[i];
                            //若包含刪除的尺寸則連帶刪除
                            if(productConfigData.productSizeId&&productConfigData.productSizeId == data.productSizeId){
                                    deleProductConfigIds.push(productConfigData.productConfigId);
                                    Product.productData.productConfigs.splice(i,1);
                                    i--;
                            }
                        }
                        if(deleProductConfigIds.length){
                            ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delAllProductConfigByProductConfigId.do",{productConfigIds:deleProductConfigIds})
                        }

                        Product.productConfigTable.reload({
                            data:Product.productData.productConfigs
                        })
                        layer.close(index);
                        //向服务端发送删除指令
                    });
                    break;
                }
            }
        });
    },
    //渲染其他選擇table
    initProductSpecificationsTable:function(productSpecificationsTableData){
        //渲染其他選擇table
        Product.productSpecificationsTable = Product.addProductTable.render({
            elem: '#productSpecificationsList'
            ,data:Product.productData.productSpecificationss
            ,cellMinWidth: 80
            ,toolbar: '#toolbarDemo2'
            ,defaultToolbar:['filter']
            ,limit:200
            ,text: {
                none: '<span><a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="addProductSize" onclick="Product.addProductSpecifications();">添加選擇</a></span>' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
            ,cols: [[ //表头
                {field: 'productSpecificationsId', title: 'id', hide:true, align: 'center',sort:true}
                , {field: 'productSpecificationsName', title: '名稱(中文)',edit:true,  align: 'center',sort:true}
                , {field: 'productSpecificationsNameEng', title: '名稱(英文)',edit:true,  align: 'center',sort:true}
                ,{field: 'productSpecificationsContent', title: '內容(中文)', edit:true, align: 'center'}
                , {field: 'productSpecificationsContentEng', title: '內容(英文)',edit:true, align: 'center'}
                , {field: 'productSpecificationsCreateTime', title: '创建时间', sort: true,hide:true, align: 'center'}
                , {
                    fixed: 'right', title: '操作',  align: 'center', width:120,templet: function (d) {
                        return "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                    }
                }
            ]]
        });

        //监听头工具栏事件
        Product.addProductTable.on('toolbar(productSpecificationsList)', function(obj){
            var checkStatus = Product.addProductTable.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'addProductSpecifications':
                    Product.addProductSpecifications();
                    break;
            };
        });

        Product.addProductTable.on('edit(productSpecificationsList)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
            console.log(obj.value); //得到修改后的值
            console.log(obj.field); //当前编辑的字段名
            console.log(obj.data); //所在行的所有相关数据
            switch (obj.field) {
                case 'productSpecificationsName':{
                    for(var n = 0 ;n<Product.productData.productSpecificationss.length;n++){
                        var sp = Product.productData.productSpecificationss[n];
                        if(sp.productSpecificationsNameEng == obj.data.productSpecificationsNameEng){
                            sp.productSpecificationsName = obj.value;
                        }
                    }
                    Product.productSpecificationsTable.reload({
                        data:Product.productData.productSpecificationss
                    })
                    break;
                }
                case 'productSpecificationsNameEng':{
                    for(var n = 0 ;n<Product.productData.productSpecificationss.length;n++){
                        var sp = Product.productData.productSpecificationss[n];
                        if(sp.productSpecificationsName == obj.data.productSpecificationsName){
                            sp.productSpecificationsNameEng = obj.value;
                        }
                    }
                    Product.productSpecificationsTable.reload({
                        data:Product.productData.productSpecificationss
                    })
                    break;
                }
            }
        });

        Product.addProductTable.on('tool(productSpecificationsList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'del':{
                    layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">尺寸</label> ]: '+' <label style="color: blue">'+data.productSpecificationsContent+'</label>'+' 吗？（連帶刪除配置列表項）', {icon:3, title:'<label style="color: red">無法恢復</label>'},function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构
                        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delProductSpecificationsByProductSpecificationsId.do",{productSpecificationsId:data.productSpecificationsId})
                        // var tableDatas = ServiceUtil.trimSpace(layui.table.cache.productConfigList);
                        for(var i =0;i<Product.productData.productSpecificationss.length;i++){
                            if(ServiceUtil.compareJson1(Product.productData.productSpecificationss[i],data)){
                                Product.productData.productSpecificationss.splice(i,1);
                                break;
                            }
                        }
                        //遍歷產品配置信息
                        for (var i=0;i<Product.productData.productConfigs.length;i++){
                            var productConfigData = Product.productData.productConfigs[i];
                            if(productConfigData.productSpecificationsSet){
                                console.log(JSON.stringify(productConfigData.productSpecificationsSet));
                                console.log(JSON.stringify(JSON.stringify(data)));
                                if(JSON.stringify(productConfigData.productSpecificationsSet).indexOf(JSON.stringify(data))!=-1){
                                    ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delProductConfigByProductConfigId.do",{productConfigId:productConfigData.productConfigId})
                                    Product.productData.productConfigs.splice(i,1);
                                }
                            }
                        }
                        Product.productConfigTable.reload({
                            data:Product.productData.productConfigs
                        })
                        layer.close(index);
                        //向服务端发送删除指令
                    });
                    break;
                }
            }
        });
    },
    //渲染產品配置table
    initProductConfigTable:function(productConfigTableData){
        //渲染其他選擇table
        Product.productConfigTable = Product.addProductTable.render({
            elem: '#productConfigList'
            ,data:Product.productData.productConfigs
            ,cellMinWidth: 80
            ,height:500
            ,toolbar: '#toolbarDemo1'
            ,defaultToolbar:['filter']
            ,limit: 200
            ,text: {
                none: '<span><a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="addProductSize" onclick="Product.addProductConfig();">添加配置</a></span>' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
            ,cols: [[ //表头
                {field: 'productConfigId', title: 'ID',  align: 'center', sort:true},
                {field: 'productSize.productSizeId', title: '尺寸(中文)',  align: 'center', sort:true, templet: function (d) {
                        if (d.productSize) {
                            return d.productSize.productSizeContent;
                        }else {
                            return""
                        }
                    }}
                , {field: 'productSize', title: '尺寸(英文)',hide:true, align: 'center',templet: function (d) {
                        if (d.productSize) {
                            return d.productSize.productSizeContentEng;
                        }else {
                            return""
                        }
                    }}
                , {field: 'productSizeContent', title: '選擇(中文)',style:'text-align:left;height:auto;',sort:true, align: 'center',templet: function (d) {
                        var data ="";
                        for(var i=0;i<d.productSpecificationsSet.length;i++){
                            data+="<span>"+d.productSpecificationsSet[i].productSpecificationsName+"： "+d.productSpecificationsSet[i].productSpecificationsContent+"</span><br>";
                        }
                        return data;
                    }}
                , {field: 'productSizeContentEng', title: '選擇(英文)',hide:true, align: 'center',style:'text-align:left;',templet: function (d) {
                        var data ="";
                        for(var i=0;i<d.productSpecificationsSet.length;i++){
                            data+="<span>"+d.productSpecificationsSet[i].productSpecificationsNameEng+"： "+d.productSpecificationsSet[i].productSpecificationsContentEng+"</span><br>";
                        }
                        return data;
                    }}
                // , {field: 'color', title: '顏色',align: 'center',templet: function (d) {
                //         if (d.color) {
                //             return d.color.colorName;
                //         }else {
                //             return""
                //         }
                //     }}
                , {field: 'productConfigWeight', title: '<span>重量（kg）</span>',edit:true, align: 'center',sort:true}
                // , {field: 'productConfigPrice', title: '原價',edit:true, align: 'center',sort:true}
                , {field: 'productConfigDiscountPrice', title: '價格（HDK$）',edit:true, align: 'center',sort:true}
                // , {field: 'productConfigDiscountPrice', title: '實價',align: 'center'}
                , {field: 'productConfigStock', title: '庫存',edit:true, align: 'center',sort:true}
                , {field: 'productConfigUrl', title: '圖片', align: 'center',sort:true,templet: function (d) {
                    if(d.productConfigUrl){
                        var url = $("#contextPath").val()+"/image/"+d.productConfigUrl;
                        // if(d.imageId){
                        //     url = $("#contextPath").val()+"/file/fileDown?fileName="+d.imagePath;
                        // }else {
                        //     url = $("#contextPath").val()+"/file/textFileDown?fileName="+d.imagePath;
                        // }
                        var id = ServiceUtil.generateUUID();
                        return "<img src='"+url+"' onclick='Upload.previewImg(\"#showImg"+id+"\")' id='showImg"+id+"' style='width: 100px;height: 80px'>"
                    }
                    return "";
                }}
                // , {field: 'productConfigCreateTime', title: '创建时间', sort: true,align: 'center'}
                , {
                     title: '操作',  align: 'center', width:120,templet: function (d) {
                        return "<a   class=\"layui-btn layui-btn-xs\" lay-event=\"edit\"><i class=\"layui-icon\">&#xe642;</i></a>" +
                            "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\"><i class=\"layui-icon\">&#xe640;</i></a></span>";
                    }
                }
            ]]
        });

        //监听头工具栏事件
        Product.addProductTable.on('toolbar(productConfigList)', function(obj){
            var checkStatus = Product.addProductTable.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'addProductConfig':
                    Product.addProductConfig();
                    break;
                case 'autoProductConfig':
                    Product.autoProductConfig();
                    break;
            };
        });

        Product.addProductTable.on('tool(productConfigList)', function(obj) {//注：tool 是工具条事件名，userList 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event //获得 lay-event 对应的值
                ,filter = obj.filter;
            switch (layEvent) {
                case 'del':{
                    layer.confirm('确定<label style="color: red">删除</label> [ <label style="color: green">配置</label> ]: '+' <label style="color: blue">ID：'+data.productConfigId+'</label>'+' 吗？', {icon:3, title:'<label style="color: red">無法恢復</label>'},function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构
                        for(var i =0;i<Product.productData.productConfigs.length;i++){
                            if(ServiceUtil.compareJson1(Product.productData.productConfigs[i],obj.data)){
                                Product.productData.productConfigs.splice(i,1);
                                break;
                            }
                        }

                        if(data.productConfigId)
                            ServiceUtil.postHandle(layer,$("#contextPath").val()+"/product/delProductConfigByProductConfigId.do",{productConfigId:data.productConfigId})
                        layer.close(index);
                        //向服务端发送删除指令
                    });
                    break;
                }
                case 'edit':{
                    Product.addProductConfig(obj);
                    break;
                }
                case 'isDiscount':{

                    if($('#isDiscount:checked').val()){
                        obj.data.update({
                            'productConfigIsDiscount': true
                        });
                        obj.data.productConfigIsDiscount=true;
                    }else {
                        obj.data.update({
                            'productConfigIsDiscount': false
                        });
                        obj.data.productConfigIsDiscount=false;
                    }
                }
            }
        });
    },
    //添加產品尺寸
    addProductSize:function () {
        var width = $(window).width()*0.8;
        if(width>700){
            width = 700;
        }
        var index = layer.open({
            type: 1,
            title: "添加產品尺寸",
            shadeClose: true,
            shade: 0.4,
            area: [width+"px", '300px'],
            content: $("#addProductSize").html(),
        });
        //表單數據獲取
        Product.form.on('submit(addProductSizeSubmit)', function(data){
            var datas = data.field;
            console.info(Product.addProductTable)

            // var tableDatas =  ServiceUtil.trimSpace(Product.addProductTable.cache.productSizeList);
            for(var i =0;i<Product.productData.productSizes.length;i++){
                var data = Product.productData.productSizes[i]
                // if(data.productSizeLong == datas.productSizeLong &&data.productSizeWidth == datas.productSizeWidth&&data.productSizeHeight == datas.productSizeHeight){
                //     layer.msg('尺寸已存在');
                //     return false;
                // }
                if(data.productSizeContentEng == datas.productSizeContentEng &&data.productSizeContent == datas.productSizeContent){
                    layer.msg('尺寸已存在');
                    return false;
                }
            }

            datas.productSizeCreateTime = ServiceUtil.curentTime();
            // datas.productSizeContent = "长:"+datas.productSizeLong+" 宽:"+datas.productSizeWidth+" 高:"+datas.productSizeHeight;
            // datas.productSizeContentEng = "L:"+datas.productSizeLong+" W:"+datas.productSizeWidth+" H:"+datas.productSizeHeight;
            datas.productSizeContent = datas.productSizeContent;
            datas.productSizeContentEng = datas.productSizeContentEng;
            var ajxdata = ServiceUtil.postAsyncHandle($("#contextPath").val()+"/getProductSizeNextId.do");
            if(!ajxdata){
                return false;
            }
            datas.productSizeId = ajxdata;
            datas.productId = $("#productId").val();
            // datas.productSizeId = "js_"+Product.jsId++;
            Product.productData.productSizes.push(datas)
            layer.close(index)
            Product.productSizeTable.reload({
                data:Product.productData.productSizes
            })
            //刷新父页面
            return false;
        });

    },
    //添加其他選擇
    addProductSpecifications:function () {
        var width = $(window).width()*0.8;
        if(width>700){
            width = 700;
        }
        var index = layer.open({
            type: 1,
            title: "添加產品尺寸",
            shadeClose: true,
            shade: 0.4,
            area: [width+"px", '600px'],
            content: $("#addProductSpecifications").html(),
        });
        //取出現有用於渲染下拉類型選擇框
        var datas = [];
        // var arry  = ServiceUtil.trimSpace(layui.table.cache.productSpecificationsList);

        for(var i=0;i<Product.productData.productSpecificationss.length;i++){
            var productSpecifications = Product.productData.productSpecificationss[i];
            var data = {
                name : productSpecifications.productSpecificationsName,
                value: productSpecifications.productSpecificationsNameEng,
            }
            datas.push(data)
        }
        datas = ServiceUtil.uniquearr(datas);//去除重複類型
        //渲染類型下拉選擇框
        console.log(datas);
        Product.formSelects.data('productSpecificationsType', 'local', {
            arr: datas
        }).btns('productSpecificationsType', ['remove', 'skin', {
            icon: 'layui-icon layui-icon-ok',   //自定义图标, 可以使用layui内置图标
            name: '添加',
            click: function(id){
                layer.open({
                    type: 1,
                    title: "添加類型",
                    shadeClose: true,
                    shade: 0.4,
                    area: ["400px", '220px'],
                    content: $("#addProductSpecificationsName").html(),
                    btn: ['提交','返回'],
                    yes: function(index,layero){
                        //獲取新增的下來選擇內容
                        var newdata = {
                            name:layero.find("#productSpecificationsName").val(),
                            value:layero.find("#productSpecificationsNameEng").val()
                        }
                        //檢測類型是否重複添加
                        for(var i = 0 ;i<datas.length;i++){
                            if(datas[i].name == newdata.name||datas[i].value==newdata.value ){
                                layer.msg('類型重複');
                                return false;
                            }
                        }
                        datas.push(newdata);
                        //關閉彈出層
                        layer.close(index);
                        // 重新渲染下來選擇框
                        Product.formSelects.data('productSpecificationsType', 'local', {
                            arr:datas
                        }).value("productSpecificationsType",[newdata.value]);
                    },
                    cancel: function(){
                        //右上角关闭回调
                    }
                });
            }
        }]);
        //表單數據獲取
        Product.form.on('submit(addProductSpecificationsSubmit)', function(data){
            var datas = data.field;//獲取表單數據
            // var tableDatas = ServiceUtil.trimSpace(layui.table.cache.productSpecificationsList);//獲取其他選擇表格數據
            datas.productSizeCreateTime = ServiceUtil.curentTime();//設置新數據創建時間
            //取出新增類型下拉框中的值
            var productSpecificationsType = Product.formSelects.value('productSpecificationsType')
            console.log(productSpecificationsType)
            datas.productSpecificationsNameEng = productSpecificationsType[0].value;
            datas.productSpecificationsName = productSpecificationsType[0].name;


            for(var i = 0; i <Product.productData.productSpecificationss.length;i++){
                var productSpecifications = Product.productData.productSpecificationss[i];
                if(datas.productSpecificationsName == productSpecifications.productSpecificationsName
                    &&(datas.productSpecificationsContent ==productSpecifications.productSpecificationsContent
                    ||datas.productSpecificationsContentEng ==productSpecifications.productSpecificationsContentEng)){
                    layer.msg('此選擇已存在');
                    return false;
                }
            }
            var ajxdata = ServiceUtil.postAsyncHandle($("#contextPath").val()+"/getProductSpecificationsNextId.do");//請求新數據id

            if(!ajxdata){
                return false;
            }

            datas.productSpecificationsId = ajxdata;//設置新數據id
            datas.productId = $("#productId").val();//設置新數據產品id

            console.log(datas);

            Product.productData.productSpecificationss.push(datas)
            layer.close(index)
            Product.productSpecificationsTable.reload({
                data:Product.productData.productSpecificationss
            })
            return false;
        });
    },
    //添加產品配置
    addProductConfig:function (row) {
        var width = $(window).width()*0.8;
        if(width>700){
            width = 700;
        }
        //彈出添加產品配置
        var index = layer.open({
            type: 1,
            title: "添加產品配置",
            shadeClose: true,
            shade: 0.4,
            area: [width+"px", '600px'],
            content: $("#addProductConfig").html()
        });



        var productSpecificationsDatas = {};//保存所有的其他選擇名稱，用於動態生成下拉選擇

        //選擇分組
        for (var i=0;i<Product.productData.productSpecificationss.length;i++){
            var obj = Product.productData.productSpecificationss[i];
            if(!productSpecificationsDatas[obj.productSpecificationsName]){
                productSpecificationsDatas[obj.productSpecificationsName] = [];
            }
            productSpecificationsDatas[obj.productSpecificationsName].push(obj)
        }
        console.log(productSpecificationsDatas);

        var productSizeReturnData=[];//保存选择后的size对象
        var productSpecificationsReturnDatas = new Array(Object.keys(productSpecificationsDatas).length)//保存选择后的其他对象
        var productSproductSpecificationsCols=[[]];
        if(row){
            productSproductSpecificationsCols[0].push({type: 'radio',width:80})
        }else{
            productSproductSpecificationsCols[0].push({type: 'checkbox', fixed: 'left'})
        }
        productSproductSpecificationsCols[0].push({field: 'productSpecificationsId', title: 'id',  align: 'center'});
        productSproductSpecificationsCols[0].push({field: 'productSpecificationsContent', title: '選擇內容(中文)', align: 'center'});
        productSproductSpecificationsCols[0].push({field: 'productSpecificationsContentEng', title: '選擇內容(英文)',align: 'center'});
        productSproductSpecificationsCols[0].push({field: 'productSpecificationsCreateTime', title: '创建时间', sort: true,hide:true, align: 'center'});

        var productIndex=0;

        var selecthtml = "<div class=\"layui-form-item\">\n" +
            "                    <label class=\"layui-form-label\">productSpecificationsName</label>\n" +
            "                    <div class=\"layui-input-block\">\n" +
            "                        <input type=\"text\" name=\"productSpecifications\" index='{productIndex}' id=\"productSpecificationsId\" lay-verify=\"title|required\" autocomplete=\"off\" placeholder=\"请選擇productSpecificationsName\" value='{value}' class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>"
        for(var i in productSpecificationsDatas) {
            var value = "";
            if(row){//如果是編輯
                $("input[name=productConfigUrl]").val(row.data.productConfigUrl)
                $("input[name=productId]").val(row.data.productId)
                $("input[name=productConfigWeight]").val(row.data.productConfigWeight)
                $("input[name=productConfigStock]").val(row.data.productConfigStock)
                $("input[name=productConfigDiscountPrice]").val(row.data.productConfigDiscountPrice)
                $("input[name=productSize]").val(row.data.productSize.productSizeContent)
                productSizeReturnData[0] = [row.data.productSize]
                for(var j =0;j<row.data.productSpecificationsSet.length;j++){
                    var sp = row.data.productSpecificationsSet[j];
                    if(sp.productSpecificationsName == i){
                        value = sp.productSpecificationsContent;
                        productSpecificationsReturnDatas[productIndex] = [sp];
                    }
                }

            }
            //動態拼接配置選項
            var id = "productSpecificationsId"+ServiceUtil.generateUUID();
            var html = selecthtml.replace(/productSpecificationsName/g,i.trim())
                .replace(/{value}/g,value).replace("productSpecificationsId",id)
                .replace(/{productIndex}/g,productIndex);
            console.log($("#productSize").parents(".layui-form-item"));
            $("#productSize").parents(".layui-form-item").after(html);
            //初始化選擇下拉選擇
            Product.initSelectTable(id.trim(),productSpecificationsDatas[i],productSproductSpecificationsCols,'productSpecificationsContent',productSpecificationsReturnDatas)
            console.log(i,":",productSpecificationsDatas[i]);
            productIndex++;
        }
        var productSizeCols=[[]];
        if(row){
            productSizeCols[0].push({type: 'radio',width:80})
        }else{
            productSizeCols[0].push({type: 'checkbox', fixed: 'left'})
        }
        productSizeCols[0].push({field: 'productSizeId', title: '編號',  align: 'center'});
        productSizeCols[0].push({field: 'productSizeContent', title: '尺寸描述(中文)',  align: 'center'});
        productSizeCols[0].push({field: 'productSizeContentEng', title: '尺寸描述(英文)', align: 'center'});
        productSizeCols[0].push({field: 'productSizeWeight', title: '重量(kg)', align: 'center'});
        productSizeCols[0].push({field: 'recCreateTime', title: '创建时间', sort: true,hide:true, align: 'center'});
        Product.initSelectTable("productSize",Product.productData.productSizes,productSizeCols,'productSizeContent',productSizeReturnData)



        var productImage = [[]];
        productImage[0].push({type: 'radio',width:80})
        productImage[0].push({field: 'imagePath', title: '圖片', align: 'center',width:130,templet: function (d) {
                var url = "";
                if(d.imageId){
                    url = $("#contextPath").val()+"/file/fileDown?fileName="+d.imagePath;
                }else {
                    url = $("#contextPath").val()+"/file/textFileDown?fileName="+d.imagePath;
                }
                return "<img src='"+url+"'  id='showImg"+id+"' style='width: 100px;height: 80px'>"
            }});
        productImage[0].push({field: 'imageOrder', title: '排序', align: 'center'});
        Product.initSelectTable("productImage",Product.productData.detailsImgNames,productImage,'imagePath',null)

        //表單數據獲取
        Product.form.on('submit(addProductConfigSubmit)', function(data){
            var datas = data.field;
            console.log(datas);
            if(row){
                var configs = Product.productData.productConfigs;
                if(row.data.productConfigId){
                    datas.productConfigId = row.data.productConfigId
                }
                var newProductSpecificationsSet = [];
                for(var i=0;i<productSpecificationsReturnDatas.length;i++){
                    newProductSpecificationsSet.push(productSpecificationsReturnDatas[i][0]);
                }
                datas.productSize = productSizeReturnData[0][0];
                datas.productSpecificationsSet =newProductSpecificationsSet ;
                datas.productSizeId = datas.productSize.productSizeId;
                datas.productSpecificationsSet.sort(function(a,b){
                    return  a.productSpecificationsId-b.productSpecificationsId;
                })
                row.data.productSpecificationsSet.sort(function(a,b){
                    return  a.productSpecificationsId-b.productSpecificationsId;
                })
                //如果配置沒改变
                if(datas.productSizeId != row.data.productSizeId||JSON.stringify(datas.productSpecificationsSet)!=JSON.stringify(row.data.productSpecificationsSet)){
                    for(var m = 0;m<configs.length;m++){
                        configs[m].productSpecificationsSet.sort(function(a,b){
                            return  a.productSpecificationsId-b.productSpecificationsId;
                        })
                        console.log(JSON.stringify( configs[m].productSpecificationsSet)==JSON.stringify(datas.productSpecificationsSet))
                        console.log(configs[m].productSizeId == datas.productSizeId)

                        if(JSON.stringify( configs[m].productSpecificationsSet) == JSON.stringify(datas.productSpecificationsSet) &&configs[m].productSizeId == datas.productSizeId){
                            layer.msg("該配置已存在", {
                                icon: 0,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            return false;
                        }
                    }
                }
                for(var m = 0;m<configs.length;m++){
                    if(JSON.stringify( configs[m].productSpecificationsSet) == JSON.stringify( row.data.productSpecificationsSet) &&configs[m].productSizeId ==  row.data.productSizeId){
                        datas.productConfigCreateTime =configs[m].productConfigCreateTime;
                        configs[m] = datas;
                    }
                }
                Product.productConfigTable.reload({
                    data:Product.productData.productConfigs
                })
                layer.msg("编辑成功", {
                    icon: 1,
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
                layer.close(index)
                return false;
            }else {
                var newdatas = [];
                for(var i = 0;i<productSizeReturnData[0].length;i++){//循环size
                    if(productSpecificationsReturnDatas.length){
                        Product.diguihandle(0,productSizeReturnData[0][i],[],productSpecificationsReturnDatas,datas,newdatas);//递归循环其他选择
                    }else{
                        var obj = JSON.parse(JSON.stringify(datas));
                        obj.productSize = productSizeReturnData[0][i];
                        obj.productSpecificationsSet = [];
                        obj.productConfigCreateTime = ServiceUtil.curentTime();
                        obj.productSizeId = productSizeReturnData[0][i].productSizeId;
                        obj.productId = $("#productId").val();
                        newdatas.push(obj);
                    }
                }
                if(!newdatas.length){
                    layer.msg("沒有可新增的配置", {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    //刷新父页面
                    return false;
                }else {
                    Product.productData.productConfigs = Product.productData.productConfigs.concat(newdatas)
                    layer.msg("添加成功", {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    layer.close(index)
                    Product.productConfigTable.reload({
                        data:Product.productData.productConfigs
                    })
                    console.log(productSizeReturnData)
                    //刷新父页面
                    return false;
                }
            }



        });
    },

    //批量生成所有可能配置
    autoProductConfig:function(){
        // var colorData = Product.formSelects.value('color', 'all');
        var newdatas = ServiceUtil.trimSpace(layui.table.cache.productConfigList);//保存生成的所有数据

        var productSizeData=ServiceUtil.trimSpace(layui.table.cache.productSizeList);//获取所有的产品size
        var productSpecificationsData = ServiceUtil.trimSpace(layui.table.cache.productSpecificationsList);//获取所有的产品其他选择

        var productSpeificationsData = []//数组分类保存所有的其他选择
        var other = {};//分类过滤

        for (var i=0;i<productSpecificationsData.length;i++){
            var obj = productSpecificationsData[i];
            if(!other[obj.productSpecificationsName]){
                other[obj.productSpecificationsName] = [];
            }
            other[obj.productSpecificationsName].push(productSpecificationsData[i])
        }
        for(var i in other) {
            productSpeificationsData.push(other[i]);
        }
        for(var i = 0;i<productSizeData.length;i++){//循环size
            Product.diguihandle(0,productSizeData[i],[],productSpeificationsData,newdatas);//递归循环其他选择
        }

        Product.productConfigTable.reload({
            data:newdatas
        })
        console.log(newdatas);
    },
    /**
     * 初始化下拉表格
     * @param id 容器id
     * @param productSpecificationsData table中初始化的數據
     * @param cols table表頭
     * @param dataName 選擇后存進input標籤的name值
     * @param productDataSave 選擇后保存的數據對象
     */
    initSelectTable:function(id,productSpecificationsData,cols,dataName,productDataSave){
        if(productSpecificationsData.length){
            layui.tableSelect.render({
                elem: '#'+id,
                searchKey: dataName,
                table: {
                    data: productSpecificationsData,
                    cols: cols,
                    limit: 1000,
                    limits:[1000]
                },
                done: function (elem, data) {
                    //选择完后的回调，包含2个返回值 elem:返回之前input对象；data:表格返回的选中的数据 []
                    //拿到data[]后 就按照业务需求做想做的事情啦~比如加个隐藏域放ID...
                    // elem.val(data.data[0][dataName]);
                    var val = "";
                    if(!productDataSave){
                        val=data.data[0][dataName];
                        elem.val(val);
                    }else {
                        for(var i =0;i<data.data.length;i++){
                            val= val +"(" +data.data[i][dataName]+(i==data.data.length-1?")":")  ");
                        }
                        elem.val(val);
                        // for(var i = 0;i<productDataSave.length;i++){
                        //     if(JSON.stringify(productDataSave[i])== JSON.stringify(data.data))//避免重复选择
                        //         return;
                        // }
                        console.log($(elem).attr("index"))
                        var index = $(elem).attr("index")?$(elem).attr("index"):0;
                        productDataSave[index] = data.data;
                    }
                }
            })
        }else {
            $("#"+id).parents(".layui-form-item").remove();
        }
    },
    /**
     * 递归处理
     * @param i 外层循环(类型)
     * @param productSize (本次数据的productSize属性)
     * @param productSpecificationsSet(本次数据的productSpecificationsSet属性)
     * @param productSpeificationsData 所有的其他选择
     * @param data 表單數據
     * @param newdatas 新配置
     */
    diguihandle:function (i,productSize,productSpecificationsSet,productSpeificationsData,data,newdatas) {
        for (var n = 0; n < productSpeificationsData[i].length; n++) {
            var newProductSpecificationsSet = productSpecificationsSet.slice()//深度拷贝
            newProductSpecificationsSet.push(productSpeificationsData[i][n])
            if(i==productSpeificationsData.length-1){
                var obj = JSON.parse(JSON.stringify(data));
                obj.productSize = productSize;
                obj.productSpecificationsSet = newProductSpecificationsSet;
                obj.productConfigCreateTime = ServiceUtil.curentTime();
                obj.productSizeId = productSize.productSizeId;
                obj.productId = $("#productId").val();

                var isRepetition = false;
                for(var m = 0; m<Product.productData.productConfigs.length;m++){
                    //排序
                    obj.productSpecificationsSet.sort(function(a,b){
                        return  a.productSpecificationsId-b.productSpecificationsId;
                    })
                    //排序
                    Product.productData.productConfigs[m].productSpecificationsSet.sort(function(a,b){
                        return  a.productSpecificationsId-b.productSpecificationsId;
                    })
                    if(obj.productSizeId == Product.productData.productConfigs[m].productSizeId && JSON.stringify(obj.productSpecificationsSet) ==JSON.stringify(Product.productData.productConfigs[m].productSpecificationsSet)){//重複配置
                        isRepetition = true;
                    }
                }
                if(!isRepetition){
                    newdatas.push(obj);
                }
            }else{
                Product.diguihandle((i + 1), productSize, newProductSpecificationsSet, productSpeificationsData,data, newdatas);
            }
        }
    },
    //弹出添加产品页面
    addProductView:function(copyProductId){
        location.href=$("#contextPath").val()+'/product/addProductView?copyProductId='+copyProductId;
        // ServiceUtil.layer_show('添加产品类型',$("#contextPath").val()+'/product/addProductView',700)
    },
    //弹出编辑产品页面
    editProductView:function(productId){
        location.href=$("#contextPath").val()+'/product/editProductView?productId='+productId;
    },
    //重新加載table
    reloadTable:function(param){
        Product.table.reload({
            where:{productTypeId:param.productTypeId}
        });
    },

    //添加產品頁面選擇類型回調
    selectTypeCallBack:function(param){
        $("#productType").text(param.productTypeDescribe);
    },
    //重置產品列表表格尺寸
    resizeProudctTable:function(){
        if(Product.table)
            Product.table.resize();
    },
    //重置添加產品頁面表格尺寸
    resizeAddProudctTable:function(){
        Product.addProductTable.resize();
        Product.productContentsFileTable.reload({
            data:Product.productData.productContentsFile
        });
        Product.productSpecificationsFileTable.reload({
            data:Product.productData.productSpecificationsFile
        });
    },

    /**
     * 圖片上傳成功回調
     * @param obj 服務器傳回來的數據
     */
    detailsimgcallback:function (obj) {
        console.log(obj);
        for(var i=0;i<obj.fileNames.length;i++){
            var newImg = {
                imageName:obj.oldFileNames[i]
                ,imagePath:obj.path[i]
                ,imageOrder:Product.productData.detailsImgNames.length+1
                ,imageType:Product.productData.detailsImgNames.length==0?1:2
                ,imageGroupType:1
                ,imageGroupId:$("#productId").val()
            };
            Product.productData.detailsImgNames.push(newImg);//把新增的圖片添加進table中
        }

        Product.productImageTable.reload({
            data:Product.productData.detailsImgNames
        })
        // Product.detailsImgNames = obj.fileNames
    },
    /**
     * 圖片刪除回調方法
     * @param
     */
    detailsimgdelcallback:function (filename) {
        console.log("刪除了:"+filename)
        //刪除table中的數據
        for(var i = 0;i< Product.productData.detailsImgNames.length;i++){
            if(Product.productData.detailsImgNames[i].imagePath.endsWith(filename)){
                Product.productData.detailsImgNames.splice(i,1);
                i--;
            }
        }
        Product.productImageTable.reload({
            data:Product.productData.detailsImgNames
        })
    },

    /**
     * 影片上傳成功回調
     * @param obj 服務器傳回來的數據
     */
    videocallback:function (obj) {
        Product.filecallback(Product.productData.productVideo,obj,Product.productVideoTable,2)
    },


    /**
     * 影片刪除回調方法
     * @param
     */
    videodelcallback:function(filename) {
        console.log("刪除了:"+filename)
        //刪除table中的數據
        Product.filedelcallback(Product.productData.productVideo,filename,Product.productVideoTable);
    },
    /**
     * 目录上傳成功回調
     * @param obj 服務器傳回來的數據
     */
    productcontentsfileocallback:function(obj){
        Product.filecallback(Product.productData.productContentsFile,obj,Product.productContentsFileTable,3)
    },
    /**
     * 目录刪除回調方法
     * @param
     */
    productcontentsfiledelcallback:function(filename) {
        console.log("刪除了:"+filename)
        //刪除table中的數據
        Product.filedelcallback(Product.productData.productContentsFile,filename,Product.productContentsFileTable);
    },

    /**
     * 规格上傳成功回調
     * @param obj 服務器傳回來的數據
     */
    productspecificationsfilecallback:function(obj){
        Product.filecallback(Product.productData.productSpecificationsFile,obj,Product.productSpecificationsFileTable,4)
    },
    /**
     * 规格刪除回調方法
     * @param
     */
    productspecificationsfiledelcallback:function(filename) {
        console.log("刪除了:"+filename)
        //刪除table中的數據
        Product.filedelcallback(Product.productData.productSpecificationsFile,filename,Product.productSpecificationsFileTable);
    },

    /**
     *
     * @param datas table数据
     * @param filename 文件名
     * @param table
     */
    filecallback:function (datas,obj,table,type) {
        console.log(obj);
        for(var i=0;i<obj.fileNames.length;i++){
            var newVideo= {
                fileName:obj.oldFileNames[i]
                ,filePath:obj.path[i]
                ,fileThumbnailsPath:"thumbnail-"+obj.path[i]+".png"
                ,fileOrder:datas.length+1
                ,fileType:type
                ,fileGroupType:1
                ,fileGroupId:$("#productId").val()
            }
            datas.push(newVideo);//把新增的圖片添加進table中
        }
        table.reload({
            data:datas
        })
    },
    filedelcallback:function (datas,filename,table) {
        console.log("刪除了:"+filename)
        //刪除table中的數據
        for(var i = 0;i< datas.length;i++){
            if(datas[i].filePath.endsWith(filename)){
                datas.splice(i,1);
                i--;
            }
        }
        table.reload({
            data:datas
        })
    },
    /**
     * 檔案上傳成功回調
     * @param obj 服務器傳回來的數據
     */
    archivesfilecallback:function (obj) {
        Product.archivesFileNames = obj.fileNames
    },
    /**
     * 檔案刪除回調方法
     * @param filename 刪除的圖片名稱
     */
    archivesfiledelcallback:function (filename) {
        if(!filename){
            Product.archivesFileNames = [];
        }else {
            var index = 0;
            for(var i=0;i<Product.archivesFileNames.length;i++){
                if(Product.archivesFileNames[i].indexOf("_@"+filename)){
                    index = i;
                    break;
                }
            }
            if (index > -1) {
                Product.archivesFileNames.splice(index, 1);
            }
        }

    },
    ztreeCallBack:function (treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("productTypeId", $("#productTypeId").val());
        // treeObj.expandNode(node, true, false);
        if(node){
            treeObj.checkNode(node, true, true);
            treeObj.selectNode(node);
        }

    },
    /**
     * 图片剪切回调
     */
    shearCallBack:function () {
        Product.shearImg.attr("src",Product.shearImg.attr("src")+"&random="+Math.random());
    }
}
Product=new productHandle();

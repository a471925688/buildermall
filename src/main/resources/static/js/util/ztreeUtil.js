var ZtreeUtil;

var zTree;
var treeNodes;
var ztreeHandleUtil=function () {

}
ztreeHandleUtil.prototype= {

    /**
     *
     * @param docmid 容器id,
     * @param url 异步访问地址
     * @param 树节点的id属性对应的后台数据属性名
     * @param pId 树节点的父id属性对应的后台数据属性名
     * @param isParent
     * @param perName 树节点的当前节点名称对应的后台数据属性名
     */
    initTree: function (paramData) {
        var autoParam = [];
        autoParam.push(paramData.id + "=" + paramData.pId);
        var otherParam = {}
        otherParam[paramData.pId] = 0;

    if(!paramData.chkStyle){
        paramData.chkStyle = "radio";
    }
        var setting = {
            edit: {
                enable: false, //如果enable为flase，那么树就不能移动了
                showRemoveBtn: false, //是否显示树的删除按钮
                showRenameBtn: false, //是否显示数的重命名按钮
                isSimpleData: true, //数据是否采用简单 Array 格式，默认false
                treeNodeKey: paramData.id,  //在isSimpleData格式下，当前节点id属性
                treeNodeParentKey: paramData.pId,//在isSimpleData格式下，当前节点的父节点id属性
                showLine: true, //是否显示节点间的连线
                removeTitle: "删除节点",//删除Logo的提示
                renameTitle: "编辑节点",//修改Logo的提示
                //拖拽
                drag: {
                    isCopy: false,//能够复制
                    isMove: false,//能够移动
                    prev: true,//不能拖拽到节点前
                    next: true,//不能拖拽到节点后
                    inner: true//只能拖拽到节点中
                }

            },
            check: {
                enable: true,
                chkStyle: paramData.chkStyle,  //单选框
                radioType: "all"   //对所有节点设置单选
            },
            data: {
                key: {
                    isParent: paramData.isParent,
                    name: 'name',
                    rootPId: 0
                }
            },
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false,
                fontCss: function getFontCss(treeId, treeNode) {
                    return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
                        color: "#333",
                        "font-weight": "normal"
                    };
                }
            },
            async: {
                enable: true,
                url: paramData.TreeUrl,
                dataFilter: filter,
                autoParam: autoParam,
            },
            callback: {
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $.fn.zTree.getZTreeObj(treeId).checkNode(treeNode, !treeNode.checked, true);
                    //把点击的id保存起来
                    $("#" + paramData.id).val(treeNode[paramData.id]);
                    //把点击的父id保存起来
                    $("#" + paramData.pId).val(treeNode[paramData.pId]);
                    //判斷是否iframe表格
                    if (paramData.iframeId) {
                        //刷新iframe中表格的数据
                        $("#" + paramData.iframeId).attr("src", paramData.tableUrl + treeNode[paramData.id]);
                    } else {
                        // var data = {}
                        // data[paramData.id] = ;
                        paramData.tableFunction(treeNode);
                    }

                },
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    var treeObj = $.fn.zTree.getZTreeObj(paramData.docmid);
                    var nodes = treeObj.getNodes();
                    if (nodes.length > 0&&!paramData.openOne) {
                        for (var i = 0; i < nodes.length; i++) {
                            treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
                        }
                    }
                    if(paramData.onAsyncSuccess)
                        paramData.onAsyncSuccess(treeNode);
                },// 异步加载正常结束的事件回调函数
            },
            //checkable : true //每个节点上是否显示 CheckBox
        };

        //对得到的json数据进行过滤，加载树的时候执行
        function filter(treeId, parentNode, responseData) {

            if (!responseData.code) {
                var contents = responseData.data;
                //当第一次加载的时候只显示一级节点，但是要让别人知道对应一级节点下是否有数据，那么就需要设置isParent,为true可以展开，下面有数据。
                //我这里是由于异步加载数据，只加载根节点以及一级节点。那么一级节点下还有子节点，所以设置isParent +號
                for (var i = 0; i < contents.length; i++) {
                    contents[i].isParent = contents[i][paramData['isParent']];
                    switch (window.localStorage.getItem("lang")) {
                        case 'cn':contents[i].name = Traditionalized(contents[i][paramData.perName]);break;
                        case 'cnn':contents[i].name = Simplized(contents[i][paramData.perName]);break;
                        case 'en':contents[i].name = contents[i][paramData.perNameEng];break;
                        default:contents[i].name = Traditionalized(contents[i][paramData.perName]);break;
                    }
                    // contents[i].icon=contents[i].perIconName;
                }
                return contents;
            } else {
                return;
            }

        };
        $.fn.zTree.init($("#" + paramData.docmid), setting);
    },


    initAllTree: function (paramData) {
        var autoParam = [];
        autoParam.push(paramData.id + "=" + paramData.pId);
        var otherParam = {}
        otherParam[paramData.pId] = 0;

        if(!paramData.chkStyle){
            paramData.chkStyle = "radio";
        }
        var setting = {
            edit: {
                enable: false, //如果enable为flase，那么树就不能移动了
                showRemoveBtn: false, //是否显示树的删除按钮
                showRenameBtn: false, //是否显示数的重命名按钮
                isSimpleData: true, //数据是否采用简单 Array 格式，默认false
                treeNodeKey: paramData.id,  //在isSimpleData格式下，当前节点id属性
                treeNodeParentKey: paramData.pId,//在isSimpleData格式下，当前节点的父节点id属性
                showLine: true, //是否显示节点间的连线
                removeTitle: "删除节点",//删除Logo的提示
                renameTitle: "编辑节点",//修改Logo的提示
                //拖拽
                drag: {
                    isCopy: false,//能够复制
                    isMove: false,//能够移动
                    prev: true,//不能拖拽到节点前
                    next: true,//不能拖拽到节点后
                    inner: true//只能拖拽到节点中
                }

            },
            check: {
                enable: true,
                chkStyle: paramData.chkStyle,  //单选框
                radioType: "all"   //对所有节点设置单选
            },
            data: {
                key: {
                    isParent: paramData.isParent,
                    name:paramData.perName,
                },
                simpleData: {
                    enable: true,
                    idKey: paramData.id,
                    pIdKey: paramData.pId,
                    rootPId: 0
                }
            },
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false,
                fontCss: function getFontCss(treeId, treeNode) {
                    return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
                        color: "#333",
                        "font-weight": "normal"
                    };
                }
            },
            callback: {
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $.fn.zTree.getZTreeObj(treeId).checkNode(treeNode, !treeNode.checked, true);
                    // var treeObj = $.fn.zTree.getZTreeObj(paramData.docmid);
                    // treeObj.expandNode(treeNode, true);
                }
            }

            //checkable : true //每个节点上是否显示 CheckBox
        };

        //对得到的json数据进行过滤，加载树的时候执行
        function filter(treeId, parentNode, responseData) {

            if (!responseData.code) {
                var contents = responseData.data;
                //当第一次加载的时候只显示一级节点，但是要让别人知道对应一级节点下是否有数据，那么就需要设置isParent,为true可以展开，下面有数据。
                //我这里是由于异步加载数据，只加载根节点以及一级节点。那么一级节点下还有子节点，所以设置isParent +號
                for (var i = 0; i < contents.length; i++) {
                    contents[i].isParent = contents[i][paramData['isParent']];
                    switch (window.localStorage.getItem("lang")) {
                        case 'cn':contents[i].name = Traditionalized(contents[i][paramData.perName]);break;
                        case 'cnn':contents[i].name = Simplized(contents[i][paramData.perName]);break;
                        case 'en':contents[i].name = contents[i][paramData.perNameEng];break;
                        default:contents[i].name = Traditionalized(contents[i][paramData.perName]);break;
                    }
                    // contents[i].icon=contents[i].perIconName;
                }
                return contents;
            } else {
                return;
            }

        };

        $.fn.zTree.init($("#" + paramData.docmid), setting,ServiceUtil.postAsyncHandle(paramData.TreeUrl,{}));
        if(paramData.checkedData){
            ZtreeUtil.checkMaterial(paramData);
        }
    },
    checkMaterial:function(paramData) {
        if(!paramData.checkedData)
            return;
        var datalist = paramData.checkedData.split("|");//将物资编码打散成数组
        //获取所有树节点
        var treeObj = $.fn.zTree.getZTreeObj(paramData.docmid);
        if (datalist != '' && datalist.length > 0) {
            var nodes = treeObj.getNodes();//获取所有子节点
            for (var i = 0; i < datalist.length; i++) {
                //通过id查找节点
                var node = treeObj.getNodeByParam(paramData.id, datalist[i], null);
                //选中该节点
                if (node != null) {//先判断节点是否为空
                    //选中该节点
                    if(!node[paramData.isParent]){
                        treeObj.checkNode(node, true, true);
                        //获取该节点父节点
                        var parent = node.getParentNode();
                        if (!parent.open) {//如果父节点没有展开则展开父节点
                            treeObj.expandNode(parent, true);
                        }
                    }

                }
            }
        }
    }
}
ZtreeUtil = new ztreeHandleUtil();
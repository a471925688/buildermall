var Main;
var mainHandle=function () {

}
mainHandle.prototype={
    initMainView:function(){
        WenSocketUtil.init({
            // openUrl:$("#ctx").val().replace(/http|https/,"ws")+"websocket/",
            openUrl:$("#ctx").val().replace(/http|https/,"ws").replace("admin/","buildermallWebsocket/"),
            cid:$("#cartUserId").val(),
        });

        layui.use('layim', function(layim){

            //演示自动回复
            var autoReplay = [
                '您好，我现在有事不在，一会再和您联系。',
                '你没发错吧？face[微笑] ',
                '洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！face[哈哈] ',
                '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
                'face[威武] face[威武] face[威武] face[威武] ',
                '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
                'face[黑线]  你慢慢说，别急……',
                '(*^__^*) face[嘻嘻] ，是贤心吗？'
            ];

            //基础配置
            layim.config({

                //初始化接口
               init: {
                    url: $("#contextPath").val()+'/cart/getInitCartData.do'
                   ,data: {}
                }
                //查看群员接口
                ,members: {
                    url: 'json/getMembers.json'
                    ,data: {}
                }
                //上传图片接口
                ,uploadImage: {
                    url: '/upload/image' //（返回的数据格式见下文）
                    ,type: '' //默认post
                }

                //上传文件接口
                ,uploadFile: {
                    url: '/upload/file' //（返回的数据格式见下文）
                    ,type: '' //默认post
                }
                //扩展工具栏
                ,tool: [{
                    alias: 'code'
                    ,title: '代码'
                    ,icon: '&#xe64e;'
                }]

                //,brief: true //是否简约模式（若开启则不显示主面板）

                //,title: 'WebIM' //自定义主面板最小化时的标题
                //,right: '100px' //主面板相对浏览器右侧距离
                //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
                ,initSkin: '2.jpg' //1-5 设置初始背景
                //,skin: ['aaa.jpg'] //新增皮肤
                ,isfriend: true //是否开启好友
                ,isgroup: false //是否开启群组
                //,min: true //是否始终最小化主面板，默认false
                ,notice: true //是否开启桌面消息提醒，默认false
                //,voice: false //声音提醒，默认开启，声音文件为：default.wav

                ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
                ,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
                ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatLog.html' //聊天记录页面地址，若不开启，剔除该项即可

            });


            // layim.chat({
            //   name: '在线客服-小苍'
            //   ,type: 'kefu'
            //   ,avatar: 'http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg'
            //   ,id: -1
            // });
            // layim.chat({
            //   name: '在线客服-心心'
            //   ,type: 'kefu'
            //   ,avatar: 'http://tva1.sinaimg.cn/crop.219.144.555.555.180/0068iARejw8esk724mra6j30rs0rstap.jpg'
            //   ,id: -2
            // });
            // layim.setChatMin();

            //监听在线状态的切换事件
            layim.on('online', function(data){
                //console.log(data);
            });

            //监听签名修改
            layim.on('sign', function(value){
                //console.log(value);
            });

            //监听自定义工具栏点击，以添加代码为例
            layim.on('tool(code)', function(insert){
                layer.prompt({
                    title: '插入代码'
                    ,formType: 2
                    ,shade: 0
                }, function(text, index){
                    layer.close(index);
                    insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
                });
            });

            //监听layim建立就绪
            layim.on('ready', function(res){
                im.contextMenu();
                im.cachedata = layui.layim.cache();
                im.layim = layui.layim;
                im.cachedata.base.findFriendUrl = $("#contextPath").val()+"/cart/findUser.do";//配置查找好友url
                im.cachedata.base.msgboxUrl = $("#contextPath").val()+"/cart/getSysMessage.do";//配置消息盒子url
                im.contentPath =  $("#contextPath").val();
                im.updateMsgBox();
                im.getOfflineMessage();
            });
            WenSocketUtil.onmessage(function(event){
                var obj = JSON.parse(event.data);
                switch (obj.type) {
                    case 1:
                        if(obj.data.orderId){
                            initCartGotoOrder(obj.data);
                         }
                        layim.getMessage(obj.data);break;
                    case 2: layim.addList({
                        type: 'friend'
                        ,avatar: obj.data.avatar //好友头像
                        ,username: obj.data.username //好友昵称
                        ,groupid: obj.data.typeId //所在的分组id
                        ,id: obj.data.id //好友ID
                        ,sign: obj.data.sign //好友签名
                    });im.markReaded(obj.data.id);
                    case 3: im.updateMsgBox();break;
                    case 4: im.delFriend(obj.data);break;
                    case 5: im.layim.setFriendStatus(obj.data, 'online');break;
                    case 6: im.layim.setFriendStatus(obj.data, 'offline');break;
                    case 7:
                        for(var i = 0;i<obj.data.length;i++){
                            if(obj.data[i].orderId) {
                                initCartGotoOrder(obj.data[i]);
                            }
                            layim.getMessage(obj.data[i])
                        };break;
                }

            });

            function initCartGotoOrder(data){
                data.titleContent = "<span style=\" font-size: x-small;color: blue\">     消息來源(訂單id:"+data.orderId+")      <a style=\"color:red\" onclick='Main.redirectUrl(\""+$("#contextPath").val()+'/order/orderDetailView?orderId='+data.orderId+"\")'>查看詳情</a></span>";
            }
            //监听发送消息
            layim.on('sendMessage', function(data){
                var To = data.to;
                //console.log(data);
                var param = {
                    avatar:data.mine.avatar,
                    content:data.mine.content,
                    id:data.mine.id,
                    username:data.mine.username,
                    type:To.type,
                    to:To.id,
                }
                ServiceUtil.postHandle(null,$("#contextPath").val()+"/cart/send.do",param);
            });

            //监听查看群员
            layim.on('members', function(data){
                //console.log(data);
            });

            //监听聊天窗口的切换
            layim.on('chatChange', function(res){
                var type = res.data.type;
                console.log(res.data.id)
                if(type === 'friend'||type === 'kefu'){
                    //模拟标注好友状态
                    im.currCartId = res.data.id;
                    im.markReaded(res.data.id);
                } else if(type === 'group'){
                    //模拟系统消息
                    layim.getMessage({
                        system: true
                        ,id: res.data.id
                        ,type: "group"
                        ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
                    });
                }
            });


        });

        ServiceUtil.postHandle(layer,$("#contextPath").val()+"/system/getSystem.do",{},function (obj) {
            if(!obj.code){
                var data = obj.data;
                $("title").text(data.systemWebName)
            }


        })



        $("#language").change(function () {
            console.log();
            window.localStorage.setItem("lang",$("#language").find("option:selected").val());
            common.initLang();
            if(document.getElementById("iframe").contentWindow.$.fn.zTree){
                console.log(document.getElementById("iframe"))
                var treeObj = document.getElementById("iframe").contentWindow.$.fn.zTree.getZTreeObj("treeDemo");
                treeObj.refresh();
            }
        })

        $("#clearCache").click(function () {
            layer.open({
                content: '<span style="color: red">此功能後台消耗巨大,請勿頻繁操作!</span>',
                yes: function(index, layero){
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
            $.get($("#contextPath").val()+"/clearAache.do",{}, function (obj) {
                console.log($("#iframe"))
                // self.location.reload();
                document.getElementById('iframe').contentWindow.location.reload(true);
            })

        })
        $.get($("#contextPath").val()+"/permission/getAllPermissonByRoleId.do",{roleId:$("#roleId").val()}, function (obj) {
            if(!obj.code){
                var parentDoc;
               var parentHtml1 = "<li id='perId' class=\"home\"><a href=\"javascript:void(0)\" name=\"perPath\" class=\"iframeurl\" title=\"\"><i class=\"perIconName\"></i><span class=\"menu-text lang\" key='{perName_key}'> perName </span></a></li>";
               var parentHtml2 = "<li id='perId'><a href=\"#\" class=\"dropdown-toggle\"><i class=\"perIconName\"></i><span class=\"menu-text lang\" key='{perName_key}'> perName </span><b class=\"arrow icon-angle-down\"></b></a><ul class=\"submenu\"> </ul></li>"
               var childHtml = "<li id='perId' class=\"home\"><a href=\"javascript:void(0)\" name=\"perPath\" title=\"<span class='lang' key='{perName_key}'></span>\" class=\"iframeurl\"><i class=\"icon-double-angle-right\"></i><span class='lang' key='{perName_key}'>perName</span></a></li>";
                var permissions = "";
                for (var i=0;i<obj.data.length;i++){
                    var permission = obj.data[i];
                    if(permission.perLevel==1){
                        parentDoc = $("#nav_list");
                        var appendHtml = "";
                        if(permission.perPath&&permission.perPath!="/"){
                            appendHtml=parentHtml1;
                        }else {
                            appendHtml=parentHtml2;
                        }
                    }else if(permission.perLevel==2) {
                        appendHtml=childHtml;
                        parentDoc = $("#permissionId"+permission.perParentId+" ul");
                        permissions+=permission.perPath+"|";
                    }else {
                        permissions+=permission.perPath+"|";
                        continue;
                    }
                    appendHtml = appendHtml.replace("perId","permissionId"+permission.perId)
                        .replace("perPath",$("#contextPath").val()+permission.perPath)
                        .replace("perIconName",permission.perIconName)
                        .replace(/{perName_key}/g,permission.perName+";"+permission.perNameEng)
                        .replace(new RegExp( 'perName' , "g" ),permission.perName)

                    parentDoc.append(appendHtml);
                }
                localStorage.setItem("permissions" ,permissions);
            }
            common.initLang();
        })

        if(ServiceUtil.getParameterByName("orderId")){
            Main.redirectUrl($("#contextPath").val()+'/order/orderDetailView?orderId='+ServiceUtil.getParameterByName("orderId"));
        }
    },
    redirectUrl:function (url) {
        $("#iframe").attr("src",url)
    }

}
Main=new mainHandle();

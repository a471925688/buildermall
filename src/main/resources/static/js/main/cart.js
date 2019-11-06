layui.define('jquery', function (exports) {
    "use strict";
    var $ = layui.jquery;
    !function(t,n,e,i){var o=function(t,n){this.init(t,n)};o.prototype={init:function(t,n){this.ele=t,this.defaults={menu:[{text:"菜单一",callback:function(t){}}],target:function(t){},width:140,itemHeight:30,bgColor:"#fff",color:"#333",fontSize:15,hoverBgColor:"#009bdd",hoverColor:"#fff"},this.opts=e.extend(!0,{},this.defaults,n),this.random=(new Date).getTime()+parseInt(1e3*Math.random()),this.eventBind()},renderMenu:function(){$(".ul-context-menu").remove();var t=this,n="#uiContextMenu_"+this.random;if(!(e(n).length>0)){var t=this,i='<ul class="ul-context-menu '+this.opts.contextItem+'" id="uiContextMenu_'+this.random+'">';e.each(this.opts.menu,function(t,n){
            n.nav ? (
                n.icon ? i += '<li class="ui-context-menu-item '+n.nav+'"><a href="javascript:void(0);"><i class="layui-icon">' + n.icon + '</i><span style="margin-left: 10px;">' + n.text + '</span><i class="layui-icon" style="float:right">' + n.navIcon + "</i></a>"+n.navBody+"</li>"
                    : i += '<li class="ui-context-menu-item '+n.nav+'"><a href="javascript:void(0);"><span>' + n.text + '</span><i class="layui-icon" style="float:right">' + n.navIcon + "</i></a>"+n.navBody+"</li>"
            ):(
                n.icon ? i += '<li class="ui-context-menu-item"><a href="javascript:void(0);"><i class="layui-icon">' + n.icon + '</i><span style="margin-left: 10px;">' + n.text + "</span></a></li>"
                    : i += '<li class="ui-context-menu-item"><a href="javascript:void(0);"><span>' + n.text + "</span></a></li>"
            )
        }),i+="</ul>",e("body").append(i).find(".ul-context-menu").hide(),this.initStyle(n),e(n).on("click",".ui-context-menu-item",function(n){t.menuItemClick(e(this)),n.stopPropagation()})}},initStyle:function(t){var n=this.opts;e(t).css({width:n.width,backgroundColor:n.bgColor}).find(".ui-context-menu-item a").css({color:n.color,fontSize:n.fontSize,height:n.itemHeight,lineHeight:n.itemHeight+"px"}).hover(function(){e(this).css({backgroundColor:n.hoverBgColor,color:n.hoverColor})},function(){e(this).css({backgroundColor:n.bgColor,color:n.color})})},menuItemClick:function(t){var n=this,e=t.index();t.parent(".ul-context-menu").hide(),n.opts.menu[e].callback&&"function"==typeof n.opts.menu[e].callback&&n.opts.menu[e].callback(t)},setPosition:function(t){var n = this.opts;var obj_h = n.menu.length * n.itemHeight + 12;var obj_w = n.width;var max_x = $(window).width();var max_y = $(window).height();var this_x = t.clientX ;var this_y = t.clientY ;var x = t.clientX+4;var y = t.clientY+4;if (max_x-this_x < (obj_w-4)) {x = max_x -obj_w;}if (max_y-this_y < (obj_h-4)) {y = max_y -obj_h;}e("#uiContextMenu_"+this.random).css({left:x,top:y }).show();

        },eventBind:function(){var t=this;this.ele.on("contextmenu",function(n){n.preventDefault(),t.renderMenu(),t.setPosition(n),t.opts.target&&"function"==typeof t.opts.target&&t.opts.target(e(this))}),e(n).on("click",function(){e(".ul-context-menu").hide()})}},e.fn.contextMenu=function(t){return new o(this,t),this}}(window,document,$);
    exports('contextMenu');
});
var im={
    cachedata:{},
    contentPath:'',
    layim:{},
    currCartId:"",//当前聊天的id
    //标记已读
    markReaded:function(toId){
        $.get(im.contentPath+"/cart/markReaded.do",{toId:toId},function(obj){})
    },
    //獲取未讀消息
    getOfflineMessage:function(){
        $.get(im.contentPath+"/cart/getOfflineMessage.do",{},function(obj){})
    },
    updateMsgBox:function(){
        $.get(im.contentPath+"/cart/getCountNotReadSysMessage.do",{},function(obj){
            if(!obj.code&&obj.data)
                im.layim.msgbox(obj.data);
        })
    },
    //创建群
    createGroup: function(othis){
        var index = layer.open({
            type: 2
            ,title: '创建群'
            ,shade: false
            ,maxmin: false
            ,area: ['550px', '400px']
            ,skin: 'layui-box layui-layer-border'
            ,resize: false
            ,content: im.cachedata.base.createGroup
        });
    },
    addFriendGroup:function(othis,type){
        var li = othis.parents('li') || othis.parent()
            , uid = li.data('uid') || li.data('id')
            , approval = li.data('approval')
            , name = li.data('name');
        if (uid == 'undifine' || !uid) {
            var uid = othis.parent().data('id'), name = othis.parent().data('name');
        }
        console.log($(othis).parent().find("img").attr("src"));
        var avatar = $(othis).parent().find("img").attr("src");
        var isAdd = false;
        if (type == 'friend') {
            var default_avatar = './uploads/person/empty2.jpg';
            if(im.cachedata.mine.id == uid){//添加的是自己
                layer.msg('不能添加自己');
                return false;
            }
            layui.each(im.cachedata.friend, function(index1, item1){
                layui.each(item1.list, function(index, item){
                    if (item.id == uid) {isAdd = true;layer.msg('玩命提示中');return;}//是否已经是好友
                });
            });
        }else{
            var default_avatar = './uploads/person/empty1.jpg';
            for (i in im.cachedata.group)//是否已经加群
            {
                if (im.cachedata.group[i].id == uid) {isAdd = true;break;}
            }
        }
        parent.layui.layim.add({//弹出添加好友对话框
            isAdd: isAdd
            ,approval: approval
            ,username: name || []
            ,uid:uid
            ,avatar: avatar
            // ,avatar: im['IsExist'].call(this, avatar)?avatar:default_avatar
            ,group:  im.cachedata.friend || []
            ,type: type
            ,submit: function(group,remark,index){//确认发送添加请求
                if (type == 'friend') {
                    $.get($("#contextPath").val()+'/cart/add.do', {uid: uid,type:1,remark:remark,groupId:group}, function (obj) {
                        if (!obj.code) {
                            // conn.subscribe({
                            //     to: uid,
                            //     message: remark
                            // });
                            layer.msg('你申请添加'+name+'为好友的消息已发送。请等待对方确认');
                        }else{
                            layer.msg(obj.msg);
                        }
                    });
                }else{
                    var options = {
                        groupId: uid,
                        success: function(resp) {
                            if (approval == '1') {
                                $.get('class/doAction.php?action=add_msg', {to: uid,msgType:3,remark:remark}, function (obj) {
                                    if (!obj.code) {
                                        layer.msg('你申请加入'+name+'的消息已发送。请等待管理员确认');
                                    }else{
                                        layer.msg('你申请加入'+name+'的消息发送失败。请刷新浏览器后重试');
                                    }
                                });

                            }else{
                                layer.msg('你已加入 '+name+' 群');
                            }
                        },
                        error: function(e) {
                            if(e.type == 17){
                                layer.msg('您已经在这个群组里了');
                            }
                        }
                    };
                    // conn.joinGroup(options);
                }
                layer.close(index);
            }

        });

    },
    delFriend:function(friendId){
        im.layim.removeList({//从我的列表删除
            type: 'friend' //或者group
            ,id: friendId //好友或者群组ID
        });
        im.removeHistory({//从我的历史列表删除
            type: 'friend' //或者group
            ,id: friendId //好友或者群组ID
        });
    },
    getInformation: function(data){//查看資料
        var id = data.id || {},type = data.type || {};
        var index = layer.open({
            type: 2
            ,title: type  == 'friend'?(im.cachedata.mine.id == id?'我的资料':'好友资料') :'群资料'
            ,shade: false
            ,maxmin: false
            // ,closeBtn: 0
            ,area: ['400px', '670px']
            ,skin: 'layui-box layui-layer-border'
            ,resize: true
            ,content: im.cachedata.base.Information+'?id='+id+'&type='+type
        });
    },
    menuChat: function(){
        return data = {
            text: "发送消息",
            icon: "&#xe63a;",
            callback: function(ele) {
                var othis = ele.parent(),
                    type = othis.data('type'),
                    name = othis.data('name'),
                    avatar = othis.data('img'),
                    id = othis.data('id');
                // id = (new RegExp(substr).test('layim')?substr.replace(/[^0-9]/ig,""):substr);
                im.layim.chat({
                    name: name
                    ,type: type
                    ,avatar: avatar
                    ,id: id
                });
            }
        }
    },
    menuInfo: function(){
        return data =  {
            text: "查看资料",
            icon: "&#xe62a;",
            callback: function(ele) {
                var othis = ele.parent(),type = othis.data('type'),id = othis.data('id');
                // id = (new RegExp(substr).test('layim')?substr.replace(/[^0-9]/ig,""):substr);
                im.getInformation({
                    id: id,
                    type:type
                });
            }
        }
    },
    menuChatLog: function(){
        return data =  {
            text: "聊天记录",
            icon: "&#xe60e;",
            callback: function(ele) {
                var othis = ele.parent(),type = othis.data('type'),name = othis.data('name'),
                    id = othis.data('id');
                im.getChatLog({
                    name: name,
                    id: id,
                    type:type
                });
            }
        }
    },
    menuNickName: function(){
        return data =  {
            text: "修改好友备注",
            icon: "&#xe6b2;",
            callback: function(ele) {
                var othis = ele.parent(),friend_id = othis.data('id'),friend_name = othis.data('name');
                layer.prompt({title: '修改备注姓名', formType: 0,value: friend_name}, function(nickName, index){
                    $.get('class/doAction.php?action=editNickName',{nickName:nickName,friend_id:friend_id},function(res){
                        var data = eval('(' + res + ')');
                        if (data.code == 0) {
                            var friendName = $("#layim-friend"+friend_id).find('span');
                            friendName.html(data.data);
                            layer.close(index);
                        }
                        layer.msg(data.msg);
                    });
                });

            }
        }
    },
    menuMove: function(html){
        return data = {
            text: "移动联系人",
            icon: "&#xe630;",
            nav: "move",//子导航的样式
            navIcon: "&#xe602;",//子导航的图标
            navBody: html,//子导航html
            callback: function(ele) {
                var friend_id = ele.parent().data('id');//要移动的好友id
                friend_name = ele.parent().data('name');

                var avatar = ele.parent().data("img");
                var default_avatar = './uploads/person/empty2.jpg';
                var signature = $('.layim-list-friend').find('#layim-friend'+friend_id).find('p').html();//获取签名
                var item = ele.find("ul li");
                item.hover(function() {
                    var _this = item.index(this);

                    var groupidx = item.eq(_this).data('groupidx');//将好友移动到分组的id
                    $.get(im.contentPath+'/cart/updateFriendType.do',{friendId:friend_id,typeId:groupidx},function(obj){
                        if (!obj.code) {
                            im.layim.removeList({//将好友从之前分组除去
                                type: 'friend'
                                ,id: friend_id //好友ID
                            });
                            im.layim.addList({//将好友移动到新分组
                                type: 'friend'
                                , avatar: avatar //好友头像
                                , username: friend_name //好友昵称
                                , groupid: groupidx //所在的分组id
                                , id: friend_id //好友ID
                                , sign: signature //好友签名
                            });
                        }
                        layer.msg(obj.msg);
                    });
                });
            }
        }
    },
    menuRemove: function(){
        return data = {
            text: "删除好友",
            icon: "&#xe640;",
            events: "removeFriends",
            callback: function(ele) {
                var othis = ele.parent(),friend_id = othis.data('id'),username,sign,avatar;

                layui.each(im.cachedata.friend, function(index1, item1){
                    layui.each(item1.list, function(index, item){
                        if (item.id == friend_id) {
                            username = item.username;
                            sign = item.sign?item.sign:"";
                            avatar = item.avatar;
                        }
                    });
                });
                layer.confirm('删除后对方将从你的好友列表消失，且以后不会再接收此人的会话消息。<div class="layui-layim-list"><li layim-event="chat" data-type="friend" data-index="0"><img src="'+avatar+'"><span>'+username+'</span><p>'+sign+'</p></li></div>', {
                    btn: ['确定','取消'], //按钮
                    title:['删除好友','background:#b4bdb8'],
                    shade: 0
                }, function(){
                    im.removeFriends(friend_id);
                }, function(){
                    var index = layer.open();
                    layer.close(index);
                });
            }
        }
    },
    menuAddMyGroup: function(){
        return  data =  {
            text: "添加分组",
            icon: "&#xe654;",
            callback: function(ele) {
                im.addMyGroup();
            }
        }

    },
    menuRename: function(){
        return  data =  {
            text: "重命名",
            icon: "&#xe642;",
            callback: function(ele) {
                var othis = ele.parent(),mygroupIdx = othis.data('id'),groupName = othis.data('name');
                layer.prompt({title: '请输入分组名，并确认', formType: 0,value: groupName}, function(mygroupName, index){
                    if (mygroupName) {
                        $.get(im.contentPath+'/cart/renameFriendType.do',{typeName:mygroupName,id:mygroupIdx},function(res){
                            if (!res.code) {
                                var friend_group = $(".layim-list-friend li");
                                for(var j = 0; j < friend_group.length; j++){
                                    var groupidx = friend_group.eq(j).find('h5').attr('data-groupidx');
                                    if(groupidx == mygroupIdx){//当前选择的分组
                                        friend_group.eq(j).find('h5').find('span').html(mygroupName);
                                    }
                                }
                                im.contextMenu();
                                layer.close(index);
                            }
                            layer.msg(res.msg);
                        });
                    }

                });
            }

        }
    },
    menuDelMyGroup: function(){
        return  data =  {
            text: "删除该组",
            icon: "&#x1006;",
            callback: function(ele) {
                var othis = ele.parent(),mygroupIdx = othis.data('id');
                layer.confirm('<div style="float: left;width: 17%;margin-top: 14px;"><i class="layui-icon" style="font-size: 48px;color:#cc4a4a">&#xe607;</i></div><div style="width: 83%;float: left;"> 选定的分组将被删除，组内联系人将会移至默认分组。</div>', {
                    btn: ['确定','取消'], //按钮
                    title:['删除分组','background:#b4bdb8'],
                    shade: 0
                }, function(){
                    im.delMyGroup(mygroupIdx);
                }, function(){
                    var index = layer.open();
                    layer.close(index);
                });
            }
        }
    },
    menuLeaveGroupBySelf: function(){
        return  data =  {
            text: "退出该群",
            icon: "&#xe613;",
            callback: function(ele) {
                var othis = ele.parent(),
                    group_id = othis.data('id'),
                    groupname = othis.data('name');
                avatar = othis.data('img');
                layer.confirm('您真的要退出该群吗？退出后你将不会再接收此群的会话消息。<div class="layui-layim-list"><li layim-event="chat" data-type="friend" data-index="0"><img src="'+avatar+'"><span>'+groupname+'</span></li></div>', {
                    btn: ['确定','取消'], //按钮
                    title:['提示','background:#b4bdb8'],
                    shade: 0
                }, function(){
                    var user = cachedata.mine.id;
                    var username = cachedata.mine.username;
                    im.leaveGroupBySelf(user,username,group_id);
                }, function(){
                    var index = layer.open();
                    layer.close(index);
                });
            }
        }
    },
    menuAddFriend: function(){
        return  data =  {
            text: "添加好友",
            icon: "&#xe654;",
            callback: function(ele) {
                var othis = ele;
                im.addFriendGroup(othis,'friend');
            }
        }
    },
    menuEditGroupNickName: function(){
        return  data =  {
            text: "修改群名片",
            icon: "&#xe60a;",
            callback: function(ele) {
                var othis = ele.parent();
                im.editGroupNickName(othis);
            }
        }
    },
    menuRemoveAdmin: function(){
        return  data =  {
            text: "取消管理员",
            icon: "&#xe612;",
            callback: function(ele) {
                var othis = ele.parent();
                im.removeAdmin(othis);
            }
        }
    },
    menuSetAdmin: function(){
        return  data =  {
            text: "设置为管理员",
            icon: "&#xe612;",
            callback: function(ele) {
                var othis = ele.parent(),user = othis.data('id');
                im.setAdmin(othis);
            }
        }
    },
    menuLeaveGroup: function(){
        return  data =  {
            text: "踢出本群",
            icon: "&#x1006;",
            callback: function(ele) {
                var othis = ele.parent();
                var friend_id = ele.parent().data('id');//要禁言的id
                var username = ele.parent().data('name');
                var groupIdx = ele.parent().data('groupidx');
                var list = new Array();
                list[0] = friend_id;
                im.leaveGroup(groupIdx,list,username)
            }
        }
    },
    menuGroupMemberGag: function(){
        return  data =  {
            text: "禁言",
            icon: "&#xe60f;",
            nav: "gag",//子导航的样式
            navIcon: "&#xe602;",//子导航的图标
            navBody: '<ul><li class="ui-gag-menu-item" data-gag="10m"><a href="javascript:void(0);"><span>禁言10分钟</span></a></li><li class="ui-gag-menu-item" data-gag="1h"><a href="javascript:void(0);"><span>禁言1小时</span></a></li><li class="ui-gag-menu-item" data-gag="6h"><a href="javascript:void(0);"><span>禁言6小时</span></a></li><li class="ui-gag-menu-item" data-gag="12h"><a href="javascript:void(0);"><span>禁言12小时</span></a></li><li class="ui-gag-menu-item" data-gag="1d"><a href="javascript:void(0);"><span>禁言1天</span></a></li></ul>',//子导航html
            callback: function(ele) {
                var friend_id = ele.parent().data('id');//要禁言的id
                friend_name = ele.parent().data('name');
                groupidx = ele.parent().data('groupidx');
                var item = ele.find("ul li");
                item.hover(function() {
                    var _index = item.index(this),gagTime = item.eq(_index).data('gag');//禁言时间
                    $.get('class/doAction.php?action=groupMemberGag',{gagTime:gagTime,groupidx:groupidx,friend_id:friend_id},function(resp){
                        var data = eval('(' + resp + ')');
                        if (data.code == 0) {
                            var gagTime = data.data.gagTime;
                            var res = {mine: {
                                    content: gagTime+'',
                                    timestamp: data.data.time
                                },
                                to: {
                                    type: 'group',
                                    id: groupidx+"",
                                    cmd: {
                                        id: friend_id,
                                        cmdName:'gag',
                                        cmdValue:data.data.value
                                    }
                                }}
                            im.sendMsg(res);
                            $("ul[data-groupidx="+groupidx+"] #"+friend_id).attr('gagtime',gagTime);
                        }
                        layer.msg(data.msg);
                    });
                });
            }
        }
    },
    menuLiftGroupMemberGag: function(){
        return  data =  {
            text: "取消禁言",
            icon: "&#xe60f;",
            callback: function(ele) {
                var friend_id = ele.parent().data('id');//要禁言的id
                friend_name = ele.parent().data('name');
                groupidx = ele.parent().data('groupidx');
                $.get('class/doAction.php?action=liftGroupMemberGag',{groupidx:groupidx,friend_id:friend_id},function(resp){
                    var data = eval('(' + resp + ')');
                    if (data.code == 0) {
                        var res = {mine: {
                                content: '0',
                                timestamp: data.data.time
                            },
                            to: {
                                type: 'group',
                                id: groupidx+"",
                                cmd: {
                                    id: friend_id,
                                    cmdName:'liftGag',
                                    cmdValue:data.data.value
                                }
                            }}
                        im.sendMsg(res);
                        $("ul[data-groupidx="+groupidx+"] #"+friend_id).attr('gagtime',0);
                    }
                    layer.msg(data.msg);
                });
            }
        }
    },
    contextMenu : function(){//定义右键操作
        var my_spread = $('.layim-list-friend >li');
        my_spread.mousedown(function(e){
            var data = {
                contextItem: "context-friend", // 添加class
                target: function(ele) { // 当前元素
                    // $(".context-friend").attr("data-id",ele[0].id.replace(/[^0-9]/ig,"")).attr("data-name",ele.find("span").html());
                    $(".context-friend").attr("data-id",ele.attr("data-id")).attr("data-name",ele.find("span").html());
                    $(".context-friend").attr("data-img",ele.find("img").attr('src')).attr("data-type",'friend');
                },
                menu:[]
            };
            data.menu.push(im.menuChat());
            data.menu.push(im.menuInfo());
            data.menu.push(im.menuChatLog());
            data.menu.push(im.menuNickName());
            console.log($(this).find('h5'));
            var currentGroupidx = $(this).find('h5').attr('data-groupidx');//当前分组id
            if(my_spread.length >= 2){ //当至少有两个分组时
                var html = '<ul>';
                for (var i = 0; i < my_spread.length; i++) {
                    var groupidx = my_spread.eq(i).find('h5').attr('data-groupidx');
                    if (currentGroupidx != groupidx) {
                        var groupName = my_spread.eq(i).find('h5 span').html();
                        html += '<li class="ui-move-menu-item" data-groupidx="'+groupidx+'" data-groupName="'+groupName+'"><a href="javascript:void(0);"><span>'+groupName+'</span></a></li>'
                    };
                };
                html += '</ul>';
                data.menu.push(im.menuMove(html));
            }
            data.menu.push(im.menuRemove());
            $(".layim-list-friend >li > ul > li").contextMenu(data);//好友右键事件
        });

        $(".layim-list-friend >li > h5").mousedown(function(e){
            var data = {
                contextItem: "context-mygroup", // 添加class
                target: function(ele) { // 当前元素
                    $(".context-mygroup").attr("data-id",ele.data('groupidx')).attr("data-name",ele.find("span").html());
                },
                menu: []
            };
            data.menu.push(im.menuAddMyGroup());
            data.menu.push(im.menuRename());
            if ($(this).parent().find('ul li').data('index') !== 0) {data.menu.push(im.menuDelMyGroup()); };

            $(this).contextMenu(data);  //好友分组右键事件
        });
        $(".layim-null").mousedown(function(e){
            var data = {
                contextItem: "context-mygroup", // 添加class
                target: function(ele) { // 当前元素
                    console.log(ele.parent().prev())
                    $(".context-mygroup").attr("data-id",ele.parent().prev().data('groupidx')).attr("data-name",ele.find("span").html());
                },
                menu: []
            };
            data.menu.push(im.menuAddMyGroup());
            data.menu.push(im.menuRename());
            if ($(this).parent().find('ul li').data('index') !== 0) {data.menu.push(im.menuDelMyGroup()); };

            $(this).contextMenu(data);  //好友分组右键事件
        });


        $(".layim-list-group > li").mousedown(function(e){
            var data = {
                contextItem: "context-group", // 添加class
                target: function(ele) { // 当前元素
                    $(".context-group").attr("data-id",ele.attr("data-id")).attr("data-name",ele.find("span").html())
                        .attr("data-img",ele.find("img").attr('src')).attr("data-type",'group')
                },
                menu: []
            };
            data.menu.push(im.menuChat());
            // data.menu.push(im.menuInfo());
            data.menu.push(im.menuChatLog());
            data.menu.push(im.menuLeaveGroupBySelf());

            $(this).contextMenu(data);  //面板群组右键事件
        });


        $('.groupMembers > li').mousedown(function(e){//聊天页面群组右键事件
            var data = {
                contextItem: "context-group-member", // 添加class
                isfriend: $(".context-group-member").data("isfriend"), // 添加class
                target: function(ele) { // 当前元素
                    $(".context-group-member").attr("data-id",ele.attr("data-id"));
                    $(".context-group-member").attr("data-img",ele.find("img").attr('src'));
                    $(".context-group-member").attr("data-name",ele.find("span").html());
                    $(".context-group-member").attr("data-isfriend",ele.attr('isfriend'));
                    $(".context-group-member").attr("data-manager",ele.attr('manager'));
                    $(".context-group-member").attr("data-groupidx",ele.parent().data('groupidx'));
                    $(".context-group-member").attr("data-type",'friend');
                },
                menu:[]
            };
            var _this = $(this);
            var groupInfo = im.layim.thisChat().data;
            var _time = (new Date()).valueOf();//当前时间
            var _gagTime = parseInt(_this.attr('gagTime'));//当前禁言时间
            if (cachedata.mine.id !== _this.attr('id')) {
                data.menu.push(im.menuChat());
                data.menu.push(im.menuInfo());
                if(3 == e.which && $(this).attr('isfriend') == 0 ){ //点击右键并且不是好友
                    data.menu.push(im.menuAddFriend())
                }
            }else{
                data.menu.push(im.menuEditGroupNickName());
            }
            if (groupInfo.manager == 1 && cachedata.mine.id !== _this.attr('id')) {//是群主且操作的对象不是自己
                if (_this.attr('manager') == 2) {
                    data.menu.push(im.menuRemoveAdmin());
                }else if (_this.attr('manager') == 3) {
                    data.menu.push(im.menuSetAdmin());
                }
                data.menu.push(im.menuEditGroupNickName());
                data.menu.push(im.menuLeaveGroup());
                if (_gagTime < _time) {
                    data.menu.push(im.menuGroupMemberGag());
                }else{
                    data.menu.push(im.menuLiftGroupMemberGag());
                }
            }//群主管理

            layui.each(cachedata.group, function(index, item){
                if (item.id == _this.parent().data('groupidx') && item.manager == 2 && _this.attr('manager') == 3 && cachedata.mine.id !== _this.attr('id')) {//管理员且操作的是群员
                    data.menu.push(im.menuEditGroupNickName());
                    data.menu.push(im.menuLeaveGroup());
                    if (_gagTime < _time) {
                        data.menu.push(im.menuGroupMemberGag());
                    }else{
                        data.menu.push(im.menuLiftGroupMemberGag());
                    }
                }//管理员管理
            })
            $(".groupMembers > li").contextMenu(data);
        })


    },
    addMyGroup: function(){//新增分组
        layer.prompt({title: '设置分组名称', formType: 0,value: "未命名"}, function(groupname, index){
            $.get(im.contentPath+'/cart/addFriendType.do',{typeName:groupname},function(obj){
                if (!obj.code) {
                    $('.layim-list-friend').append('<li><h5 layim-event="spread" lay-type="false" data-groupidx="'+obj.data.id+'"><i class="layui-icon">&#xe602;</i><span>'+obj.data.typeName+'</span><em>(<cite class="layim-count"> 0</cite>)</em></h5><ul class="layui-layim-list"><span class="layim-null">该分组下暂无好友</span></ul></li>');
                    im.contextMenu();
                    layer.close(index);
                }else{
                    layer.msg(data.msg);
                }
            });
        });
    },
    delMyGroup: function(groupidx){//删除分组
        $.get(im.contentPath+'/cart/delFriendType.do', {id:groupidx}, function (res) {
            if (!data.code) {
                var group = $('.layim-list-friend li') || [];
                for(var j = 0; j < group.length; j++){//遍历每一个分组
                    groupList = group.eq(j).find('h5').attr('data-groupidx');
                    if(groupList == groupidx){//要删除的分组
                        if (group.eq(j).find('ul span').hasClass('layim-null')) {//删除的分组下没有好友
                            group.eq(j).remove();
                        }else{
                            // var html = group.eq(j).find('ul').html();//被删除分组的好友
                            var friend = group.eq(j).find('ul li');
                            var number = friend.length;//被删除分组的好友个数
                            for (var i = 0; i < number; i++) {
                                var friend_id = friend.eq(i).attr('id').replace(/^layim-friend/g, '');//好友id
                                var friend_name = friend.eq(i).find('span').html();//好友id
                                var signature = friend.eq(i).find('p').html();//好友id
                                var avatar = '../uploads/person/'+friend_id+'.jpg';
                                var default_avatar = './uploads/person/empty2.jpg';
                                im.layim.removeList({//将好友从之前分组除去
                                    type: 'friend'
                                    ,id: friend_id //好友ID
                                });
                                im.layim.addList({//将好友移动到新分组
                                    type: 'friend'
                                    , avatar: im['IsExist'].call(this, avatar)?avatar:default_avatar //好友头像
                                    , username: friend_name //好友昵称
                                    , groupid: data.data //将好友添加到默认分组
                                    , id: friend_id //好友ID
                                    , sign: signature //好友签名
                                });
                            };
                        }

                    }
                }
                im.contextMenu();
                layer.close(layer.index);
            }else{
                layer.msg(data.msg);
            }
        });
    },
    removeFriends: function (friendId) {
            $.get(im.contentPath+'/cart/delFriend.do', {friendId: friendId}, function (res) {
                if (!res.code) {
                    var index = layer.open();
                    layer.close(index);
                    im.delFriend(friendId);
                }else{
                    layer.msg(data.msg);
                }
            });
    },
    removeHistory: function(data){//删除好友或退出群后清除历史记录
        var history = im.cachedata.local.history;
        delete history[data.type+data.id];
        im.cachedata.local.history = history;
        layui.data('layim', {
            key: im.cachedata.mine.id
            ,value: im.cachedata.local
        });
        $('#layim-history'+data.id).remove();
        var hisElem = $('.layui-layim').find('.layim-list-history');
        var none = '<li class="layim-null">暂无历史会话</li>'
        if(hisElem.find('li').length === 0){
            hisElem.html(none);
        }
    },
}
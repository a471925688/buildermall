define(function (require, exports, module) {
    var selectedFriendID = 0;
    var chatList = [];
    var selectedChat = {};
    var chatLogUrl = "/WebIM/MyMessageHistory";
    var active = {};
    var menu = require("easyui/menu.module");
    var laytpl = require("laytpl");
    layui.use('layim', function (layim) {
        layim.config({
            brief: false, //是否简约模式（如果true则不显示主面板）
            title: '我的WebIM',
            min: true,
            //initSkin: '3.jpg',
            //isAudio: true,
            //isVideo: true,
            notice: false,
            voice: '',
            isfriend: true,
            isgroup: true,
            maxLength: 500,
            copyright: false,
            //msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html', //消息盒子页面地址，若不开启，剔除该项即可
            //find: layui.cache.dir + 'css/modules/layim/html/find.html', //发现页面地址，若不开启，剔除该项即可
            chatLog: chatLogUrl,
            init: {
                url: '/WebIM/GetMyFriendList',
                type: 'get',
                data: {}
            },
            members: {
                url: '/WebIM/GetGroupMembers',
                data: {}
            }
        });
        layim.on('ready', function (options) {
            $("#layui-layim").on('contextmenu', "ul.layim-list-friend", function (e) {
                e.preventDefault();
                if (e.target.tagName == "UL") {
                    $("#id-easyui-menu-1").menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            });
            $("#layui-layim").on('contextmenu', "ul.layim-list-friend li h5", function (e) {
                e.preventDefault();
                $("#id-easyui-menu-2").menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            });
            $("#layui-layim").on('contextmenu', "ul.layim-list-friend li ul.layui-layim-list li", function (e) {
                e.preventDefault();
                selectedFriendID = $(this).attr("data-id");
                $("#id-easyui-menu-3").menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            });
            active.initMenu();
            homePage.getMessageByLayIM = function (msg) {
                var msgData = eval("(" + msg + ")")
                layim.getMessage(msgData);
                //更新消息为已经读取状态
                if ($("#layui-layim-chat").length > 0) {
                    if (!$("#layui-layim-chat").parent().is(":hidden")) {
                        if (selectedChat.id == msgData.id)
                            active.readMessage();
                    }
                }
            }
            homePage.updateLayIMFriendStatus = function (userID, isOnline) {
                if (isOnline)
                    layim.setFriendStatus(userID, 'online');
                else
                    layim.setFriendStatus(userID, 'offline');
            }
            //需要延迟加载
            setTimeout(function () {
                active.setAllStatus();
                //加载离线消息
                active.loadUnReadMsgList();
            }, 3000);
        });
        layim.on('online', function (status) {
            changeMineStatusByLayIM(homePage.userID, status);
        });
        layim.on('sign', function (value) {
            $.post("/WebIM/UpdateUserSign", {
                userID: homePage.userID,
                sign: value
            }, function (msg) {
                if (msg == "success") {
                    console.log("签名已保存.")
                }
                else
                    myLayer.extend.alert('修改失败:' + msg, 8);
            });
        });
        layim.on('sendMessage', function (res) {
            if (res.to.type == "group") {
                $.ajaxGet({
                    url: "/WebIM/GetGroupMembers",
                    data: { id: res.to.id },
                    success: function (result) {
                        var ids = "";
                        $.each(result.data.list, function (i, item) {
                            if (item.id != homePage.userID) {
                                ids += item.id + ",";
                            }
                        });
                        res.to.userID = $.trimend(ids, ',');
                        saveMessage(res, function () {
                            sendMessageByLayIM(JSON.stringify(res.mine), JSON.stringify(res.to));
                        });
                    }
                });
            }
            else {
                res.to.userID = res.to.id;
                saveMessage(res, function () {
                    sendMessageByLayIM(JSON.stringify(res.mine), JSON.stringify(res.to));
                });
            }
        });
        layim.on('members', function (data) {
            console.log(data);
        });
        layim.on('chatChange', function (obj) {
            chatLogUrl = "/WebIM/MyMessageHistory?id=" + obj.data.id + "&type=" + obj.data.type;
            var cache = layui.layim.cache();
            var local = layui.data('layim')[cache.mine.id]; //获取当前用户本地数据
            if (local.chatlog) {
                var chat = chatList.where("o.type=='" + obj.data.type + "'&&o.id=='" + obj.data.id + "'")[0];
                if (!chat) {
                    selectedChat.id = obj.data.id;
                    selectedChat.type = obj.data.type;
                    chatList.push({
                        id: obj.data.id,
                        type: obj.data.type,
                        isRead: false
                    });
                    active.readMessage();
                }
            }
        });
        active = {
            setAllStatus: function () {
                $.ajaxGet({
                    url: window.CONFIG.SIGNALR_BASEURL + 'api/OnlineUser/GetOnlineUsers',
                    data: {
                        key: homePage.appName,
                        userID: homePage.userID
                    },
                    success: function (result) {
                        if (result) {
                            var onlineUsers = JSON.parse(result);
                            $(onlineUsers).each(function (i, item) {
                                layim.setFriendStatus(item.UserInfo.ID, 'online');
                            })
                        }
                    }
                });
            },
            reload: function (clearCache) {
                $.ajaxGet({
                    url: "/WebIM/GetMyFriendList",
                    data: { clearCache: clearCache },
                    success: function (result) {
                        var cache = layui.layim.cache();
                        var local = layui.data('layim')[cache.mine.id]; //获取当前用户本地数据
                        if (result.code == 0) {
                            laytpl($("#id-layim-list-friend-template").html()).render({
                                list: result.data.friend
                            }, function (html) {
                                $("#layui-layim ul.layim-list-friend").replaceWith(html);
                                $("#layui-layim ul.layim-list-friend li h5").each(function (i, item) {
                                    var spread = $(item).attr("layim-event") + i;
                                    if (local[spread] == "true")
                                        $(item).click();
                                    active.setAllStatus();
                                });
                            });
                        }
                        else
                            myLayer.extend.alert('按钮加载失败:' + result.msg, 8);
                    }
                });
            },
            removeFriend: function () {
                myLayer.extend.confirm("您确认要删除当前好友吗.", function () {
                    $.post("/WebIM/DeleteMyFriend", { "userID": selectedFriendID },
                        function (msg) {
                            if (msg == "success") {
                                //删除一个好友
                                layim.removeList({
                                    id: selectedFriendID,
                                    type: 'friend'
                                });
                            }
                            else
                                myLayer.extend.alert('删除失败:' + msg, 8);
                        });
                });
            },
            removeGroup: function () {
                layer.msg('已成功删除[前端群]', {
                    icon: 1
                });
                //删除一个群组
                layim.removeList({
                    id: 101
                    , type: 'group'
                });
            },
            //置灰离线好友
            setGray: function () {
                layim.setFriendStatus(168168, 'offline');

                layer.msg('已成功将好友[马小云]置灰', {
                    icon: 1
                });
            },
            initMenu: function () {
                $.ajaxGet({
                    url: "/WebIM/GetFriendGroupTreeData",
                    success: function (result) {
                        var jsonData = JSON.parse(result);
                        $(jsonData).each(function (i, item) {
                            $("#id-easyui-menu-3").find("[name='easyui-menu-sub']").append("<div name='moveTo' id=" + item.id + ">" + item.text + "</div>");
                        });
                        //右键菜单click
                        menu.initMenu($("#id-div-layim-menu div[name='easyui-menu']"), {
                            onClick: function (item) {
                                if (item.name == "editFriendGroup") {//分组管理器
                                    homePage.openFrameTab("分组管理器", "/WebIM/FriendGroupManage");
                                }
                                else if (item.name == "editFriend") {//分组管理器
                                    homePage.openFrameTab("好友管理器", "/WebIM/FriendManage");
                                }
                                else if (item.name == "reload") {
                                    active.reload(true);
                                }
                                else if (item.name == "removeFriend") {
                                    active.removeFriend();
                                }
                                else if (item.name == "showOnline") {

                                    myLayer.extend.alert('显示在线好友', 1);
                                }
                                else if (item.name = "moveTo") {
                                    var top = $("#layui-layim ul.layim-list-friend").scrollTop();
                                    $.ajaxPost({
                                        url: "/WebIM/ChangeFriendGroup",
                                        data: { "userID": selectedFriendID, "groupID": item.id },
                                        success: function (msg) {
                                            if (msg == "success") {//保存成功
                                                active.reload(true);
                                                $("#layui-layim").animate({ 'scrollTop': top }, 100)
                                            }
                                            else {
                                                myLayer.extend.alert('保存失败:' + msg, 8);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            },
            loadUnReadMsgList: function () {
                $.ajaxGet({
                    url: "/WebIM/GetUnReadMessageList",
                    success: function (result) {
                        if (result.status == 1) {
                            var cache = layui.layim.cache();
                            var local = layui.data('layim')[cache.mine.id]; //获取当前用户本地数据
                            if (local) {
                                delete local.chatlog;
                                //向localStorage同步数据
                                layui.data('layim', {
                                    key: cache.mine.id
                                    , value: local
                                });
                            }
                            $(result.data).each(function (i, item) {
                                var time = utils.dateFormatter(item.CreateOn, "yyyy/MM/dd hh:mm:ss.S");
                                layim.getMessage({
                                    username: item.UserName,
                                    avatar: item.FaceSrc,
                                    id: item.ChatID,
                                    type: item.ChatType,
                                    content: item.ChatContent,
                                    mine: item.Mine,
                                    fromid: item.FromUserID,
                                    timestamp: new Date(time)
                                });
                            });
                        }
                        else
                            myLayer.extend.alert('消息加载失败:' + result.error, 8);
                    }
                });
            },
            readMessage: function () {
                $.ajaxPost({
                    url: "/WebIM/ReadMessage/",
                    data: {
                        type: selectedChat.type,
                        chatID: selectedChat.id
                    },
                    success: function (msg) {
                        if (msg == "success") {
                            var chat = chatList.where("o.type=='" + selectedChat.type + "'&&o.id=='" + selectedChat.id + "'")[0];
                            chat.isRead = true;
                        }
                        else
                            myLayer.extend.alert('消息发送失败:' + msg, 8);
                    }
                });
            }
        }
        homePage.customChat = function (params) {
            layim.chat({
                id: params.id,
                name: params.userName,
                type: 'friend',
                avatar: params.avatar
            });
        }
        function saveMessage(res, fn) {
            $.ajaxPost({
                url: "/WebIM/addMessage/",
                data: {
                    UserName: res.mine.username,
                    FaceSrc: res.mine.avatar,
                    ChatID: res.to.type == "friend" ? res.mine.id : res.to.id,
                    FromUserID: res.mine.id,
                    ChatType: res.to.type,
                    ChatContent:$.trim(res.mine.content),
                    Mine: false,
                    IsRead: false,
                    ToUserID: res.to.userID
                },
                success: function (msg) {
                    if (msg == "success")
                        fn();
                    else
                        myLayer.extend.alert('消息发送失败:' + msg, 8);
                }
            });
        }
    });
});

WebIM.js
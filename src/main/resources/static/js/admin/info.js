var AdminInfo;
var adminInfoHandle=function () {

}
adminInfoHandle.prototype={
    //初始化管理员页面
    initInfoView:function () {
        //初始化管理员列表
        var data = {userName:$("#loginUserName").val()};
        layui.use(['table','form'], function() {
            var table = layui.table
                ,form=layui.form;
            var tableIns = table.render({
                elem: '#adminInfoList'
                , height: $(window).height()-100
                , url: $("#contextPath").val()+'/sysLog/getSysLogPageByUserName.do' //数据接口
                , title: '管理员登录记录表'
                , page: true //开启分页
                , limit: 15
                ,cellMinWidth: 80
                ,where: data
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'logId', title: 'ID', sort: true, fixed: 'left'}
                    , {field: '', title: '<span class="lang" key="content">内容</span>', align: 'center', templet: function (d) {
                            if(d.logState){
                                return"<span class='lang' key='login_failed'>登录失败</span>"
                            }else {
                                return "<span class='lang' key='login_successfully'>登录成功</span>"
                            }
                        }}
                    , {field: 'logAddr', title: '<span class="lang" key="login_address">登录地点</span>', align: 'center'}
                    , {field: 'logUserName', title: '<span class="lang" key="login_name">用户名</span>', align: 'center'}
                    , {field: 'logIp', title: '<span class="lang" key="client_IP">客户端ip</span>', align: 'center'}
                    , {field: 'logTime', title: '<span class="lang" key="login_time">登录时间</span>',  align: 'center'}
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

        })
    },
    //修改信息按钮点击事件
    editAdminInfoView:function(adminId){
        $('.text_info').attr("disabled", false);
        $('.text_info').addClass("add");
        $('#Personal').find('.xinxi').addClass("hover");
        $('#Personal').find('.btn-success').css({'display': 'block'});
        $("input[name='adminGender'][value='"+$("#adminGender").attr("key")+"']").attr("checked",true);
    },
    //保存修改
    editAdminInfo:function() {
        var num;
        var str = "";
        $(".xinxi input[type$='text']").each(function (n) {
            if ($(this).val() == "") {
                layer.alert(str += "" + $(this).attr("name") + "不能为空！\r\n", {
                    title: '提示框',
                    icon: 0,
                });
                num++;
                return false;
            }
        });
        if (num > 0) {
            return false;
        }
        else {
            var param = {
                loginUserName:$("#loginUserName").val()
                ,adminGender:$("input[name='adminGender']:checked").val()
                ,adminPhone:$("#adminPhone").val()
                ,adminEmail:$("#adminEmail").val()
                ,adminRealName:$("#adminRealName").val()
                ,adminAddress:$("#adminAddress").val()
            }
            $.post($("#contextPath").val()+'/admin/editCurrentAdminInfo.do', param, function (obj) {
                var icon;
                if(obj.code){
                    icon = 2;
                }else {
                    icon=1
                }
                layer.alert(obj.msg, {
                    title: '提示框',
                    icon: icon,
                });
            })
            $('#Personal').find('.xinxi').removeClass("hover");
            $('#Personal').find('.text_info').removeClass("add").attr("disabled", true);
            $('#Personal').find('.btn-success').css({'display': 'none'});
            $("#adminGender").text(param.adminGender)
            $("#adminGender").attr("key",param.adminGender)
        }
    },
    //修改密码
    changePassword:function () {
        layer.open({
            type: 1,
            title: '修改密码',
            area: ['600px', '300px'],
            shadeClose: true,
            content: $('#change_Pass'),
            btn: ['确认修改'],
            yes: function (index, layero) {
                if ($("#password").val() == "") {
                    layer.alert('原密码不能为空!', {
                        title: '提示框',
                        icon: 0,

                    });
                    return false;
                }
                if ($("#Nes_pas").val() == "") {
                    layer.alert('新密码不能为空!', {
                        title: '提示框',
                        icon: 0,

                    });
                    return false;
                }

                if ($("#c_mew_pas").val() == "") {
                    layer.alert('确认新密码不能为空!', {
                        title: '提示框',
                        icon: 0,

                    });
                    return false;
                }
                if (!$("#c_mew_pas").val || $("#c_mew_pas").val() != $("#Nes_pas").val()) {
                    layer.alert('密码不一致!', {
                        title: '提示框',
                        icon: 0,
                    });
                    return false;
                }
                else {
                    var param = {
                        oldPassWord:$("#password").val()
                        ,newPassWord:$("#c_mew_pas").val()
                    }
                    $.post($("#contextPath").val()+'/admin/editLoginPassWord.do', param, function (obj) {
                        var icon;
                        if(obj.code){
                            icon = 2;
                        }else {
                            icon=1
                        }
                        layer.alert(obj.msg, {
                            title: '提示框',
                            icon: icon,
                        });
                    })

                }
                layer.close(index);
            }
        });
    }
}
AdminInfo=new adminInfoHandle();
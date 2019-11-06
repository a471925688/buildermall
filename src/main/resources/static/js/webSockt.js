$(document).ready(function(){

})
var WenSocketUtil;
var websockt=function () {

}
websockt.prototype = {
    socket:{},
    config:{
        openUrl:"",
        cid:'',
    },
    init:function (config) {
        this.config = config?config:this.config;
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            this.socket = new WebSocket(this.config.openUrl+this.config.cid);
            //打开事件
            this.socket.onopen = this.onopen;
            // //获得消息事件
            // this.socket.onmessage = this.onmessage;
            //关闭事件
            this.socket.onclose = this.onclose;
            //发生了错误事件
            this.socket.onerror = this.onerror;
        }
    },
    onopen:function(){
        console.log("Socket 已打开");
        //socket.send("这是来自客户端的消息" + location.href + new Date());
    },
    onclose:function(){
        console.log("Socket已关闭");
        this.socket = new WebSocket(this.config.openUrl+this.config.cid);
        // layer.alert('鏈接超時!', {
        //     skin: 'layui-layer-molv' //样式类名
        //     ,closeBtn: 0
        //     ,anim: 6 //动画类型
        // }, function(){
        //     location.reload()
        // });
    },
    onerror:function () {
        alert("Socket发生了错误");
        var index= layer.alert('webScoket鏈接錯誤!!', {
            skin: 'layui-layer-molv' //样式类名
            ,closeBtn: 0
            ,anim: 6 //动画类型
        }, function(){
            layer.close(index)
        });
    },
    send:function(data){
        console.log("发送消息");
        //發送消息
        this.socket.send(data);
    },
    onmessage:function (fun) {
        this.socket.onmessage = fun;
    }
}
WenSocketUtil = new websockt();
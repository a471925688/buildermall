var cropperImage = {};
$(document).ready(function() {


    //弹出框水平垂直居中
    (window.onresize = function () {
        var win_height = $(window).height();
        var win_width = $(window).width();
        if (win_width <= 768){
            $(".tailoring-content").css({
                "top": (win_height - $(".tailoring-content").outerHeight())/2,
                "left": 0
            });
        }else{
            $(".tailoring-content").css({
                "top": (win_height - $(".tailoring-content").outerHeight())/2,
                "left": (win_width - $(".tailoring-content").outerWidth())/2
            });
        }
    })();

    $('#tailoringImg').cropper({
        aspectRatio: 722 / 573,// 默认比例
        preview: '.previewImg',// 预览视图
        guides: false, // 裁剪框的虚线(九宫格)
        autoCropArea: 0.8, // 0-1之间的数值，定义自动剪裁区域的大小，默认0.8
        movable: true, // 是否允许移动图片
        dragCrop: false, // 是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
        movable: true, // 是否允许移动剪裁框
        resizable: true, // 是否允许改变裁剪框的大小
        zoomable: true, // 是否允许缩放图片大小
        mouseWheelZoom: true, // 是否允许通过鼠标滚轮来缩放图片
        touchDragZoom: true, // 是否允许通过触摸移动来缩放图片
        rotatable: true, // 是否允许旋转图片
        crop: function (e) {
            // 输出结果数据裁剪图像。
        }
    });
// 旋转
    $(".cropper-rotate-btn").on("click", function () {
        $('#tailoringImg').cropper("rotate", 45);
    });
// 复位
    $(".cropper-reset-btn").on("click", function () {
        $('#tailoringImg').cropper("reset");
    });
// 换向
    var flagX = true;
    $(".cropper-scaleX-btn").on("click", function () {
        if (flagX) {
            $('#tailoringImg').cropper("scaleX", -1);
            flagX = false;
        } else {
            $('#tailoringImg').cropper("scaleX", 1);
            flagX = true;
        }
        flagX != flagX;
    });




// 关闭裁剪框
    $(".closeTailor").on("click", function () {
        $(".tailoring-container").toggle();
    });

})
// 确定按钮点击事件
function sureCut(fun){
    if ($("#tailoringImg").attr("src") == null) {
        return false;
    } else {
        var cas = $('#tailoringImg').cropper('getCroppedCanvas');// 获取被裁剪后的canvas
        var base64 = cas.toDataURL('image/jpeg'); // 转换为base64
        $("#finalImg").prop("src", base64);// 显示图片
        var src = $('#tailoringImg').attr("src");
        var url = $("#contextPath").val()+(src.indexOf("textFileDown")!=-1? '/file/cutPicturesText.do': '/file/cutPictures.do');
        var fileName = src.substring(src.indexOf("?")+1,src.length);
        uploadFile(url,encodeURIComponent(base64),fileName,fun)//编码后上传服务器

        $(".tailoring-container").toggle();// 关闭裁剪框

    }
}

//ajax请求上传
function uploadFile(url,file,fileName,fun) {
    $.ajax({
        url:url,
        type: 'POST',
        data: "newFile=" + file+"&imagePath="+cropperImage.imagePath+"&imageThumbnailsPath="+cropperImage.imageThumbnailsPath,
        // data:cropperImage,
        async: true,
        success: function (data) {
            fun();
            console.log(data)
        }
    });
}
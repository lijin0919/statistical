function chooseChannel(data) {
    alert(data)
    $("#channelBtn").html(data+"<span class=\"caret\"></span>");
    //上传关键词选择渠道
    $.ajax({
        url:"channel",
        type:"GET",
        data:{channel:data},
        success:function (obj) {
            alert(obj);
        }
    });
}

$(document).ready(function () {






});
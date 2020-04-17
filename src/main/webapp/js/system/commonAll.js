$(function () {
    $.messager.model = {
        ok: {text: '确认'},
        cancel: {text: '取消'}
    }
})

    function handlerMessage(data) {
        if (data.success){
            $.messager.alert("温馨提示",'操作成功2秒后自动跳转');
            setTimeout(function () {
                window.location.reload();
            },1000)

        }else{
            $.messager.popup(data.msg);
        }

}
//禁用数组添加[]的功能
jQuery.ajaxSettings.traditional = true;
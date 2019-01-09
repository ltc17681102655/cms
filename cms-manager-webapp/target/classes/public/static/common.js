!function($){
    $.fn.setData=function(data){
        var self=$(this);
        $(this).find("input,textarea").each(function(){
            try{
                $(this).val( data[this.name]||this.value );
                $(this).trigger("change");
            }catch(e){}
        });

        $(this).find("select").each(function(){
            var t=this,v = data[t.name];
            $(t).find("option").each(function(){
                try{
                    if(this.value==v){
                        $(t).val(v);
                        $(t).trigger("change");
                        return self;
                    }
                }catch(e){}
            });

            $(t).find("option").each(function(){
                try{
                    if($(this).text()==v){
                        $(t).find(":selected").prop("selected",false);
                        $(this).prop("selected",true);
                        $(t).trigger("change");
                        return self;
                    }
                }catch(e){}
            });
        });

        $(this).find(":checkbox,:radio").each(function(){
            try{
                var v=data[this.name],
                    _v=$(this).val();
                if(_v && $.trim(_v).length>0 && v==this.value || $.inArray(_v,v)){
                    $(this).prop("checked","checked");
                }
            }catch(e){}
        });
    }
}((window.jQuery))


/**
 * bootbox 扩展，直接替换自带函数.
 * @author <a href="mailto:yangmujiang@xiaomashijia.com">Reamy(杨木江)</a>
 * @date 2014-10-16  11:05:13
 */
$.confirm = function(message,confirmF,cancelF) {
    bootbox.confirm({message:message,buttons:{ cancel: {label:"取消"}, confirm: {label:"确定"} },callback:function (result) {
            if (result) {
                if ($.isFunction(confirmF)) {
                    confirmF();
                }
            } else {
                if ($.isFunction(cancelF)) {
                    cancelF();
                }
            }
        }});

    return 'bootbox';
}

$.alert = function (message,callBack) {
    bootbox.alert({message:message,buttons:{ok: {label:"确定"} },callback:function () {
            if ($.isFunction(callBack)) {
                callBack();
            }
        }});

    return 'bootbox';
}

$.prompt = function (title,value,confirmF,cancelF,inputType) {
    bootbox.prompt({title:title,value:value?value:"",inputType:inputType?inputType:"text",buttons:{ cancel: {label:"取消"}, confirm: {label:"确定"} },callback:function (result) {
            if (result!=null) {
                if ($.isFunction(confirmF)) {
                    confirmF(result);
                }
            } else {
                if ($.isFunction(cancelF)) {
                    cancelF();
                }
            }
        }});

    return 'bootbox';
}


function packetType(response) {

    if( response && response.hasOwnProperty("body") && response.hasOwnProperty("resultCode") ){
        return 1;
    }
    else if( response && response.hasOwnProperty("success") && response.hasOwnProperty("data") ){
        return 2;
    }

    return 0;
}


function tryConvertPageResponse(body) {

    body = body || {};
    //新版本分页格式
    if (body.hasOwnProperty("totalElements") && body.hasOwnProperty("content")) {
        return {total: body["totalElements"], rows: body["content"]};
    }

    //老版本分页格式
    if (body.hasOwnProperty("list") && body.hasOwnProperty("totalCount")) {
        return {total: body["totalCount"], rows: body["list"]};
    }

    return body;
}

/**
 * 响应拆包
 * @param xhr
 */
function tryUnpack(xhr) {
    var success = xhr.success;
    xhr.success = function (result, status, _deferred) {


        if ($.isFunction(success)) {

            switch ( packetType(result) ){

                case 1 :{

                    if( result["resultCode"] != 0 ){
                        alert(result.message)
                    }else {
                        var data = tryConvertPageResponse(result.body);
                        success(data);
                    }
                }break;


                case 2 :{

                    if( result["success"] == false ){
                        alert(result.message)
                    }else {
                        var data = tryConvertPageResponse(result.data);
                        success(data);
                    }

                }break;


                default:{
                    success(result);
                }
            }

        }
    }
}

var jj_admin_info = JSON.parse(decodeURI($.cookie("jj_admin_info") || "{}"));
var token = jj_admin_info["access_token"] || "";

$.ajaxSetup({
    global: true,
    headers: {TBSAccessToken: token},
    beforeSend: function (deferred, xhr) {
        tryUnpack(xhr);
        deferred
            .always(function () {
            })
            .error(function (xhr, status, e) {
            })
            .fail(function () {
                console.log(arguments)
            });

    }
});


Date.prototype.pattern=function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "/u65e5",
        "1" : "/u4e00",
        "2" : "/u4e8c",
        "3" : "/u4e09",
        "4" : "/u56db",
        "5" : "/u4e94",
        "6" : "/u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};
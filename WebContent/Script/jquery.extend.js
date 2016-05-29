/// <reference path="jquery-1.4.4.js" />
///* 该文件存放JQuery和String扩展函数*/


jQuery.extend({
    //设置一个Cookie
    setCookie: function(cookie_temppara, value) {
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + 2)
        document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
    },
    //获取Cookie的Values
    getCookie: function(name) {
        var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        if (arr != null) return decodeURI(arr[2]); return null
    },
    //删除名称为name的Cookie  
    Deletecookie: function(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
    },
    $$: function(id) {
        ///<summary>扩展Jquery函数,用于直接取得服务器控件对象</summary>
        return $.getServerJQObjById(id);
    },

    jq: function(id) {
        return jquery(id);
    },

    getServerJQObjById: function(id) {
        ///<summary>扩展Jquery函数,用于直接取得服务器控件对象</summary>
        return jQuery("input[id*=" + id + "]");

    },

    Alert: function(jqObj, message) {
        ///<summary>提示框</summary>
        if (jqObj.val().length == 0) {
            alert(message);
            jqObj.focus();
            return false;
        }
        return true;

    },

    getUrlParamValue: function(name) {
        ///<summary>
        ///Jquery扩展函数,取得地址栏Url参数值
        ///</summary>
        ///<param name="name" type="String">
        ///参数名称
        ///</param>
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        } else {
            return null;
        }
    },

    getRadioByName: function(name) {
        ///<summary>
        ///Jquery扩展函数,用于取得一个Radio对象
        ///</summary>
        ///<param name="id" type="String">
        ///Radio控件name,无需带“#”前缀
        ///</param>
        return $("input:radio[@name=" + name + "]")
    },

    setRadioByValue: function(name, value) {
        ///<summary>设置Radio选中</summary>
        ///<param name="name">控件名称</param>
        ///<param name="value">要勾选的值</param>
        var obj = $.getRadioByName(name);
        obj.each(function() {
            if ($(this).val() == value) {
                $(this).attr("checked", true);
            }
        })
    }

});

//获取字符数组 
String.prototype.ToCharArray = function() {
    return this.split("");
}
//获取N个相同的字符串 
String.prototype.Repeat = function(num) {
    var tmpArr = [];
    for (var i = 0; i < num; i++) tmpArr.push(this);
    return tmpArr.join("");
}
//逆序 
String.prototype.Reverse = function() {
    return this.split("").reverse().join("");
}
//测试是否是数字 
String.prototype.IsNumeric = function() {
    var tmpFloat = parseFloat(this);
    if (isNaN(tmpFloat)) return false;
    var tmpLen = this.length - tmpFloat.toString().length;
    return tmpFloat + "0".Repeat(tmpLen) == this;
}
//测试是否是整数 
String.prototype.IsInt = function() {
    if (this == "NaN") return false;
    return this == parseInt(this).toString();
}
// 合并多个空白为一个空白 
String.prototype.resetBlank = function() {
    return this.replace(/s+/g, " ");
}
// 除去左边空白 
String.prototype.LTrim = function() {
    return this.replace(/^s+/g, "");
}
// 除去右边空白 
String.prototype.RTrim = function() {
    return this.replace(/s+$/g, "");
}
// 除去两边空白 
String.prototype.trim = function() {
    return this.replace(/(^s+)|(s+$)/g, "");
}
// 保留数字 
String.prototype.getNum = function() {
    return this.replace(/[^d]/g, "");
}
// 保留字母 
String.prototype.getEn = function() {
    return this.replace(/[^A-Za-z]/g, "");
}
// 保留中文 
String.prototype.getCn = function() {
    return this.replace(/[^u4e00-u9fa5uf900-ufa2d]/g, "");
}
// 得到字节长度 
String.prototype.getRealLength = function() {
    return this.replace(/[^x00-xff]/g, "--").length;
}
// 从左截取指定长度的字串 
String.prototype.left = function(n) {
    return this.slice(0, n);
}
// 从右截取指定长度的字串 
String.prototype.right = function(n) {
    return this.slice(this.length - n);
}
// HTML编码 
String.prototype.HTMLEncode = function() {
    var re = this;
    var q1 = [/x26/g, /x3C/g, /x3E/g, /x20/g];
    var q2 = ["&", "<", ">", " "];
    for (var i = 0; i < q1.length; i++)
        re = re.replace(q1[i], q2[i]);
    return re;
}
// Unicode转化 
String.prototype.ascW = function() {
    var strText = "";
    for (var i = 0; i < this.length; i++) strText += "&#" + this.charCodeAt(i) + ";";
    return strText;
}
//移除最后一个字符串
String.prototype.RemoveLastChar = function() {

    if (this.length > 0) {
        return this.substring(0, this.length - 1)
    }
    else {
        return "";
    }
}
$(function() {
    $("input[type='checkbox'],input[type='radio']").css({ "cursor": "pointer" });
    $("input[type='text'],input[type='password']").addClass("global");
})


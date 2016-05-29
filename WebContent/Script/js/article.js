var http = getHTTPObject();
/**
 * 创建XMLHttpRequest对象的方法。
 */
function getHTTPObject() {
	var xmlhttp = null;
    if (window.ActiveXObject) {
      var versions = ['Microsoft.XMLHTTP', 'MSXML6.XMLHTTP', 'MSXML5.XMLHTTP', 'MSXML4.XMLHTTP', 'MSXML3.XMLHTTP', 'MSXML2.XMLHTTP', 'MSXML.XMLHTTP'];

      for (var i = 0; i < versions.length; i ++ ) {
        try {
          xmlhttp = new ActiveXObject(versions[i]);
          break;
        } catch (ex) {
          continue;
        }
      }
    } else {
      xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
}
function handleHttpResponse() {
	if(http.readyState == 4) {
		if(http.status == 200) {
			if(http.responseText){
			    document.getElementById('returnguangbo').innerHTML = http.responseText;
             }else{
				 window.location.href="mypage.jsp";
            	  }
			} else {
			alert("你所请求的页面发生异常!");
		}
	}
}

/**
 * 发布微博
 * @return
 */
function guangbo() {
	var textarea=document.getElementById("textarea").value;
	var user=document.getElementById("user").value;
	if(textarea==''){
		document.getElementById("returnguangbo").innerHTML="<font color=red>微博内容不能为空！</font>";
		return false;
		}
	url= "servlet/LogServlet?name=" + encodeURI(encodeURI(textarea)) +"&user="+user+"&action=login";
	http.open("GET",url,true);
	http.onreadystatechange = handleHttpResponse;
	http.send(null);
	}


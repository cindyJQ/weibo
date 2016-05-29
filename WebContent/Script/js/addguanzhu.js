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
			    document.getElementById('gzthis').innerHTML = http.responseText;
             
			} else {
			alert("你所请求的页面发生异常!");
		}
	}
}
/**
 * 添加好友关注
 * @return
 */
function addguangzhu() {
	var myname=document.getElementById("myname").value;
	var fname=document.getElementById("fname").value;
	var fimage=document.getElementById("fimage").value;
	
	url= "servlet/FriendServlet?myname=" + encodeURI(encodeURI(myname)) + "&frename="+encodeURI(encodeURI(fname))+"&freimage="+fimage+"&action=guangzhu";
	http.open("GET",url,true);
	http.onreadystatechange = handleHttpResponse;
	http.send(null);
	}

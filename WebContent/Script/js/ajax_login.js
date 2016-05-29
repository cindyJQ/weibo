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
			    document.getElementById('returnresult').innerHTML = http.responseText;
             
			} else {
			alert("你所请求的页面发生异常!");
		}
	}
}



/**
 * 用户登录
 */
function is_user(){
	var name=document.getElementById("username").value;
	var messageArea;
	if ( name == '' ) {
		
        messageArea=document.getElementById('returnname').innerHTML = "<font color=red>*用户名不能为空</font>";
    } else if ( name.length < 3 ) {
    	messageArea=document.getElementById('returnname').innerHTML = "<font color=red>*用户名必须大于3位数</font>";
	}
}
function is_pass(){
	var password=document.getElementById("password").value;
	var messageArea1;
	if ( password=='') {
		messageArea1=document.getElementById('returnpassword').innerHTML = "<font color=red>*密码不能为空</font>";
	} else if ( password.length < 3 ) {
		messageArea1=document.getElementById('returnpassword').innerHTML = "<font color=red>*密码必须大于3位数</font>";
	} 	
}

function is_regpass(password){
	if ( password=='') {
		document.getElementById('regpass').innerHTML = "<font color=red>密码不能为空</font>";
	} else if ( password.length < 3 ) {
		document.getElementById('regpass').innerHTML = "<font color=red>密码必须大于3位数</font>";
	}
}
function is_querenpass(password2){
	var password1=document.getElementById("userpass").value;
	var password2=password2;
	if(password2==''){
		document.getElementById('returnmima').innerHTML = "<font color=red>必须输入确认密码</font>";
	}else if( password1!=password2) {
		document.getElementById('returnmima').innerHTML = "<font color=red>两次密码不匹配，请重新确认密码</font>";
		document.form2.userpass2.focus();
	}
}
function is_email(email){
	var regemail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
	if ( email=='') {
		document.getElementById('isemail').innerHTML = "<font color=red>邮箱不能为空</font>";
	} else if(!regemail.test(email)){
		document.getElementById('isemail').innerHTML = "<font color=red>邮箱格式不符合要求</font>";
	}
}
function is_rand(rand){
	if ( rand=='') {
		document.getElementById('returnrand').innerHTML = "<font color=red>验证码不能为空</font>";
	} else if(rand.lenght<4){
		document.getElementById('returnrand').innerHTML = "<font color=red>验证码长度不符合要求</font>";
	}
}
/**
 * 用户登录
 * @return
 */
function login() {
	var name=document.getElementById("name").value;
	var password=document.getElementById("password").value;
//	url= "userServlet?name=" + encodeURI(encodeURI(name)) + "&password="+password+"&action=login";
//	http.open("GET",url,true);
	http.onreadystatechange = handleHttpResponse;
	http.send(null);
	}

/**
 * 检测用户名.
 */
function is_registered(user) {
	var name =user;
	url="servlet/LogServlet";
	url += "?name=" + encodeURI(encodeURI(name)) + "&action=isregist";
	if ( name == '' ) {
        document.getElementById('return').innerHTML =  "<font color=red>用户名不能为空</font>";
    } else if ( name.length < 2 ) {
		document.getElementById('return').innerHTML = "<font color=red>用户名必须大于3位数</font>";
	} else {
		http.open("GET",url,true);
		http.onreadystatechange = handleHttpResponse2;
		http.send(null);
		return true;
	}
}
/**
 * 用户注册
 */
function regist(){
	var name=document.getElementById("username").value;
	var password=document.getElementById("userpass").value;
	var email=document.getElementById("email").value;
	var rand=document.getElementById("rand").value;
	if ( name=='') {
		return false;
	} else if ( password =='') {
		return false;
	} else if ( email=='' ) {
		return false;
	 }else if ( rand.length < 4 ) {
		return false;
	}
    url= "servlet/LogServlet?name=" + encodeURI(encodeURI(name)) + "&password="+password+ "&email="+email+ "&rand="+rand+"&action=regist";
	http.open("GET",url,true);
	http.onreadystatechange = handleHttpResponse3;
	http.send(null);
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.jiaqi.model.*,com.jiaqi.dao.*,java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 网页样式 -->
<link rel="stylesheet" type="text/css" href="Script/css/index.css">
<link rel="stylesheet" type="text/css" href="Script/css/pagination.css">
<link rel="stylesheet" type="text/css" href="Script/css/table.css">
<link rel="stylesheet" type="text/css" href="Script/css/Customer.css">
<script type="text/javascript" src="Script/js/ajax_login.js"></script>
<title>微博之家</title>
<!-- 登陆和注册的样式 -->
<script src="denglujs/jquery-1.11.0.js" type=text/javascript></script>
<script type="text/javascript" src="denglujs/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="denglujs/jquery.XYTipsWindow.min.2.8.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		$("#reg").click(function() {
			window.location.href = "register.jsp";
		});

	})
</script>
<!-- 
 <script type="text/javascript">
 	var xmlHttp;
 	function createXMLHttpRequest(){
 		if(window.ActiveObject){
 			xmlHttp=new ActiveObject("Microsoft.XMLHTTP");
 		}else if(window.XMLHttpRequest){
 			xmlHttp=new XMLHttpRequest();
 		}
 	}
 	function validate(){
 		createXMLHttpRequest();
 		var username=document.getElementById("username");
 		var url="validation.do?username="+escape(username.value);
 		xmlHttp.open("GET", url, true);
 		xmlHttp.onreadystatechange=handleStateChange;
 		xmlHttp.send(null);
 	}
 	function handleStateChange(){
 		if(xmlHttp.readyState==4){
 			if(xmlHttp.status==200){
 				var message=xmlHttp.responseXML.getElementsByTagName("message")[0].firstChild.data;
 				var messageArea=document.getElementById("results");
 				messageArea.innerHTML="<p>"+message+"</p>";
 				
 			}
 		}
 	}
 </script>
  -->
<link href="denglucss/box_style.css" type="text/css" rel="stylesheet" />


</head>

<body id="totop">

	

	<c:if test="${artlist==null}">
		<c:redirect url="/article?action=getart"></c:redirect>
	</c:if>
	
	


	<!--头部导航等-->
	<div>

		<div id="toppic">
			<div id="brand"></div>
			<div id="topWordMenu">
				<ul>

					<li><a href="mypage.jsp">我的微博</a></li>
					<li><a href="#">找人</a></li>
					<li><a href="setting.html">设置</a></li>
					<li><a href="loginout.jsp">退出</a></li>

				</ul>
				
			</div>
			<div id="user">


				<c:if test="${sessionScope.user!=null }">

					<img src="user?action=readpic&userid=${sessionScope.user.userid }"
						alt="请登录" width="25px" height="25px" border="0" /> 
												         
						${sessionScope.user.username }
					  </c:if>

				<c:if test="${sessionScope.user==null }">
					<img src="images/user_avatar.png" alt="请登录" />
							游客
					  </c:if>

			</div>
			
		</div>

	</div>

	<div id="bar"></div>
	<!-- 中间 -->
	<div>


		<!-- 中间内容-->
		<div id="content">

			<img src="images/AXXR.gif" />热门微博：<br> <br>
			<hr color="#d0e9c6">
			<br> <br>

			<div>
				<div>

					<c:if test="${empty artlist}">
						<br />&nbsp;&nbsp;&nbsp;没有用户发布微博.<br />
					</c:if>
					<c:forEach var="myList" items="${artlist}">
						<div class="item">
							<table width="450" border="0px" cellpadding="1" cellspacing="0">
								<tr>
									<td width="70" align="center" valign="top"><a href="all?userid=${myList.user.userid}&username=${myList.user.username}"><img
											src="user?action=readpic&userid=${myList.user.userid }" width="54"
											height="54" alt="" title="" /></a> <a href="all?userid=${myList.user.userid}&username=${myList.user.username}">${myList.user.username }</a></td>
									<td width="380">:
										<if:test=${myList.rootid!=0}>转发: </if:test>
									    ${myList.content}
									</td>
								</tr>

							</table>
							<div>发布时间：${myList.datetime}</div>
							<br>
							<div>
								<a onclick="reXianShi(this)">评论</a>&nbsp;
								
								<a href="article?action=zhuan&id=${myList.id}"> 转发</a>&nbsp;
								
								<c:if test="${myList.user.userid!=sessionScope.user.userid}">
									<a href="user?action=addfri&fri=${myList.user.userid}">+关注</a>
								
								</c:if>
							</div>
							<div></div>
							<hr
								style="width: 580px; noshade =noshade; border: 1 dotted #3131B9" />
						</div>

					</c:forEach>
				</div>
				<br>

			</div>
			<hr color="#f2dede">
			<br> <br>





			<div class="pagination">

				<a href="">首页</a> <a href="">前一页</a> <a href=""></a> <a href="">下一页</a>
				<a href="">尾页</a>

			</div>

		</div>
		
		<div id="regist">

			

			<div class="top10">
				<button id="reg"
					style="background-image: url('images/register.gif'); width: 150px; height: 50px"></button>


			</div>
			<br> <br>
			<div class="modal-body" data-backdrop="static" id="login">
				<form method="post" action="user" name="login_form" id="login_form">


					<div class="alert alert-info">请登录微博，不要发布违法信息哦！</div>

					<br>
					<div class="cnt_b">
						<div class="formRow">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-user"></i></span> <input
									type="text" id="username" name="username" placeholder="用户名"
									value="" onblur="is_user()" />
								<div id="returnname"></div>
							</div>
						</div>
						<br /> <br />
						<div class="formRow">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-lock"></i></span> <input
									type="password" id="password" name="password" placeholder="密码"
									value="" onblur="is_pass()" /> <input type="hidden"
									name="action" value="login">
								<div id="returnpassword"></div>
							</div>
						</div>
						<br /> <br />
						<div class="formRow clearfix">
							<label class="checkbox"><input type="checkbox" id="check"
								name="check" />七天免登录</label>
						</div>

					</div>
					<div class="btm_b clearfix"></div>
					<br>
					<div>
						<input class="btn btn-inverse pull-right" type="submit" value="">

					</div>
				</form>
				

			</div>
			

			
			<br> <br> <br>
			<hr color="#ADBFF3">
			<br>
			<div>
				<font color="#404040" style="" size="5">友情链接</font><br> <br>

				<div id="footerLink">


					<ul>

						<li><a href="#" class="a2">广告服务</a></li>

						<li><a href="#" class="a2">保护隐私权</a></li>
						<li><a href="#" class="a2">免责条款</a></li>
						<li><a href="#" class="a2">法律顾问</a></li>
						<li><a href="#" class="a2">意见反馈</a></li>
					</ul>

				</div>
			</div>

		</div>
	</div>
	<!-- 尾部 -->
	<div id="footer">
		<br>@贾琦版权所有 <a id="backtop1" href="#totop"><span></span>回到顶部</a>
	</div>

</body>
</html>
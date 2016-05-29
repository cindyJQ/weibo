<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.jiaqi.model.*,com.jiaqi.dao.*, java.net.URLDecoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="denglucss/box_style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="Script/css/login.css">
<title>微博之家登录页面</title>

<script src="denglujs/jquery-1.11.0.js" type=text/javascript></script>
<script type="text/javascript" src="denglujs/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="denglujs/jquery.XYTipsWindow.min.2.8.js"></script>
	<script type="text/javascript" src="Script/js/ajax_login.js"></script>
</head>
<body>

	
	<!-- 判断是否有cookie -->
	<%
		String username = "";
		String password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("cu")){
					username = URLDecoder.decode(cookies[i].getValue(),"utf-8");
					
				}
				if (cookies[i].getName().equals("cp"))
					password = cookies[i].getValue();
			}
			if (username!="") {
				
				
				User user=new User();
				user.setUsername(username);
				user.setPassword(password);
				User user1=new UserDAO().login(user);
				request.getSession().setAttribute("user", user1);
				RequestDispatcher dispatcher=null;
				try {
					dispatcher=request.getRequestDispatcher("mypage.jsp");
					dispatcher.forward(request, response);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}
	%>
	<div id="bac">
			
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
				<br><br>
				<a href="index.jsp">先不登录，随便逛逛~</a>

			</div>
	</div>
	
</body>
</html>
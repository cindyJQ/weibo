<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>个人主页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="Script/css/mypage.css">
    <link rel="stylesheet" type="text/css" href="Script/css/regist.css">
    <script type="application/javascript" src="Script/js/article.js"></script>

  </head>
  
  <body id="totop1">

  <!--头部导航等-->
  
   
  <div id="bar">
  	<div id="logo"></div>
   
    <ul>
      <li><a href="index.jsp">回到首页 </a></li>
      <li><a href="mypage.jsp">我的微博 </a></li>
      <li><a href="loginout.jsp">退出登录 </a></li>
    </ul>
    <p>&nbsp;</p>
   </div>
 <!--内容 开始-->   
<div id="content">
		<div id="newarticle">
			<div class="myguangbo">
				<br>
				<div class="my">我的粉丝</div>
				<br>
				<hr style="width: 580px; background-color:  #3131B9" />
				<br>
			</div>
			<div class="allinfo">
				<c:if test="${sessionScope.fenlist==null}">
					<br />&nbsp;&nbsp;&nbsp;抱歉，您还没有任何粉丝，快去交朋友吧!<br />
				</c:if>
				<c:forEach var="fenList" items="${sessionScope.fenlist}">
					<div class="item">
						<div class="touxiang">
							<img
								src="user?action=readpic&userid=${fenList.userid }"
								alt="用户头像" width="50px" height="50px" />
						</div>
						<a href="all?userid=${fenList.userid}&username=${fenList.username}">用户名：${fenList.username }</a>
						<hr
							style="width: 580px; noshade =noshade; border: 1 dotted #3131B9" />
					</div>
				</c:forEach>
			</div>
		</div>

		<div id="userinfo">
  		<div >
	   		<br>
	   		
        		<c:if test="${sessionScope.user!=null }">
										
						<img src="user?action=readpic&userid=${sessionScope.user.userid }"  width="150px" height="160px" border="0"/> 
						
						<br>						         
						${sessionScope.user.username }
					  </c:if>
					  
        	
        	<br>
	   
	   </div>
	   <div>
	   <br/>
	   <span style="display:block;background:#d0e9c6;"> 个人信息</span><br/>
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">用户名：${sessionScope.user.username}</span> 
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">真实姓名：${sessionScope.user.realname}</span> 
	    
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">邮箱：${sessionScope.user.email}</span>
	    
	   
	   <br/>
	        
  		</div>
  
  
  
  
  
  
  </div> 
  </div>
  <!--body 结束了-->
 
  <div id="footer">
    <br>@贾琦版权所有
	
    
  </div> 
  </body>
</html>

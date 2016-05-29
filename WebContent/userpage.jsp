<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
      <li><a href="user?action=addfri&fri=${user1.userid }">+关注</a></li>
    
    </ul>
    <p>&nbsp;</p>
   </div>
 <!--内容 开始-->   
<div id="content">
 		<div id="newarticle">
			
		  <div id="myarticle">
			    
			    <div class="myguangbo">
			      <div class="my">他的微博</div>
			    </div>
			    <div class="allinfo">
					<c:if test="${empty sessionScope.alist}">
						<br />&nbsp;&nbsp;&nbsp;当前用户尚未发布任何微博.<br />
					</c:if>
					<c:forEach var="myList" items="${sessionScope.alist}">
						<div class="item">
							<div class="touxiang">
								<img src="user?action=readpic&userid=${myList.user.userid }"
									alt="用户头像" width="50px" height="50px" />
							</div>
							<div class="leirong">
								<font color="#2C2563">${myList.user.username}：</font><span
									style="font-weight: normal;"><font color="#343630">${myList.content}</font></span><br />
								<br /> <font color="#585350">发布时间：</font><span
									class="goodsPrice"><font color="#D5D7F4">${myList.datetime}</font></span>&nbsp;&nbsp;&nbsp;&nbsp;
									<font color="#585350">回复次数:</font><span class="goodsPrice"><font
									color="#D5D7F4"></font></span><br />
							</div>
							<hr
								style="width: 580px; noshade =noshade; border: 1 dotted #3131B9" />
						</div>
					</c:forEach>

				</div>
		  </div>
  		</div>
  
  <div id="userinfo">
  		<div >
	   		<br>
	   		
        		<c:if test="${sessionScope.user1!=null }">
										
						<img src="user?action=readpic&userid=${sessionScope.user1.userid }"  width="150px" height="160px" border="0"/> 
						
						<br>						         
						${sessionScope.user1.username }
					  </c:if>
					  
        	
        	<br>
	   		
	   
	   </div>
	   <div>
	   <br/>
	   <span style="display:block;background:#d0e9c6;"> 个人信息</span><br/>
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">用户名：${sessionScope.user.username}</span> 
	     
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">性别：${sessionScope.user.sex}</span>
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">邮箱：${sessionScope.user.email}</span>
	    
	   
	   <br/>
	    <span style="display:block;background:#d0e9c6;"> &nbsp;&nbsp;他的关注</span><br/>
	    <div id="userlist">
	    
	    			好友列表<br><br>
	    			<hr >
			    	<c:if test="${empty sessionScope.ulist}">
						<br />&nbsp;&nbsp;&nbsp;当前用户尚未有好友.<br />
					</c:if>
					<c:forEach var="friList" items="${sessionScope.ulist}">
						<div class="item">
							<div class="touxiang">
								<img src="user?action=readpic&userid=${friList.userid }"
									alt="用户头像" width="50px" height="50px" />
									${friList.username}
							</div>
							
							<hr
								style="width: 300px; noshade =noshade; border: 1 dotted #3131B9" />
						</div>
					</c:forEach>
		
  		</div>
  </div>
  </div> 
  </div>
  <!--body 结束了-->
 
  <div id="footer">
    <br>@贾琦版权所有
	
    
  </div> 
  </body>
</html>

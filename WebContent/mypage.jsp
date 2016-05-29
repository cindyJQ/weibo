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
    <script type="text/javascript" src="Script/jquery-1.11.0.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#textarea").keyup(function(){
				var len=$(this).val().length;
				if(len>139){
					$(this).val($(this).val().substring(0,140));
				}
				var num=140-len;
				$("#word").text(num);
			});
			
		});
	
	</script>
  </head>
  
  <body id="totop1">


	<c:if test="${sessionScope.user.username==null}">
		
		<c:redirect url="index.jsp"></c:redirect>
	</c:if>
	<c:if test="${artlist==null}">
		<c:redirect url="/article?action=getallart"></c:redirect>
	</c:if>
	<c:if test="${frilist==null}">
		<c:redirect url="/user?action=selectfri"></c:redirect>
	</c:if>

    
  <!--头部导航等-->
  
   
  <div id="bar">
  	<div id="logo"></div>
   
    <ul>
      <li><a href="index.jsp">回到首页 </a></li>
      
      <li><a href="user?action=selectfen">粉丝 </a></li>
      <li><a href="loginout.jsp">退出登录 </a></li>
    </ul>
    <p>&nbsp;</p>
   </div>
 <!--内容 开始-->   
<div id="content">
 		<div id="newarticle">
			<div> 		
			  <br/>
			   <h3>&nbsp; &nbsp; &nbsp;来，说说你在做什么，想什么(你可以输入<span style="color: red">140</span>个字，现在剩余<span id="word" style="color: red">140</span>个)</h3>
			   <form action="article?action=addart" method="post" name="form1">
				     <textarea name= "textarea" id="textarea" cols= "65 " rows= "8 " style= "width:400px; text-aling:left;height:100px; overflow:hidden;border:1 solid #C0C4E4;margin-left:50px;"> </textarea>
				     <input type="hidden" name="user" id="user" value="${sessionScope.user.username}"/>
				     <span id="returnguangbo"></span>
				    <span style="float:right;margin-right:150px;"><input type="submit"  value="发布>>" class="btn" /></span>
			    </form>
		  </div>
		  <div id="myarticle">
			    
			    <div class="myguangbo">
			      <div class="my">我的微博</div>
			    </div>
			    <div class="allinfo">
					<c:if test="${empty artlist}">
						<br />&nbsp;&nbsp;&nbsp;当前用户尚未发布任何微博.<br />
					</c:if>
					<c:forEach var="myList" items="${artlist}">
						<div class="item">
							<div class="touxiang">
								<img src="user?action=readpic&userid=${sessionScope.user.userid }"
									alt="用户头像" width="50px" height="50px" />
							</div>
							<div class="leirong">
								<font color="#2C2563">${sessionScope.user.username}：</font><span
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
	   		
        		<c:if test="${sessionScope.user!=null }">
										
						<img src="user?action=readpic&userid=${sessionScope.user.userid }"  width="150px" height="160px" border="0"/> 
						
						<br>						         
						${sessionScope.user.username }
					  </c:if>
					  
        	
        	<br>
	   		
	   		<a href="update.jsp">修改个人信息</a>
	   
	   </div>
	   <div>
	   <br/>
	   <span style="display:block;background:#d0e9c6;"> 个人信息</span><br/>
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">用户名：${sessionScope.user.username}</span> 
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">真实姓名：${sessionScope.user.realname}</span> 
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">性别：${sessionScope.user.sex}</span> 
	    <span style="display:block;margin-bottom:3px;color:#333;font-size:16px;">邮箱：${sessionScope.user.email}</span>
	    
	   
	   <br/>
	    <span style="display:block;background:#d0e9c6;">我的关注</span><br/>
	    <div id="userlist">
	    			好友列表<br><br>
	    			<hr >	
			    	<c:if test="${empty frilist}">
						<br />&nbsp;&nbsp;&nbsp;当前用户尚未有好友.<br />
					</c:if>
					<c:forEach var="friList" items="${frilist}">
						<div >
							<div >
								
									<a href="all?userid=${friList.userid}&username=${friList.username}">
									<img src="user?action=readpic&userid=${friList.userid }"
											alt="用户头像" width="50px" height="50px" /></a>
								<!-- 
								<c:if test="${friList.path==null}">
										<a href="userpage.jsp?userid=${friList.userid}&username=${friList.username}">
										<img src="upload/images/uploadphoto.gif"
											alt="用户头像" width="50px" height="50px" /></a>
								</c:if>
								 -->
							<a href="all?userid=${friList.userid}&username=${friList.username}">	${friList.username}</a>
							</div>
							
							
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

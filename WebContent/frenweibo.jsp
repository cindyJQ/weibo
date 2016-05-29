<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<c:if test="${sessionScope.userbean.name==null}">
	<c:redirect url="./index.jsp"></c:redirect>
</c:if>
<c:if test="${frendartlist==null}">
	<c:redirect url="ArticleServlet?action=getfrendart"></c:redirect>
</c:if>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'mypage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="script/css/mypage.css">
    <link rel="stylesheet" type="text/css" href="script/css/regist.css">
    <script type="application/javascript" src="script/js/article.js"></script>

  </head>
  
  <body>
  <!--头部导航等-->
  <div id="top">
   <div id="logo"></div>
   <div id="my"> <font color=red>${sessionScope.userbean.name}</font> 欢迎您！<a href="servlet/LogServlet?action=logout">注销登录</a></div>
  <div id="bar">
    <ul>
      <li><a href="index.jsp">回到首页 </a></li>
      <li><a href="myfriend.jsp">我的好友 </a></li>
     <li><a href="mypage.jsp">我的主页 </a></li>
    </ul>
    <p>&nbsp;</p>
   </div>
  <div>
 <!--内容 开始-->   
<div id="content">
 
  </div>
  <div id="userinfo">
  <span style="display:block;padding:15px 0px 10px 50px;"><img src="upload/images/${sessionScope.userbean.image}" alt="${sessionScope.userbean.name}" width="150px" height="160px" border="0"/><a href="upload.jsp">上传新头像</a></span>
    <span style="display:block;margin-bottom:3px;color:#9C412E;font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名：${sessionScope.userbean.name}</span> 
    <span style="display:block;margin-bottom:3px;color:#333;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：${sessionScope.userbean.email}</span>
    <span style="display:block;color:#BA7263;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注册日期：${sessionScope.userbean.regdate}</span><br/>
    <span style="display:block;background:#999;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改个人信息</span><br/>
   <hr style="width:290px;noshade=noshade;margin-left:5px;"/>
  </div>
  <div id="myarticle">
    <hr style="width:600px;noshade=noshade;"/>
    <div class="myguangbo">
      <div class="my">好友微博</div>
    </div>
    <div class="allinfo">
    <c:if test="${empty frendartlist}"><br/>&nbsp;&nbsp;&nbsp;该好友尚未发布任何微博！<br/></c:if>
    <div style="width:550px;height:30px;text-align:left;font-size:16px;">该好友的的近期微博：</div>
    <c:forEach var="frendartlist" items="${frendartlist}">
	  <div class="item">
	   <div class="leirong"><font color="#2C2563">${frendartlist.username}说：</font><span class="goodsPrice"><font color="#343630">${frendartlist.content}</font></span><br />         <br/>
		<font color="#585350">发布时间：</font><span class="goodsPrice"><font color="#D5D7F4">${frendartlist.pubdate}</font></span>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#585350">回复次数:</font><span class="goodsPrice"><font color="#D5D7F4">${frendartlist.replycount}</font></span><br />
        </div>
		<hr style="width:580px;noshade=noshade;border:1 dotted #3131B9 "/>
	  </div>
   </c:forEach>

   </div>
  </div>
  </div> 
   <div id="footer">
  
    
  </div> 
  </body>
</html>

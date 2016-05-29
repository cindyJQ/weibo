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
<c:if test="${artlist==null}">
	<c:redirect url="ArticleServlet?action=getallart"></c:redirect>
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
	<link rel="stylesheet" type="text/css" href="Script/css/index.css">
	<link rel="stylesheet" type="text/css" href="Script/css/regist.css">
    <script type="application/javascript" src="script/js/article.js"></script>

  </head>
  
  <body>
  <!--头部导航等-->
  <div id="top">
   <div id="logo"></div>
   <div id="my"> 欢迎您！退出</div>
  <div id="bar">
    <ul>
      <li><a href="myedit.jsp">个人信息修改 </a></li>
      <li><a href="myfriend.jsp">我的好友 </a></li>
     <li><a href="mypage.jsp">我的主页 </a></li>
    </ul>
    <p>&nbsp;</p>
   </div>
  <div>
 <!--内容 开始-->   
<div id="content">
 
  <div id="newarticle"><br/>
   <h3>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;来，说说你在做什么，想什么</h3>
   <form action="servlet/ArticleServlet?action=addart" method="post" name="form1">
     <textarea name= "textarea" id="textarea" cols= "65 " rows= "8 " style= "width:400px; text-aling:left;height:100px; overflow:hidden;border:1 solid #C0C4E4;margin-left:50px;"> </textarea>
     <input type="hidden" name="user" id="user" value=<%=(String)request.getSession(true).getAttribute("user")%> />
     <span id="returnguangbo"></span>
    <span style="float:right;margin-right:150px;"><input type="submit"  value="发布>>" class="btn" /></span>
    </form>
  </div>
  <div id="userinfo">
   <span style="display:block;padding:15px 0px 10px 50px;"><img src="images/${sessionScope.userbean.image}" alt="上传头像" width="150px" height="160px"/></span>
    <span style="display:block;margin-bottom:3px;color:#9C412E;font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名：${sessionScope.userbean.name}</span> 
    <span style="display:block;margin-bottom:3px;color:#333;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：${sessionScope.userbean.email}</span>
    <span style="display:block;color:#BA7263;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注册日期：${sessionScope.userbean.regdate}</span><br/>
   <hr style="width:290px;noshade=noshade;margin-left:5px;"/>

  </div>
  <div id="myarticle">
    <hr style="width:600px;noshade=noshade;"/>
    <div class="myguangbo">
      <div class="my">我的微博</div>
    </div>
    <div class="allinfo">
    <c:if test="${empty artlist}"><br/>&nbsp;&nbsp;&nbsp;当前用户尚未发布任何微博.<br/></c:if>
    <c:forEach var="myList" items="${artlist}">
	  <div class="item">
	    <div class="touxiang"><img src="images/${sessionScope.userbean.image}" alt="用户${myList.username}头像" width="50px" height="50px"/></div>
		<div class="leirong"><font color="#2C2563">${myList.username}：</font><span class="goodsPrice"><font color="#343630">${myList.content}</font></span><br />         <br/>
		<font color="#585350">发布时间：</font><span class="goodsPrice"><font color="#D5D7F4">${myList.pubdate}</font></span>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#585350">回复次数:</font><span class="goodsPrice"><font color="#D5D7F4">${myList.replycount}</font></span><br />
        </div>
		<hr style="width:580px;noshade=noshade;border:1 dotted #3131B9 "/>
	  </div>
   </c:forEach>

   </div>
  </div>
  </div> 
</body>
</html>

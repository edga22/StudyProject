<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<jsp:useBean class="system.PostMgr" id="PostMgr" />
<%
	// mod 0: summary, 1: 게시물 관리, 2: 유저 관리 //
	int mod = 0;
	if(request.getParameter("mod") != null) mod = Integer.parseInt(request.getParameter("mod"));
	
%>
<!DOCTYPE html>
<html>
<link href="./css/global.css" rel="stylesheet">
<link href="./css/navbar.css" rel="stylesheet">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>어드민 페이지</title>
	
</head>
<body>
<div class="navbar-wrapper">
<div class="navbar">
	<ul class="navbar nav">
		<li class="nav-item"><a class="link" href="index">Angel위키</a></li>
		<li class="nav-item"><a class="link" href="admin?mod=1">게시물 관리</a></li>
		<li class="nav-item">유저 관리</li>
	</ul>
	<ul class="navbar pull-right">
		<li class="nav-item"><a href="logout.jsp">로그아웃</a></li>
	</ul>
</div>
</div>
<div class="content-wrapper">
<%if(mod == 0){ %>
	<jsp:include page="admin_summary.jsp"></jsp:include>
<%}else if(mod == 1){ %>
	<jsp:include page="admin_mgrpost.jsp"></jsp:include>
<%} %>
</div>
</body>
</html>
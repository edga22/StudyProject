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
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	
<title>어드민 페이지</title>
<link href="./css/bootstrap.min.css" rel="stylesheet" />	
<link href="./css/dashboard.css" rel="stylesheet" />
</head>
<body>
<!-- Top Navbar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
       	   </button>
			<a class="navbar-brand" href="index">Angel위키</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="disabled"><a href="#">Settings</a>
				<li><a href="logout.jsp">로그아웃</a></li>
			</ul>
		</div>
	</div>
</nav>

<!-- Content -->
<div class="container-fluid">
<div class="row">
	<!-- Sidebar -->
	<div class="col-sm-3 col-md-2 sidebar">
		<ul class="nav nav-sidebar">
		<li <%if(mod==0){%>class="active"<%} %>> <a href="admin">Overview</a><span class="sr-only">(current)</span></li>
		<li <%if(mod==1){%>class="active"<%} %>> <a href="admin?mod=1">게시물 관리</a></li>
		<li <%if(mod==2){%>class="active"<%} %>> <a href="admin?mod=2">맴버 관리</a></li>
		</ul>
		<ul class="nav nav-sidebar"></ul>
	</div>
	
	<!-- Main article -->
	<div class="col-sm-9 sol-sm-offset-3 col-md-10 col-md-offset-2 main">
	<%if(mod == 0){ %>
		<h1 class="page-header"></h1>
		<jsp:include page="admin_summary.jsp"></jsp:include>
	<%}else if(mod == 1){ %>
		<jsp:include page="admin_mgrpost.jsp"></jsp:include>
	<%}else if(mod == 2){ %>
		<jsp:include page="admin_mgrmember.jsp"></jsp:include>
	<%} %>
	</div>
</div>
</div>

<!-- Bootstrap core JavaScript
 ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="./js/bootstrap.js"></script>
</body>
</html>
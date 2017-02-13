<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<jsp:useBean class="system.PostMgr" id="PostMgr" />
<%

%>
<!DOCTYPE html>
<html>
<link href="./css/global.css" rel="stylesheet">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>어드민 페이지입니다</title>
	
</head>
<body>
<div class="navbar-wrapper">
</div>
<div class="content-wrapper">
	<jsp:include page="admin_mgrpost.jsp"></jsp:include>
</div>
</body>
</html>
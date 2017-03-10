<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.TagMgr" import="system.TagSet" %>
<!DOCTYPE html>

<html>
<%

TagMgr tagmgr = new TagMgr();
TagSet[] tagset = tagmgr.getTagList();

%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<link href="./css/bootstrap.min.css" rel="stylesheet">
<link href="./css/global.css" rel="stylesheet">
<style>
.btn button {
	height:34px;
}
</style>

<title>Angel Wiki - 태그 목록</title>
</head>
<body>
<nav class="navbar navbar-default">
	<jsp:include page="navbar.jsp"></jsp:include>	
</nav>

<div class="container">
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
	<%
	String tagSize = "";
	for(TagSet output:tagset){
		if(output.getTagCnt() < 1) continue;
		if(output.getTagCnt() < 2){ tagSize="<h6>"; }
		else if(output.getTagCnt() < 5){ tagSize="<h5>"; }
		else if(output.getTagCnt() < 10){ tagSize="<h4>"; }
		else {  tagSize="<h3>"; }
	%>
	
	<%=tagSize%>	
	<a href="index?mod=4&tag=<%= output.getTagName() %>"><%= output.getTagName() %> (<%= output.getTagCnt() %>)</a>
			
	<%} %>
	</div>
	<div class="col-md-2">
	</div>
</div>

</div>

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
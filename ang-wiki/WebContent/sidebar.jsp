<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<jsp:useBean class="system.PostSearch" id="PostSc" />
<!DOCTYPE html>
<html>
<head>
<link href="./css/sidebar.css" rel="stylesheet">
</head>
<div class="side-content">	
	<div class="side-title">
		<span>최근 등록된 글</span>
	</div>
	<div class="side-article">
		<ul>
		<%
		Posts[] recentPosts = PostSc.getRecentPosts(5);
		for(Posts result:recentPosts){
		%>
			<li><a href="<%="index?title="+result.getTitle()%>">
			<%=result.getTitle() %> - 
			<span class="time"><%=result.getWritetime().toString().substring(5) %></span></a></li>
		<%
		}
		%>
		</ul>
	</div>
</div>
<div class="side-content">
	<div class="side-title">
		<span>최근 변경된 글</span>
	</div>
	<div class="side-article">
		<ul class="side-article">
		<%
		Posts[] recentMods = PostSc.getRecentMods(5);
		for(Posts result:recentMods){
		%>
			<li><a href="<%="index?title="+result.getTitle()%>">
			<%=result.getTitle() %> - 
			<span class="time"><%=result.getModtime().toString().substring(5)%></span></a></li>
		<%
		}
		%>
		</ul>
	</div>
</div>
</html>
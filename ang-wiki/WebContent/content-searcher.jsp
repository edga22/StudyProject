<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<!DOCTYPE html>
<jsp:useBean id="PostMgr" class="system.PostMgr" />
<%
	request.setCharacterEncoding("UTF-8");
	Posts[] scResult = (Posts[])session.getAttribute("Posts");
	String title = (String)session.getAttribute("title");
	
%>
<html>
<link href="./css/content.css" rel="stylesheet">
<link href="./css/content-searcher.css" rel="stylesheet">

<div class="wiki-title-wrapper">
	<div class="wiki-btn-group">
		
	</div>
	<span class="wiki-title">
		검색결과 - <%=title %>		
	</span>
</div>
<div class="wiki-content-wrapper">
	<ol class="result-list">
	<%
	String linkURL;
	for(int i=1;i<=scResult.length;i++){
		Posts outpost = scResult[i-1];		
		linkURL = "index.jsp?title=" + outpost.getTitle();
	%>
		<li class="result-list-item"><span><a href="<%=linkURL%>"><%=outpost.getTitle() %></a>
		</span><span style="font-size:1rem">rev <%=outpost.getModcnt() %></span>
		<span class="time">-<%=outpost.getModtime()==null? outpost.getWritetime():outpost.getModtime() %></span></li>
	<%} %>
	</ol>
</div>
</html>
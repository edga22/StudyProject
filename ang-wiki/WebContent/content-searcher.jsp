<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>

<jsp:useBean id="PostMgr" class="system.PostMgr" />
<%
	request.setCharacterEncoding("UTF-8");
	Posts[] scResult = (Posts[])session.getAttribute("Posts");
	Posts[] scTagResult = (Posts[])session.getAttribute("TagPosts");
	String title = (String)session.getAttribute("title");	
	String tag = (String)session.getAttribute("tag");
%>
<link href="./css/content.css" rel="stylesheet">
<link href="./css/content-searcher.css" rel="stylesheet">

<div class="wiki-title-wrapper">
	<div class="wiki-btn-group">
		
	</div>
	<span class="wiki-title">
		검색결과 - 제목: <%=title %>		
	</span>
</div>
<div class="wiki-content-wrapper">
	<ol class="result-list">
	<%
	String linkURL;	
	if(scResult != null){
	for(int i=1;i<=scResult.length;i++){
		Posts outpost = scResult[i-1];		
		linkURL = "index?title=" + outpost.getTitle();
	%>
		<li class="result-list-item"><span><a href="<%=linkURL%>"><%=outpost.getTitle() %></a>
		</span>
		<span class="time">rev <%=outpost.getModcnt() %>-<%=outpost.getModtime()==null? outpost.getWritetime():outpost.getModtime() %></span></li>
	<%}
	}
	else{%>
		<li class="result-list.item">검색 결과가 없습니다.</li>
	<%
	}
	%>
	</ol>
</div>

<div class="wiki-title-wrapper">
	<div class="wiki-btn-group">
		
	</div>
	<span class="wiki-title">
		검색결과 - 태그: <%=tag %>		
	</span>
</div>
<div class="wiki-content-wrapper">
	<ol class="result-list">
	<%
		
	if(scTagResult != null){
	for(int i=1;i<=scTagResult.length;i++){
		Posts outpost = scTagResult[i-1];		
		linkURL = "index?title=" + outpost.getTitle();
	%>
		<li class="result-list-item"><span><a href="<%=linkURL%>"><%=outpost.getTitle() %></a>
		</span>
		<span class="time">rev <%=outpost.getModcnt() %>-<%=outpost.getModtime()==null? outpost.getWritetime():outpost.getModtime() %></span></li>
	<%}
	}
	else{%>
		<li class="result-list.item">검색 결과가 없습니다.</li>
	<%
	}
	%>
	</ol>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<%@ page import="org.commonmark.node.*" import="org.commonmark.parser.Parser" import="org.commonmark.renderer.html.HtmlRenderer" %>

<jsp:useBean id="PostMgr" class="system.PostMgr" />
<%
	request.setCharacterEncoding("UTF-8");
	String title = request.getParameter("title");
	if(title == null) title = "대문";
	
	String revision = request.getParameter("rev");
	int rev = (revision != null)? Integer.parseInt(revision) : 0;
	Posts post = PostMgr.getPost();	
	PostMgr.readPost(title, rev);
	
	String modTime = "";
	if(post.getModtime() == null) modTime = post.getWritetime().toString();
	else modTime = post.getModtime().toString();
	
	String modCnt = "";
	modCnt = String.valueOf(post.getModcnt()+1);
	
	// 파싱 부분
	if(post.getContent() == null) post.setContent("내용이 없습니다.");
	Parser parser = Parser.builder().build();
	Node document = parser.parse(post.getContent());
	HtmlRenderer renderer = HtmlRenderer.builder().build();
	
	String tags[] = null;

	tags = post.getTags().split(" ");
%>

<link href="./css/content.css" rel="stylesheet">
<link href="./css/content-reader.css" rel="stylesheet">

<div class="wiki-title-wrapper">
	<div class="wiki-btn-group">
		<form method="get" action="index">
		<input type="hidden" name="title" value="<%=title%>">
		<input type="hidden" name="writer" value="<%=post.getWriter() %>">
		<div class="btn-group">
		<button class="btn btn-default" type="submit" name="rev" value="<%=(rev==0)? post.getModcnt() : rev-1 %>">이전버전</button>
		<button class="btn btn-default" type="submit" name="mod" value="1">편집</button>
		<button class="btn btn-default" type="submit" name="mod" value="3">삭제</button>
		</div>
		</form>
	</div>
	<span class="wiki-title" style="padding-top:-5px;">
		<%=title %>		
	</span>
	<div class="time hidden-xs">
	<% if(post.getModcnt() == 0) modCnt = "변경 된 기록이 없습니다."; %>
		최근 변경 시간 : <%=modTime %>, rev : <%=(rev==0)?modCnt : rev %>
	</div>
</div>
<div id="wiki-content-wrapper">
	<p>
	<%for(String tag:tags){	%>
	 <button class="btn btn-primary btn-xs"><%=tag %></button>
	 <%} %>
	</p>
	<br>
	<%=renderer.render(document) %>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="system.Posts" %>
<jsp:useBean id="PostMgr" class="system.PostMgr" />
<%	
	request.setCharacterEncoding("UTF-8");
	String title = request.getParameter("title");
	
	PostMgr.readPost(title, 0);	
	
	String modTime = "";
	if(PostMgr.getPost().getModtime() == null) modTime = PostMgr.getPost().getWritetime().toString();
	else modTime = PostMgr.getPost().getModtime().toString();
%>
<html>
<link href="./css/content.css" rel="stylesheet">
<link href="./css/content-writer.css" rel="stylesheet">
<form method="post" action="content-moderOK.jsp">
	<input type="hidden" name="writer" value="<%=PostMgr.getPost().getWriter()%>"/>
	<input type="hidden" name="idx" value="<%=PostMgr.getPost().getId() %>" />
	<div class="wiki-title-wrapper">
		<div class="wiki-btn-group">			
			<button class="btn btn2" type="submit" >수정하기</button>			
		</div>		
		<span class="wiki-title">
		<input class="input in-title" type="text" name="title" value="<%=PostMgr.getPost().getTitle() %>" />
		</span>
		<span class="time">
		최근 변경 시간 : <%=modTime %>
		</span>	
	</div>
	<div class="wiki-content-wrapper">
		<textarea class="input in-content" name="content"><%=PostMgr.getPost().getContent() %></textarea>
	</div>
</form>
</html>
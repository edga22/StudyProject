<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="./css/content.css" rel="stylesheet">
<link href="./css/content-writer.css" rel="stylesheet">
<% 
	String memberID = (String)session.getAttribute("memID");
%>
<form method="post" action="postwriteOK.jsp">
	<input type="hidden" name="writer" value="<%=memberID%>"/>
	<div class="wiki-title-wrapper">
		<div class="wiki-btn-group">			
			<button class="btn btn2" type="submit" >글쓰기</button>			
		</div>		
		<input class="input in-title" type="text" name="title" placeholder="글 제목" />	
	</div>
	<div class="wiki-content-wrapper">
		<textarea class="input in-content" name="content" placeholder=""></textarea>
	</div>
</form>
</html>
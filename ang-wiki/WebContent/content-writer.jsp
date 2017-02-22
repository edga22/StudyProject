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
			<button class="btn btn-success" type="submit" >글쓰기</button>			
		</div>		
		<input class="form-control" style="width:20em;" type="text" name="title" placeholder="글 제목" />	
	</div>
	<div class="wiki-content-wrapper">
		<textarea class="form-control" rows="30" name="content" placeholder="글 내용"></textarea>
	</div>
</form>
</html>
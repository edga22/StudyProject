<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="PostMgr" class="system.PostMgr" />
<html>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String title = request.getParameter("title");
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");
	String url = "index?title="+URLEncoder.encode(title, "UTF-8");
	if(!PostMgr.writePost(title, writer, content, null)){
%>
		<script>
		alert("글쓰기에 실패하였습니다.");
		history.go(-1);
		</script>
<%
	}else{
		response.sendRedirect(url);
	}
%>
</html>
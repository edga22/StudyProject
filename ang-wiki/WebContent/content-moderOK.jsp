<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<jsp:useBean id="PostMgr" class="system.PostMgr" />
<html>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

String memberID = (String)session.getAttribute("memID");
String writer = request.getParameter("writer");
String title = (String)session.getAttribute("title"); if(title == null) title = "대문";
String content = request.getParameter("content");

if(!PostMgr.modPost(title, content, memberID)){
%>
	<script>
	alert("글수정에 실패하였습니다.");
	history.go(-1);
	</script>		
<%
}
response.sendRedirect("index.jsp?title="+URLEncoder.encode(title, "UTF-8"));
%>
</html>
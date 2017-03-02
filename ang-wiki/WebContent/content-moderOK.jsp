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
String title = request.getParameter("title");
String content = request.getParameter("content");
String tags = request.getParameter("tags");

if(tags.contains("관리자") && !memberID.equals("admin")){
%>
	<script>
	alert("관리자 권한이 없습니다.");
	history.go(-1);
	</script>
<%
}
else if(!PostMgr.modPost(title, content, memberID, tags)){
%>
	<script>
	alert("글수정에 실패하였습니다.");
	history.go(-1);
	</script>
<%
}else response.sendRedirect("index?title="+URLEncoder.encode(title, "UTF-8"));
%>
</html>
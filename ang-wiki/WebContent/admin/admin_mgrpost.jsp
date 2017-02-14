<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<!DOCTYPE html>
<jsp:useBean id="Postsc" class="system.PostSearch"></jsp:useBean>
<jsp:useBean id="Postmgr" class="system.PostMgr"></jsp:useBean>

<html>
<%
	Posts[] mgrPost = null;
	mgrPost = Postsc.getList();
%>
<body>
<div class="admin-article">
	<h2>게시물 관리</h2>
	<form method="post">
	<table class="table1" action="admin_delOK.jsp">
	<tr>
		<th>ID</th><td>제목</td><td>작성자</td><td>작성시간</td><td>최근수정</td><td>rev</td>
	</tr>
	<%
	for(int i=0;i<mgrPost.length;i++){
	%>
	<tr>
		<th><%=mgrPost[i].getId() %></th>
		<td><%=mgrPost[i].getTitle() %></td>
		<td><%=mgrPost[i].getWriter() %></td>
		<td><%=mgrPost[i].getWritetime() %></td>
		<td><%=mgrPost[i].getModtime() %></td>
		<td><%=mgrPost[i].getModcnt() %></td>
	</tr>
	<%} %>
	</table>
	</form>
</div>
</body>
</html>
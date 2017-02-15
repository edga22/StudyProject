<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<!DOCTYPE html>
<jsp:useBean id="Postsc" class="system.PostSearch"></jsp:useBean>
<jsp:useBean id="Postmgr" class="system.PostMgr"></jsp:useBean>

<%
	Posts[] mgrPost = null;
	mgrPost = Postsc.getList();
	
%>

<div class="admin-article">
	<h2>게시물 관리</h2>
	<form method="post" action="admin_delOK.jsp">
	<table class="table1" style="font-size: 0.8rem;">
	<tr>
		<th>ID</th><td>제목</td><td>작성자</td><td>작성시간</td><td>최근수정</td><td>rev</td><td></td>
	</tr>
	<%
	for(int i=0;i<mgrPost.length;i++){
	%>	
	<tr>
		<th><%=mgrPost[i].getId() %></th>
		<td><a href="index.jsp?title=<%=mgrPost[i].getTitle() %>"> <%=mgrPost[i].getTitle() %> </a></td>
		<td><%=mgrPost[i].getWriter().equals("null")?"익명":mgrPost[i].getWriter() %></td>
		<td><%=mgrPost[i].getWritetime() %></td>
		<td><%=mgrPost[i].getModtime()==null?"":mgrPost[i].getModtime() %></td>
		<td><%=mgrPost[i].getModcnt() %></td>
		<td><a style="text-decoration : none;" href="index.jsp?title=<%=mgrPost[i].getTitle() %>&writer=admin&mod=1">편집 </a>&nbsp;
		<a style="text-decoration : none;" href="index.jsp?title=<%=mgrPost[i].getTitle() %>&writer=admin&mod=3"> 삭제 </a></td>
	</tr>
	<%} %>
	</table>
	</form>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<jsp:useBean id="Postsc" class="system.PostSearch"></jsp:useBean>
<jsp:useBean id="Postmgr" class="system.PostMgr"></jsp:useBean>

<%
	String column = null;
	String value = null;
	int viewPage = 0;
	int totalPage = 1;
	if(request.getParameter("column") != null) column = request.getParameter("column");
	if(request.getParameter("value") != null) value = request.getParameter("value");
	if(request.getParameter("page") != null) viewPage = Integer.parseInt(request.getParameter("page"));
	
	Posts[] mgrPost = null;
	if(column == null || value == null)mgrPost = Postsc.getList();
	else mgrPost = Postsc.getColumnPosts(column, value);
	totalPage += mgrPost.length / 20;
%>

<div class="admin-article">
	<h2>게시물 관리</h2>
	<div class="result-table" style="margin-bottom: 0.5rem;">
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
	</div>
	
	<div class="search-box" style="width: 300px; margin: 0 auto;">
	<form action="admin">
	<input type="hidden" name="mod" value="1">
	<select name="column">
		<option value="title">제목</option>
		<option value="writer">작성자</option>
	</select>
	<input type="text" name="value" />
	<button type="submit">검색</button>
	</form>
	</div>
	
	<div class="page-box" style="width: 300px; margin: 0 auto;">
	
	</div>
	
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="system.Members" %>
<!DOCTYPE html>
<jsp:useBean id="MemberMgr" class="system.MemberMgr"/>
<%
	Members[] mgrMember = null;
	mgrMember = MemberMgr.getMemberList();
%>
<div class="admin-article">
	<h2>맴버 관리</h2>
	<table class="table1" style="font-size: 0.8rem;">
	<tr>
		<th>ID</th><td>nickname</td><td>email</td><td>가입일</td><td></td>
	</tr>
	<%for(int i=0;i<mgrMember.length;i++){ %>
	<tr>
		<th><%=mgrMember[i].getId() %></th>
		<td><%=mgrMember[i].getNickname() %></td>
		<td><%=mgrMember[i].getEmail() %></td>
		<td><%=mgrMember[i].getRegisterDate() %></td>
		<td><a href="admin?mod=1&column=writer&value=<%=mgrMember[i].getId() %>">작성글 보기</a></td>
	</tr>
	<%} %>
	</table>
</div>
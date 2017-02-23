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
<h2 class="sub-header">맴버 관리</h2>
<div class="table-responsive" style="margin-bottom: 0.5rem">
	<table class="table table-striped">
	<thead>
	<tr>
		<th>ID</th><td>nickname</td><td>email</td><td>가입일</td><td></td>
	</tr>
	</thead>
	<tbody>
	<%for(int i=0;i<mgrMember.length;i++){ %>
	<tr>
		<th><%=mgrMember[i].getId() %></th>
		<td><%=mgrMember[i].getNickname() %></td>
		<td><%=mgrMember[i].getEmail() %></td>
		<td><%=mgrMember[i].getRegisterDate() %></td>
		<td><a href="admin?mod=1&column=writer&value=<%=mgrMember[i].getId() %>">작성글 보기</a></td>
	</tr>
	<%} %>
	</tbody>
	</table>
</div>
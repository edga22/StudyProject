<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Posts" %>
<!DOCTYPE html>
<jsp:useBean id="Postsc" class="system.PostSearch"></jsp:useBean>

<%
	Posts[] post = Postsc.getList();
%>
<h3>전체 게시글 수 : <%=post.length %></h3>

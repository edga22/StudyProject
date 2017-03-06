<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<jsp:useBean id="memMgr" class="system.MemberMgr" />
<!DOCTYPE html>
<html>
<%
	request.setCharacterEncoding("UTF-8");
	String memberID = "";
	String memberPW = "";
	String title = "대문";
	
	if(request.getParameter("memberID") != null) memberID = request.getParameter("memberID");
	if(request.getParameter("memberPW") != null) memberPW = request.getParameter("memberPW");
	if(session.getAttribute("title") != null) title = (String)session.getAttribute("title");
	
	if(memMgr.passCheck(memberID, memberPW)){
		session.setAttribute("memInfo", memMgr.getMember(memberID, memberPW));
		session.setAttribute("memID", memberID);
		session.setMaxInactiveInterval(600);
%>
<script>
	alert("로그인 성공");
</script>
<%
	response.sendRedirect("index?title="
		+URLEncoder.encode(title, "UTF-8"));
	}else{
%>
<script>
	alert("아이디와 비밀번호를 확인하세요");
	history.go(-1);
</script>
<%
	}
%>
<head>
</head>
<body>
</body>
</html>
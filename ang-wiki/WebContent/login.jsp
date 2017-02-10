<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("memID") != null){
		response.sendRedirect("index.jsp");
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/global.css" rel="stylesheet">
<link href="./css/login.css" rel="stylesheet">
<title>WIKI-로그인</title>
</head>
<body>
<div class="login-wrapper">	
	<div class="login-inner">	
		<div class="login-sheet">
		<form method="post" action="loginOK.jsp">
		<table class="table1">
			<tr>
				<td colspan="2" align="center">로그인 </td>
			</tr>
			<tr>
				<th scope="row">ID</th><td><input type="text" name="memberID"></td>
			</tr>
			<tr>
				<th scope="row">PASSWORD</th><td><input type="password" name="memberPW"></td>
			</tr>				
			<tr>
				<td colspan="2" align="center">
				
				<a class="btn btn2" href="signup.jsp">회원가입</a> 										
				<button class="btn btn1" type="submit">로그인</button>
				</td>
			</tr>
		</table>
		</form>		
		</div>			
	</div>	
</div>
</body>
</html>
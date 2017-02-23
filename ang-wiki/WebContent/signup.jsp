<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("memInfo") != null){
		response.sendRedirect("index");
	}
%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원 가입</title>
<link href="./css/bootstrap.min.css" rel="stylesheet">
<link href="./css/login.css" rel="stylesheet">
</head>
<body>
<div class="login-wrapper">
	<div class="login-inner">
		<div class="login-sheet">
		<form method="post" action="signupcheck.jsp">
		<table class="table1">
			<tr>
				<td colspan="2" align="center">회원가입 </td>
			</tr>
			<tr>
				<th scope="row">ID</th><td><input class="form-control" type="text" name="memberID" required></td>
			</tr>
			<tr>
				<th scope="row">PASSWORD</th><td><input class="form-control" type="password" name="memberPW" required></td>
			</tr>
			<tr>
				<th scope="row">NICKNAME</th><td><input class="form-control" type="text" name="memberNK"></td>
			</tr>				
			<tr>
				<th scope="row">E-MAIL</th><td><input class="form-control" type="email" name="memberEM" required></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
								 										
				<button  class="btn btn-primary" type="submit">제출하기</button>				
				
				</td>
			</tr>
		</table>		
		</form>	
		</div>
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="./js/bootstrap.js"></script>
</body>
</html>
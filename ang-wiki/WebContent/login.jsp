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
<link href="./css/bootstrap.min.css" rel="stylesheet">
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
				<th scope="row">ID</th><td><input class="form-control" type="text" name="memberID"></td>
			</tr>
			<tr>
				<th scope="row">PASSWORD</th><td><input class="form-control" type="password" name="memberPW"></td>
			</tr>				
			<tr>
				<td colspan="2" align="center">
				
				<a class="btn btn-primary" href="signup.jsp">회원가입</a> 										
				<button class="btn btn-default" type="submit">로그인</button>
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
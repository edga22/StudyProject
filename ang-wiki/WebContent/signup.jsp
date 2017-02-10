<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 가입</title>
<link href="./css/global.css" rel="stylesheet">
<link href="./css/login.css" rel="stylesheet">
</head>
<body>
<div class="login-wrapper">
	<div class="login-inner">
		<div class="login-sheet">
		<form method="post" action="signupOk.jsp">
		<table class="table1">
			<tr>
				<td colspan="2" align="center">회원가입 </td>
			</tr>
			<tr>
				<th scope="row">ID</th><td><input type="text" name="memberID"></td>
			</tr>
			<tr>
				<th scope="row">PASSWORD</th><td><input type="password" name="memberPW"></td>
			</tr>
			<tr>
				<th scope="row">NICKNAME</th><td><input type="text" name="memberNK"></td>
			</tr>				
			<tr>
				<th scope="row">E-MAIL</th><td><input type="text" name="memberEM"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
								 										
				<button  class="btn btn1" type="submit">제출하기</button>				
				
				</td>
			</tr>
		</table>		
		</form>	
		</div>
	</div>
</div>
</body>
</html>
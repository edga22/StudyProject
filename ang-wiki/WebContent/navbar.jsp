<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%	
	String memberID = (String)session.getAttribute("memID");
	String greeting = "";
	String loginhref = "";
	String loginword = "";
	boolean admin = false;
	if(memberID != null){		
		greeting = memberID+ "님 환영합니다";	
		loginhref = "logout.jsp";
		loginword = "로그아웃";
		if(memberID.equals("admin")) admin = true;
	}else{	
		loginhref = "login.jsp";
		loginword = "로그인";
	}
	
	if(request.getParameter("title") != null) session.setAttribute("title", request.getParameter("title"));
	else session.setAttribute("title", "대문");
%>
<html>
<link href="./css/global.css" rel="stylesheet">
<link href="./css/navbar.css" rel="stylesheet">
<div class="navbar">
	<ul class="navbar nav">
		<li class="nav-item">
			<a class="link" href="index.jsp">Angel위키</a>
		</li>
		<li class="nav-item">
			
		</li>
	</ul>
	<ul class="navbar pull-right"><% if(admin){ %>
		<li class="nav-item">
			<a class="link" href="admin">관리자페이지</a>
		</li>
		<%} %>
		<li class="nav-item">
			<a class="link" href="index.jsp?mod=2">글쓰기</a>
		</li>
		<li class="nav-item">		
		<form action="index.jsp" method="get">
			<div class="search-box">
			<input type="text" name="title" class="in-search" placeholder="Search" tabindex="1">			
			<button id="searchBtn" class="btn-small btn2" type="submit"> 
				<img alt="search" src="./source/search.png" height="15" width="15">
			</button>
			</div>
		</form>
		</li>
		<li class="nav-item">
		<%=greeting %>
		<a class="link" href="<%=loginhref %>"><span><%=loginword %></span></a>
		</li>
	</ul>
</div>	 		
</html>
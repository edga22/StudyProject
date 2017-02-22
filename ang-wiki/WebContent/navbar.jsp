<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Members" %>
<!DOCTYPE html>
<%	
	Members member = (Members)session.getAttribute("memInfo");
	String memberID = null;
	String nickname = null;
	if(member != null){
		memberID = member.getId();
		nickname = member.getNickname();
	}else session.setAttribute("memID", request.getRemoteAddr());
	String greeting = "";
	String loginhref = "";
	String loginword = "";
	
	boolean admin = false;
	if(memberID != null){		
		greeting = nickname;	
		loginhref = "logout.jsp";
		loginword = "로그아웃";
		if(memberID.equals("admin")) admin = true;
	}else{	
		loginhref = "login.jsp";
		loginword = "로그인";
	}	
%>
<html>
<div class="container">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		Menu
		</button>
		<a class="navbar-brand" href="index">Angel위키</a>
	</div>
	<div class="navbar-collapse collapse" id="navber">
		<ul class="nav navbar-nav">
			<li>
			<% if(admin){ %>			
			<a href="admin">관리자페이지</a>
			<%} %>
			</li>
			<li>
			<a href="index?mod=2">글쓰기</a>	
			</li>
			<li>				
			<p class="navbar-text"><%=greeting %></p>
			</li>		
			<li>
			<a href="<%=loginhref %>"><%=loginword %></a>		
			</li>
		</ul>					
		<form class="navbar-form navbar-right" action="index" method="get">
			<div class="form-group">
			<input type="text" name="title" class="form-control" placeholder="Search" tabindex="1">						
			</div>
			<button id="searchBtn" class="btn btn-success" type="submit"> 
				검색
			</button>
		</form>
		
		
	</div>	
</div>	 		
</html>
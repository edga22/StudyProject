<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="system.Members" %>
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
<div class="container">
	<div class="navbar-header">		
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainnavbar">
		Menu<span class="caret"></span></button>		
		<a class="navbar-brand" href="index">Angel위키</a>
	</div>
	<div class="navbar-collapse collapse" id="mainnavbar">
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
			<p class="navbar-text" style="margin : 0; padding : 15px"><%=greeting %></p>
			</li>		
			<li>
			<a href="<%=loginhref %>"><%=loginword %></a>		
			</li>
		</ul>					
		<form class="navbar-form navbar-right" action="index" method="get">
			<div class="input-group">
				<input type="text" name="title" class="form-control" placeholder="Search" tabindex="1">	
			<div class="input-group-btn">
				<button id="searchBtn" class="btn btn-primary" type="submit" name="mod" value="4"><i class="glyphicon glyphicon-search"></i></button>
				<button id="goBtn" class="btn btn-success" type="submit"><i class="glyphicon glyphicon-arrow-right"></i></button>
			</div>
			</div>
		</form>		
	</div>	
</div>	 
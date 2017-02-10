<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ page import="system.Posts" %>
<!DOCTYPE html>
<jsp:useBean id="PostSc" class="system.PostSearch"></jsp:useBean>
<html>

<%
	request.setCharacterEncoding("UTF-8");
	String pageTitle = "";
	String title = request.getParameter("title");
	String editmod = request.getParameter("mod");
	/*
		에디트 모드 ( 0 = 뷰어,검색결과 / 1 = 편집 / 2 = 새글 / 3=삭제 확인)
	*/	
	int mod = (editmod != null)? Integer.parseInt(editmod) : 0;
	
	if(mod == 2) title = "새글 쓰기";
	else if(mod == 1) title += " 수정";
	else if(mod == 3) title += " 삭제 확인";
	else if(title == null) title = "대문";
	
	if(mod == 0 && !title.equals("대문")){
		Posts[] scResult = PostSc.getTitlePosts(title);
		if(scResult.length != 1){}
	}
	
	
	pageTitle = "Angel 위키 - "+title;
	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/global.css" rel="stylesheet">
<link href="./css/index.css" rel="stylesheet">
<link href="./css/content.css">
<title><%=pageTitle %></title>
</head>
<body>
<div class="navbar-wrapper">
	<jsp:include page="navbar.jsp"></jsp:include>	
</div>

<div class="content-wrapper">
<aside class="sidebar">
	<jsp:include page="sidebar.jsp"></jsp:include>
</aside>
<article class="wiki-article">
<% 
	if(mod == 0){
%>
	<jsp:include page="content-reader.jsp"></jsp:include>
<%	}else if(mod == 1){ %>
	<jsp:include page="content-moder.jsp"></jsp:include>
<%	}else if(mod == 2){ %>
	<jsp:include page="content-writer.jsp"></jsp:include>
<%	}else if(mod == 3){ %>
	<jsp:include page="content-delete.jsp"></jsp:include>	
<%	} %>	
</article>

</div>
<div class="foot-wrapper">
	<div class="foot">
	<span>서버정보 : <%=application.getServerInfo() %></span>
	
	</div>
</div>
</body>
</html>
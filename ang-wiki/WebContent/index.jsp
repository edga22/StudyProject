<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ page import="system.Posts" %>
<!DOCTYPE html>
<jsp:useBean id="PostSc" class="system.PostSearch"></jsp:useBean>
<html>
<%
/*	인덱스 페이지에서 대부분의 유져 서비스를 랜더링 	*/
	request.setCharacterEncoding("UTF-8");
	String pageTitle = "";
	String title = request.getParameter("title"); if(title == null) title = "대문"; // 타이틀이 없으면 대문 불러오기
	String editmod = request.getParameter("mod");
	String tag = request.getParameter("tag");
		
	/*
		에디트 모드 ( 0 = 뷰어 / 1 = 편집 / 2 = 새글 / 3 = 삭제 확인 / 4 = 검색)
	*/	
	int mod = (editmod != null)? Integer.parseInt(editmod) : 0;
	
	if((mod == 0 && !PostSc.containsTitle(title)) || mod == 4){	// 없는 제목이거나 검색기능일때
		mod = 4;
		if(tag == null)tag = title;
		else title = tag;
		Posts[] scTagResult = PostSc.getTagPosts(tag);
		session.setAttribute("TagPosts", scTagResult);
				
		Posts[] scResult = PostSc.getTitlePosts(title);
		session.setAttribute("Posts", scResult);		
	}
	else if(mod == 1) title += " 수정";
	else if(mod == 2) title = "새글 쓰기";	
	else if(mod == 3) title += " 삭제 확인";
	
	session.setAttribute("title", title);
	session.setAttribute("tag", tag);
	pageTitle = "Angel 위키 - "+title;		
%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->


<link href="./css/bootstrap.min.css" rel="stylesheet">
<link href="./css/global.css" rel="stylesheet">

<title><%=pageTitle %></title>
</head>
<body>
<nav class="navbar navbar-default">
	<jsp:include page="navbar.jsp"></jsp:include>	
</nav>

<div class="container">
<div class="row">

	<div class="col-md-9 col-sm-12">
	<% 
		if(mod == 0){ %>
		<jsp:include page="content-reader.jsp"></jsp:include>
	<%	}else if(mod == 1){ %>
		<jsp:include page="content-moder.jsp"></jsp:include>
	<%	}else if(mod == 2){ %>
		<jsp:include page="content-writer.jsp"></jsp:include>
	<%	}else if(mod == 3){ %>
		<jsp:include page="content-delete.jsp"></jsp:include>	
	<%	}else if(mod == 4){ %>
		<jsp:include page="content-searcher.jsp"></jsp:include>	
	<%	} %>
	</div>
	<div class="col-md-3 hidden-xs">
		<jsp:include page="sidebar.jsp"></jsp:include>
	</div>
	
</div>
</div>
<footer class="foot-wrapper">
<% String clientAddr = request.getRemoteAddr(); %>
	<div class="foot">
	<span>접속주소 : <%=clientAddr %></span><span>, 서버정보 : <%=application.getServerInfo() %></span>	
	</div>
</footer>

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
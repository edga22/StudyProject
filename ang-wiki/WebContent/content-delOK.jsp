<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<jsp:useBean id="PostMgr" class="system.PostMgr" />
<html>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

String memberID = (String)session.getAttribute("memID");
String writer = request.getParameter("writer");
String title = (String)session.getAttribute("title"); if(title == null) title = "대문";

if((memberID!=null)?(memberID.equals(writer) || memberID.equals("admin")):false){
	if(!PostMgr.delPost(title)){
	%>
		<script>
		alert("글삭제에 실패하였습니다.");		
		history.go(-1);
		</script>		
	<%
	}else {
		%>
		<script>
		alert("글삭제에 성공하였습니다.");		
		location.replace('index');
		</script>
		<%
	}
}else{
	%>
	<script>
	alert("삭제할 권한이 없습니다.");
	history.go(-1);
	</script>		
	<%	
}
%>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="memMgr" class="system.MemberMgr" />
<!DOCTYPE html>
<html>
<%
	String id = request.getParameter("memberID");
	String pw = request.getParameter("memberPW");
	String nk = request.getParameter("memberNK");
	String em = request.getParameter("memberEM");
	
	if(!memMgr.signUp(id, pw, nk, em)){		// 가입에 실패할 경우		
%>
	<script type="text/javascript">
	alert("회원 가입에 실패하였습니다.");
	history.go(-1);
	</script>
<%
	}else{
%>
	<script type="text/javascript">
	alert("회원 가입에 성공하였습니다.");
	location.href="index.jsp";
	</script>
<%
	}
%>
</html>
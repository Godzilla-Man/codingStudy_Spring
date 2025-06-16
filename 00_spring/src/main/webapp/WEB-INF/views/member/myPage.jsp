<%@page import="com.example.demo.member.model.dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>마이페이지</h1>
	
	<hr>
	
	<%
	Member loginMember = (Member) session.getAttribute("loginMember");
	%>
	
	아이디 : ${loginMember.memberId} <br>
	이름 : ${loginMember.memberName} <br>
	이메일 : ${loginMember.memberEmail} <br>
	전화번호 : ${loginMember.memberPhone} <br>	
	<%if(loginMember.getMemberLevel().equals("1")) {%>
	관리자
	<%}else if(loginMember.getMemberLevel().equals("2")) {%>
	정회원
	<%}else { %>
	준회원
	<%} %>
	<br>
		
</body>
</html>
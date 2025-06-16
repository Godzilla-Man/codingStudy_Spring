<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>동적 SQL 결과 페이지</h1>
	
	<hr>
	
	<h3>동적 SQL - 1 - 회원 정보</h3>
	
	<table border='1'>
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>이메일</th>
			<th>주소</th>
			<th>전화번호</th>
			<th>등급</th>
			<th>가입일</th>
		</tr>
				
		<c:forEach var="m" items="${memberList }">
		<tr>
			<td>${m.memberNo }</td>
			<td>${m.memberId }</td>
			<td>${m.memberName }</td>
			<td>${m.memberEmail }</td>
			<td>${m.memberAddr }</td>
			<td>${m.memberPhone }</td>
			<td>${m.levelName }</td>		
			<td>${m.enrollDate }</td>
		</tr>		
		</c:forEach>
	</table>
	
</body>
</html>
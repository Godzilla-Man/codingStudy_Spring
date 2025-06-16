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
	<h1>전체회원조회</h1>
	
	<hr>
	
	<table border="1">
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>이메일</th>
			<th>전화번호</th>
			<th>가입일</th>
		</tr>
		<c:forEach items="${memberList}" var="member">
			<tr>
				<td>${member.memberId}</td>
				<td>${member.memberName}</td>
				<td>${member.memberEmail}</td>
				<td>${member.memberPhone}</td>
				<td>${member.enrollDate}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>
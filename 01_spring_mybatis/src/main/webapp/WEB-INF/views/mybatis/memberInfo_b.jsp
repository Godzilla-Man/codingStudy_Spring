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
	
	<h3>For - 회원 정보</h3>
	
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
			
			<c:if test="${m.memberLevel eq 1 }">
			<td>관리자</td>
			</c:if>
			
			<c:if test="${m.memberLevel eq 2 }">
			<td>정회원</td>
			</c:if>
			
			<c:if test="${m.memberLevel eq 3 }">
			<td>준회원</td>
			</c:if>
			
			<td>${m.enrollDate }</td>
		</tr>		
		</c:forEach>
	</table>
	
</body>
</html>
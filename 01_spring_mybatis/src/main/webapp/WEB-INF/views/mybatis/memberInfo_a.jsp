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
	
	<h3>If - 회원정보</h3>
	
	<table border='1'>
		<tr>
			<c:if test="${not empty chkInfo.SFlag1}">
			<th>번호</th>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag2}">
			<th>아이디</th>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag3}">
			<th>이름</th>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag4}">
			<th>이메일</th>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag5}">
			<th>전화번호</th>
			</c:if>
		</tr>
		
		<c:forEach var="m" items="${memberList }">
		<tr>
			<c:if test="${not empty chkInfo.SFlag1}">
			<td>${m.memberNo }</td>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag2}">
			<td>${m.memberId }</td>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag3}">
			<td>${m.memberName }</td>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag4}">
			<td>${m.memberEmail }</td>
			</c:if>
			<c:if test="${not empty chkInfo.SFlag5}">
			<td>${m.memberPhone }</td>
			</c:if>
		</tr>		
		</c:forEach>
			
	</table>
	
</body>
</html>
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
	<h1>게시글 목록</h1>
	
	<hr>
	
	<a href='/notice/writeFrm'>작성하기</a>
	<table border='1'>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>			
		</tr>
		<c:forEach var="n" items="${noticeList}">
			<tr>
				<!-- RNum 순서대로 번호가 매겨지도록 함. 게시글은 매퍼에서 게시글 등록일 내림차순 형태로 정렬 -->
				<td>${n.RNum}</td>
				<td><a href='/notice/getInfo?noticeNo=${n.noticeNo}'>${n.noticeTitle}</a></td>
				<td>${n.noticeWriter}</td>
				<td>${n.noticeDate}</td>
			</tr>
		</c:forEach>
	</table>
	<div>
		${pageNavi}
	</div>
	<div>
		<form action='/notice/getList' method='get'>
			<%-- 조건 입력 후 게시글 요청 시, 조건에 만족하는 새로운 게시글 목록이 조회되므로 1페이지 요청 --%>		
			<input type='hidden' name='reqPage' value='1'>
			<select name="select">
				<option value="title" <c:if test="${select eq 'title'}">selected</c:if>>제목</option>
				<option value="id" <c:if test="${select eq 'id'}">selected</c:if>>아이디</option>				
			</select>
			<input type='text' name='keyword' value="${keyword}">
			<input type='submit' value='검색'>
		</form>
	</div>	
</body>
</html>
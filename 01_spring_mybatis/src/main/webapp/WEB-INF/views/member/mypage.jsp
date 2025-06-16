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
	<h1>마이페이지</h1>
	
	<hr>
	
	<%--
	회원 정보 출력
	수정하기 버튼 클릭 시, DB에 정보 업데이트
	form 태그로 제작
	수정 대상 정보 : 비밀번호, 이름, 주소, 전화번호
	
	(1) 회원 정보 수정
	 - db 작업이 필요한지? -> O
	 - SQL은 ? ->
	   update tbl_member
	      set member_pw = ?
	         ,member_name = ?
	         ,member_addr = ?
	         ,member_phone = ?
        where member_no = ?	 
	 --%>
	 
	
	<form action='/member/update' method='post'> 
		<input type='hidden' name='memberNo' value='${loginMember.memberNo}'>
		<table border='1'>		
			<tr>
				<th>ID</th>
				<td>${loginMember.memberId}</td>			
			</tr>
			
			<tr>
				<th>PW</th>
				<td><input type='password' name='memberPw'></td>			
			</tr>
			
			<tr>
				<th>이름</th>
				<td><input type='text' name='memberName' value='${loginMember.memberName}'></td>			
			</tr>
			
			<tr>
				<th>주소</th>
				<td><input type='text' name='memberAddr' value='${loginMember.memberAddr}'></td>			
			</tr>
			
			<tr>
				<th>전화번호</th>
				<td><input type='text' name='memberPhone' value='${loginMember.memberPhone}'></td>			
			</tr>
			
			<tr>
				<th>이메일</th>
				<td>${loginMember.memberEmail}</td>
			</tr>
			
			<tr>
				<th>등급</th>				
				<c:if test='${loginMember.memberLevel eq 1}'>
					<td>관리자</td>
				</c:if>
				<c:if test='${loginMember.memberLevel eq 2}'>
					<td>정회원</td>
				</c:if>
				<c:if test='${loginMember.memberLevel eq 3}'>
					<td>준회원</td>
				</c:if>				
			</tr>
			
			<tr>
				<th>가입일</th>
				<td>${loginMember.enrollDate}</td>
			</tr>
			
			<tr>
				<th colspan='2'>
					<input type='submit' value='수정하기'>
					<a href='/member/delete?memberNo=${loginMember.memberNo}'>탈퇴하기</a>
				</th>
			</tr>			
		</table>				
	</form>
	
</body>
</html>
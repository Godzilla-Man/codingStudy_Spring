<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<h1>관리자 페이지</h1>
	
	<hr>
	
	<table border='1'>
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>이메일</th>
			<th>등급</th>
			<th>변경</th>			
		</tr>
		
		<c:forEach var="m" items="${memberList}">
		<tr>
			<td>${m.memberNo }</td>
			<td>${m.memberId }</td>
			<td>${m.memberName }</td>
			<td>${m.memberEmail }</td>
			<td>
				<select name="memberLevel">
					<c:choose>
						<c:when test="${m.memberLevel eq 1 }">
							<option value="1" selected>관리자</option>
							<option value="2">정회원</option>
							<option value="3">준회원</option>
						</c:when>
						<c:when test="${m.memberLevel eq 2 }">
							<option value="1">관리자</option>
							<option value="2" selected>정회원</option>
							<option value="3">준회원</option>
						</c:when>
						<c:when test="${m.memberLevel eq 3 }">
							<option value="1">관리자</option>
							<option value="2">정회원</option>
							<option value="3" selected>준회원</option>
						</c:when>			
					</c:choose>
				</select>
			</td>
			<td>
				<button onclick="updateLevel(this)">등급 변경</button>
			</td>
		</tr>		
		</c:forEach>		
	</table>
	
	<script>
	function updateLevel(btnObj){
		let trEl = $(btnObj).parents('tr');		
		
		let memberNo = $(trEl).find('td').eq(0).html(); //tr의 자식 td들 중 첫번째 요소의 값
		let memberLevel = $(trEl).find('select[name=memberLevel] option:selected').val();		
		
		$.ajax({
			url : '/member/updateLevel',
			type : 'get',
			data : {'memberNo' : memberNo, 'memberLevel' : memberLevel},
			success : function(res){
				if(res > 0) {
					alert('정상적으로 등급이 변경되었습니다.');
				}
			},
			error : function(){
				console.log('ajax 오류');
			}
			
		})
		
	}
	</script>
</body>
</html>
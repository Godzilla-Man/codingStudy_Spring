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
	<h1>마이바티스 동적 SQL 테스트 페이지</h1>
	
	<hr>
	
	<h3>IF 테스트</h3>
	
	<pre>
	체크한 컬럼만 조회. 체크 갯수에 따라 select 절의 조회 대상 컬럼 갯수가 달라지도록
	</pre>
	
	<form action='/mybatis/dynamic/ifTest' method='get'>
    	<input type="checkbox" name="sFlag1"> No
    	<input type="checkbox" name="sFlag2"> Id
    	<input type="checkbox" name="sFlag3"> Name
    	<input type="checkbox" name="sFlag4"> Email
    	<input type="checkbox" name="sFlag5"> Phone
    	<input type="submit" value="회원정보 요청">
    </form>
    
    <h3>ForEach 테스트</h3>
    <pre>
    체크한 회원들의 모든 컬럼 정보 조회. where 절의 조회 대상 회원 명수가 달라지도록 처리
    </pre>
    <form action='/mybatis/dynamic/forTest' method='get'>
    	<table border='1'>
    		<tr>
    			<th>선택</th>
    			<th>아이디</th>
   			</tr>
   			
   			<c:forEach var="m" items="${allMemberList }">
   			<tr>
   				<td><input type='checkbox' name='members' value='${m.memberNo}'></td>
   				<td>${m.memberId }</td>
 			</tr>
 			</c:forEach>
 			
 			<tr>
 				<th colspan='2'>
 					<input type='submit' value='회원정보 요청'>
 				</th>
			</tr>   			
    	</table>
    </form>
    
    <h3>choose 테스트</h3>
    <pre>
    	이름으로 검색 라디오 체크 및 검색 => 입력한 이름을 포함하는 회원 조회
    	아이디로 검색 라디오 체크 및 검색 => 입력한 아이디와 일치하는 회원 조회
    </pre>
    <form action='/mybatis/dynamic/cooseTest' method='get'>
    	<input type='radio' name='select' value='name'> 이름으로 검색
    	<input type='radio' name='select' value='id'> 아이디로 검색
    	<input type='text' name='keyword'>
    	<input type='submit' value='회원 정보 요청'>
    </form>
    
    <h3>동적 테스트 - 1</h3>
    <pre>
    	체크한 지역에 거주하는 회원 정보 조회
    </pre>
    <form action='/mybatis/dynamic/test1' method='get'>
    	<input type='checkbox' name='sFlag1' value='서울'> 서울
    	<input type='checkbox' name='sFlag2' value='경기'> 경기
    	<input type='checkbox' name='sFlag3' value='대전'> 대전
    	<input type='checkbox' name='sFlag4' value='부산'> 부산
    	
    	<br>
    	
    	<input type='submit' value='회원 정보 요청'>
    </form>
    
    <h3>동적 SQL 테스트 -2</h3>
    <pre>
    	등급 정보 포함 체크박스 체크 시, tbl_level에 존재하는 level_name
    	컬럼명도 조회    
    </pre>
    
    <form action='/mybatis/dynamic/test2' method='get'>
    	<input type='checkbox' name='sFlag1'> 등급 정보 포함
    	<br>
    	<input type='submit' value='회원 정보 요청'>
    </form>
    
    <h3>동적 SQL 테스트 - 3</h3>
    <pre>
    	이름 체크 및 검색어 입력 => 입력한 이름 포함하는 회원 조회 후, 이름 오름차순으로 정렬
    	주소 체크 및 검색어 입력 => 입력한 주소 포함하는 회원 조회 후, 주소 오름차순으로 정렬
    	전화번호 체크 및 검색어 입력 => 입력한 전화번호 포함하는 회원 조회 후, 전화번호 오름차순으로 정렬
    </pre>
    
    <form action='/mybatis/dynamic/test3' method='get'>
    	<input type='radio' name='sFlag1' value='member_name'> 이름
    	<input type='radio' name='sFlag1' value='member_addr'> 주소
    	<input type='radio' name='sFlag1' value='member_phone'> 전화번호
    	<br>
    	검색 : <input type='text' name='sFlag2'>
    	<input type='submit' value='회원정보 요청'>
    </form>
    
</body>
</html>
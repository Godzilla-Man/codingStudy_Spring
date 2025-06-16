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
	<h1>Hello, Mybatis Project</h1>
	
	<%--
	Mybatis Framework : Java 애플리케이션과 데이터베이스간의 상호 작용을 간편하게 하기 위한 영속성 프레임워크.
	
	영속성 : 애플리케이션이 종료되어도 데이터가 유지되는 성질을 의미한다.
		- Mybatis, Hivernate, JPA 등등
		
	Mybatis 특징)
	
	(1) 자동 매핑 : DB 조회 결과를 JAVA 객체로 자동으로 매핑하는 기능을 제공.(기존 DAO에서 ResultSet.getString() 작업)
	(2) XML 파일에 SQL을 작성 : SQL과 JAVA 코드가 분리되어 작성되기 때문에 가독성 증가, 유지보수도 용이하게 됨.
	(3) 동적 SQL 지원 : if, choose, foreach와 같은 구문을 지원하여, 동적인 SQL 작성이 가능하다.
	(4) SQL 재사용 : XML에 작성된 하나의 SQL을 여러곳에서 호출하여 재사용이 가능.
	
	Mybatis 세팅)
	
	- Window - Preferences - XML - XML Catalog에 Mapper 관련 설정
	- Window - Preferences - XML (Wild Web Developer)의 Download ~ 체크박스 체크
	- 프로젝트 생성 시, 관련 의존성 추가(Mybatis Framework)
	- application.properties에 Mapper 경로, Null 관련 설정
	- src/main/resource에 mapper XML 파일 생성
	- DAO 파일은 interface로 생성하고, 어노테이션은 @Mapper를 사용한다.
	- src/main/resource 하위에 XML 파일들이 위치할 mapper 폴더 생성
	- mapper 폴더 하위에, XML 파일 생성(파일명 지정 -> 첫번 째 라디오 버튼 선택 후 Next -> 두번 째 라디오 버튼 선택 후, 하단에서 관련 설정 클릭 후 Next -> Finish )
	- 생성된 XML 파일 내부에, mapper 시작 태그에 namespace 속성 작성. 속성 값은 DAO 파일 경로
	
	BCrypt 세팅)
	
	- 메이븐 레포지토리 사이트에서 Boot 기준 시큐리티 라이브러리 검새갛여, dependency 태그 pom.xml에 추가
	- WebConfig.java 파일에 BCrypt 객체를 생성하여 IoC 컨테이너에 Bean으로 등록
	- Application.java에 시큐리티 적용 시, 첫 화면이 제공되는 로그인 화면이 보이는 설정을 제거하는 설정을 작성.	
	 --%>	
	 
	<hr>	
	
	<c:if test="${empty sessionScope.loginMember }">  
	<form action='/member/login' method='post'>
		ID : <input type='text' name='memberId'> <br>
		PW : <input type='password' name='memberPw'> <br>
		<input type='submit' value='로그인'> <a href='/member/joinFrm'>회원가입</a>
		
	</form> 
	</c:if>
	
	<c:if test="${not empty sessionScope.loginMember }">
		<h1>[${loginMember.memberName}]님 환영합니다~~~~~~~~~~~~</h1>
		<a href='/member/mypage'>마이페이지</a> <br>
		<a href='/member/logout'>로그아웃</a>
		<a href='/member/allMemberList'>전체회원조회</a>
		<c:if test="${loginMember.memberLevel eq 1 }">
		<a href='/member/adminPage'>관리자 마이페이지</a>
		</c:if>
		
		<a href='/mybatis/dynamic/movePage'>동적 SQL 테스트 페이지로 이동</a>
		
		<!-- 게시글 목록 요청하며, 1페이지 요청 -->	
		<a href='/notice/getList?reqPage=1'>게시글 목록 페이지로 이동</a>	
	</c:if>
	
</body>
</html>
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
	<%--
	
	#Framework : 개발에 필요한 기본적인 기능들을, 일정한 구조와 규칙대로 미리 구현해둔 툴.
				 개발자가 효율적으로 애플리케이션을 만들 수 있도록 재사용 가능한 코드와 도구들의 집합.
				 아키텍쳐와 규칙을 따르므로, 코드 구조가 체계적이고 유지보수가 용이.
				 
	#Spring Framework
		- JAVA 기반의 엔터프라이즈급(기업용) 애플리케이션을 개발하기 위한 경량화된 프레임워크	 
		- 기존 EJB 기반의 기업용 프레임워크의 비효율성과 복잡도, 프로젝트의 무거움을 보완하고자 나온 프레임워크 
		
	#Maven
		- 자바용 프로젝트 빌드 관리 도구
		- 기존에 라이브러리 파일들(.jar)을 수동으로 다운로드 받아, 폴더에 추가하는 방식 => pom(Project Object Model).xml에 라이브러리 정보를 작성하여, 자동으로 다운로드하여 관리.
		- pom.xml : 한 개의 프로젝트에서 사용하는 라이브러리 버전, 자바 버전, 프레임워크 버전들을 통합해서 관리하는 파일
		- Maven과 같은 빌드 관리 도구로, Gradle이라는 관리 도구도 존재함.
	
	#Ioc와 DI (스프링 핵심 기술)
		- Ioc (Inversion of Control) : 제어의 역전 또는 제어의 역행이라는 의미.
			- 스프링 프레임워크에서는 자바의 객체를 Bean이라고 얘기하는데, 이 객체의 생명주기를 개발자가 관리하는 것이 아닌, Ioc 컨테이너가 제어하겠다는 의미.(서블릿 역할과의 차이점은?)
		- DI : 의존성을 주입한다라는 의미로, Ioc 컨테이너가 관리하는 Bean을 자동으로 읽어와 객체에 주입.
	
	#Spring MVC
		- DispatcherServlet : 클라이언트 요청이 들어오면, 가장 먼저(첫번째) 요청을 위임받는 프론트 컨트롤러. 컨트롤러에 해당 요청을 위임하는 역할 및 컨트롤러 처리 결과를 뷰에게 전달하는 역할.		
		- HandlerMapping : 각 컨트롤러 메소드별 URL 정보를 가지고 있다가, DispatcherServlet이 적절한 컨트롤러에 요청할 수 있도록 지원한다.
		- Controller : 클라이언트 요청에 대해서 처리하고, 적절한 View나 Model(데이터 저장 객체)를 반환하는 역할.
		- ViewResolver : 컨트롤러가 반환하는 논리적인 뷰 이름을 실제 뷰 페이지 경로로 변환하는 역할.
		
			
	 --%>
	<h1>Hello, Spring Boot</h1>
	
	<hr>
	
	<h2>1. 스프링 GET, POST 처리</h2>
	
	<h3><a href='/getTest'>GET 요청 테스트</a></h3>
	<form action='/postTest' method='post'>
		<input type='submit' value='POST 요청 테스트'>
	</form>
	
	<h2>2. 서블릿 VS 스프링 요청 파라미터 처리</h2>
	
	<h3>기존 Servlet 방식</h3>
	<form action='/servletParamTest' method='post'>
		ID : <input type='text' name='testId'> <br>
		PW : <input type='password' name='testPw'> <br>
		<input type='submit' value='서블릿 파라미터 테스트'>
	</form>
	
	<h3>Spring 방식 - 1</h3>
	<form action='/springParamTest1' method='post'>
		ID : <input type='text' name='testId'> <br>
		PW : <input type='password' name='testPw'> <br>
		<input type='submit' value='스프링 파라미터 테스트1'>
	</form>
	
	<h3>Spring 방식 - 2</h3>
	<form action='/springParamTest2' method='post'>
		ID : <input type='text' name='memberId'> <br>
		PW : <input type='password' name='memberPw'> <br>
		<input type='submit' value='스프링 파라미터 테스트2'>
	</form>
	
	<h2>3. 서블릿 VS 스프링 응답 데이터 처리</h2>	
	<h3><a href='/servletResponseTest'>기존 Servlet 방식</a></h3>
	<h3><a href='/springResponseTest1'>스프링 방식 1</a></h3>
	<h3><a href='/springResponseTest2'>스프링 방식 2</a></h3>
	
	<h2>4. 스프링 요청 및 응답 테스트</h2>
	<form action='/springReqResTest' method='post'>
		ID : <input type='text' name='memberId'> <br>
		PW : <input type='password' name='memberPw'> <br>
		이름 : <input type='text' name='memberName'> <br>
		주소 : <input type='text' name='memberAddr'> <br>
		<input type='submit' value='스프링 요청 및 응답 테스트'>
	</form>	
	
	<hr>
	
	<h2>AJAX 테스트</h2>	
		
	<button type='button' id='ajaxTest1'>Ajax 테스트 1</button>
	<script>
		//기존 서블릿 방식의 응답
		$('#ajaxTest1').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest1',
				type : 'get',
				success : function(res){
					console.log(res);
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>
	
	<button type='button' id='ajaxTest2'>Ajax 테스트 2</button>
	<script>
		//기존 서블릿 방식의 응답
		$('#ajaxTest2').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest2',
				type : 'get',
				success : function(res){
					//res = JSON.parse(res);
					console.log(res);
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>
	
	<button type='button' id='ajaxTest3'>Ajax 테스트 3</button>
	<script>
		//기존 서블릿 방식의 응답
		$('#ajaxTest3').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest3',
				type : 'get',
				success : function(res){					
					console.log(res);
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>	
	
	<button type='button' id='ajaxTest4'>Ajax 테스트 4</button>
	<script>
		//기존 서블릿 방식의 응답
		$('#ajaxTest4').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest4',
				type : 'get',
				success : function(res){					
					console.log(res);
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>	
	
	<button type='button' id='ajaxTest5'>Ajax 테스트 5</button>
	<script>
		//기존 서블릿 방식의 응답
		$('#ajaxTest5').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest5',
				type : 'get',
				success : function(res){					
					for(let i=0; i<res.length; i++){
						console.log("===================")
						console.log('아이디 : ' + res[i].memberId);
					}
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>		
	
	<%-- 2025-06-10 스프링 두번째 수업 --%>
	<button type='button' id='ajaxTest6'>Ajax 테스트 6</button>
	<script>
		//스프링 방식 - 요청 파라미터 - 자바 객체와 바인딩 되는 객체
		$('#ajaxTest6').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest6',
				type : 'post',
				data : JSON.stringify( //자바스크립트 객체를 문자열 형태로 변환
						{memberNo : '1', memberId : 'qoeor123', memberPw : '1234', memberName : '갈코덕'}
					   ),
				contentType : 'application/json', //요청 파라미터 형식 선언
				success : function(res){					
	
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>	
	
	<button type='button' id='ajaxTest7'>Ajax 테스트 7</button>
	<script>
		//스프링 방식 - 요청 파라미터 - 자바 객체와 바인딩 되는 객체
		$('#ajaxTest7').on('click', function(){
			$.ajax({
				url : '/ajax/ajaxTest7',
				type : 'post',
				data : JSON.stringify( //자바스크립트 객체를 문자열 형태로 변환
						[
							{memberNo : '1', memberId : 'qoeor123', memberPw : '1234', memberName : '갈코덕1'},
							{memberNo : '2', memberId : 'qoeor224', memberPw : '1234', memberName : '갈코덕2'},
							{memberNo : '3', memberId : 'qoeor325', memberPw : '1234', memberName : '갈코덕3'}
						]
					    ),
				contentType : 'application/json', //요청 파라미터 형식 선언
				success : function(res){					
	
				},
				error : function(){
					console.log('ajax 통신 오류 발생');
				}
			})
		});
	</script>	
	
	<br><hr><br>
	
	<c:if test="${empty loginMember}">
	<form action='/member/login' method='post'>
		아이디 : <input type='text' name='memberId'> <br>
		비밀번호 : <input type='password' name='memberPw'>
		<input type='submit' value='로그인'>
		<a href='/member/joinFrm'>회원가입</a>	
	</form>
	</c:if>
	
	<c:if test="${not empty loginMember}">
		<h1>[${loginMember.memberName}]님 환영합니다!!!!</h1>
		<a href='/member/logout'>로그아웃</a>
		<a href='/member/mypage'>마이페이지</a>
		<a href='/member/delete'>회원탈퇴</a>
		<a href='/member/allMemberList'>전체회원조회</a>
	</c:if>
	
</body>
</html>
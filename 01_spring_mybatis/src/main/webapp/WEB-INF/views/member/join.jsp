<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<h1>회원가입</h1>
	
	<hr>
	
	<form action='/member/join' method='post'>
		ID : <input type='text' name='memberId'>
		<button type='button' id='idDuplChkBtn'>중복확인</button>
		<br>
		PW : <input type='password' name='memberPw'> <br>
		이름 : <input type='text' name='memberName'> <br>
		전화번호 : <input type='text' name='memberPhone'> <br>
		주소 : <input type='text' name='memberAddr'> <br>
		이메일 : <input type='text' name='memberEmail'> <br>
		<input type='submit' value='회원가입'>
	</form>
	
	<script>
	$('#idDuplChkBtn').on('click', function(){
		let memberId = $('input[name=memberId]').val();
		
		$.ajax({
			url : '/member/idDuplChk',
			type : 'get',
			data : {memberId : memberId},
			success : function(res){
				if(res > 0){
					alert('이미 사용중인 아이디입니다.');
				}else {
					alert('사용 가능한 아이디입니다.');
				}
			},
			error : function(){
				console.log('ajax 통신 오류');
			}
		})
	});
	</script>
</body>
</html>
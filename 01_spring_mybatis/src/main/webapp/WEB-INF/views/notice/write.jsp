<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>게시글 작성</h1>
	
	<hr>
	
	<form action='/notice/write' method='post' enctype="multipart/form-data">
		<input type='hidden' name='noticeWriter' value='${loginMember.memberNo}'>		
		제목 : <input type="text" name="noticeTitle"> <br>
		첨부파일 : <input type="file" name="files" multiple> <br>
		내용 : <textarea rows="5" cols="5" name="noticeContent"></textarea>
		<input type='submit' value='작성하기'>		
	</form>
	

</body>
</html>
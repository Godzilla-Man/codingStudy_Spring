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
	<h1>게시글 수정하기</h1>
	
	<hr>
	
	<form action="/notice/update" method="post" enctype="multipart/form-data">
		<input type="hidden" name="noticeNo" value="${notice.noticeNo}">
		<table border="1">
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="noticeTitle" value="${notice.noticeTitle}">
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<%-- 게시글 작성 시, 파일 업로드한 경우 텍스트로 보여주고, 재업로드 버튼 생성 --%>
					<c:if test="${not empty notice.fileList}">
						<c:forEach var="file" items="${notice.fileList}">
							<span>${file.fileName}</span> &nbsp;
						</c:forEach>
						<a href="javascript:void(0)" onclick="fileReUpload(this)">재업로드</a>
					</c:if>
					<%-- 게시글 작성 시, 파일 업로드 하지 않은 경우, 업로드 할 수 있는 input 태그 생성 --%>
					<c:if test="${empty notice.fileList}">
						<input type="file" name="files" multiple>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="noticeContent">${notice.noticeContent}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="수정하기">
				</td>
			</tr>
		</table>
	</form>
	<script>
	function fileReUpload(obj){
		//obj => 재업로드 a 태그
		$(obj).parent().find('span').remove(); //기존 파일 목록 제거
		
		//재업로드 할 수 있도록, input type=file인 태그 추가
		let inputEl = $('<input>');
		inputEl.attr('type', 'file');
		inputEl.attr('name', 'files');
		inputEl.attr('multiple', true);
		
		//td 태그 하위에 input 태그 추가
		$(obj).parent().append(inputEl);
		
		//재업로드 a태그 삭제
		$(obj).parent().find('a').remove();
	}
	</script>

</body>
</html>
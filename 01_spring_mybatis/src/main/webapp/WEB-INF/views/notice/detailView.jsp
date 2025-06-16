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
	<h1>게시글 상세보기</h1>
	
	<hr>
	
	<table border='1'>
		<tr>
			<th>번호</th>
			<td>${notice.noticeNo}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${notice.noticeTitle}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${notice.noticeWriter}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<c:if test="${not empty notice.fileList}">
					<c:forEach var="file" items="${notice.fileList}">
						<a href="javascript:void(0)" onclick="fileDown('${file.fileName}', '${file.filePath}')">${file.fileName}</a>
					</c:forEach>
				</c:if>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${notice.noticeContent}</td>			
		</tr>
		<tr>
			<th>조회수</th>
			<td>${notice.readCount}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${notice.noticeDate}</td>
		</tr>		
	</table>
	
	<!-- 게시물 삭제하기 -->
	<c:if test="${notice.noticeWriter eq loginMember.memberId}">
		<div>
			<a href="/notice/delete?noticeNo=${notice.noticeNo}">삭제하기</a>
			<a href="/notice/updateFrm?noticeNo=${notice.noticeNo}">수정하기</a>
		</div>
	</c:if>
	
	<script>
	function fileDown(fileName, filePath){
		fileName = encodeURIComponent(fileName);
		filePath = encodeURIComponent(filePath);
		location.href="/notice/fileDown?fileName=" + fileName + "&filePath=" + filePath;
	}
	
	</script>

</body>
</html>
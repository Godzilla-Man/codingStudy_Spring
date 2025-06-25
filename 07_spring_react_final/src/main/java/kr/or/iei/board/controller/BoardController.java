package kr.or.iei.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardFile;
import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.common.annotation.NoTokenCheck;
import kr.or.iei.common.model.dto.ResponseDTO;
import kr.or.iei.common.util.FileUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Value("${file.uploadPath}")
	private String uploadPath;
	
	@GetMapping("/list/{reqPage}")
	@NoTokenCheck //게시글 목록 조회는 로그인이 필요하지 않으므로, 어노테이션 작성하여 AOP에서 토큰 검증하지 않도록 처리
	public ResponseEntity<ResponseDTO> selectBoardList(@PathVariable int reqPage){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 조회 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			//게시글 목록과 페이지 네비게이션 정보 조회
			HashMap<String, Object> boardMap = service.selectBoardList(reqPage);
			res = new ResponseDTO(HttpStatus.OK, "", boardMap, "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	/*
	 * @ModelAttribute : multipart/form-data 형식일 때, 자바 객체로 바인딩 하기 위한 어노테이션
	 */	
	@PostMapping("/editorImage")
	public ResponseEntity<ResponseDTO> uploadEditorImage(@ModelAttribute MultipartFile image){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "에디터 이미지 업로드 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			String filePath = fileUtil.uploadFile(image, "/editor/");
			//res.resData => "/editor/20250624/20250624151520485_00485.jpg" 
			res = new ResponseDTO(HttpStatus.OK, "", "/editor/" + filePath.substring(0, 8) + "/" + filePath, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	@PostMapping
	public ResponseEntity<ResponseDTO> insertBoard(@ModelAttribute MultipartFile [] boardFile, //첨부파일 객체 배열
												   @ModelAttribute MultipartFile boardThumb,   //썸네일 파일 객
												   @ModelAttribute Board board)	{			   //게시글 정보(제목, 내용, 작성자)
	
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 등록 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			//썸네일 파일 업로드
			if(boardThumb != null) { //썸네일 파일 업로드 안한 경우 form에 append 해주지 않음. 이 때 boardThumb에는 null이 들어있음.
				String filePath = fileUtil.uploadFile(boardThumb, "/board/thumb/"); //업로드한 파일명
				board.setBoardThumbPath(filePath);
			}
			
			ArrayList<BoardFile> fileList = new ArrayList<>();
			
			//첨부파일 업로드
			if(boardFile != null) {
				for(int i=0; i<boardFile.length; i++) {
					MultipartFile mFile = boardFile[i]; //첨부파일 1개
					
					String filePath = fileUtil.uploadFile(mFile, "/board/"); //파일 업로드
					
					BoardFile file = new BoardFile();
					file.setFileName(mFile.getOriginalFilename()); //사용자가 업로드한 실제 파일명
					file.setFilePath(filePath);					   //서버 저장 파일명
					
					fileList.add(file);
				}
			}
			
			//DB에 게시글 정보 등록
			int result = service.insertBoard(board, fileList);
						
			res = new ResponseDTO(HttpStatus.OK, "게시글이 등록되었습니다.", true, "success");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	@GetMapping("/{boardNo}")
	@NoTokenCheck
	public ResponseEntity<ResponseDTO> selectOneBoard(@PathVariable int boardNo){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 정보 조회 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			Board board = service.selectOneBoard(boardNo);
			res = new ResponseDTO(HttpStatus.OK,"",board, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	@GetMapping("/file/{boardFileNo}")
	public ResponseEntity<Resource> fileDown(@PathVariable int boardFileNo) throws FileNotFoundException{
		//DB에서 파일 1개 정보를 조회
		BoardFile boardFile = service.selectBoardFile(boardFileNo);
		
		//응답할 리소스로 만들어줄 파일 객체 생성
		String savePath = uploadPath + "/board/";
		File file = new File(savePath + boardFile.getFilePath().substring(0, 8) + File.separator + boardFile.getFilePath());
		
		//응답할 리소스 생성
		Resource resource = new InputStreamResource(new FileInputStream(file));
		
		//응답 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;"); //파일 다운로드임을 명시
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); //응답 데이터가 바이너리 타입임으로 명시
		
		return ResponseEntity.status(HttpStatus.OK)
				             .headers(headers)
				             .contentLength(file.length())
				             .body(resource);
	}
	
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ResponseDTO> deleteBoard(@PathVariable int boardNo){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 삭제 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			//DB 게시글, 파일 정보 삭제
			Board delBoard = service.deleteBoard(boardNo);
			
			if(delBoard != null) {
				//썸네일 파일 삭제				
				if(delBoard.getBoardThumbPath() != null) {
					String savePath = uploadPath + "/board/thumb/" + delBoard.getBoardThumbPath().substring(0, 8) + File.separator + delBoard.getBoardThumbPath();
					File file = new File(savePath);
					if(file.exists()) {
						file.delete();
					}
				}
				
				//첨부파일 삭제
				List<BoardFile> delFileList = delBoard.getFileList();
				if(delFileList != null) {
					String savePath = uploadPath + "/board/";
					for(BoardFile delFile : delFileList) {
						File file = new File(savePath + delFile.getFilePath().substring(0, 8) + File.separator + delFile.getFilePath());
						
						if(file.exists()) {
							file.delete();
						}
					}
				}
				
				//에디터 이미지 제거
				//<img> 태그에서 src 속성을 추출하기 위한 정규 표현식
				String regExp = "<img[^>]*src=[\\\"']([^\\\"']+)[\\\"'][^>]*>";
				Pattern pattern = Pattern.compile(regExp);
				Matcher matcher = pattern.matcher(delBoard.getBoardContent());
				
				while(matcher.find()) {
					String imageUrl = matcher.group(1); // http://locahost:9999/editor/20250624/20250624162600775_04771.jpg
					
					String filePath = imageUrl.substring(imageUrl.lastIndexOf("/") + 1); // 20250624162600775_04771.jpg
					String savePath = uploadPath + "/editor/" + filePath.substring(0, 8) + File.separator;
					
					File file = new File(savePath + filePath);
					if(file.exists()) {
						file.delete();
					}
				}
				
				res = new ResponseDTO(HttpStatus.OK, "게시글이 삭제 되었습니다.", true, "success");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	@PatchMapping
	public ResponseEntity<ResponseDTO> updateBoard(@ModelAttribute MultipartFile [] boardFile, //추가 첨부 파일 리스트
												   @ModelAttribute MultipartFile boardThumb,   //새 썸네일 파일 객체
												   @ModelAttribute Board board,				   //게시글 번호, 제목, 내용, 삭제 파일 번호 배열
												   String prevThumbPath){					   //기존 썸네일 이미지 파일명
		
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 수정 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			//추가 썸네일 업로드 시
			if(boardThumb != null) {
				String filePath = fileUtil.uploadFile(boardThumb, "/board/thumb/"); //썸네일 파일 업로드
				board.setBoardThumbPath(filePath); //DB에 저장 파일명 업데이트를 위함.
				
				//게시글 등록 시, 썸네일 이미지를 업로드 하지 않았을 수 있으므로 null이 아닐 때 처리
				if(prevThumbPath != null) {
					String savePath = uploadPath + "/board/thumb/";
					File file = new File(savePath + prevThumbPath.substring(0, 8) + File.separator + prevThumbPath);
					
					if(file.exists()) {
						file.delete();
					}
				}
			}
			
			//추가 첨부파일 업로드 처리
			if(boardFile != null) {
				ArrayList<BoardFile> addFileList = new ArrayList<>();
				
				for(int i=0; i<boardFile.length; i++) {
					String filePath = fileUtil.uploadFile(boardFile[i], "/board/"); //첨부파일 업로드
					
					BoardFile addFile = new BoardFile();
					addFile.setFileName(boardFile[i].getOriginalFilename()); //사용자 업로드 파일명
					addFile.setFilePath(filePath);						     //서버 저장 파일명
					addFile.setBoardNo(board.getBoardNo());					 //게시글 번호
					
					addFileList.add(addFile);
					
				}
				
				board.setFileList(addFileList);
			}
			
			//DB작업 후, 서버에서 첨부파일 삭제를 위해 파일 리스트 조회
			//board : 게시글 번호, 게시글 제목, 게시글 내용, 썸네일(추가 업로드한 경우만), 추가 첨부파일(추가 업로드한 경우만), 삭제 파일번호 배열(삭제 대상 파일이 존재할때만)
			ArrayList<BoardFile> delFileList = service.updateBoard(board);
			
			//서비스에서 반환해준 삭제 파일 리스트 null일 수 있으므로 체크 후, 서버 삭제
			if(delFileList != null) {
				String savtPath = uploadPath + "/board/";
				
				for(int i=0; i<delFileList.size(); i++) {
					BoardFile delFile = delFileList.get(i);
					
					File file = new File(savtPath + delFile.getFilePath().substring(0, 8) + File.separator + delFile.getFilePath());
					if(file.exists()) {
						file.delete();
					}
				}
			}
			
			
			res = new ResponseDTO(HttpStatus.OK, "게시글이 정상적으로 수정 되었습니다.", true, "success");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}

}

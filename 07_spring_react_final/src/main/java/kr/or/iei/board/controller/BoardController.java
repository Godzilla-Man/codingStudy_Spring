package kr.or.iei.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	

}

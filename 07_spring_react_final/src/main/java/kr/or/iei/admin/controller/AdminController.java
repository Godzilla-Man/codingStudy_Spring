package kr.or.iei.admin.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.iei.admin.model.service.AdminService;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.common.model.dto.ResponseDTO;
import kr.or.iei.member.model.dto.Member;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@GetMapping("/board/{reqPage}")
	public ResponseEntity<ResponseDTO> selectBoardList(@PathVariable int reqPage){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,	"게시글 목록 조회 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			HashMap<String, Object> boardMap = service.selectBoardList(reqPage);
			res = new ResponseDTO(HttpStatus.OK, "", boardMap, "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		
				
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//게시글 수정
	@PatchMapping("/board")
	public ResponseEntity<ResponseDTO> updateBoardStatus(@RequestBody Board board){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "게시글 상태 변경 중 오류가 발생하였습니다.", false, "error");
		
		try {
			int result = service.updateBoardStatus(board);
			
			if(result > 0) {
				res = new ResponseDTO(HttpStatus.OK, "", true, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	@GetMapping("/member/{reqPage}")
	public ResponseEntity<ResponseDTO> selectMemberList(@PathVariable int reqPage) {
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "회원 목록 조회 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			
			HashMap<String, Object> memberMap = service.selectMemberList(reqPage);
			res = new ResponseDTO(HttpStatus.OK, "", memberMap, "");
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	@PatchMapping("/member")
	public ResponseEntity<ResponseDTO> updateMemberStatus(@RequestBody Member member){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "회원 상태 변경 중 오류가 발생하였습니다.", false, "error");
		
		try {
			int result = service.updateMemberStatus(member);
			
			if(result > 0) {
				res = new ResponseDTO(HttpStatus.OK, "", true, "");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}

}

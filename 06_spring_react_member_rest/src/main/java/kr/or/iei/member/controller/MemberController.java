package kr.or.iei.member.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.iei.common.ResponseDTO;
import kr.or.iei.member.model.dto.Member;
import kr.or.iei.member.model.service.MemberService;

/*
 * API(Application Programming Interface)
 * - 클라이언트 & 서버간의 요청 및 응답에 대한 인터페이스
 * - 이기종 소프트웨어(ex: 스프링과 리액트) 시스템과 통신하기 위해, 따라야하는 규칙 또는 규격을 의미.
 * 
 * REST(Representational State Transfer)
 * - 자원(리소스)을, 이름으로 구분하고 해당 자원의 상태를 주고받는 모든 것을 의미
 * 	> 자원(리소스)를 이름으로 구분 : 파일, 이미지, DB 데이터 등 소프트웨어가 관리하는 모든 것을 HTTP URI를 통해 명시적으로 작성.
 *  > ex) http://localhost:9999/notice (게시글이 자원일 때, 게시글에 대한 등록, 수정, 삭제, 조회 등의 모든 요청에 대한 URI를 /notice로 작성)
 *  > 자원의 상태를 주고받는 : 클라이언트 요청(CRUD)에 따라, 적절한 응답을 보낸다.
 *  
 * REST는 HTTP URI를 통해 자원(데이터)을 구분하고, HTTP Method를 통해 CRUD를 구분 및 처리하도록 설계된 아키텍쳐(구조)를 의미한다.  
 * 	- C(생성) : POST
 *	- R(조회) : GET
 *	- U(변경) : PUT(DB 테이블 기준으로, 1개 ROW의 모든 컬럼 정보를 수정할 때 사용. 단, 업데이트 할 ROW가 없는 경우 INSERT 됨.)
 *	- U(변경) : PATCH(DB 테이블 기준으로, 1개 ROW의 일부 컬럼 정보를 수정 할 때 사용.)
 *	- D(삭제) : DELETE 
 *
 * REST URI 설계 원칙
 * - URI 마지막 문자로 슬래시(/)를 작성하지 않는다. => http://locahost:9999/notice/ (X) => http://locahost:9999/notice (O)
 * - URI에 동사는 작성하지 않고, 명사로 작성한다. => http://localhost:9999/notice/getList (X) => http://localhost:9999/notice (O)
 * - URI에 대문자는 사용하지 않는다.
 * - URI중, 경로가 변하는 부분은 유일한 값으로 대체한다. (회원번호, 게시글 번호와 같은 PK 성질 값)
 * 
 * 게시글 전체 조회 : GET http://localhost:9999/notice
 * 게시글 상세 조회 : GET http://localhost:9999/notice/{noticeNo}
 * 게시글 등록 : POST http://localhost:9999/notice
 * 게시글 수정 : PATCH http://localhost:9999/notice
 * 게시글 삭제 : DELETE http://localhost:9999/notice
 * 
 * REST 특징
 * - Stateless(무상태) : 작업(모든 요청)을 처리하기 위한, 상태 정보(세션)을 서버가 따로 저장하고 관리하지 않는다.(ex. 서버에서 세션 정보를 저장하지 않고, 모든 요청에 필요한 정보를 클라이언트가 전달해 주어야 함.)
 * (기존) 로그인 : 클라이언트가 ID,PW를 입력하고, 서버에 로그인 요청 => 서버에서 DB에 있는 회원 정보 조회 후, 회원 정보를 세션에 저장. => 이후 클라이언트 요청 => 서버에서 세션에 로그인 회원 정보를 검증.
 * (현재) 로그인 : 클라이언트가 ID,PW를 입력하고, 서버에 로그인 요청 => 서버에서 DB에 있는 회원 정보 조회 후, JWT(JSON 웹 토큰) 토큰 발급하여, 클라이언트에 전달 => 이후 클라이언트 요청(요청 시마다 토큰 포함) => 서버에서 토큰 검증
 * - Client-Server 구조 : 서버는 클라이언트 요청에 대한 데이터 제공만 하고, 클라이언트는 인증이나 상태에 대한 정보들을 직접 관리하므로 클라이언트와 서버의 역할이 명확해진다.
 * - Self-Decriptice-Message(자체 설명적 메시지) : 요청과 응답 데이터만으로, 클라이언트 및 서버가 각각 데이터의 의미를 알 수 있어야 함. 	 
 * 
 * Restful
 * - REST 설계 원칙을 모두 지켜 개발된 애플리케이션을 Restful하다~ 라고 표현함.
 * - Restful 하지 못한 경우란, REST 설계 원칙을 올바르게 준수하지 않는 시스템을 의미.
 * 	 ex1) 모든 요청을 POST로만 처리하는 API
 * 	 ex2) URI의 행위(동사)에 대한 부분이 들어가는 경우 => http://localhost:9999/notice/updateNotice
 * - Restful한 API를 구현하는 목적은 성능 향상에 있는 것이 아니라, 일관적인 규칙 또는 규약을 통해 API의 이해도, 클라이언트&서버간의 원활한 통신을
 *   높이는게 주 목적이니, 만약에 성능 향상에 중요한 상황에서는 굳이 모든 REST 설꼐 원칙 또는 특징을 살려 Rest API를 구현할 필요는 없다.   
 * 
 * Swagger Framework
 * 
 * - REST API 웹 서비스에 대한 API 문서화, 테스트를 도와주는 프레임워크
 * 
 * 
 */

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
@Tag(name="MEMBER", description = "회원관리 API")
public class MemberController {

	@Autowired
	private MemberService service;
	
	
	//전체 회원 조회 == GET
	@GetMapping	
	@Operation(summary = "전체 회원 조회", description = "DB에 등록된 전체 회원 목록을 조회")
	public ResponseEntity<ResponseDTO> selectAllMember(){
		
		//전체 회원 조회 API 처리 중, 비정상 처리 됐을 때 응답할 객체를 미리 생성해놓고, 정상 처리 됐을 때 재할당
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null);
		
		try { //try : 예외가 발생할만한 코드
			ArrayList<Member> memberList = service.selectAllMember();
			res = new ResponseDTO(HttpStatus.OK, "조회 성공", memberList);
			
		}catch (Exception e) { //catch : 예외가 발생했을 때 처리할 코드
			e.printStackTrace(); //console 탭에 오류 내용 출력
		}		
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//회원 1명 조회
	@GetMapping("/{memberId}")
	@Operation(summary = "회원 1명 조회", description = "아이디를 입력받아, 일치하는 회원 조회")
	public ResponseEntity<ResponseDTO> selectOneMember(@PathVariable String memberId) {
		
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null);
		
		try {
			Member member = service.selectOneMember(memberId);
			
			if(member != null) {
				res = new ResponseDTO(HttpStatus.OK, "조회 성공", member);				
			}else {
				//member가 null일때 HTTP 통신은 정상적이므로, OK로 응답해주고 메세지를 다르게 작성.
				res = new ResponseDTO(HttpStatus.OK, "일치하는 회원이 존재하지 않습니다.", null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//회원 정보 등록
	@PostMapping 
	@Operation(summary = "회원 정보 등록", description = "아이디, 이름, 전화번호, 소개글을 입력받아 회원 정보 등록")
	public ResponseEntity<ResponseDTO> insertMember(@RequestBody Member member) {
		
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "등록 실패", false);
		
		try {
			int result = service.insertMember(member);
			
			if(result > 0) {
				res = new ResponseDTO(HttpStatus.OK, "등록 성공", true);				
			}else {
				res = new ResponseDTO(HttpStatus.OK, "등록 실패", false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//회원 정보 삭제
	//http://localhost:9999/member/user01
	@DeleteMapping("/{memberId}")
	@Operation(summary = "회원 정보 삭제", description = "아이디를 입력받아 회원 정보 삭제")
	public ResponseEntity<ResponseDTO> deleteMember(@PathVariable String memberId) {
		
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패", false);
		
		try {
			int result = service.deleteMember(memberId);
			
			if(result > 0) {
				res = new ResponseDTO(HttpStatus.OK, "삭제 성공", true);
			}else {
				res = new ResponseDTO(HttpStatus.OK, "삭제 실패", false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}				
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//회원 정보 수정(이름, 소개글)
	@PatchMapping
	@Operation(summary = "회원 정보 수정", description = "아이디, 이름, 소개글 입력받아 회원 정보 수정")
	public ResponseEntity<ResponseDTO> updateMember(@RequestBody Member member) {
		
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "수정 실패", false);
		
		try {
			int result = service.updateMember(member);
			
			if(result > 0) {
				res = new ResponseDTO(HttpStatus.OK, "수정 성공", true);
			}else {
				res = new ResponseDTO(HttpStatus.OK, "수정 실패" ,false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
}

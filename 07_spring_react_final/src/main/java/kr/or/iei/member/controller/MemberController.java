package kr.or.iei.member.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import kr.or.iei.common.annotation.NoTokenCheck;
import kr.or.iei.common.dto.ResponseDTO;
import kr.or.iei.member.model.dto.LoginMember;
import kr.or.iei.member.model.dto.Member;
import kr.or.iei.member.model.service.MemberService;

@RestController
@CrossOrigin("*")
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	
	//아이디 중복 체크
	@GetMapping("/{memberId}/chkId")
	@NoTokenCheck
	public ResponseEntity<ResponseDTO> chkMemberId(@PathVariable String memberId){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "아이디 중복 체크 중, 오류가 발생하였습니다.",false, "error");		
		try {
			int count = service.chkMemberId(memberId);
			res  = new ResponseDTO(HttpStatus.OK, "", count, "success");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return new ResponseEntity<ResponseDTO>(res,res.getHttpStatus());		
	}
	
	
	//회원가입
	@PostMapping
	@NoTokenCheck
	public ResponseEntity<ResponseDTO> insertMember(@RequestBody Member member){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			int result = service.insertMember(member);			
			if(result > 0) {
				res = new ResponseDTO(HttpStatus.OK, "회원가입이 완료되었습니다. 로그인 화면으로 이동합니다.", true, "success");
			}else {
				res = new ResponseDTO(HttpStatus.OK, "회원가입 중 오류가 발생하였습니다.", false, "warning");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//로그인
	@PostMapping("/login")
	@NoTokenCheck
	public ResponseEntity<ResponseDTO> memberLogin(@RequestBody Member member){
		System.out.println("memberLogin 테스트!!!!!!!");
		
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			LoginMember loginMember = service.memberLogin(member);
			if(loginMember == null) {
				res = new ResponseDTO(HttpStatus.OK, "아이디 및 비밀번호를 확인하세요.", null, "warning");
			}else {
				res = new ResponseDTO(HttpStatus.OK, "", loginMember, "");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	/*
	//테스트 로그인
	@GetMapping("/test")
	public void test(HttpServletRequest request) {
		if(request != null) {
			String accessToken = request.getHeader("Authorization");
			System.out.println("accessToken : " + accessToken);
		}
	}
	*/
	
	
	//내 정보 - 회원 1명 조회
	
	/*
	@GetMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> selectOneMember(HttpServletRequest request, @PathVariable String memberId){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "회원 정보 조회 중, 오류가 발생하였습니다.", null, "error");
		
		try {
			//헤더에서 토큰 추출.
			String accessToken = request.getHeader("Authorization");
			
			Object resObj = service.selectOneMember(accessToken, memberId);
			
			if(resObj instanceof HttpStatus httpStatus) { //토큰 검증 도중, 예외가 발생한 경우				
				res = new ResponseDTO(httpStatus, "토큰 검증 오류", null, "error");
			}else { //토큰 검증 통과로, DB에서 회원 정보 조회한 경우
				res = new ResponseDTO(HttpStatus.OK, "", resObj, "");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	*/
	//AOP 적용 이후에 코드가 어떻게 바뀌는지 테스트
	@GetMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> selectOneMember(@PathVariable String memberId){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "회원 정보 조회 중, 오류가 발생하였습니다.", null, "error");
		
		try {
						
			Object resObj = service.selectOneMember(memberId);
			
			if(resObj instanceof HttpStatus httpStatus) { //토큰 검증 도중, 예외가 발생한 경우				
				res = new ResponseDTO(httpStatus, "토큰 검증 오류", null, "error");
			}else { //토큰 검증 통과로, DB에서 회원 정보 조회한 경우
				res = new ResponseDTO(HttpStatus.OK, "", resObj, "");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	/*
	//회원 정보 수정
	@PatchMapping
	public ResponseEntity<ResponseDTO> updateMember(HttpServletRequest request, @RequestBody Member member){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "수정 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			//헤더에서 토큰 추출
			String accessToken = request.getHeader("Authorization");
			
			Object resObj = service.updateMember(accessToken, member);
			
			if(resObj instanceof HttpStatus httpStatus) {
				res = new ResponseDTO(httpStatus, "토큰 검증 오류", null, "error");
			}else {
				//토큰 검증 통과 == resObj == 회원 정보 수정 결과(int)
				if((Integer) resObj > 0) {
					//정상 수정 된 경우
					res = new ResponseDTO(HttpStatus.OK, "회원 정보 수정이 완료되었습니다.", true, "success");
				}else {
					//수정되지 않은 경우
					res = new ResponseDTO(HttpStatus.OK, "수정 중, 오류가 발생하였습니다.", false, "warning");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	*/
	//AOP 적용 이후에 코드가 어떻게 바뀌는지 테스트
	//회원 정보 수정
		@PatchMapping
		public ResponseEntity<ResponseDTO> updateMember(@RequestBody Member member){
			ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "수정 중, 오류가 발생하였습니다.", false, "error");
			
			try {				
				int result = service.updateMember(accessToken, member);
				
				if(result > 0) {
					res = new ResponseDTO(httpStatus, "토큰 검증 오류", null, "error");
				}else {
					//토큰 검증 통과 == resObj == 회원 정보 수정 결과(int)
					if((Integer) resObj > 0) {
						//정상 수정 된 경우
						res = new ResponseDTO(HttpStatus.OK, "회원 정보 수정이 완료되었습니다.", true, "success");
					}else {
						//수정되지 않은 경우
						res = new ResponseDTO(HttpStatus.OK, "수정 중, 오류가 발생하였습니다.", false, "warning");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
		}
	
	
	//회원 탈퇴
	@DeleteMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> deleteMember(HttpServletRequest request, @PathVariable String memberId){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 중, 오류가 발생하였습니다.", false, "error");
		
		try {			
			//헤더에서 토큰 추출
			String accessToken = request.getHeader("Authorization");
			
			Object resObj = service.deleteMember(accessToken, memberId);
			
			if(resObj instanceof HttpStatus httpStatus) {
				res = new ResponseDTO(httpStatus, "토큰 검증 오류", false, "error");
				if((Integer) resObj > 0) {
					res = new ResponseDTO(HttpStatus.OK, "회원 탈퇴가 정상 처리 되었습니다.", true, "success");
				}else {
					res = new ResponseDTO(HttpStatus.OK, "삭제 중, 오류가 발생하였습니다.", false, "warning");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//비밀번호 변경 - 기존 비밀번호 체크
	@PostMapping("/checkPw")
	public ResponseEntity<ResponseDTO> checkPw(HttpServletRequest request, @RequestBody Member member){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "기존 비밀번호 체크 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			
			//헤더에서 토큰 추출
			String accessToken = request.getHeader("Authorization");
			
			Object resObj = service.chkMemberPw(accessToken, member);
			
			if(resObj instanceof HttpStatus httpStatus) {
				res = new ResponseDTO(httpStatus, "토큰 검증 오류", false, "error");
			}else {
				//토큰 검증 성공 ==> 비밀번호 일치 결과 (true or false)
				res = new ResponseDTO(HttpStatus.OK, "", resObj, "");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
		
	}
	
	
	//비밀번호 변경 - 변경 처리
	@PatchMapping("/memberPw")
	public ResponseEntity<ResponseDTO> updateMemberPw(HttpServletRequest request, @RequestBody Member member){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호 변경 중, 오류가 발생하였습니다.", false, "error");
		
		try {
			//헤더에서 토큰 추출
			String accessToken = request.getHeader("Authorization");
			
			Object resObj = service.updateMemberPw(accessToken, member);
			
			if(resObj instanceof HttpStatus httpStatus) {
				res = new ResponseDTO(httpStatus, "토큰 검증 오류", false, "error");
			}else {
				//토큰 검증 성공 => 업데이트 결과에 따라서 처리
				if((Integer)resObj > 0) {
					res = new ResponseDTO(HttpStatus.OK, "비밀번호가 정상적으로 변경 되었습니다.", true, "success");
				}else {
					res = new ResponseDTO(HttpStatus.OK, "비밀번호 변경 중, 오류가 발생하였습니다.", false, "warning");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}
	
	
	//refreshToken으로 accessToken 재발급 처리
	@PostMapping("/refresh")
	public ResponseEntity<ResponseDTO> refreshToken(HttpServletRequest request, @RequestBody Member member){
		ResponseDTO res = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 재발급 실패", null, "error");
		
		try {
			//헤더에서 토큰 추출 - accessToken 재발급 받기 위해, 헤더에 refreshToken을 포함시켜 전달함.
			String refreshToken = request.getHeader("refreshToken");
			
			Object resObj = service.refreshToken(refreshToken, member);
			
			if(resObj instanceof HttpStatus httpStatus) {
				res = new ResponseDTO(httpStatus, "refresh 토큰 검증 오류", null, "error");
			}else {
				//accessToken 재발급 완료
				res = new ResponseDTO(HttpStatus.OK, "토큰 재발급 성공", resObj, "success");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseDTO>(res, res.getHttpStatus());
	}	
	
}

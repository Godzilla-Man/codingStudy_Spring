package com.example.demo.member.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.model.dto.Member;
import com.example.demo.member.model.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller //자동으로 객체 생성하여 컨테이너에 등록
@RequestMapping("/member") //회원 관리 기능 요청 URL은 모두 /member로 시작함. 공통 URL 작성
public class MemberController {
	
	public MemberController() {
		System.out.println("MemberController가 생성되었습니다.");
	}		
	
	/*
	 * 아래 Service는 new 연산자를 사용하지 않았음. ==> 참조자료형의 빈 값인 null을 가지고 있다.
	 * 이때, Autowired 어노테이션을 작성하면, 컨테이너에 등록되어 있는 객체(Bean)중에
	 * 타입이 일치하는 객체를 주입해준다. DI(Dependency Injection) 의존성 주입!!
	 *  
	 */	
	@Autowired
	private MemberService service;	
	
	//회원가입 페이지로 이동
	@GetMapping("/joinFrm") //공통 URL 부분 제외하고 작성
	public String joinFrm() {
		//1. 인코딩 - 필터
		//2. 값 추출
		//3. 로직
		//4. 결과 처리 - 회원가입 페이지로 이동
		
		//application.properties의 prefix와 suffix 부분 제외한 경로
		//실제 jsp 경로 => webapp/WEB-INF/views/member/join.jsp
		return "member/join";
	}
	
	@PostMapping("/join")
	public String join(Member member) {
		//1. 인코딩 - 필터
		
		//2. 값 추출 - input name 속성값과 일치하는 변수를 가진 DTO 클래스 매개변수에 작성
		//System.out.println(member.toString()); //회원가입 입력 정보가 모두 들어있음.
		
		//3. 로직 - 회원가입
		//서비스 객체 생성필요 없음!! 컨테이너에 등록된 객체를 전역변수 service에 주입 받았음.
		int result = service.join(member);
		
		//4. 결과 처리
		//성공 시, index.jsp로 이동하고 실패했을 때는 join.jsp로 이동.
		if(result > 0 ) {
			//return "index";	//이렇게 작성해도 정상 이동
			return "redirect:/";
		}else {
			//return "member/join"; //이렇게 작성해도 정상 이동
			return "redirect:/member/joinFrm";
		}
		
	}
	
	@GetMapping("/idDuplChk")
	@ResponseBody
	public int idDuplChk(String memberId) {		
		//1. 인코딩 - 필터
		//2. 값 추출 - 클라이언트 전달 객체 속성명과 동일한 명칭의 매개변수
		//3. 로직 - 중복 체크
		int cnt = service.idDuplChk(memberId);
		
		//4. 결과 처리
		return cnt;
	}
	
	@PostMapping("/login")
	public String login(HttpSession session, String memberId, String memberPw) {
		//1. 인코딩 - 필터
		//2. 값 추출 - 클라이언트 전달 객체 속성명과 동일한 명칭의 매개변수
		//3. 로직 - 중복 체크
		Member loginMember = service.login(memberId, memberPw);
		
		//4. 결과 처리
		if(loginMember == null) {
			//loginFail.jsp로 이동
			return "member/loginFail";
		}else {
			//매개 변수에 session 작성하면, 자동 주입 됨.
			session.setAttribute("loginMember", loginMember);
			
			//index.jsp로 이동
			return "index";
		}			
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//1. 인코딩 - 필터
		//2. 값 추출 - X
		//3. 로직 - 로그아웃 - 세션파기
		session.invalidate();
		
		//4. 결과 처리		
		return "index";
	}
	
	@GetMapping("/mypage")
	public String myPage() {
		
		return "member/myPage";
	}
	
	@GetMapping("/delete")
	public String delete(HttpSession session) {
		//1. 인코딩 - 필터
		
		//2. 값 추출 - X
		
		//3. 로직 - 회원탈퇴
		//delete from tbl_member where member_no = ?
		Member loginMember = (Member) session.getAttribute("loginMember");
		int result = service.delete(loginMember.getMemberNo());
		
		// 아래 내용은 로그아웃 세션과 똑같은 코드이기에, 추가 작성하지 않고 리다이렉트 시켜 /logout 페이지로 넘겨줌.
		if(result == 0) {
			return "index";
		}else {
			return "redirect:/member/logout";
		}		
	}
	
	//모델 사용하여 전체 회원 조회
	@GetMapping("/allMemberList")
	public String allMemberList(Model model) {
		//1. 인코딩 - 필터
		
		//2. 값 추출 - X
		
		//3. 로직 - 전체회원조회
		ArrayList<Member> memberList = service.allMemberList();
		model.addAttribute("memberList", memberList);
		
		//4. 결과 처리
		return "member/allMemberList";
	}
	
}

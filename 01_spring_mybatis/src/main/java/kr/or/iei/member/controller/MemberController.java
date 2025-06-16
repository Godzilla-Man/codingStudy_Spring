package kr.or.iei.member.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.or.iei.member.model.dto.Member;
import kr.or.iei.member.model.service.MemberService;

@Controller
@RequestMapping("/member") //회원 관리 기능 공통 URL
public class MemberController {	
	
	@Autowired
	private MemberService service;
	
	
	@GetMapping("/joinFrm")
	public String joinFrm() {		
		return "member/join";
	}
	
	@PostMapping("/join")
	public String join(Member member) {
		
		int result = service.join(member);
		
		if(result > 0) {
			return "index";
		}else {
			return "member/joinFail";
		}		
	}
	
	@GetMapping("/idDuplChk")
	@ResponseBody
	public int idDuplChk(String memberId) {
		int result = service.idDuplChk(memberId);
		return result;
	}
	
	
	
	@PostMapping("/login")
	public String login(Member member, HttpSession session) {
		Member loginMember = service.login(member);
		
		if(loginMember == null) {
			return "member/loginFail";
		}else {
			session.setAttribute("loginMember", loginMember);
			return "redirect:/";
		}
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "member/mypage";
	}
	
	@PostMapping("/update")
	public String update(Member member) {
		int result = service.update(member);
		
		if(result > 0) {			
			return "redirect:/member/logout";
		}else {
			return "redirect:/member/mypage";
		}		
		
	}
	
	@GetMapping("/delete")
	public String delete(String memberNo) {
		int result = service.delete(memberNo);
		
		if(result > 0) {			
			return "redirect:/member/logout";
		}else {
			return "redirect:/member/mypage";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/allMemberList")
	public String allMemberList(Model model) {
		ArrayList<Member> memberList = service.selectAllMemberList();
		
		//화면 구현에 필요한 데이터 Model 객체에 등록
		model.addAttribute("memberList", memberList);
		
		return "member/allMemberList";
	}
	
	@GetMapping("/adminPage")
	public String adminPage(Model model) {
		//전체 회원 조회 메소드 재사용
		ArrayList<Member> memberList = service.selectAllMemberList();
		
		model.addAttribute("memberList", memberList);
		
		return "member/adminPage";
	}
	
	@GetMapping("/updateLevel")
	@ResponseBody
	public int updateLevel(Member member) {
		int result = service.updateLevel(member);
		return result;		
	}
}

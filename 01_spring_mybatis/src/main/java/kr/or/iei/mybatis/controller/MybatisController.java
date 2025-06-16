package kr.or.iei.mybatis.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.mybatis.model.dto.MybatisMember;
import kr.or.iei.mybatis.model.service.MybatisService;

@Controller
@RequestMapping("/mybatis/dynamic")
public class MybatisController {


	
	@Autowired
	private MybatisService service;


	
	@GetMapping("/movePage")
	public String movePage(Model model) {
		
		//forEach 테스트를 위한 전체 회원 조회
		ArrayList<MybatisMember> allMemberList = service.selectAllMemberList();
		model.addAttribute("allMemberList", allMemberList);
		
		
		return "mybatis/dynamicTest";
	}
	
	@GetMapping("/ifTest")
	public String ifTest(MybatisMember m, Model model) {		
		
		ArrayList<MybatisMember> memberList = service.selectIfTest(m);
		
		model.addAttribute("memberList", memberList);
		
		//사용자가 체크한 체크박스 정보가 들어있는 객체. 화면에 테이블 컬럼을 제어하기 위해 등록
		model.addAttribute("chkInfo", m);
		
		return "mybatis/memberInfo_a";
	}
	
	@GetMapping("/forTest")
	public String forTeset(String [] members, Model model) {
		ArrayList<MybatisMember> memberList = service.selectForTest(members);
		model.addAttribute("memberList", memberList);
		return "mybatis/memberInfo_b";
	}
	
	@GetMapping("/cooseTest")
	public String chooseTest(String select, String keyword, Model model) {
		
		ArrayList<MybatisMember> memberList = service.selectChoosetest(select, keyword);
		model.addAttribute("memberList", memberList);
		
		return "mybatis/memberInfo_c";
	}
	
	@GetMapping("/test1")
	public String dynamicTest1(MybatisMember member, Model model) {
		ArrayList<MybatisMember> memberList = service.selectDynamicTest1(member);
		model.addAttribute("memberList", memberList);
				
		return "mybatis/memberInfo_d";
	}
	
	@GetMapping("/test2")
	public String dynamicTest2(String sFlag1, Model model) {
		ArrayList<MybatisMember> memberList = service.selectDynamicTest2(sFlag1);
		model.addAttribute("memberList", memberList);
		return "mybatis/memberInfo_e";
	}
	
	@GetMapping("/test3")
	public String dynamicTest3(MybatisMember member, Model model) {
		ArrayList<MybatisMember> memberList = service.selectDynamicTest3(member);
		model.addAttribute("memberList", memberList);
		return "mybatis/memberInfo_f";
	}
	
}

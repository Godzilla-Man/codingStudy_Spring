package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.model.dto.Member;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ajax") //현재 컨트롤러로 요청되는 URL 중, 공통 URL 부분

public class AjaxController {

    private final FirstController firstController;

    AjaxController(FirstController firstController) {
        this.firstController = firstController;
    }
	
	//아래 요청별 메소드 작성 시, 상단 공통 URL부분 제외하고 작성
	
	//기존 서블릿 방식의 응답 - 응답 데이터 : 기본 자료형 or String
	@GetMapping("/ajaxTest1")
	public void ajaxTest1(HttpServletResponse response) {
		String resData = "ajaxTest1 응답 문자열"; //응답 데이터
		
		response.setContentType("text/html; charset=utf-8");
		
		try {
			response.getWriter().print(resData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//기존 서블릿 방식의 응답 - 
	@GetMapping("/ajaxTest2")
	public void ajaxTest2(HttpServletResponse response) {
		//DB에서 조회한 회원 객체
		Member m = new Member("1", "qowo0420", "1234", "배재현", "qowo04@naverm.com", "010-1212-1212", "부천", "1", "2025-06-09");
		
		/*
		//방법1) JSONObject 이용
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("memberNo",  m.getMemberNo());
		jsonObj.put("memberId", m.getMemberId());
		jsonObj.put("memberPw", m.getMemberPw());
		jsonObj.put("memberName", m.getMemberName());
		jsonObj.put("memberEmail", m.getMemberEmail());		
		
		//response.setContentType("text/html; charset=utf-8"); // JSON 형태의 문자열을 응답(스크립트에서 응답된 문자열을 JSON.parser 메소드를 통해 Object 형태로 변환)
		response.setContentType("application/json; charset=utf-8"); // 스크립트에서 JSON.parse() 작업 하지 않아도 됨.
		*/
		
		//방법2) Gson 이용
		Gson gson = new Gson();
		String jsonStr = gson.toJson(m); // {memberNo : 1, memberId : "qowo0420"..}
		response.setContentType("application/json; charset=utf-8");		
		
		try {
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//스프링 방식의 응답 - 응답 데이터 : 기본 자료형 or String 
	/*
	 * ResponseBody : 일반적으로 String을 return 하면 View 페이지 경로로 인식함.
	 *			      이 때, return 되는 데이터가 View 페이지 경로가 아니라,
	 *				  데이터임을 알려주기 위한 어노테이션.   	       	
	 */
	@GetMapping("/ajaxTest3")
	@ResponseBody
	public String ajaxTest3() {
		String resData = "ajaxTest1 응답 문자열"; //응답 데이터
		
		return resData;		
	}
	
	//스프링 방식의 응답 - 응답 데이터 : 객체
	//객체 -> JSON 형태 문자열로 변환하는 작업 X (스프링이 자동 처리)
	
	@GetMapping("/ajaxTest4")
	@ResponseBody
	public Member ajaxTest4() {
		//DB에서 조회한 회원 객체
		Member m = new Member("1", "qowo0420", "1234", "배재현", "qowo04@naverm.com", "010-1212-1212", "부천", "1", "2025-06-09");
		
		return m;
	}
	
	//스프링 방식의 응답 - 응답 데이터 : 회원 리스트
	@GetMapping("/ajaxTest5")
	@ResponseBody
	public ArrayList<Member> ajaxTest5() {
		Member m1 = new Member("1", "qowo0420", "1234", "배재현1", "qowo041@naverm.com", "010-1212-1212", "부천", "1", "2025-06-09");
		Member m2 = new Member("2", "qowo0421", "1234", "배재현2", "qowo042@naverm.com", "010-1212-1212", "수도", "1", "2025-06-09");
		Member m3 = new Member("3", "qowo0422", "1234", "배재현3", "qowo043@naverm.com", "010-1212-1212", "강천", "1", "2025-06-09");
		
		ArrayList<Member> memberList = new ArrayList<Member>();
		memberList.add(m1);
		memberList.add(m2);
		memberList.add(m3);
		
		return memberList;	
	}
	
	//<%-- 2025-06-10 스프링 두번째 수업 --%>
	//스프링 방식 - 요청 파라미터 - 자바 객체와 바인딩 되는 객체
	/*
	 * @RequestBody : 클라이언트가 요청 본문에 전달한 데이터 => 자바 객체로 변환할 때 사용
	 * 
	 */
	@PostMapping("/ajaxTest6")
	@ResponseBody
	public void ajaxTest6(@RequestBody Member member) {
		System.out.println("No : " + member.getMemberNo());
		System.out.println("Id : " + member.getMemberId());
		System.out.println("Pw : " + member.getMemberPw());
		System.out.println("Name : " + member.getMemberName());
	}
	
	
	@PostMapping("/ajaxTest7")
	@ResponseBody
	public void ajaxTest7(@RequestBody ArrayList<Member> memberList) {
		for(int i=0; i<memberList.size(); i++) {
			Member member = memberList.get(i);
			System.out.println("No : " + member.getMemberNo());
			System.out.println("Id : " + member.getMemberId());
			System.out.println("Pw : " + member.getMemberPw());
			System.out.println("Name : " + member.getMemberName());
			
			System.out.println("==================================");
		}		
	}
	
}

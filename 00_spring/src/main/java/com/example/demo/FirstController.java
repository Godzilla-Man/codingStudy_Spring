package com.example.demo;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.member.model.dto.Member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 컴포넌트 : Spring 컨테이너에서 관리되는 특정 기능 수행을 담당하는 객체
 * 
 * @Controller는 컴포넌트 중, 한가지로 클래스에 대한 객체를 자동 생성하기 위해 사용된다.
 * 이러한 컴포넌트가 작성된 클래스들에 대한 객체를 자동으로 생성하여, IoC 컨테이너에 등록하고,
 * 등록된 객체들을 Bean이라고 칭한다.
 * 
 * 이렇게 생성된 Bean들의 생명주기(생성부터 소멸까지)를 IoC 컨테이너가 담당한다.
 * Dynamic Web Project에서 각 서블릿에 대한 생명주기를 담당했던, 서블릿 컨테이너처럼
 * 스프링 프레임워크에서는 IoC 컨테이너가 담당.
 * 
 * 이렇게 개발자가 Bean의 생명주기를 관리하지 않고, 컨테이너가 담당하는 것을 제어의 역행
 * (Inversion of Control)이라고 한다.
 * 
 * 
 * 
 */

@Controller
public class FirstController {
	
	public FirstController() {
		System.out.println("FirstController 객체가 생성되었습니다.");
	}
	
	@GetMapping("/getTest") //클라이언트 Get 요청을 받아 처리할 어노테이션. POST방식일 경우 PostMapping
	public void getTest() {
		System.out.println("FirstController getTest()가 호출되었습니다.");
	}
	
	@PostMapping("/postTest")
	public void postTest() {
		System.out.println("FirstController postTest()가 호출되었습니다.");
	}
	
	@PostMapping("/servletParamTest")
	public void servletParamTest(HttpServletRequest request) {
		String testId = request.getParameter("testId");
		String testPw = request.getParameter("testPw");
		
		System.out.println("testId : " + testId);
		System.out.println("testPw : " + testPw);
	}
	
	/*
	 * 스프링 방식 - 1
	 * 
	 * input 태그 name 속성 값과, 매개변수명 일치하게 작성
	 */
	
	@PostMapping("/springParamTest1")
	public void springParamTest1(String testId, String testPw) {
		System.out.println("spring testId : " + testId);
		System.out.println("spring testPw : " + testPw);
	}
	
	/*
	 * 스프링 방식 - 2
	 * 
	 * input 태그 name 속성 값과, DTO 클래스 변수 명칭 일치하게 작성 
	 */
	
	@PostMapping("/springParamTest2")
	public void springParamTest2(Member member) {
		System.out.println("memberId : " + member.getMemberId());
		System.out.println("memberPw : " + member.getMemberPw());		
	}
	
	@GetMapping("/servletResponseTest")
	public void servletResponseTest(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("res1", "servlet responseData1");
		request.setAttribute("res2", "servlet responseData2");
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/resTest/servletResTest.jsp");
		
		try {
			view.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/*
	 * 스프링 방식 - 1
	 * 
	 * String을 return하면 HttpMessageConvertor가 View 경로로 인식.
	 * 이 때, application.properties에 JSP 관련 세팅(prefix, suffix)정보를 읽어와
	 * return 하는 문자열 앞과 뒤에 각각 경로를 붙여준다.
	 * 
	 * - application.properties - 
	 * prefix : /WEB-INF/views/
	 * suffix : .jsp
	 * 
	 *  /WEB-INF/views/ + "resTest/springResTest" + .jsp
	 *  
	 *  Model : 데이터만을 등록하고, 응답할 떄 사용되는 클래스(데이터 사용 범위는 request와 동일)
	 * 
	 */
	
	@GetMapping("/springResponseTest1")
	public String springResponseTest1(Model model) {
		model.addAttribute("res1", "spring Model resData1");
		model.addAttribute("res2", "spring Model resData2");
		
		return "resTest/springResTest";
	}
	
	/*
	 * 스프링 방식 - 2
	 * 
	 * ModelAndView : 데이터와 응답할 페이지 정보를 같이 등록할 때 사용한다.
	 */
	
	@GetMapping("/springResponseTest2")
	public ModelAndView springResponseTest2() {
		ModelAndView mav = new ModelAndView("resTest/springResTest"); // 이동할 페이지 경로
		
		//응답 데이터 등록
		mav.addObject("res1", "spring ModelAndView resData1");
		mav.addObject("res2", "spring ModelAndView resData2");
		
		return mav;
	}
	
	
	//★연습문제★
	@PostMapping("/springReqResTest")
	public String springReqResTest(Member member, Model model) {
		
		//Model에 화면 구현에 필요한 데이터 등록
		model.addAttribute("member", member);
		
		//application.properties의 prefix와 suffix를 제외한 경로를 문자열 형태로 리턴 == View 경로		
		return "resTest/springReqResTest";
	}		
	
}

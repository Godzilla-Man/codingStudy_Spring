package kr.or.iei.notice.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.iei.notice.model.dto.Notice;
import kr.or.iei.notice.model.service.NoticeService;

/* RestController : 현재 컨트롤러에 작성되는 모든 메소드를 비동기 방식으로 처리.
 * React 프로젝트에서 axios를 통해, 비동기 요청을 보내고, Spring 프로젝트에서는
 * 페이지를 전환하는 것이 아닌, 데이터만 응답 시켜줌.
*/
@RestController //ResponseBody를 일일이 기재해줘야하는 것을 생략 해도됨.
@CrossOrigin("*") //모든 타 출처에서의 요청 허용. (개발 환경이므로, 모든 타 출처 요청 허용. 실제 운영 환경에서는 특정 도메인 또는 IP에 대한 요청만 허용하도록 처리 할 것.)
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService service;
	
	
	@GetMapping("/getList")
	public ArrayList<Notice> getList() {
		
		ArrayList<Notice> list = service.selectNoticeList();		
		return list;
	}
	
	
	/*
	 * RequestBody : 요청 본문(Body)에 존재하는 데이터를 자바 객체로 전달받기 위해 사용되는 어노테이션
	 */
	@PostMapping("/write")
	public int write(@RequestBody Notice notice) {
		int result = service.insertNotice(notice);
		return result;
	}
	
	
	/*
	 * PathVariable : URL 경로에서 변수 값을 추출하여, 매개 변수에 할당하기 위한 어노테이션
	 */
	@GetMapping("/detail/{noticeNo}")
	public Notice selectOneNotice(@PathVariable String noticeNo) {
		//System.out.println("noticeNo : " + noticeNo);
		Notice notice = service.selectOneNotice(noticeNo);
		return notice;
	}
	
	
	@GetMapping("/delete/{noticeNo}")
	public int deleteOneNotice(@PathVariable String noticeNo) {
		
		int result = service.deleteOneNotice(noticeNo);
		return result;		
	}
	
	
	@PostMapping("/update")
	public int updateNotice(@RequestBody Notice notice) {
		int result = service.updateNotice(notice);
		return result;
	}

}

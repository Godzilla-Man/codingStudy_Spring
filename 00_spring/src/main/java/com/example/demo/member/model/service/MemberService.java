package com.example.demo.member.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.member.model.dao.MemberDao;
import com.example.demo.member.model.dto.Member;

@Service //자동으로 객체 생성하여 컨테이너에 등록
public class MemberService {
	
	public MemberService() {
		System.out.println("MemberService가 생성되었습니다.");
	}
	
	//컨테이너에 등록된 객체 중, 타입이 일치하는 객체를 주입받음.
	@Autowired
	private MemberDao dao;

	
	/*
	 * 기존 서블릿 + JSP 프로젝트에서는 conn.setAutoCommit(false);를 지정하여
	 * 자동 커밋을 방지한 후, 상황에 따라서 개발자가 commit 또는 rollback을 진행하였음.
	 * 아래 Transactional 어노테이션을 작성하면, 해당 메소드에서 오류가 발생할 시 rollback 처리를 해줌.
	 * 
	 */
	@Transactional
	public int join(Member member) {				
		return dao.join(member);
	}

	//단순 select 이므로, Transactional 작성 안함!!
	public int idDuplChk(String memberId) {		
		return dao.idDuplChk(memberId);
	}

	public Member login(String memberId, String memberPw) {
		
		return dao.login(memberId, memberPw);
	}
	
	@Transactional
	public int delete(String memberNo) {
			
		return dao.delete(memberNo);
	}

	public ArrayList<Member> allMemberList() {
		
		return dao.allMemberList();
	}
	
	
	
}

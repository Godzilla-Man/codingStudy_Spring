package kr.or.iei.member.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.dto.Member;

@Service
public class MemberService {
	
	@Autowired
	private MemberDao dao;

	public ArrayList<Member> selectAllMember() {
		
		return dao.selectAllMember();
	}
	
	
	public Member selectOneMember(String memberId) {
		
		return dao.selectOneMember(memberId);
	}
	
	
	@Transactional
	public int insertMember(Member member) {
		
		return dao.insertMember(member);
	}

	
	@Transactional
	public int deleteMember(String memberId) {
		
		return dao.deleteMember(memberId);
	}

	
	@Transactional
	public int updateMember(Member member) {
		
		return dao.updateMember(member);
	}




	
	
}

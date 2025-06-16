package kr.or.iei.member.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.dto.Member;

@Service
public class MemberService {
	
	
	@Autowired
	private MemberDao dao;
	
	@Autowired //WebConfig에서 Bean으로 등록한 객체 주입 받기
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int join(Member member) {		
		
		//사용자가 입력한 평문 전달하여, 암호화된 비밀번호 등록
		String encPassword = encoder.encode(member.getMemberPw());
		member.setMemberPw(encPassword); //암호화된 비밀번호로 세팅		
		
		// 평문 : 1111 => 아래 결과를 보면 암호화 할때마다 결과값이 다름.
		// encPassword : $2a$10$fyOf2jWDDwy11vEGZySlfuLOThOt9wLicWOJ6VxrQT1VdEA/q.Xlu
		// encPassword : $2a$10$xrmiaL0QeWe6NqztWpdlgudImVted6HAiP6cGPZir.TuwoYt3.zBC
		//System.out.println("encPassword : " + encPassword);
		
		return dao.join(member);
	}

	public int idDuplChk(String memberId) {		
		return dao.idDuplChk(memberId);
	}

	public Member login(Member member) {	
		/*
		 * 아이디만을 가지고 회원 정보 조회
		 * 
		 * DB에 저장된 비밀번호 => 암호화된 60글자 비밀번호
		 * 사용자가 입력한 비밀번호 => 평문 비밀번호
		 * 
		 * SQL 조건식에서 같은지 비교할 수 없음.
		 * 아이디만을 가지고 일치하는 회원의 비밀번호를 조회한 후, 
		 * BCrypt에서 제공하는 메소드를 사용하여, 평문과 암호화된 비밀번호가 같은지 검증.
		 */
		Member loginMember = dao.login(member.getMemberId());
		
		//loginMember == null => 입력한 아이디와 일치하는 회원이 없음.
		if(loginMember == null) {
			return null;
		}else {
			//BCrypt가 제공하는 메소드르르 사용하여, 평문과 암호화된 비밀번호 검증
			boolean pwChk = encoder.matches(member.getMemberPw(), loginMember.getMemberPw());
			
			if(pwChk) {	// 비밀번호 일치
				return loginMember;
			}else {	// 아이디는 일치하지만, 비밀번호는 불일치
				return null;
			}
		}
	}
	
	@Transactional
	public int update(Member member) {
		
		//사용자가 수정한 비밀벊 == 평문 ==> DB에 업데이트 할 때에는 암호화된 비밀번호로!
		String encPassword =  encoder.encode(member.getMemberPw());
		member.setMemberPw(encPassword);
		
		return dao.update(member);
	}
	
	@Transactional
	public int delete(String memberNo) {
		
		return dao.delete(memberNo);
	}
	
	public ArrayList<Member> selectAllMemberList() {
		
		return dao.selectAllMemberList();
	}
	
	@Transactional
	public int updateLevel(Member member) {
		 
		return dao.updateLevel(member);
	}
}

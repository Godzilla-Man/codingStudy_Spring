package kr.or.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.common.util.JwtUtils;
import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.dto.LoginMember;
import kr.or.iei.member.model.dto.Member;

@Service
public class MemberService {
	
	@Autowired
	private MemberDao dao;
	
	
	//WebConfig에서, 생성하여 컨테이너에 등록해놓은 객체 주입받아 사용하기
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private JwtUtils jwtUtils;

	public int chkMemberId(String memberId) {
		return dao.chkMemberId(memberId);
	}
	
	@Transactional
	public int insertMember(Member member) {
		String encodePw = encoder.encode(member.getMemberPw()); //평문 => 암호화 60글자
		member.setMemberPw(encodePw);
		return dao.insertMember(member);
	}

	public LoginMember memberLogin(Member member) {
		Member chkMember = dao.memberLogin(member.getMemberId()); //아이디로 회원 정보 조회
		
		//아이디 잘못 입력하여, chkMember가 null인 경우 비밀번호 검증 불필요
		if(chkMember == null) {
			return null;
		}
		
		if(encoder.matches(member.getMemberPw(), chkMember.getMemberPw())) {
			//평문 == 암호화 비밀번호(일치한경우)
			String accessToken = jwtUtils.createAccessToken(chkMember.getMemberId(), chkMember.getMemberLevel());
			String refreshToken = jwtUtils.createRefreshToken(chkMember.getMemberId(), chkMember.getMemberLevel());
			
			//스토리지에 저장되지 않도록 처리(비밀번호 검증 이외에 필요가 없으므로)
			chkMember.setMemberPw(null);
			
			LoginMember loginMember = new LoginMember(chkMember, accessToken, refreshToken);
			
			return loginMember;
		}else {
			//평문 != 암호화 비밀번호(일치하지 않은 경우)
			return null;			
		}
		
		
	}

	/*
	public Object selectOneMember(String accessToken, String memberId) {
		//토큰 검증 결과 획득
		Object resObj = jwtUtils.validateToken(accessToken);
		
		if(resObj instanceof HttpStatus httpStatus) {
			return resObj;
			
		}else { //토큰 검증 결과 반환 자료형이 Member일 때 == 토큰이 유효할 때 
			
			//DB에서 회원 정보 조회하여 리턴
			Member member = dao.selectOneMember(memberId);
			member.setMemberPw(null); //내 정보 컴포넌트에서 비밀번호 불필요 하므로, null 처리
			return member;
		}
	} */
	
	public Member selectOneMember(String memberId) {
		//DB에서 회원 정보 조회하여 리턴
		Member member = dao.selectOneMember(memberId);
		member.setMemberPw(null); //내 정보 컴포넌트에서 비밀번호 불필요 하므로, null 처리
		return member;
	}
	
	/*
	@Transactional
	public Object updateMember(String accessToken, Member member) {
		Object resObj = jwtUtils.validateToken(accessToken);
		
		if(resObj instanceof HttpStatus httpStatus) {
			//resObj가 HttpStatus인 경우 == 토큰 검증 도중 예외가 발생한 경우 == 토큰이 유효하지 않음.
			return resObj;
		}else {
			//resObj가 Member인 경우 == 토큰이 유효한 경우
			return dao.updateMember(member);
		}
		
		
	} */
	
	@Transactional
	public int updateMember(Member member) {
		return dao.updateMember(member);
	}
	
	/*
	@Transactional
	public Object deleteMember(String accessToken, String memberId) {
		Object resObj = jwtUtils.validateToken(accessToken);
		
		if(resObj instanceof HttpStatus httpsStatus) {
			return resObj;
		}else {
			return dao.deleteMember(memberId);
		}
	}
	*/
	
	@Transactional
	public int deleteMember(String memberId) {
		return dao.deleteMember(memberId);
	}
	
	/*
	public Object checkMemberPw(String accessToken, Member member) {
		Object resObj = jwtUtils.validateToken(accessToken);
		
		if(resObj instanceof HttpStatus httpStatus) {
			return resObj;
		}else {
			//토큰 검증 성공!
			
			//사용자 입력 비밀번호 == 평문, DB의 memberPw는 암호화된 비밀번호이므로, BCrypt 메소드 사용
			Member m = dao.selectOneMember(member.getMemberId());
			
			//기존 비밀번호 일치 결과 (true or false) 리턴
			return encoder.matches(member.getMemberPw(), m.getMemberPw());
		}
	} */
	
	public boolean checkMemberPw(Member member) {	
		//사용자 입력 비밀번호 == 평문, DB의 memberPw는 암호화된 비밀번호이므로, BCrypt 메소드 사용
		Member m = dao.selectOneMember(member.getMemberId());
		
		//기존 비밀번호 일치 결과 (true or false) 리턴
		return encoder.matches(member.getMemberPw(), m.getMemberPw());
		
	}
	
	/*
	@Transactional
	public Object updateMemberPw(String accessToken, Member member) {
		Object resObj = jwtUtils.validateToken(accessToken);
		
		if(resObj instanceof HttpStatus httpStatus) {
			return resObj;
		}else {
			//토큰 검증 성공
			
			//평문 => 암호화
			String encodePw = encoder.encode(member.getMemberPw());
			member.setMemberPw(encodePw);
			
			//DB 업데이트
			return dao.updateMemberPw(member);
		}
	} */
	
	@Transactional
	public int updateMemberPw(Member member) {
		//평문 => 암호화
		String encodePw = encoder.encode(member.getMemberPw());
		member.setMemberPw(encodePw);
		
		//DB 업데이트
		return dao.updateMemberPw(member);
	}

	/*
	public Object refreshToken(String refreshToken, Member member) {
		//1. refreshToken도 만료되었는지 검증하기 위해 메소드 호출
		Object resObj = jwtUtils.validateToken(refreshToken);
		
		if(resObj instanceof HttpStatus httpStatus) {
			//refreshToken도 만료가 된 경우
			return resObj;
		}else {
			//refreshToken 검증 통과 => accessToken 재발급 처리
			String accessToken = jwtUtils.createAccessToken(member.getMemberId(), member.getMemberLevel());
			return accessToken;
		}
	} */
	
	public String refreshToken(Member member) {
		//refreshToken 검증 통과 => accessToken 재발급 처리
		String accessToken = jwtUtils.createAccessToken(member.getMemberId(), member.getMemberLevel());
		return accessToken;
		
	}
}

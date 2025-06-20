package kr.or.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	//회원 가입 여부 체크
	public int chkMemberId(String memberId) {
		return dao.chkMemberId(memberId);
	}
	
	
	//회원 가입
	@Transactional
	public int insertMember(Member member) {
		String encodePw = encoder.encode(member.getMemberPw()); //평문 => 암호화 60글자
		member.setMemberPw(encodePw);		
		return dao.insertMember(member);
	}

	
	//로그인
	public LoginMember memberLogin(Member member) {
		Member chkMember = dao.memberLogin(member.getMemberId());
		
		//아이디 잘못 입력하여, chkMember가 null인 경우 비밀번호 검증 불필요
		if(chkMember == null) {
			return null;
		}
		
		if(encoder.matches(member.getMemberPw(), chkMember.getMemberPw())) {
			//평문 == 암호화 비밀번호 일치하는 경우 jwt토큰을 이용한 로그인 처리 / 최소한의 정보 저장 원칙을 위해 비밀번호는 토큰안에 넣지 않음.			
			String accessToken = jwtUtils.createAccessToken(chkMember.getMemberId(), chkMember.getMemberLevel());
			String refreshToken = jwtUtils.createRefreshToken(chkMember.getMemberId(), chkMember.getMemberLevel());
			
			//스토리지에 저장되지 않도록 처리(비밀번호 검증 이외에 필요가 없으므로 null 처리 진행)
			chkMember.setMemberPw(null);	
			
			LoginMember loginMember = new LoginMember(chkMember, accessToken, refreshToken);
						
			return loginMember;
		}else {
			//평문 != 암호화 비밀번호(일치하지 않은 경우;
			return null;
		}		
	}
	
	
}

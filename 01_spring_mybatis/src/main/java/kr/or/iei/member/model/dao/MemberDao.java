package kr.or.iei.member.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.or.iei.member.model.dto.Member;

@Mapper
public interface MemberDao {

	int join(Member member);

	int idDuplChk(String memberId);

	Member login(String memberId);

	int update(Member member);

	int delete(String memberNo);

	ArrayList<Member> selectAllMemberList();

	int updateLevel(Member member);
	
}

package kr.or.iei.member.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.or.iei.member.model.dto.Member;

@Mapper
public interface MemberDao {

	ArrayList<Member> selectAllMember();

	int insertMember(Member member);

	Member selectOneMember(String memberId);

	int deleteMember(String memberId);

	int updateMember(Member member);

}

package com.example.demo.member.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.member.model.dto.Member;

//RowMapper : DB에서 조회한 결과(ResultSet)를 Java 객체로 변환하는 역할을 담당.
@Component //여러곳에서 재사용하기 때문에, 컨테이너에 Bean으로 등록하여 재사용
public class MemberRowMapper implements RowMapper<Member>{

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member member = new Member();
		member.setMemberNo(rs.getString("member_no"));
		member.setMemberId(rs.getString("member_id"));
		member.setMemberPw(rs.getString("member_pw"));
		member.setMemberName(rs.getString("member_name"));
		member.setMemberAddr(rs.getString("member_addr"));
		member.setMemberPhone(rs.getString("member_phone"));
		member.setMemberEmail(rs.getString("member_email"));
		member.setMemberLevel(rs.getString("member_level"));
		member.setEnrollDate(rs.getString("enroll_date"));
		
		return member;
	}
	
}

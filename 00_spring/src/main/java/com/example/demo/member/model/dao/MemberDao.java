package com.example.demo.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.member.model.dto.Member;

@Repository //자동으로 객체 생성하여 컨테이너에 등록
public class MemberDao {
	
	public MemberDao() {
		System.out.println("MemberDao가 생성되었습니다");
	}
	
	@Autowired
	private MemberRowMapper rowMapper;	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int join(Member member) {
		//1. 쿼리 작성
		String query = "insert into tbl_member values (seq_member.nextval, ?, ?, ?, ?, ?, ?, default, sysdate)";
		
		//2. 파라미터 Object 배열 생성
		Object [] paramArr = {member.getMemberId(),
							  member.getMemberPw(),
							  member.getMemberName(),
							  member.getMemberEmail(),
							  member.getMemberPhone(),
							  member.getMemberAddr()};
		
		//3. 쿼리 수행 (insert, update, delete 수행 시, 모두 update 메소드를 사용)
		int result = jdbcTemplate.update(query, paramArr);
		
		//4. 결과 반환		
		return result;
	}

	public int idDuplChk(String memberId) {
		//1. 쿼리 작성
		String query = "select count(*) from tbl_member where member_id = ?";
		
		//2. 파라미터 Object 배열 생성
		Object [] paramArr = {memberId};
		
		//3. 쿼리 수행(단건 조회 시, queryForObject)
		int cnt = jdbcTemplate.queryForObject(query, Integer.class, paramArr);
		
		//4. 결과 반환
		return cnt;
	}

	public Member login(String memberId, String memberPw) {
		//1. 쿼리 작성
		String query = "select * from tbl_member where member_id = ? and member_pw = ?";
		
		//2. 파라미터 Object 배열 생성
		Object [] paramArr = {memberId, memberPw};
		
		//3. 쿼리 수행	
		List<Member> memberliList = jdbcTemplate.query(query, rowMapper, paramArr);
		
		//4. 결과 반환
		if(memberliList.isEmpty()) {
			return null;
		}else {
			return memberliList.get(0);
		}		
	}

	public int delete(String memberNo) {
		
		//1. 쿼리 작성
		String query = "delete from tbl_member where member_no = ?";
		
		//2. 파라미터 Object 배열 생성
		Object [] paramArr = {memberNo};
		
		//3. 쿼리 수행
		int result = jdbcTemplate.update(query, paramArr);
		
		//4. 결과 반환		
		return result;
	}

	public ArrayList<Member> allMemberList() {
		
		//1. 쿼리 작성
		String query = "select * from tbl_member";
		
		//2. 파라미터 Object 배열 생성
		
		
		//3. 쿼리 수행
		List<Member> memberList = jdbcTemplate.query(query, rowMapper);
		
		//4. 결과 반환			
		return (ArrayList<Member>) memberList;
	}

}

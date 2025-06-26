package kr.or.iei.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.admin.model.dao.AdminDao;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.common.model.dto.PageInfo;
import kr.or.iei.common.util.PageUtil;
import kr.or.iei.member.model.dto.Member;

@Service
public class AdminService {
	
	@Autowired
	private AdminDao dao;
	
	@Autowired
	private PageUtil pageUtil;

	public HashMap<String, Object> selectBoardList(int reqPage) {
		
		int viewCnt = 10;	//한 페이지당 보여줄 게시글 갯수(기존 게시글 목록과 다르게 10개씩 표기하기 위해)
		int pageNaviSize = 5;	//페이지 네비게이션 길이
		int totalCount = dao.selectBoardCount();	//전체 게시글 갯수
		
		//페이지 네비게이션 정보
		PageInfo pageInfo = pageUtil.getPageInfo(reqPage, viewCnt, pageNaviSize, totalCount);
		
		//게시글 목록 조회
		ArrayList<Board> boardList = dao.selectBoardList(pageInfo);
		
		HashMap<String, Object> boardMap = new HashMap<String, Object>();
		boardMap.put("boardList", boardList);
		boardMap.put("pageInfo", pageInfo);
				
		return boardMap;
	}
	
	@Transactional
	public int updateBoardStatus(Board board) {
			
		return dao.updateBoardStatus(board);
	}

	public HashMap<String, Object> selectMemberList(int reqPage) {
		
		int viewCnt = 10;
		int pageNaviSize = 5;
		int totalCount = dao.selectMemberCount();
		
		PageInfo pageInfo = pageUtil.getPageInfo(reqPage, viewCnt, pageNaviSize, totalCount);
		
		ArrayList<Member> memberList = dao.selectMemberList(pageInfo);
		
		HashMap<String, Object> memberMap = new HashMap<String, Object>();
		memberMap.put("memberList", memberList);
		memberMap.put("pageInfo", pageInfo);
		
		return memberMap;
	}
	
	@Transactional
	public int updateMemberStatus(Member member) {
		
		return dao.updateMemberStatus(member);
	}
	
	
}

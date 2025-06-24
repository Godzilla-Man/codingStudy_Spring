package kr.or.iei.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.board.model.dao.BoardDao;
import kr.or.iei.board.model.dto.Board;
import kr.or.iei.board.model.dto.BoardFile;
import kr.or.iei.common.model.dto.PageInfo;
import kr.or.iei.common.util.PageUtil;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao dao;
	
	@Autowired
	private PageUtil pageUtil;

	public HashMap<String, Object> selectBoardList(int reqPage) {
		
		int viewCnt = 12;	//한 페이지당 게시글 수
		int pageNaviSize = 5;	//페이지 네비게이션 길이
		int totalCount = dao.selectBoardCount();	//전체 게시글 수
		
		
		//페이징 정보
		PageInfo pageInfo = pageUtil.getPageInfo(reqPage, viewCnt, pageNaviSize, totalCount); 			
		
		//게시글 목록
		ArrayList<Board> boardList = dao.selectBoardList(pageInfo);
		
		HashMap<String, Object> boardMap = new HashMap<String, Object>();
		boardMap.put("boardList", boardList);
		boardMap.put("pageInfo", pageInfo);
		
		return boardMap;
	}
	
	
	@Transactional
	public int insertBoard(Board board, ArrayList<BoardFile> fileList) {
		//(1) 게시글 번호 조회
		int boardNo = dao.selectBoardNo();
		
		board.setBoardNo(boardNo);
		
		//(2) 게시글 정보 등록
		int result = dao.insertBoard(board);
		
		//(3) 게시글 파일 정보 등록
		if(result > 0) {
			for(int i=0; i<fileList.size(); i++) {
				BoardFile file = fileList.get(i);  //fileName과 filePath만 세팅되어 있음.				
				file.setBoardNo(boardNo);	   //조회해온 게시글 번호 세팅
				dao.insertBoardFile(file);
			}
		}
				
		
		return result;
	}
}

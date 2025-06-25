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


	public Board selectOneBoard(int boardNo) {
		/* 기존 게시글 상세 정보
		 * (1) 게시글 정보 조회
		 * (2) 게시글에 대한 파일 정보 조회
		 * 
		 * mapper에서 한 번에 처리. 
		 */
		
		return dao.selectOneBoard(boardNo);
	}


	public BoardFile selectBoardFile(int boardFileNo) {
		// TODO Auto-generated method stub
		return dao.selectBoardFile(boardFileNo);
	}

	@Transactional	
	public Board deleteBoard(int boardNo) {
		Board board = dao.selectOneBoard(boardNo); //게시글 상세 조회 시, 메소드 재사용 (게시글 정보와 게시글에 대한 파일 정보)
		
		if(board != null) {
			int result = dao.deleteBoard(boardNo);
			
			if(result > 0) {
				return board;
			}else {
				return null;
			}
		}
		return null;
	}

	@Transactional	
	public ArrayList<BoardFile> updateBoard(Board board) {
		//게시글 정보 수정
		int result = dao.updateBoard(board);
		
		//서버에서 삭제할 파일리스트 조회
		if(result > 0) {
			ArrayList<BoardFile> delFileList = new ArrayList<BoardFile>();
			
			//화면에서 삭제 클릭하지 않았다면, null이므로 
			if(board.getDelBoardFileNo() != null) {
				//서버에서 삭제할 파일 리스트 조회
				delFileList = dao.selectDelBoardFile(board.getDelBoardFileNo());
				//삭제할 파일 정보 DB에서 삭제
				dao.deleteBoardFile(board.getDelBoardFileNo());
			}
			
			//추가 첨부 파일 등록
			if(board.getFileList() != null) {
				for(int i=0; i<board.getFileList().size(); i++) {
					BoardFile addFile = board.getFileList().get(i);
					dao.insertBoardFile(addFile);
				}
			}
			
			return delFileList;
		}
		
		return null;
		
	}
	
	


}

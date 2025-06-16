package kr.or.iei.notice.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.notice.model.dao.NoticeDao;
import kr.or.iei.notice.model.dto.Notice;
import kr.or.iei.notice.model.dto.NoticeFile;
import kr.or.iei.notice.model.dto.NoticePageData;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeDao dao;

	public NoticePageData selectNoticeList(int reqPage, String select, String keyword) {
		
		//한 페이지에 보여줄 게시글의 갯수
		int viewNoticeCnt = 10;
		
		/* 1페이지 요청 => 1 ~ 10 (reqPage : 1, start : 1, end : 10)
		 * 2페이지 요청 => 11 ~ 20 (reqPage : 2, start : 11, end : 20)
		 * 3페이지 요청 => 21 ~ 30 (reqPage : 3, start : 21, end : 30) 
		 * 
		 */
		int end = reqPage * viewNoticeCnt;
		int start = end - viewNoticeCnt + 1;
		
		//SQL 수행에 필요한 데이터 저장할 Map
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("start", start+"");
		map.put("end", end+"");
		map.put("select", select);
		map.put("keyword", keyword);
		
		//게시글 목록 조회
		ArrayList<Notice> noticeList = dao.selectNoticeList(map);
		
		//페이지 네비게이션 작업
		
		//전체 게시글 갯수
		//전체 게시글 갯수를 가지고, 페이지 갯수를 계산하므로 사용자가 선택 및 검색한 조건이 필요.
		int totCnt = dao.selectNoticeTotalCount(map);
		
		//전체 페이지 갯수 계산 
		/*
		 * Ex1) 전체 게시글 갯수 == 93개 == 10개 페이지
		 * Ex2) 전체 게시글 갯수 == 90개 == 9개 페이지
		 * Ex3) 전체 게시글 갯수 == 29개 == 3개 페이지
		 * Ex4) 전체 게시글 갯수 == 30개 == 3개 페이지 		 
		 * 
		 */
		
		//전체 페이지 갯수
		int totPage = 0;
		if(totCnt % viewNoticeCnt > 0) {
			totPage = totCnt / viewNoticeCnt + 1;
		}else {
			totPage = totCnt / viewNoticeCnt;
		}
		
		//페이지 네비게이션 제작
		
		//페이지 네비게이션 사이즈 (숫자 5개)
		int pageNaviSize = 5; 
		
		//페이지 시작 번호
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		
		//화면에 보여질 페이지 네비게이션 문자열(HTML 코드)
		String pageNavi = "";
		
		//이전버튼
		if(pageNo != 1) {
			pageNavi += "<a href='/notice/getList?reqPage=" + (pageNo-1) + "&select="+ select +"&keyword=" + keyword + "'>이전</a>&nbsp;&nbsp;";
		}else {
			pageNavi += "<a href='/notice/getList?reqPage=" + (pageNo-1) + "'>이전</a>&nbsp;&nbsp;";
		}
		
		for(int i=0; i<pageNaviSize; i++) {
			
			//요청 페이지와 그 외의 페이지
			if(pageNo == reqPage) {				
				pageNavi += "<span>" + pageNo + "</span> &nbsp;&nbsp;";
			}else {
				if(keyword != null) {
					pageNavi += "<a href='/notice/getList?reqPage=" + pageNo + "&select=" +select+ "&keyword=" +keyword+"'>" + pageNo + "</a>&nbsp;&nbsp;";					
				}else {
					pageNavi += "<a href='/notice/getList?reqPage=" + pageNo + "'>" + pageNo + "</a>&nbsp;&nbsp;";	
				}
			}
			
			pageNo++;
			
			//페이지 네비게이션 사이즈 == 5. 5개 전부 그리지 않고, 마지막 페이지 출력했으면 반복문 종료
			if(pageNo > totPage) {
				break;
			}
		}
		
		//다음 버튼
		if(pageNo <= totPage) {
			if(keyword != null) {
				pageNavi += "<a href='/notice/getList?reqPage=" + pageNo + "&select=" + select + "&keyword=" + keyword +"'>다음</a>";				
			}else {
				pageNavi += "<a href='/notice/getList?reqPage=" + pageNo + "'>다음</a>"; 				
			}
		}
		NoticePageData pd = new NoticePageData(noticeList, pageNavi);
		return pd;
	}

	@Transactional
	public int insertNotice(Notice notice, ArrayList<NoticeFile> fileList) {		
		//(1) 게시글 번호 조회
		String noticeNo = dao.selectNoticeNo();
		
		//(2) 부모 테이블인 tbl_notice 먼저 Insert
		notice.setNoticeNo(noticeNo);
		int result = dao.insertNotice(notice);
		
		if(result > 0) {
			
			//(3) 자식 테이블인 tbl_notice_file에 Insert
			for(int i=0; i<fileList.size(); i++) {
				NoticeFile file = fileList.get(i);
				file.setNoticeNo(noticeNo); //게시글 번호
				result = dao.insertNoticeFile(file);
			}
		}
		
		return result;
	}
	
	@Transactional
	public Notice selectOneNotice(String noticeNo) {
		// (1) 게시글 정보 조회
		Notice notice = dao.selectOneNotice(noticeNo);
		
		// (2) 조회수 업데이트	
		if(notice != null) {
			int result = dao.updateReadCount(noticeNo);
			
			// (3) 게시글 파일 리스트 조회
			ArrayList<NoticeFile> fileList = dao.selectNoticeFileList(noticeNo);
			notice.setFileList(fileList);
			
		}
		
		return notice;
	}
	
	@Transactional
	public ArrayList<NoticeFile> deleteNotice(String noticeNo) {
		/* (1) 게시글 정보 삭제			 (TBL_NOTICE)
		 * (2) 게시글에 대한 파일 정보 삭제 (TBL_NOTICE_FILE) => CASCADE 삭제 옵션으로, 게시글 정보 삭제하면 자동 삭제처리 됨.
		 * (3) 서버에서 삭제하기 위한, 게시글에 대한 파일 목록 조회
		 */
		
		//(3) 서버에서 삭제하기 위한, 게시글에 대한 파일 목록 조회
		ArrayList<NoticeFile> delFileList = dao.selectNoticeFileList(noticeNo);
		
		//(1) 게시글 정보 삭제
		int result = dao.deleteNotice(noticeNo);
		
		if(result > 0) {
			return delFileList;
		}else {
			return null;
		}		
	}
	
	@Transactional
	public ArrayList<NoticeFile> updateNotice(Notice notice, ArrayList<NoticeFile> fileList, boolean fileIsNull) {
		
		/* 
		 * 
		 */
		
		//게시글 정보 수정
		int result = dao.updateNotice(notice);
		
		//컨트롤러에 반환할 삭제 파일 리스트
		ArrayList<NoticeFile> delFileList = null;
		
		if(result > 0) {
			/* [순서 중요!!]
			 * (1) 서버에서 삭제하기 위한 기존 파일 리스트 목록 조회
			 * (2) 게시글 기존 파일 정보 삭제
			 * (3) 업로드한 파일 정보 등록 
			 * 
			 */
			
			//(1) 서버에서 삭제하기 위한 기존 파일 목록 조회
			delFileList = dao.selectNoticeFileList(notice.getNoticeNo());
			
			//(2) 게시글 기존 파일 정보 삭제
			/* fileIsNull이 false일 때만 기존 파일 정보 삭제.
			 * 
			 * filsIsNull이 true인 경우는, 게시글 첫 작성 시 파일 업로드를 하였고,
			 * 수정 시에 제목 및 내용만 수정한 경우임. 이 때 파일에 대한 처리는 하지 않았으므로, 
			 * 기존 파일 정보를 삭제하면 안된다. 
			 */
			
			if(!fileIsNull) {				
				result = dao.deleteNoticeFile(notice.getNoticeNo());
			}
			
			//(3) 업로드한 파일 정보 등록
			for(int i=0; i<fileList.size(); i++) {
				NoticeFile insFile = fileList.get(i);
				result = dao.insertNoticeFile(insFile);
			}			
		}		
		return delFileList;
	}
}

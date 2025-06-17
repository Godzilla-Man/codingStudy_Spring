package kr.or.iei.notice.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.notice.model.dao.NoticeDao;
import kr.or.iei.notice.model.dto.Notice;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeDao dao;

	public ArrayList<Notice> selectNoticeList() {

		return dao.selectNoticeList();
	}

	@Transactional
	public int insertNotice(Notice notice) {
		
		return dao.insertNotice(notice);
	}

	public Notice selectOneNotice(String noticeNo) {
		
		return dao.selectOneNotice(noticeNo);
	}
	
	@Transactional
	public int deleteOneNotice(String noticeNo) {
		
		return dao.deleteOneNotice(noticeNo);
	}
	
	@Transactional
	public int updateNotice(Notice notice) {

		return dao.updateNotice(notice);
	}

}

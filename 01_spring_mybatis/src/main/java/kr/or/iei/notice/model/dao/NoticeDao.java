package kr.or.iei.notice.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import kr.or.iei.notice.model.dto.Notice;
import kr.or.iei.notice.model.dto.NoticeFile;

@Mapper
public interface NoticeDao {

	ArrayList<Notice> selectNoticeList(HashMap<String, String> map);

	int selectNoticeTotalCount(HashMap<String, String> map);

	String selectNoticeNo();

	int insertNotice(Notice notice);

	int insertNoticeFile(NoticeFile file);

	Notice selectOneNotice(String noticeNo);

	int updateReadCount(String noticeNo);

	ArrayList<NoticeFile> selectNoticeFileList(String noticeNo);

	int deleteNotice(String noticeNo);

	int updateNotice(Notice notice);

	int deleteNoticeFile(String noticeNo);	
	
}

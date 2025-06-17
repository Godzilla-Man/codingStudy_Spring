package kr.or.iei.notice.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.or.iei.notice.model.dto.Notice;

@Mapper
public interface NoticeDao {

	ArrayList<Notice> selectNoticeList();

	int insertNotice(Notice notice);

	Notice selectOneNotice(String noticeNo);

	int deleteOneNotice(String noticeNo);

	int updateNotice(Notice notice);

}

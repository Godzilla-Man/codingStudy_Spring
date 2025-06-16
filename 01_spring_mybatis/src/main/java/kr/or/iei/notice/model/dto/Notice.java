package kr.or.iei.notice.model.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notice {
	
	private String rNum; //행번호
	private String noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriter;
	private String noticeDate;
	private String readCount;
	
	//게시글에 대한 파일 리스트
	private ArrayList<NoticeFile> fileList;

}

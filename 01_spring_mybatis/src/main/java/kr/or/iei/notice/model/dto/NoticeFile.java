package kr.or.iei.notice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeFile {
	
	private String fileNo;
	private String noticeNo;
	private String fileName;
	private String filePath;

}

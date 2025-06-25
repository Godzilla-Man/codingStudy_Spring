package kr.or.iei.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardThumbPath;
	private String boardContent;
	private String boardWriter;
	private int boardStatus;
	private String boardDate;
	
	//게시글에 대한 파일 정보 저장 변수
	private List<BoardFile> fileList;
	
	//삭제 파일 번호 배열 저장 변수
	private int [] delBoardFileNo;
}

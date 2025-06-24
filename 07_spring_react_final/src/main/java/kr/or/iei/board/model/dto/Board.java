package kr.or.iei.board.model.dto;

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
}

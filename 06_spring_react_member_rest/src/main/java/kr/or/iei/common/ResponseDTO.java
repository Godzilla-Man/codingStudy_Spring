package kr.or.iei.common;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "응답 데이터 형식")

public class ResponseDTO {
	
	@Schema(description = "HTTP 통신 상태")
	private HttpStatus httpStatus;
	@Schema(description = "응답 메시지")
	private String clientMsg;
	@Schema(description = "응답 데이터(등록, 수정, 삭제 시 boolean, 목록 조회 시 객체 리스트)")
	private Object resData;
	
}

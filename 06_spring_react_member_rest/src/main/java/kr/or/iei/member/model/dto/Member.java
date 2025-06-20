package kr.or.iei.member.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "회원 정보 객체")
public class Member {
	
	@Schema(description = "회원 아이디")
	private String memberId;
	@Schema(description = "회원 이름")
	private String memberName;
	@Schema(description = "회원 전화번호")
	private String memberPhone;
	@Schema(description = "회원 소개글")
	private String memberIntro;
	@Schema(description = "회원 가입일")
	private String enrollDate;

}

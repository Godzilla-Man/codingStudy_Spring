package kr.or.iei.mybatis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MybatisMember {
	
	private String memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberEmail;
	private String memberPhone;
	private String memberAddr;
	private String memberLevel;
	private String enrollDate;
	
	//동적 SQL - 2번 - 등급명 저장칼럼
	private String levelName;
				  
	private String sFlag1;
	private String sFlag2;
	private String sFlag3;
	private String sFlag4;
	private String sFlag5;

}

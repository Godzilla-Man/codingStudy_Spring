package com.example.demo.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Member {	
	private String memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberEmail;
	private String memberPhone;
	private String memberAddr;
	private String memberLevel;
	private String enrollDate;
}

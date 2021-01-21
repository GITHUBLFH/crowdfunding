package com.liufuhao.crowd.entity.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberVO implements Serializable{

	private String loginacct;
	
	private String userpswd;
	
	private String username;
	
	private String email;
	
	private String phoneNum;
	
	private String code;
}

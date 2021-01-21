package com.liufuhao.crowd.entity.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberLoginVO implements Serializable{
	
	private Integer id;
	
	private String username;
	
	private String email;
	
}

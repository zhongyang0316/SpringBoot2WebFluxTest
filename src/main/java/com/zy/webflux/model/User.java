package com.zy.webflux.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class User implements Serializable {
	
	private Long userId;
	
	private String name;
	
	private Integer age;
	
	public User(Long userId, String name, Integer age){
		this.userId = userId;
		this.name = name;
		this.age = age;
	}

}

package com.app.dto;

import lombok.Data;

@Data
public class UserLoginRequest {

	private String email;
	private String pwd;
}

package com.app.dto;

import lombok.Data;

@Data
public class UserUnlockRequest {

	private String email;
	private String tempPwd;
	private String newPwd;
	private String confirmNewPwd;
}

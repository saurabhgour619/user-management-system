package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.constants.AppConstants;
import com.app.service.UserService;

@RestController
public class UserForgetPwdController {

	@Autowired
	private UserService userService;
	
	@PostMapping("users/forgetPwd")
	public String forgetPwd(@RequestBody String email) {
		boolean isPwdReset = userService.forgetPwd(email);
		if(isPwdReset) {
			return AppConstants.PASSWORD_RESET;
		}
		return AppConstants.REGISTERED_EMAIL_ID;
		
	}
}

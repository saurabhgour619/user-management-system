package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.constants.AppConstants;
import com.app.dto.UserLoginRequest;
import com.app.service.UserService;

@RestController
public class UserLoginController {

	@Autowired
	private UserService userService;

	@PostMapping("/login/user")
	public String loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		boolean isValidUser = userService.checkLogin(userLoginRequest);
		return isValidUser ? AppConstants.LOGIN_SUCCESS : AppConstants.INVALID_LOGIN;
	}
}

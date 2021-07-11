package com.app.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserLoginRequest;
import com.app.service.UserService;

@RestController
public class UserLoginController {

	private UserService userService;

	public String loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		boolean isValidUser = userService.checkLogin(userLoginRequest);
		return isValidUser ? "Login Successful" : "Invalid credentials";
	}
}
